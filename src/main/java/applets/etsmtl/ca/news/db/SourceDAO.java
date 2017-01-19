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
            String findByKey = "SELECT * FROM sources WHERE key = ?";
            PreparedStatement st = this.connection.prepareStatement(findByKey,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString( 1, key );
            ResultSet result = st.executeQuery();
            if(result.first()) {
                source = getDataFromResult(result);
            }
            result.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public boolean isExisting(String key) {
        try {
            String findByKey = "SELECT key FROM sources WHERE key = ?";
            PreparedStatement st = this.connection.prepareStatement(findByKey,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, key);
            ResultSet result = st.executeQuery();
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
            String findAll = "SELECT * FROM sources";
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            findAll
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

    public void update(Source source) {
        try {

            String req_insert_source = "" +
                    "UPDATE sources " +
                    "SET name = ?, type = ?::type_source, url_image = ?, value = ? " +
                    "WHERE key = ?";
            PreparedStatement preparedStatement = ConnectionSingleton.getInstance().prepareStatement(req_insert_source);

            preparedStatement.setString(5, source.getKey());
            preparedStatement.setString(1, source.getName());
            preparedStatement.setString(2, source.getType());
            preparedStatement.setString(3, source.getUrlImage());
            preparedStatement.setString(4, source.getValue());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Source> findByType(String type){
        List<Source> sources = new ArrayList<Source>();
        try {
            String findByType = "SELECT * FROM sources WHERE type = ?::type_source";
            PreparedStatement st = this.connection.prepareStatement(findByType,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, type);
            ResultSet result = st.executeQuery();
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
