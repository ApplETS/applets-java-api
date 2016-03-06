package applets.etsmtl.ca.news.jobs.strategy;

import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Source;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;

/**
 * Created by nicolas on 26/01/16.
 */
public class FacebookNewsFetcher implements IFetchNewsStrategy {

    private String key;
    private String value;

    private String token;

    private SourceDAO sourceDao;
    private OkHttpClient okhttpcli;

    public FacebookNewsFetcher(String key, String value, String token) {
        this.key = key;
        this.value = value;

        this.token = token;

        this.sourceDao = new SourceDAO();
        this.okhttpcli = new OkHttpClient();
    }

    /*******************************************
     * Fetching sources
     */

    private String getDataFromUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = this.okhttpcli.newCall(request).execute();

        return response.body().string();
    }

    @Override
    public void fetchSources() {
        String url_source = "https://graph.facebook.com/v2.5/" + this.value + "?access_token=" + this.token;
        String url_picture_source = "https://graph.facebook.com/v2.5/" + this.value + "/picture?access_token=" + this.token + "&redirect=false";

        if(!this.sourceDao.isExisting(this.key)) {

            try {

                String data_name = getDataFromUrl(url_source);
                String data_picture = getDataFromUrl(url_picture_source);

                JSONObject Jobjet_data = new JSONObject(data_name);
                JSONObject Jobjet_picture = new JSONObject(data_picture);

                String name = Jobjet_data.getString("name");
                String imageURL = Jobjet_picture.getJSONObject("data").getString("url");

                Source source = new Source();

                source.setType("facebook");

                source.setKey(this.key);
                source.setName(name);
                source.setValue(this.value);
                source.setUrlImage(imageURL);

                this.sourceDao.add(source);
            }

            catch(JSONException e) { System.out.println(e); }
            catch(IOException e) { System.out.println(e); }
        }

    }

    @Override
    public void fetchNouvelles() {
        // https://graph.facebook.com/v2.5/" + this.value + "feed?access_token=
    }
}