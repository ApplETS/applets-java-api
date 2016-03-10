package applets.etsmtl.ca.news;

import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Source;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by club on 09/03/16.
 */
@Path("events")
public class EventsResources {

    @GET
    @Path("list/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Event> getEvents(@PathParam("id") String id) {
        Event event = new Event();
        event.setId("id");
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        return events;
    }

    @GET
    @Path("db")
    public Source testDB() {
        SourceDAO sourceDAO = new SourceDAO();
        Source source = sourceDAO.find("ets");
        return source;
    }

    @GET
    @Path("sources")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Source> getSources() {

        Source source = new Source();
        source.setKey("Key");
        source.setName("Name");
        source.setType("Facebook");
        source.setUrlImage("url image");

        ArrayList<Source> sources = new ArrayList<>();
        sources.add(source);
        return sources;
    }
}
