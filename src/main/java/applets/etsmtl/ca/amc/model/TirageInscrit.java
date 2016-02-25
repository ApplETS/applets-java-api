package applets.etsmtl.ca.amc.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by valentin-debris on 2016-02-10.
 */

@XmlRootElement
public class TirageInscrit {

    private int id;
    private String nom;
    private Participant participant;
    //private ArrayList<TiragePrix> prix; //liste des prix gagnés

    public TirageInscrit() { //prix = new ArrayList<TiragePrix>();
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

//    public ArrayList<TiragePrix> getPrix() {
//        return prix;
//    }
//
//    public void setPrix(ArrayList<TiragePrix> prix) {
//        this.prix = prix;
//    }
}
