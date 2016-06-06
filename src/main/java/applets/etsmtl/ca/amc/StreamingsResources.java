package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.StreamingDAO;
import applets.etsmtl.ca.amc.model.Streaming;
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
@Path("amc-streamings")
@Api(value = "/Streaming")
public class StreamingsResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne un streaming",
            notes = "Retourne un streaming"
    )
    public Response getStreaming(@PathParam("key") int key) {
        StreamingDAO eventDAO = new StreamingDAO();
        Streaming streaming = eventDAO.find(key);

        if(streaming == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Streaming non trouvé pour l'id "+key).build();
        else
            return Response.status(200).entity(streaming).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les streamings",
            notes = "Retourne tous les streamings"
    )
    public Response getAllStreamings() {
        StreamingDAO eventDAO = new StreamingDAO();
        ArrayList<Streaming> allEvent = (ArrayList<Streaming>)eventDAO.findAll();
        return Response.status(200).entity(allEvent).build();
    }
}
