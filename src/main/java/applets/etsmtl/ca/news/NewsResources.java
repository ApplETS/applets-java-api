package applets.etsmtl.ca.news;

/**
 * Created by gnut3ll4 on 22/01/16.
 */

import applets.etsmtl.ca.news.db.ConnectionSingleton;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Nouvelle;
import applets.etsmtl.ca.news.model.Source;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("news")
public class NewsResources {

    @GET
    @Path("list/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Nouvelle> getNouvelles(@PathParam("key") String key) {

        Nouvelle nouvelle = new Nouvelle();
        nouvelle.setDate(new Date());
        nouvelle.setId("id");
        nouvelle.setLink("link");
        nouvelle.setMessage("message");
        nouvelle.setTitre("titre");
        nouvelle.setUrlPicture("picture url");
        ArrayList<Nouvelle> nouvelles = new ArrayList<>();
        nouvelles.add(nouvelle);
        return nouvelles;
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
