package serviceTest;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreateCardTest {

    @Test
    public void createCard() {
        String key = System.getenv("API_KEY");
        String token = System.getenv("TOKEN");
        given()
                .queryParam("header", "")
                .queryParam("idList", "")
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .post("https://api.trello.com/1/cards")
                .then()
                .statusCode(200);


    }
}
