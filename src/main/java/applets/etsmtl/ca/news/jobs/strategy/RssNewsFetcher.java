package applets.etsmtl.ca.news.jobs.strategy;

import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Source;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.URL;

public class RssNewsFetcher implements IFetchNewsStrategy {

    private String key;
    private String value;

    private SourceDAO sourceDao;
    private OkHttpClient okhttpcli;

    public RssNewsFetcher(String key, String value) {
        this.key = key;
        this.value = value;

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

        if(!this.sourceDao.isExisting(this.key)) {
            try {

                String data = getDataFromUrl(this.value);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(new URL(this.value)));

                Source source = new Source();

                String imageUrl = feed.getImage().getUrl(); // Return null if no image

                source.setType("rss");

                source.setKey(this.key);
                source.setName(this.key);
                source.setValue(this.value);
                source.setUrlImage(imageUrl);

                this.sourceDao.add(source);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (FeedException e) {
                e.printStackTrace();
            }
        }
    }

    /*******************************************
     * Fetching nouvelles
     */

    @Override
    public void fetchNouvelles() {

    }
}
