package core;

import utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;

public class BaseAPI {

    public RequestSpecification get_req_spec_with_auth() throws IOException {
        HashMap<String, String> header = new HashMap<String, String>() {{
            put("Authorization", "Bearer " + new PropertyReader().readGlobalProp("authToken"));
        }};
        RequestSpecification req_spec = new RequestSpecBuilder().setBaseUri(
                        new PropertyReader().readGlobalProp("baseURI"))
                .addHeaders(header)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON).build();
        return req_spec;
    }

    public void verify_response_code(Response res, int response_code) {
        res.then().assertThat().statusCode(response_code);
    }
}
