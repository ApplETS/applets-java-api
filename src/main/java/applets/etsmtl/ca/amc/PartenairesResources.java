package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.PartenaireDAO;
import applets.etsmtl.ca.amc.model.Partenaire;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-partners")
@Api(value="/Partenaires", description = "")
public class PartenairesResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne un partenaire",
            notes = "Retourne un partenaire"
    )
    public Response getPartenaire(@PathParam("key") int key) {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        Partenaire partenaire = partenaireDAO.find(key);

        if(partenaire == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Partenaire non trouvé pour l'id "+key).build();
        else
            return Response.status(200).entity(partenaire).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les partenaires",
            notes = "Retourne tous les partenaires"
    )
    public Response getAllPartenaires() {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        ArrayList<Partenaire> allPartenaire = (ArrayList<Partenaire>)partenaireDAO.findAll();

        return Response.status(200).entity(allPartenaire).build();
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les partenaires pour un évènement",
            notes = "Retourne tous les partenaires pour un évènement"
    )
    //return all the partners for an event
    public Response getAllPartenairesForEvent(@PathParam("key") int idEvent) {
        PartenaireDAO partenaireDAO = new PartenaireDAO();
        ArrayList<Partenaire> allPartenaire = (ArrayList<Partenaire>)partenaireDAO.findAll(idEvent);

        return Response.status(200).entity(allPartenaire).build();
    }
}
