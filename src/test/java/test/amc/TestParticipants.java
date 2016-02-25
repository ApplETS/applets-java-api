package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestParticipants {

    private String id_event = "28";
    private String id_equipe = "30";
    private String id_participant = "27";
   /* @Before
    public void setUp(){
        RestAssured.basePath = "http://localhost:8080";
    }*/

    @Test
    public void testGetSingleParticipant() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Participant1 Test")
                ).when().get("/rest/amc-participants/id/"+ id_participant);
    }

    @Test
    public void testGetParticipantNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-participants/id/-1");
    }

    @Test
    public void testGetAllParticipantsForEvent() {
        expect().statusCode(200)
                .body(
                        "nom", hasItems("Participant1 Test", "Participant2 Test", "Participant3 Test")
                ).when().get("/rest/amc-participants/all-event/"+id_event);
    }

    @Test
    public void testGetAllParticipantsForEquipeEvent() {
        ArrayList<String> alParticipants1 = new ArrayList<String>();
        alParticipants1.add("Participant1 Test");
        alParticipants1.add("Participant3 Test");

        ArrayList<String> alParticipants2 = new ArrayList<String>();
        alParticipants2.add("Participant2 Test");

        expect().statusCode(200)
                .body(
                        "nom", hasItems("Participant1 Test", "Participant3 Test")
                ).when().get("/rest/amc-participants/all-equipe-event/"+id_event+"-"+ id_equipe);
    }

    @Test
    public void testGetParticipantsEventNotExist() {
        expect().statusCode(200)
                .body(
                        "", empty()
                ).when().get("/rest/amc-participants/all-event/-1");
    }
}