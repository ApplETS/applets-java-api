package applets.etsmtl.ca.news.jobs;

import applets.etsmtl.ca.news.db.ConnectionSingleton;
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
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Created by nicolas on 24/01/16.
 */
public class SourcesJob implements Job {

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void upgradeListTypeSources(String[] array_type_sources) {

        try {
            Connection connection = ConnectionSingleton.getInstance();
            Statement stmt = connection.createStatement();
            /*TO DO : String sql = "La requête modifiant les types de sources, faire un implode pour le tableau en paramètre";
            stmt.executeUpdate(sql);*/

            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            exit(1);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Map<String, IFetchNewsStrategy> mapFetchers = new HashMap<>();

        try {

            String[] array_type_sources = new String[]{"rss", "facebook", "twitter"};
            upgradeListTypeSources(array_type_sources);


            final String filePath = getClass().getResource("/news/sources.json").getPath();
            String sources_content = readFile(filePath, StandardCharsets.UTF_8);

            JSONObject typeSourceNews = new JSONObject(sources_content);

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

                mapFetchers.put(key, new FacebookNewsFetcher(key));
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
