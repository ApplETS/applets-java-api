package applets.etsmtl.ca.news.jobs.strategy;

import applets.etsmtl.ca.news.db.SourceDAO;

/**
 * Created by nicolas on 26/01/16.
 */
public class RssNewsFetcher implements IFetchNewsStrategy {

    private String key;
    private String value;

    private SourceDAO sourceDao;

    public RssNewsFetcher(String key, String value) {
        this.key = key;
        this.value = value;

        this.sourceDao = new SourceDAO();
    }

    @Override
    public void fetchSources() {
        if(this.sourceDao.isExisting(this.key))
            this.sourceDao.add(this.key, this.value);
    }

    @Override
    public void fetchNouvelles() {

    }
}
