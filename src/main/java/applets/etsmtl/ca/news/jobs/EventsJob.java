package applets.etsmtl.ca.news.jobs;

import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.jobs.strategy.FacebookNewsFetcher;
import applets.etsmtl.ca.news.model.Source;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nicolas on 24/01/16.
 */
public class EventsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, FacebookNewsFetcher> mapFetchers = new HashMap<>();

        try {
            SourceDAO sourceDao = new SourceDAO();

            List<Source> list_sources = sourceDao.findByType("facebook");

            // ADD parse token facebook
            String accesstoken_facebook = System.getenv("FACEBOOK_ACCESS_TOKEN");

            for(Source source : list_sources) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, accesstoken_facebook));
            }

            for (Map.Entry<String, FacebookNewsFetcher> entry : mapFetchers.entrySet())
                entry.getValue().fetchEvenements();

        }
        catch(Exception e) { System.out.println(e); }

    }
}