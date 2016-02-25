package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class Musique {

    private int id;
    private String nom;
    private String lien;
    private int nbVote;
    private int dejaJoue; //0=pas élue, 1=élue et joué prochainement, 2=élue et déjà joué
    private boolean votePourElle; //0=pas voté pour elle (ou pas voté du tout), 1=voté pour elle. Basé sur l'IP

    public Musique() { }

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

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public int getNbVote() {
        return nbVote;
    }

    public void setNbVote(int nb_vote) {
        this.nbVote = nb_vote;
    }

    public int getDejaJoue() {
        return dejaJoue;
    }

    public void setDejaJoue(int deja_joue) {
        this.dejaJoue = deja_joue;
    }

    public boolean isVotePourElle() {
        return votePourElle;
    }

    public void setVotePourElle(boolean votePourElle) {
        this.votePourElle = votePourElle;
    }
}
