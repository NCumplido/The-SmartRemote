package nich.project.thesmartremote;

/*
Some code from:

Dr. Tom Owen: CSC306
Swansea University
 */

public class Device {

    // Labels table name
    public static final String TABLE = "Device";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_bearing = "bearing";

    // property help us to keep data
    public int device_ID;
    public String name;
    public int bearing;

    public Device(String name, int bearing) {
        this.name = name;
        this.bearing = bearing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }

}
