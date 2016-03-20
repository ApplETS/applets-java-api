package test.amc;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.jayway.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ValentinD on 2016-02-20.
 */
public class TestEvenements {

    private String id_event = ConstantsTest.EVENT_ID;

   /* @Before
    public void setUp(){
        RestAssured.basePath = "http://localhost:8080";
    }*/

    @Test
    public void testGetSingleEvenement() {
        expect().statusCode(200)
                .body(
                        "nom", equalTo("Evenement1 Test"),
                        "presentation", equalTo("Presentation1 Test"),
                        "dateDebut", equalTo("2016-02-20T17:00:00-05:00"),
                        "dateFin", equalTo("2016-02-21T17:00:00-05:00"),
                        "tirageSorts.description", hasItems("Un super prix1", "Un super prix2"),
                        "partenaires.nom", hasItems("Partenaire1 Test", "Partenaire2 Test"),
                        "intervenants.nom", hasItems("Interv1 Test", "Interv2 Test"),
                        "tirageInscrits.participant.nom", hasItems("Participant1 Test", "Participant2 Test"),
                        "equipes.nom", hasItems("Equipe1 Test", "Equipe2 Test"),
                        "equipes.prix", hasItems("Prix du cégep")
                ).when().get("/rest/amc-events/id/"+id_event);
    }

    @Test
    public void testGetAllEvenements() {
        ArrayList<String> alPartenaires = new ArrayList<String>();
        alPartenaires.add("Partenaire1 Test");
        alPartenaires.add("Partenaire2 Test");
        ArrayList<String> alPartenaires2 = new ArrayList<String>();
        alPartenaires2.add("Partenaire1 Test");

        ArrayList<String> alIntervenants1 = new ArrayList<String>();
        alIntervenants1.add("Interv1 Test");
        alIntervenants1.add("Interv1 Test");
        alIntervenants1.add("Interv2 Test");

        ArrayList<String> alIntervenants2 = new ArrayList<String>();
        alIntervenants2.add("Interv3 Test");
        alIntervenants2.add("Interv1 Test");

        ArrayList<String> alDescr1 = new ArrayList<String>();
        alDescr1.add("Un super prix1");
        alDescr1.add("Un super prix2");
        ArrayList<String> alDescr2 = new ArrayList<String>();
        alDescr2.add("Un super prix3");

        ArrayList<String> alEquipe1 = new ArrayList<>();
        alEquipe1.add("Equipe1 Test");
        alEquipe1.add("Equipe2 Test");
        ArrayList<String> alEquipe2 = new ArrayList<>();
        alEquipe2.add("Equipe3 Test");

        ArrayList<String> alPrix = new ArrayList<>();
        alPrix.add("Prix du cégep");
        alPrix.add(null);

        ArrayList<String> alPrix2 = new ArrayList<>();
        alPrix2.add(null);

        expect().statusCode(200)
                .body(
                        "nom", hasItems("Evenement1 Test", "Evenement2 Test"),
                        "presentation", hasItems("Presentation1 Test","Presentation2 Test"),
                        "partenaires.nom", hasItems(alPartenaires, alPartenaires2),
                        "intervenants.nom", hasItems(alIntervenants2, alIntervenants1),
                        "tirageSorts.description", hasItems(alDescr1, alDescr2),
                        "equipes.nom", hasItems(alEquipe1, alEquipe2),
                        "equipes.prix", hasItems(alPrix, alPrix2)
                ).when().get("/rest/amc-events/all");
    }

    @Test
    public void testEventNotExist() {
        expect().statusCode(200)
                .body(
                        "id", equalTo(0)
                ).when().get("/rest/amc-events/id/-1");
    }
}