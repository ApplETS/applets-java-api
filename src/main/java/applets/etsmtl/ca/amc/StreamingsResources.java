package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.StreamingDAO;
import applets.etsmtl.ca.amc.model.Streaming;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-streamings")
public class StreamingsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/events/list/sgjstgstg
    @Produces({MediaType.APPLICATION_JSON+";charset=utf-8", MediaType.APPLICATION_XML})
    public Streaming getStreaming(@PathParam("key") String key) {
        StreamingDAO eventDAO = new StreamingDAO();
        Streaming streaming = eventDAO.find(key);
        return streaming;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON+";charset=utf-8", MediaType.APPLICATION_XML})
    public ArrayList<Streaming> getAllStreamings() {
        StreamingDAO eventDAO = new StreamingDAO();
        ArrayList<Streaming> allEvent = (ArrayList<Streaming>)eventDAO.findAll();
        return allEvent;
    }

    @GET
    @Path("db")
    public Streaming testDB() {
        StreamingDAO eventDAO = new StreamingDAO();
        Streaming event = eventDAO.find("1");
        return event;
    }
}
