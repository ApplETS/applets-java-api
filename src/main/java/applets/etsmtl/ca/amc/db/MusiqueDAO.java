package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Musique;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class MusiqueDAO extends DAO<Musique> {
    @Override
    public Musique find(String key) {
        Musique musique = new Musique();
        try {
            String selectStatement = "SELECT * FROM musique WHERE id_musique = ? ";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,Integer.parseInt(key));
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                musique.setId(result.getInt("id_musique"));
                musique.setTitre(result.getString("titre"));
                musique.setAuteur(result.getString("auteur"));
                musique.setImage(result.getString("image"));
                musique.setLien(result.getString("lien"));
                musique.setNbVote(result.getInt("nombre_vote"));
                musique.setDejaJoue(result.getInt("deja_joue"));
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musique;
    }

    @Override
    public List<Musique> findAll() {
        ArrayList<Musique> alMusique = new ArrayList<Musique>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM musique ORDER BY titre"
                    );
            while (result.next()) {
                Musique musique = new Musique();
                musique.setId(result.getInt("id_musique"));
                musique.setTitre(result.getString("titre"));
                musique.setAuteur(result.getString("auteur"));
                musique.setImage(result.getString("image"));
                musique.setLien(result.getString("lien"));
                musique.setNbVote(result.getInt("nombre_vote"));
                musique.setDejaJoue(result.getInt("deja_joue"));

                alMusique.add(musique);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alMusique;
    }

    /**
     * Return the musique Based on the column "deja_joue"
     * @param type
     *  0 = not elected, still on vote process
     *  1 = elected, and will be play soon
     *  2 = elected and already played
     * @return list of musique
     */
    public List<Musique> findAllFromType(int type) {
        ArrayList<Musique> alMusique = new ArrayList<Musique>();

        try {
            String selectStatement = "SELECT * FROM musique " +
                                        "WHERE deja_joue =  ? " +
                                        "ORDER BY titre";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,type);
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                Musique musique = new Musique();
                musique.setId(result.getInt("id_musique"));
                musique.setTitre(result.getString("titre"));
                musique.setAuteur(result.getString("auteur"));
                musique.setImage(result.getString("image"));
                musique.setLien(result.getString("lien"));
                musique.setNbVote(result.getInt("nombre_vote"));
                musique.setDejaJoue(result.getInt("deja_joue"));

                alMusique.add(musique);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alMusique;
    }

    /**
     * Return the musique Based on the column "deja_joue"
     * @param type
     *  0 = not elected, still on vote process
     *  1 = elected, and will be play soon
     *  2 = elected and already played
     * @param adresseIP
     * @return list of musique
     */
    public List<Musique> findAllFromType(int type, String adresseIP) {
        ArrayList<Musique> alMusique = new ArrayList<Musique>();

        try {
            String selectStatement = "SELECT * FROM musique " +
                    "WHERE deja_joue =  ? " +
                    "ORDER BY titre";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,type);
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                Musique musique = new Musique();
                musique.setId(result.getInt("id_musique"));
                musique.setTitre(result.getString("titre"));
                musique.setAuteur(result.getString("auteur"));
                musique.setImage(result.getString("image"));
                musique.setLien(result.getString("lien"));
                musique.setNbVote(result.getInt("nombre_vote"));
                musique.setDejaJoue(result.getInt("deja_joue"));

                musique.setVotePourElle(hasVotedFor(result.getInt("id_musique"), adresseIP));

                alMusique.add(musique);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alMusique;
    }

    private boolean hasVotedFor(int idMusique, String adresseIP) {
        boolean votedFor = false;

        try {
            String selectStatement = "SELECT * FROM musique_vote " +
                                        "WHERE id_musique =  ? " +
                                        "AND adresse_ip =  ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,idMusique);
            prepStmt.setString(2,adresseIP);
            ResultSet result = prepStmt.executeQuery();
            if(result.first()) {
                votedFor = true;
                System.out.println("Has voted for id: "+idMusique);
            } else
                System.out.println("Has NOT voted for id: "+idMusique);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votedFor;
    }

    public boolean addVoteforSong(int idMusique, String adresseIP) {
        boolean votedWorks = false;
        int nbVote = 0; //Can't vote for more than 5 songs. Reset each hour

        boolean alreadyVoted = hasVotedFor(idMusique, adresseIP);

        try {
            String selectStatement = "SELECT COUNT(*) as nbVote FROM musique_vote " +
                                        "WHERE adresse_ip = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,adresseIP);
            ResultSet result = prepStmt.executeQuery();
            if(result.first()) {
                nbVote = result.getInt("nbVote");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(nbVote < 5 && !alreadyVoted) {
            try {
                String selectStatement = "INSERT INTO musique_vote (id_musique, adresse_ip) VALUES (?,?)";
                PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                prepStmt.setInt(1,idMusique);
                prepStmt.setString(2,adresseIP);

                int nbInsert = prepStmt.executeUpdate();
                if(nbInsert == 1) {
                    System.out.println("1:Musique_vote inserted");

                    selectStatement = "UPDATE musique SET nombre_vote=nombre_vote+1 WHERE id_musique =?";
                    prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    prepStmt.setInt(1,idMusique);

                    int nbUpdate = prepStmt.executeUpdate();
                    if(nbUpdate == 1) {
                        System.out.println("2:Musique count updated");
                        votedWorks = true;
                    } else {
                        System.out.println("2:ERROR:Musique count not updated");

                        selectStatement = "DELETE FROM musique_vote WHERE id_musique=? AND  adresse_ip=?";
                        prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        prepStmt.setInt(1,idMusique);
                        prepStmt.setString(2,adresseIP);
                        prepStmt.executeUpdate();
                    }
                } else
                    System.out.println("1:ERROR:Musique_vote not inserted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return votedWorks;
    }
}
