package stepsAPI;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import apiSpecs.UsersSpecs;
import org.assertj.core.api.SoftAssertions;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UsersSteps  {
    UsersSpecs user_specs;
    HashMap<String, String> create_user_data;
    HashMap<String, String> update_user_data;
    Response res;

    @Given("^setup create user api$")
    public void setup_create_user_api() throws IOException {
        user_specs = new UsersSpecs();
        create_user_data = new HashMap<>();
    }

    @When("^set (.*) as (.*)$")
    public void set_test_data(String key_name, String content) {
        if(content.equals("empty")){
            create_user_data.put(key_name, "");
        }else{
            create_user_data.put(key_name, content);
        }
    }

    @When("^creates user with the details provided$")
    public void creates_user() {
        res = user_specs.create_user(create_user_data);
    }

    @Given("^update details of user with email (.*)$")
    public void update_details_of_user_with_email(String search_by_email) throws IOException{
        user_specs = new UsersSpecs();
        update_user_data = new HashMap<>();
        update_user_data.put("email",search_by_email);
    }

    @When("^update (.*) of user to (.*)$")
    public void build_user_details_for_update(String key_name, String value){
        update_user_data.put(key_name, value);
    }

    @When("^updates user with details provided$")
    public void updates_user_with_the_details_provided() {
        String user_id = user_specs.get_user_id_by_email(update_user_data.get("email"));
        res = given(user_specs.req_spec_with_auth)
                .log()
                .all()
                .when()
                .log()
                .all()
                .body(update_user_data)
                .patch(user_specs.user_resource_path+ "/" + user_id)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    @When("test data is prepared")
    public void prepare_test_data(){
        user_specs.create_user(update_user_data);
    }

    @Then("^verify the response code is (\\d+)$")
    public void verify_response_code(int response_code) {
        user_specs.verify_response_code(res, response_code);
    }

    @Then("^verify the user is created successfully$")
    public void verify_the_user_is_created_successfully() {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(res.then().extract().body().jsonPath().get("data['name']").toString()).isEqualTo(
                create_user_data.get("name"));
        softAssertions.assertThat(res.then().extract().body().jsonPath().get("data['email']").toString()).isEqualTo(
                create_user_data.get("email"));
        softAssertions.assertThat(res.then().extract().body().jsonPath().get("data['gender']").toString()).isEqualTo(
                create_user_data.get("gender"));
        softAssertions.assertThat(res.then().extract().body().jsonPath().get("data['status']").toString()).isEqualTo(
                create_user_data.get("status"));
        softAssertions.assertAll();
    }

    @Then("^verify the user details are updated successfully$")
    public void verify_the_user_is_updated_successfully() {
        SoftAssertions softAssertions = new SoftAssertions();
        for(String key: update_user_data.keySet()){
            softAssertions.assertThat(res.then().extract().body().jsonPath().get("data['" + key + "']").toString()).isEqualTo(
                    update_user_data.get(key));
        }
        softAssertions.assertAll();
    }

    @And("cleanup the user data created")
    public void cleanup_user_data(){
        String user_id = res.body().jsonPath().get("data['id']").toString();
        user_specs.delete_user_by_id(user_id);
    }
}
