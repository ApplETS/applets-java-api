package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestPartenaires {

    private String id_event = ConstantsTest.EVENT_ID;
    private String id_partenaire = ConstantsTest.PARTEN_ID;
   /* @Before
    public void setUp(){
        RestAssured.basePath = "http://localhost:8080";
    }*/

    @Test
    public void testGetSinglePartenaire() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Partenaire1 Test")
                ).when().get("/rest/amc-partners/id/"+id_partenaire);
    }

    @Test
    public void testGetPartenaireNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-partners/id/-1");
    }

    @Test
    public void testGetAllPartenairesForEvent() {
        expect().statusCode(200)
                .body(
                        "nom", hasItems("Partenaire1 Test", "Partenaire2 Test")
                ).when().get("/rest/amc-partners/all-event/"+id_event);
    }

    @Test
    public void testGetPartenairesEventNotExist() {
        expect().statusCode(200)
                .body(
                        "", empty()
                ).when().get("/rest/amc-partners/all-event/-1");
    }
}