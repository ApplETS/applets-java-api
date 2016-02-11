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
@Path("amcpartners")
public class PartenairesResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/partenaires/id/1
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Partenaire getPartenaire(@PathParam("key") String key) {
        PartenaireDAO partenanireDAO = new PartenaireDAO();
        Partenaire partenaire = partenanireDAO.find(key);

        return partenaire;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Partenaire> getAllPartenainres() {
        PartenaireDAO partenanireDAO = new PartenaireDAO();
        ArrayList<Partenaire> allPartenaire = (ArrayList<Partenaire>)partenanireDAO.findAll();

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
