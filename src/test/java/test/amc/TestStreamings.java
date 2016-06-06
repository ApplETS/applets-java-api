package test.amc;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestStreamings {

    private String id_streaming = ConstantsTest.STREAM_ID;

    @Test
    public void testGetSingleStreaming() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Hall A"),
                        "lien", equalTo("urlStream1")
                ).when().get("/rest/amc-streamings/id/"+ id_streaming);
    }

    @Test
    public void testGetAllStreamings() {
        expect().statusCode(200)
                .body(
                        "nom", hasItems("Hall A", "Hall B"),
                        "lien", hasItems("urlStream1", "urlStream2")
                ).when().get("/rest/amc-streamings/all");
    }

    @Test
    public void testStreamingNotExist() {
        expect().statusCode(404)
                .body(equalTo("Participant non trouvé pour l'id -1")).when().get("/rest/amc-streamings/id/-1");
    }
}