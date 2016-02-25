package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestEquipes {

    private String id_event = "28";
    private String id_equipe = "30";
   /* @Before
    public void setUp(){
        RestAssured.basePath = "http://localhost:8080";
    }*/

    @Test
    public void testGetSingleEquipe() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Equipe1 Test")
                ).when().get("/rest/amc-equipes/id/"+ id_equipe);
    }

    @Test
    public void testGetEquipeNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-equipes/id/-1");
    }

    @Test
    public void testGetAllEquipesForEvent() {
        ArrayList<String> alParticipants1 = new ArrayList<String>();
        alParticipants1.add("Participant1 Test");
        alParticipants1.add("Participant3 Test");

        ArrayList<String> alParticipants2 = new ArrayList<String>();
        alParticipants2.add("Participant2 Test");

        expect().statusCode(200)
                .body(
                        "nom", hasItems("Equipe1 Test", "Equipe2 Test"),
                        "prix", hasItems("Prix cégép"),
                        "participants.nom", hasItems(alParticipants1, alParticipants2)
                ).when().get("/rest/amc-equipes/all-event/"+id_event);
    }

    @Test
    public void testGetEquipesEventNotExist() {
        expect().statusCode(200)
                .body(
                        "", empty()
                ).when().get("/rest/amc-equipes/all-event/-1");
    }
}