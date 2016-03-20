package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class BooleanVar {

    private boolean valueBool;
    private String explication;

    public BooleanVar() { }

    public String getExplication() {
        return explication;
    }

    public void setExplication(String explication) {
        this.explication = explication;
    }

    public BooleanVar(boolean boolVar) {
        this.valueBool = boolVar;
    }

    public boolean isValueBool() {
        return valueBool;
    }

    public void setValueBool(boolean valueBool) {
        this.valueBool = valueBool;
    }
}
