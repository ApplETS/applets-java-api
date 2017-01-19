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
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, IFetchNewsStrategy> mapFetchers = new HashMap<>();

        try {

            // Read sources.json file containing all the sources with their value to be accessed
            final String filePath = getClass().getResource("/news/sources.json").getPath();
            String sourcesContent = readFile(filePath, StandardCharsets.UTF_8);

            JSONObject typeSourceNews = new JSONObject(sourcesContent);

            // ADD parse token facebook
            String facebookAccessToken = System.getenv("FACEBOOK_ACCESS_TOKEN");

            // Load RSS sources key value in the map
            JSONArray rssListSources = (JSONArray) typeSourceNews.getJSONArray("rss");
            for (int i = 0; i < rssListSources.length(); i++) {
                String key = rssListSources.getJSONObject(i).get("key").toString();
                String value = rssListSources.getJSONObject(i).get("value").toString();

                mapFetchers.put(key, new RssNewsFetcher(key, value));
            }

            // Load Facebook sources key in the map
            JSONArray facebookListSources = (JSONArray) typeSourceNews.getJSONArray("facebook");
            for (int i = 0; i < facebookListSources.length(); i++) {
                String key = facebookListSources.getJSONObject(i).get("key").toString();
                String value = facebookListSources.getJSONObject(i).get("value").toString();

                mapFetchers.put(key, new FacebookNewsFetcher(key, value, facebookAccessToken));
            }

            // Load Twitter sources key in the map
            JSONArray twitterListSources = (JSONArray) typeSourceNews.getJSONArray("twitter");
            for (int i = 0; i < twitterListSources.length(); i++) {
                String key = twitterListSources.getJSONObject(i).get("key").toString();

                mapFetchers.put(key, new TwitterNewsFetcher(key));
            }

            for (Map.Entry<String, IFetchNewsStrategy> entry : mapFetchers.entrySet())
                entry.getValue().fetchSources();
        } catch (IOException | JSONException e) {
            System.out.println(e);
        }
    }
}