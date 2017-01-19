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

            List<Source> list_sources_facebook = sourceDao.findByType("facebook");
            List<Source> list_sources_rss = sourceDao.findByType("rss");
            List<Source> list_sources_twitter = sourceDao.findByType("twitter");

            // ADD parse token facebook
            String accesstoken_facebook = System.getenv("FACEBOOK_ACCESS_TOKEN");

            for(Source source : list_sources_facebook) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, accesstoken_facebook));
            }

            for(Source source : list_sources_rss) {
                String key = source.getKey();
                String value = source.getValue();

                mapFetchers.put(key, new RssNewsFetcher(value, accesstoken_facebook));
            }

            for(Source source : list_sources_twitter) {
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
