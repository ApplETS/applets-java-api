package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Streaming;

import java.sql.PreparedStatement;
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
    public Streaming find(int key) {
        Streaming streaming = new Streaming();
        try {
            String selectStatement = "SELECT * FROM streaming WHERE id_streaming = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,key);
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                streaming.setId(result.getInt("id_streaming"));
                streaming.setNom(result.getString("nom"));
                streaming.setLien(result.getString("lien"));
            }
            else
                return null;
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
