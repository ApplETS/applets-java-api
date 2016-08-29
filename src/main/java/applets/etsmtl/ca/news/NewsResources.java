package applets.etsmtl.ca.news;

/**
 * Created by gnut3ll4 on 22/01/16.
 */

import applets.etsmtl.ca.news.db.NouvellesDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Nouvelle;
import applets.etsmtl.ca.news.model.Source;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("news")
public class NewsResources {

    private final NouvellesDAO nouvellesDAO;
    private final SourceDAO sourceDAO;

    @Inject
    public NewsResources(NouvellesDAO nouvellesDAO, SourceDAO sourceDAO)
    {
        this.nouvellesDAO = nouvellesDAO;
        this.sourceDAO = sourceDAO;
    }


    @GET
    @Path("list/{key}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Nouvelle> getNouvelles(@PathParam("key") String key) {
        List<Nouvelle> nouvelles = new ArrayList<Nouvelle>();

        Source source = sourceDAO.find(key);

        nouvelles.addAll(nouvellesDAO.findAllForSource(source.getKey()));

        return nouvelles;
    }

    @GET
    @Path("sources")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Source> getSources() {
        SourceDAO sourceDAO = new SourceDAO();
        List<Source> sources = sourceDAO.findAll();
        return sources;
    }

}
