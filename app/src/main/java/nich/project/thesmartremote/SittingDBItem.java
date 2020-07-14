package nich.project.thesmartremote;
/*
Parts of code from:
Dr. Tom Owen: CSC306    TVYM
Swansea University
*/

import java.util.ArrayList;

    ///////////////////////////////////////// OBJECT CLASS FOR SQL DATABASE /////////////////////////////////////////
public class SittingDBItem {

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

}

