package applets.etsmtl.ca.news;

import applets.etsmtl.ca.news.db.EventDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Source;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcantvez on 09/03/16.
 */
@Path("events")
public class EventsResources {

    @GET
    @Path("list/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Event> getEvents(@PathParam("id") String id) {
        List<Event> events = new ArrayList<Event>();

        EventDAO eventDAO = new EventDAO();
        SourceDAO sourceDAO = new SourceDAO();
        Source source = sourceDAO.find(id);

        events.addAll(eventDAO.findFollowingEvents(id));

        return events;
    }

    @GET
    @Path("sources")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Source> getSources() {

        SourceDAO sourceDAO = new SourceDAO();
        List<Source> sources = sourceDAO.findByType("facebook");
        return sources;
    }
}
