package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.PartenaireDAO;
import applets.etsmtl.ca.amc.model.Partenaire;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-partners")
public class PartenairesResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/partenaires/id/1
    @Produces(MediaType.APPLICATION_JSON)
    public Partenaire getPartenaire(@PathParam("key") String key) {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        Partenaire partenaire = partenaireDAO.find(key);

        return partenaire;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Partenaire> getAllPartenaires() {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        ArrayList<Partenaire> allPartenaire = (ArrayList<Partenaire>)partenaireDAO.findAll();

        return allPartenaire;
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //return all the partners for an event
    public ArrayList<Partenaire> getAllPartenairesForEvent(@PathParam("key") String idEvent) {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        ArrayList<Partenaire> allPartenaire = (ArrayList<Partenaire>)partenaireDAO.findAll(idEvent);

        return allPartenaire;
    }

    @GET
    @Path("db")
    public Partenaire testDB() {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        Partenaire partenaire = partenaireDAO.find("1");

        return partenaire;
    }
}
