package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.IntervenantDAO;
import applets.etsmtl.ca.amc.model.Intervenant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-intervs")
public class IntervenantsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/intervenants/id/1
    @Produces(MediaType.APPLICATION_JSON)
    public Intervenant getIntervenant(@PathParam("key") String key) {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        Intervenant intervenant = intervenantDAO.find(key);

        return intervenant;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Intervenant> getAllIntervenants() {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        ArrayList<Intervenant> allIntervenant = (ArrayList<Intervenant>)intervenantDAO.findAll();

        return allIntervenant;
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //return all the intervenants for an event
    public ArrayList<Intervenant> getAllIntervenantsForEvent(@PathParam("key") String idEvent) {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        ArrayList<Intervenant> allIntervenant = (ArrayList<Intervenant>)intervenantDAO.findAll(idEvent);

        return allIntervenant;
    }

    @GET
    @Path("db")
    public Intervenant testDB() {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        Intervenant intervenant = intervenantDAO.find("1");

        return intervenant;
    }
}
