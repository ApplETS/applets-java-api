package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class GlobalData {

    private int id;
    private String eventbriteID;
    private String ionicID;
    private String ionicBearer;
    private String ionicProfile;

    public GlobalData() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventbriteID() {
        return eventbriteID;
    }

    public void setEventbriteID(String eventbriteID) {
        this.eventbriteID = eventbriteID;
    }

    public String getIonicID() {
        return ionicID;
    }

    public void setIonicID(String ionicID) {
        this.ionicID = ionicID;
    }

    public String getIonicBearer() {
        return ionicBearer;
    }

    public void setIonicBearer(String ionicBearer) {
        this.ionicBearer = ionicBearer;
    }

    public String getIonicProfile() {
        return ionicProfile;
    }

    public void setIonicProfile(String ionicProfile) {
        this.ionicProfile = ionicProfile;
    }
}
