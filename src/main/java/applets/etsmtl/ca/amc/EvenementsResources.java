package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EvenementDAO;
import applets.etsmtl.ca.amc.model.Evenement;
import io.swagger.annotations.*;

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
@Api(value="/Évènements")
@Path("amc-events")
public class EvenementsResources {

    @GET
    @Path("id/{key}")
    @ApiOperation(
            value = "Retourne un évènement",
            notes = "Retourne un évènement"
    )
//    @ApiResponses(value= {
//            @ApiResponse(code = 200, message = "Successful retrieval of event"),
//            //@ApiResponse(code = 404, message = "Event records not found"),
//            @ApiResponse(code = 500, message = "Internal servererror")
//    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvenement(@PathParam("key") int key) {
        EvenementDAO eventDAO = new EvenementDAO();
        Evenement evenement = eventDAO.find(key);

        if(evenement == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Évènement non trouvé pour l'id "+key).build();
        else
            return Response.status(200).entity(evenement).build();
    }

    @GET
    @Path("all")
    @ApiOperation(
            value = "Retourne tous les évènements",
            notes = "Retourne tous les évènements"
    )
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Evenement> getAllEvenements() {
        EvenementDAO eventDAO = new EvenementDAO();
        ArrayList<Evenement> allEvent = (ArrayList<Evenement>)eventDAO.findAll();
        return allEvent;
    }
}
