package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.IntervenantDAO;
import applets.etsmtl.ca.amc.model.Intervenant;
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
@Path("amc-intervs")
@Api(value="/Intervenants", description = "")
public class IntervenantsResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne un intervenant",
            notes = "Retourne un intervenant"
    )
    public Response getIntervenant(@PathParam("key") int key) {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        Intervenant intervenant = intervenantDAO.find(key);

        if(intervenant == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Intervenant non trouvé pour l'id "+key).build();
        else
            return Response.status(200).entity(intervenant).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les intervenants",
            notes = "Retourne tous les intervenants"
    )
    public Response getAllIntervenants() {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        ArrayList<Intervenant> allIntervenant = (ArrayList<Intervenant>)intervenantDAO.findAll();

        return Response.status(200).entity(allIntervenant).build();
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //return all the intervenants for an event
    public Response getAllIntervenantsForEvent(@PathParam("key") int idEvent) {
        IntervenantDAO intervenantDAO = new IntervenantDAO();
        ArrayList<Intervenant> allIntervenant = (ArrayList<Intervenant>)intervenantDAO.findAll(idEvent);

        return Response.status(200).entity(allIntervenant).build();
    }
}
