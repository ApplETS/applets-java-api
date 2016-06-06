package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.GlobalData;
import applets.etsmtl.ca.amc.model.Token;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class GlobalDataDAO extends DAO<GlobalData> {
    @Override
    public GlobalData find(int token) {
        //Methode qui ne sert à rien
        return null;
    }

    @Override
    public List<GlobalData> findAll() {
        //Methode qui ne sert à rien
        return null;
    }

    public GlobalData findData() {
        GlobalData data = new GlobalData();
        try {
            String selectStatement = "SELECT * FROM global_data";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                data.setId(result.getInt("id_general"));
                data.setEventbriteID(result.getString("eventbrite_id_app"));
                data.setIonicID(result.getString("ionic_id_app"));
                data.setIonicBearer(result.getString("ionic_bearer_app"));
                data.setIonicProfile(result.getString("ionic_profile_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
