package apiSpecs;

import core.BaseAPI;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UsersSpecs extends BaseAPI{
    public RequestSpecification req_spec_with_auth;
    public  String user_resource_path = "/public/v1/users";

    public UsersSpecs() throws IOException {
        req_spec_with_auth = get_req_spec_with_auth();
    }

    public String get_user_id_by_email(String email){
        Response get_user_id = given(req_spec_with_auth)
                .when()
                .queryParam("email", email)
                .get(user_resource_path)
                .then()
                .extract()
                .response();
        return get_user_id.then().extract().jsonPath().get("data[0]['id']").toString();
    }

    public Response delete_user_by_id(String user_id){
        Response res = given(req_spec_with_auth)
                .log()
                .all()
                .when()
                .log()
                .all()
                .delete(user_resource_path+ "/" + user_id)
                .then()
                .log()
                .all()
                .extract()
                .response();
        return res;
    }

    public Response create_user(HashMap<String, String> test_data){
        Response res = given(req_spec_with_auth)
                .when()
                .body(test_data)
                .post(user_resource_path)
                .then()
                .log()
                .all()
                .extract()
                .response();
        return res;
    }
}
