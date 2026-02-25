package serviceTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreateTest{

    public String id = "";

    @Test
    public void createTest() {


        //Board ekleme
        String boardId = given()
                .queryParam("key", "")
                .queryParam("token", "")
                .queryParam("name", "DenemeBoard2")
                .when()
                .post("https://api.trello.com/1/boards/")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        System.out.println("Responsedan gelen id:" + boardId);
        id = boardId;

    }
}


