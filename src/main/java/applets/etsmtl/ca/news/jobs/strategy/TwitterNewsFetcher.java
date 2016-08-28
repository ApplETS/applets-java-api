package applets.etsmtl.ca.news.jobs.strategy;

/**
 * Created by nicolas on 26/01/16.
 */
public class TwitterNewsFetcher implements IFetchNewsStrategy {

    private String key;

    public TwitterNewsFetcher(String key) {
        this.key = key;
    }

    @Override
    public void fetchSources() {

    }

    @Override
    public void fetchNouvelles() {

    }
}
