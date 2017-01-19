package applets.etsmtl.ca.news.jobs;

import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.jobs.strategy.FacebookNewsFetcher;
import applets.etsmtl.ca.news.jobs.strategy.IFetchNewsStrategy;
import applets.etsmtl.ca.news.jobs.strategy.RssNewsFetcher;
import applets.etsmtl.ca.news.jobs.strategy.TwitterNewsFetcher;
import applets.etsmtl.ca.news.model.Source;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Map<String, IFetchNewsStrategy> mapFetchers = new HashMap<>();

        try {
            SourceDAO sourceDao = new SourceDAO();

            List<Source> listSourcesFacebook = sourceDao.findByType("facebook");
            List<Source> listSourcesRss = sourceDao.findByType("rss");
            List<Source> listSourcesTwitter = sourceDao.findByType("twitter");

            // ADD parse token facebook
            String facebookAccessToken = System.getenv("FACEBOOK_ACCESS_TOKEN");

            for(Source source : listSourcesFacebook) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, facebookAccessToken));
            }

            for(Source source : listSourcesRss) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new RssNewsFetcher(value, facebookAccessToken));
            }

            for(Source source : listSourcesTwitter) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new TwitterNewsFetcher(value));
            }

            for (Map.Entry<String, IFetchNewsStrategy> entry : mapFetchers.entrySet())
                entry.getValue().fetchNouvelles();

        }
        catch(Exception e) { System.out.println(e); }
    }
}
