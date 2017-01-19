package applets.etsmtl.ca.news;

import applets.etsmtl.ca.news.db.EventDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Source;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("events")
public class EventsResources {

    private final EventDAO eventDAO;
    private final SourceDAO sourceDAO;

    @Inject
    public EventsResources(EventDAO eventDAO, SourceDAO sourceDAO) {
        this.eventDAO = eventDAO;
        this.sourceDAO = sourceDAO;
    }

    @GET
    @Path("list/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Event> getEvents(@PathParam("id") String id) {
        List<Event> events = new ArrayList<Event>();

        Source source = sourceDAO.find(id);

        events.addAll(eventDAO.findFollowingEvents(id));

        return events;
    }

    @GET
    @Path("sources")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Source> getSources() {

        List<Source> sources = sourceDAO.findByType("facebook");
        return sources;
    }
}
