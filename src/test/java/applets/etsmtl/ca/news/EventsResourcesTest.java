package news;

import applets.etsmtl.ca.news.EventsResources;
import applets.etsmtl.ca.news.db.EventDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Source;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class EventsResourcesTest extends JerseyTest {

    @Mock
    private EventDAO eventDAO;

    @Mock
    private SourceDAO sourceDAO;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        EventsResources resource = new EventsResources(eventDAO, sourceDAO);
        ResourceConfig config = new ResourceConfig();
        config.register(resource);
        return config;
    }

    @Test
    public void testGetEvents() {
        String sourceKey = "aeets", message = "Ceci est un test";

        Event event = new Event();
        event.setId_source(sourceKey);
        event.setNom(message);

        ArrayList<Event> list = new ArrayList<>();
        list.add(event);
        list.add(event);
        when(eventDAO.findFollowingEvents(sourceKey)).thenReturn(list);

        Source source = new Source();
        source.setKey(sourceKey);
        when(sourceDAO.find(sourceKey)).thenReturn(source);

        List<Event> response = target("/events/list/" + sourceKey).request().get(new GenericType<List<Event>>() {
        });

        Assert.assertTrue(message.equals(response.get(0).getNom()));
        Assert.assertTrue(response.size() == list.size());
    }

    @Test
    public void testGetSources() {
        ArrayList<Source> sources = new ArrayList<>();

        Source source1 = new Source();
        source1.setName("source1");
        source1.setType("facebook");

        sources.add(source1);
        sources.add(source1);

        when(sourceDAO.findByType("facebook")).thenReturn(sources);

        List<Source> response = target("/events/sources").request().get(new GenericType<List<Source>>() {
        });

        Assert.assertTrue("source1".equals(response.get(0).getName()));
        Assert.assertTrue(response.size() == sources.size());

    }


}


