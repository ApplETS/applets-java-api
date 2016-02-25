package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Streaming;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class StreamingDAO extends DAO<Streaming> {
    @Override
    public Streaming find(String key) {
        Streaming streaming = new Streaming();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM streaming WHERE id_streaming = '" +key+"'"
                    );
            if(result.first()) {
                streaming.setId(result.getInt("id_streaming"));
                streaming.setNom(result.getString("nom"));
                streaming.setLien(result.getString("lien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return streaming;
    }

    @Override
    public List<Streaming> findAll() {
        ArrayList<Streaming> alStreaming = new ArrayList<Streaming>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM streaming ORDER BY nom"
                    );
            while (result.next()) {
                Streaming streaming = new Streaming();
                streaming.setId(result.getInt("id_streaming"));
                streaming.setNom(result.getString("nom"));
                streaming.setLien(result.getString("lien"));

                alStreaming.add(streaming);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alStreaming;
    }
}
