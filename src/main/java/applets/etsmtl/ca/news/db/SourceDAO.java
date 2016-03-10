package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Source;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            if(result.first()) {
                source = getDataFromResult(result);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public boolean isExisting(String key) {
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT key FROM sources WHERE key = '" +key+"'"
                    );
            if(result.first())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Source> findAll() {
        List<Source> sources = new ArrayList<Source>();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM sources"
                    );
            while(result.next()) {
                sources.add(getDataFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sources;
    }

    public void add(Source source) {
        try {

            String req_insert_source = "INSERT INTO sources (key, name, type, url_image, value) VALUES (?,?,?::type_source,?,?)";
            PreparedStatement preparedStatement = ConnectionSingleton.getInstance().prepareStatement(req_insert_source);

            preparedStatement.setString(1, source.getKey());
            preparedStatement.setString(2, source.getName());
            preparedStatement.setString(3, source.getType());
            preparedStatement.setString(4, source.getUrlImage());
            preparedStatement.setString(5, source.getValue());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Source> findByType(String type){
        List<Source> sources = new ArrayList<Source>();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM sources where type = '"+type+"'"
                    );
            while(result.next()) {
                sources.add(getDataFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sources;
    }


    @Override
    protected Source getDataFromResult(ResultSet result) throws SQLException {
        Source source = new Source();
        source.setUrlImage(result.getString("url_image"));
        source.setType(result.getString("type"));
        source.setName(result.getString("name"));
        source.setKey(result.getString("key"));
        source.setValue(result.getString("value"));
        return source;
    }


}
