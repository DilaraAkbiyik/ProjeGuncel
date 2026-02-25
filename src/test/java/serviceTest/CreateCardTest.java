package serviceTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static jdk.internal.vm.vector.VectorSupport.extract;

public class CreateCardTest {

    @Test
    public void createCard() {
        given()
                .queryParam("header", "")
                .queryParam("idList", "")
                .queryParam("key", "")
                .queryParam("token", "")
                .when()
                .post("https://api.trello.com/1/cards")
                .then()
                .statusCode(200);


    }
}
