package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EquipeDAO;
import applets.etsmtl.ca.amc.model.Equipe;
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
@Path("amc-equipes")
@Api(value = "/Équipes")
public class EquipesResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne une équipe",
            notes = "Retourne une équipe"
    )
    public Response getEquipe(@PathParam("key") int idEquipe) {
        EquipeDAO equipeDAO = new EquipeDAO();
        Equipe equipe = equipeDAO.find(idEquipe);

        if(equipe == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Équipe non trouvée pour l'id "+idEquipe).build();
        else
            return Response.status(200).entity(equipe).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les équipes",
            notes = "Retourne toutes les équipes"
    )
    public Response getAllEquipes() {
        EquipeDAO equipeDAO = new EquipeDAO();
        ArrayList<Equipe> allEquipe = (ArrayList<Equipe>)equipeDAO.findAll();

        return Response.status(200).entity(allEquipe).build();
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les équipes pour un évènement",
            notes = "Retourne toutes les équipes pour un évènement"
    )
    //return all the equipes for an event
    public Response getAllEquipesForEvent(@PathParam("key") int idEvent) {
        EquipeDAO equipeDAO = new EquipeDAO();
        ArrayList<Equipe> allEquipe = (ArrayList<Equipe>)equipeDAO.findAll(idEvent);

        return Response.status(200).entity(allEquipe).build();
    }
}
