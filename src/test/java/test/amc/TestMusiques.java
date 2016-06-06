package test.amc;

import org.junit.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestMusiques {

    private String id_musique = ConstantsTest.MUSIQUE_ID;
    private String adresse_ip = ConstantsTest.ADRESSE_IP;

    @Test
    public void testGetSingleMusique() {
        expect().statusCode(200)
                .body(
                        "titre", equalTo("Musique1 Test"),
                        "dejaJoue", equalTo(0),
                        "lien", equalTo("urlMsq1")
                //        "nbVote", equalTo(10)
                ).when().get("/rest/amc-musiques/id/"+ id_musique);
    }

    @Test
    public void testGetMusiqueNotExist() {
        expect().statusCode(404)
                .body(equalTo("Musique non trouvée pour l'id -1"))
                .when().get("/rest/amc-musiques/id/-1");
    }

    @Test
    public void testGetAllMusiques() {
        ArrayList<String> alParticipants1 = new ArrayList<String>();
        alParticipants1.add("Participant1 Test");
        alParticipants1.add("Participant3 Test");

        ArrayList<String> alParticipants2 = new ArrayList<String>();
        alParticipants2.add("Participant2 Test");

        expect().statusCode(200)
                .body(
                        "titre", hasItems("Musique1 Test", "Musique2 Test", "Musique3 Test", "Musique4 Test", "Musique5 Test"),
                //        "nbVote", hasItems(15, 22, 2, 40, 10),
                        "dejaJoue", hasItems(0, 1, 2)
                ).when().get("/rest/amc-musiques/all");
    }

    @Test
    public void testGetMusiquesForVote() {
        expect().statusCode(200)
                .body(
                        "titre", hasItems("Musique1 Test", "Musique4 Test"),
                 //       "nbVote", hasItems(22, 10),
                        "dejaJoue", hasItems(0),
                        "votePourElle", hasItems(true, false)
                ).when().get("/rest/amc-musiques/all-vote/"+adresse_ip);
    }

    @Test
    public void testGetMusiquesElected() {
        expect().statusCode(200)
                .body(
                        "titre", hasItems("Musique2 Test", "Musique3 Test"),
                 //       "nbVote", hasItems(2, 40),
                        "dejaJoue", hasItems(1),
                        "votePourElle", hasItems(true, false)
                ).when().get("/rest/amc-musiques/all-elected/"+adresse_ip);
    }

    @Test
    public void testGetMusiquesPlayed() {
        expect().statusCode(200)
                .body(
                        "titre", hasItems("Musique5 Test"),
                 //       "nbVote", hasItems(15),
                        "dejaJoue", hasItems(2),
                        "votePourElle", hasItems(true)
                ).when().get("/rest/amc-musiques/all-played/"+adresse_ip);
    }

    @Test
    public void testVoteForMusique() {
        expect().statusCode(200)
                .body(equalTo(false))
                .when().post("/rest/amc-musiques/vote/musique/"+id_musique+"/adresseIP/"+adresse_ip);
    }
}