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

public class EventsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, FacebookNewsFetcher> mapFetchers = new HashMap<>();

        try {
            SourceDAO sourceDao = new SourceDAO();

            List<Source> listSources = sourceDao.findByType("facebook");

            // ADD parse token facebook
            String facebookAccessToken = System.getenv("FACEBOOK_ACCESS_TOKEN");

            for (Source source : listSources) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, facebookAccessToken));
            }

            for (Map.Entry<String, FacebookNewsFetcher> entry : mapFetchers.entrySet())
                entry.getValue().fetchEvenements();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}