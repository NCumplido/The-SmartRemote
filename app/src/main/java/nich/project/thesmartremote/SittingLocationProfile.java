package nich.project.thesmartremote;

/*
Parts of code from:
Dr. Tom Owen: CSC306    TVYM
Swansea University
*/
import java.util.ArrayList;

///////////////////////////////////////// OBJECT CLASS FOR SQL DATABASE /////////////////////////////////////////
public class SittingLocationProfile {

    // Labels table name
    public static final String TABLE = "SittingLocation";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_devices = "devices";
    public static final String KEY_bearings = "bearings";

    // property help us to keep data
    public int device_ID;
    public String name;
    public String devices;
    public int bearings;
    public ArrayList<Device> listDevices;

    public SittingLocationProfile() {
    }

    public SittingLocationProfile(String name, String devices, int bearings, ArrayList<Device> listDevices) {
        this.name = name;
        this.devices = devices;
        this.bearings = bearings;
        this.listDevices = listDevices;
    }

    public int getDevice_ID() {
        return device_ID;
    }

    public void setDevice_ID(int device_ID) {
        this.device_ID = device_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public int getBearings() {
        return bearings;
    }

    public void setBearings(int bearings) {
        this.bearings = bearings;
    }

    public ArrayList<Device> getListDevices() {
        return listDevices;
    }

    public void setListDevices(ArrayList<Device> listDevices) {
        this.listDevices = listDevices;
    }

}
