package applets.etsmtl.ca.amc.db;

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
public class TokenDAO extends DAO<Token> {
    @Override
    public Token find(String token) {
        //Methode qui ne sert à rien
        return null;
    }

    @Override
    public List<Token> findAll() {
        ArrayList<Token> alTokens = new ArrayList<Token>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM token " +
                                "ORDER BY last_use DESC"
                    );
            while (result.next()) {
                Token token = new Token();
                token.setId(result.getInt("id_token"));
                token.setValue(result.getString("valeur"));

                Timestamp ts = result.getTimestamp("last_use");
                Date dateLastUse = new Date(ts.getTime());
                token.setLastUse(dateLastUse);

                alTokens.add(token);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTokens;
    }


    public boolean addToken(String tokenValue) {
        boolean inscritWorks = false;
        try {
            String selectStatement = "INSERT INTO token (valeur, last_use) VALUES ( ?, ?)";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,tokenValue);
            prepStmt.setTimestamp(2,getCurrentDate());

            int nbInsert = prepStmt.executeUpdate();
            if(nbInsert == 1)
                inscritWorks = true;
            else
                System.out.println("2:ERROR:Token not inserted");

        } catch (SQLException e) {
            //If catch, because duplicate -> update the last use
            try {
                String selectStatement = "UPDATE token SET last_use=CURRENT_TIMESTAMP  WHERE valeur = ?";
                PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1,tokenValue);
                int nbUpdate = prepStmt.executeUpdate();
                if(nbUpdate == 1)
                    inscritWorks = true;
                else
                    System.out.println("3:ERROR:Token not updated");

            }catch (SQLException e2){
                System.out.println("1:ERROR:Token not inserted-duplicate");
            }
        }

        return inscritWorks;
    }

    private static Timestamp getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());
    }
}
