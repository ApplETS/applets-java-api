package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestTirageSorts {

    private String id_event = "28";
    private String id_tirageSort = "27";
   /* @Before
    public void setUp(){
        RestAssured.basePath = "http://localhost:8080";
    }*/

    @Test
    public void testGetSingleTirageSort() {
        expect().statusCode(200)
                .body(
                        "titre", equalTo("A Gagner1"),
                        "description", equalTo("Un super prix1"),
                        "dateDebut", equalTo("2016-02-20T21:00:00-05:00"),
                        "dateFin", equalTo("2016-02-20T22:00:00-05:00"),
                        "prix.prix", hasItems("Une télé", "Une caméra"),
                        "prix.gagnant.nom", hasItems("Participant1 Test", "Participant2 Test")
                ).when().get("/rest/amc-tiragesorts/id/"+id_tirageSort);
    }

    @Test
    public void testGetTirageSortNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-tiragesorts/id/-1");
    }

    @Test
    public void testGetAllTirageSortsForEvent() {
        ArrayList<String> alPrix1 = new ArrayList<String>();
        alPrix1.add("Une télé");
        alPrix1.add("Une caméra");
        ArrayList<String> alPrix2 = new ArrayList<String>();
        alPrix2.add("Un livre");

        ArrayList<String> alGagnants1 = new ArrayList<String>();
        alGagnants1.add("Participant1 Test");
        alGagnants1.add("Participant2 Test");

        expect().statusCode(200)
                .body(
                        "titre", hasItems("A Gagner1", "A Gagner2"),
                        "description", hasItems("Un super prix1", "Un super prix2"),
                        "dateDebut", hasItems("2016-02-20T21:00:00-05:00", "2016-02-21T10:00:00-05:00"),
                        "dateFin", hasItems("2016-02-20T22:00:00-05:00", "2016-02-21T13:00:00-05:00"),
                        "prix.prix", hasItems(alPrix1, alPrix2),
                        "prix.gagnant.nom", hasItems(alGagnants1)
                ).when().get("/rest/amc-tiragesorts/all-event/"+id_event);
    }

    @Test
    public void testGetTirageSortsEventNotExist() {
        expect().statusCode(200)
                .body(
                        "", empty()
                ).when().get("/rest/amc-tiragesorts/all-event/-1");
    }
}