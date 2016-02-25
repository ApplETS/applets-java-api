package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class Equipe {

    private int id;
    private ArrayList<Participant> participants;
    private String nom;
    private String description;
    private String lienVideo;
    private String prix; //Nom du prix remporté s'il y a lieu

    public Equipe() {
        participants = new ArrayList<Participant>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLienVideo() {
        return lienVideo;
    }

    public void setLienVideo(String lienVideo) {
        this.lienVideo = lienVideo;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
