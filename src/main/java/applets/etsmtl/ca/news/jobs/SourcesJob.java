package applets.etsmtl.ca.news.jobs;

import applets.etsmtl.ca.news.jobs.strategy.FacebookNewsFetcher;
import applets.etsmtl.ca.news.jobs.strategy.IFetchNewsStrategy;
import applets.etsmtl.ca.news.jobs.strategy.RssNewsFetcher;
import applets.etsmtl.ca.news.jobs.strategy.TwitterNewsFetcher;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SourcesJob implements Job {

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, IFetchNewsStrategy> mapFetchers = new HashMap<>();

        try {

            // Read sources.json file containing all the sources with their value to be accessed
            final String filePath = getClass().getResource("/news/sources.json").getPath();
            String sources_content = readFile(filePath, StandardCharsets.UTF_8);

            JSONObject typeSourceNews = new JSONObject(sources_content);

            // ADD parse token facebook
            String accesstoken_facebook = System.getenv("FACEBOOK_ACCESS_TOKEN");

            // Load RSS sources key value in the map
            JSONArray rss_list_sources = (JSONArray) typeSourceNews.getJSONArray("rss");
            for(int i=0; i < rss_list_sources.length(); i++) {
                String key = rss_list_sources.getJSONObject(i).get("key").toString();
                String value = rss_list_sources.getJSONObject(i).get("value").toString();

                mapFetchers.put(key, new RssNewsFetcher(key, value));
            }

            // Load Facebook sources key in the map
            JSONArray facebook_list_sources = (JSONArray) typeSourceNews.getJSONArray("facebook");
            for(int i=0; i < facebook_list_sources.length(); i++) {
                String key = facebook_list_sources.getJSONObject(i).get("key").toString();
                String value = facebook_list_sources.getJSONObject(i).get("value").toString();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, accesstoken_facebook));
            }

            // Load Twitter sources key in the map
            JSONArray twitter_list_sources = (JSONArray) typeSourceNews.getJSONArray("twitter");
            for(int i=0; i < twitter_list_sources.length(); i++) {
                String key = twitter_list_sources.getJSONObject(i).get("key").toString();

                mapFetchers.put(key, new TwitterNewsFetcher(key));
            }

            for (Map.Entry<String, IFetchNewsStrategy> entry : mapFetchers.entrySet())
                entry.getValue().fetchSources();
        }
        catch(IOException e) { System.out.println(e); }
        catch(JSONException e) { System.out.println(e); }
    }
}