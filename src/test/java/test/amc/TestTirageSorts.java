package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestTirageSorts {

    private String id_event = ConstantsTest.EVENT_ID;
    private String id_tirageSort = ConstantsTest.TIRAGE_ID;

    @Test
    public void testGetSingleTirageSort() {
        expect().statusCode(200)
                .body(
                        "titre", equalTo("A Gagner1"),
                        "description", equalTo("Un super prix1"),
                        "dateDebut", equalTo(1455998400),
                        "dateFin", equalTo(1456002000),
                        "prix.prix", hasItems("Une télé", "Une caméra"),
                        "prix.gagnant.nom", hasItems("Participant1 Test", "Participant2 Test")
                ).when().get("/rest/amc-tiragesorts/id/"+id_tirageSort);
    }

    @Test
    public void testGetTirageSortNotExist() {
        expect().statusCode(404)
                .body(equalTo("Tirage non trouvé pour l'id -1")).when().get("/rest/amc-tiragesorts/id/-1");
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
                        "dateDebut", hasItems(1455998400, 1458550800),
                        "dateFin", hasItems(1456002000,1458561600),
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