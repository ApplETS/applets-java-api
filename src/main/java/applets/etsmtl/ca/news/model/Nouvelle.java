package applets.etsmtl.ca.news.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by gnut3ll4 on 22/01/16.
 */
@XmlRootElement
public class Nouvelle {

    private String id;
    private String titre;
    private String message;
    private String link;
    private Date date;
    private String urlPicture;

    public Nouvelle() {
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
}
