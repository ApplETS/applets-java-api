package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EvenementDAO;
import applets.etsmtl.ca.amc.model.Evenement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-events")
public class EvenementsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/events/list/sgjstgstg
    @Produces(MediaType.APPLICATION_JSON)
    public Evenement getEvenement(@PathParam("key") String key) {
        EvenementDAO eventDAO = new EvenementDAO();
        Evenement evenement = eventDAO.find(key);
        return evenement;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Evenement> getAllEvenements() {
        EvenementDAO eventDAO = new EvenementDAO();
        ArrayList<Evenement> allEvent = (ArrayList<Evenement>)eventDAO.findAll();
        return allEvent;
    }

    @GET
    @Path("db")
    public Evenement testDB() {
        EvenementDAO eventDAO = new EvenementDAO();
        Evenement event = eventDAO.find("1");
        return event;
    }
}
