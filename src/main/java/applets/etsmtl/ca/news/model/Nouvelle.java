package applets.etsmtl.ca.news.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Nouvelle implements Comparable{

    private String id;
    private String id_source;
    private String titre;
    private String message;
    private String link;
    private Date date;
    private String urlPicture;

    public Nouvelle() {
    }

    public String getId_source() {
        return id_source;
    }

    public void setId_source(String id_source) {
        this.id_source = id_source;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int compareTo(Nouvelle o) {
        return this.date.compareTo(o.getDate());
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((Nouvelle)o).getDate());
    }
}
