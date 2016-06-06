package test.amc;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestIntervenants {

    private String id_event = ConstantsTest.EVENT_ID;
    private String id_intervenant = ConstantsTest.INTERV_ID;

    @Test
    public void testGetSingleIntervenant() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Interv1 Test")
                ).when().get("/rest/amc-intervs/id/"+ id_intervenant);
    }

    @Test
    public void testGetIntervenantNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-intervs/id/-1");
    }

    @Test
    public void testGetAllIntervenantsForEvent() {
        expect().statusCode(200)
                .body(
                        "nom", hasItems("Interv1 Test", "Interv2 Test"),
                        "description", hasItems("Reunion1 Test", "Reunion2 Test", "Reunion5 Test"),
                        "dateDebut", hasItems(1455987600, 1456063200, 1456042500),
                        "dateFin", hasItems(1455991200, 1456066800, 1456043820)
                ).when().get("/rest/amc-intervs/all-event/"+id_event);
    }

    @Test
    public void testGetIntervenantsEventNotExist() {
        expect().statusCode(200)
                .body(
                        "", empty()
                ).when().get("/rest/amc-intervs/all-event/-1");
    }
}