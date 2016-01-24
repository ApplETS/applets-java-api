package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
public class SourceDAO extends DAO<Source> {
    @Override
    public Source find(String key) {
        Source source = new Source();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM sources WHERE key = '" +key+"'"
                    );
            if(result.first())
                source.setUrlImage(result.getString("url_image"));
                source.setType(result.getString("type"));
                source.setName(result.getString("name"));
                source.setKey(key);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public List<Source> findAll() {
        //TODO
        return null;
    }
}
