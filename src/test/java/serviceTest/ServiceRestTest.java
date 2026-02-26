package serviceTest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class ServiceRestTest extends BaseServiceTest{

    @Test
    public void createTest() {

        //Create board
        Response responseBoard = given()
                .baseUri("https://api.trello.com/1/boards/")
                .formParam("name", "TestBoard")
                .formParam("key",key)
                .formParam("token", token)
                .when()
                .post()
                .then()
                .extract()
                .response();

        //Control status code
        responseBoard
                .then()
                .statusCode(200);

        //Response and id
        responseBoard.prettyPrint();
        boardId = responseBoard.jsonPath().getString("id");
        System.out.println("Board id : " + boardId);


        //------------------------------------------------------------------
        //Create list
        Response responseList = given()
                .baseUri("https://api.trello.com/1/lists")
                .formParam("idBoard", boardId)
                .formParam("name", "TestList")
                .formParam("key",key)
                .formParam("token", token)
                .when()
                .post()
                .then()
                .extract()
                .response();

        //Control status code
        responseList
                .then()
                .statusCode(200);

        //Response and id
        responseList.prettyPrint();
        listId = responseList.jsonPath().getString("id");
        System.out.println("Board id : " + listId);


        //------------------------------------------------------------------
        //Create first Card
        Response responseCard = given()
                .baseUri("https://api.trello.com/1/cards")
                .formParam("idList", listId)
                .formParam("key", key)
                .formParam("token", token)
                .formParam("name", "TestCard1")
                .when()
                .post()
                .then()
                .extract()
                .response();


        //Control status code
        responseCard
                .then()
                .statusCode(200);

        //Response and id
        responseCard.prettyPrint();
        firstCardId = responseCard.jsonPath().getString("id");
        System.out.println("Card id : " + firstCardId);


        //------------------------------------------------------------------
        //Create second Card
        Response responseCard2 = given()
                .baseUri("https://api.trello.com/1/cards")
                .formParam("idList", listId)
                .formParam("key", key)
                .formParam("token", token)
                .formParam("name", "TestCard2")
                .when()
                .post()
                .then()
                .extract()
                .response();


        //Control status code
        responseCard2
                .then()
                .statusCode(200);

        //Response and id
        responseCard2.prettyPrint();
        secondCardId = responseCard2.jsonPath().getString("id");
        System.out.println("Card id : " + secondCardId);


        //------------------------------------------------------------------
        //Update Second Card
        Response responseUpdate = given()
                .baseUri("https://api.trello.com/1/cards/" + secondCardId)
                .formParam("key", key)
                .formParam("token", token)
                .formParam("name", "TestCardUpdated")
                .when()
                .put()
                .then()
                .extract()
                .response();


        //Control status code
        responseUpdate
                .then()
                .statusCode(200);

        //Response and id
        responseUpdate.prettyPrint();
        String cardName = responseUpdate.jsonPath().getString("name");
        System.out.println("New card name : " + cardName);


        //------------------------------------------------------------------
        //Delete First Card
        Response responseDeleteFirstCard= given()
                .baseUri("https://api.trello.com/1/cards/" + firstCardId)
                .formParam("key", key)
                .formParam("token", token)
                .when()
                .put()
                .then()
                .extract()
                .response();

        //Control status code
        responseDeleteFirstCard
                .then()
                .statusCode(200);

        //Response and id
        responseDeleteFirstCard.prettyPrint();



        //------------------------------------------------------------------
        //Delete Seconds Card
        Response responseDeleteSecondCard= given()
                .baseUri("https://api.trello.com/1/cards/" + secondCardId)
                .formParam("key", key)
                .formParam("token", token)
                .when()
                .put()
                .then()
                .extract()
                .response();


        //Control status code
        responseDeleteSecondCard
                .then()
                .statusCode(200);

        //Response and id
        responseDeleteSecondCard.prettyPrint();


        //------------------------------------------------------------------
        //Delete Seconds Card
        Response responseDeleteBoard= given()
                .baseUri("https://api.trello.com/1/boards/" + boardId)
                .formParam("key", key)
                .formParam("token", token)
                .when()
                .put()
                .then()
                .extract()
                .response();


        //Control status code
        responseDeleteBoard
                .then()
                .statusCode(200);

        //Response and id
        responseDeleteBoard.prettyPrint();

    }
}



