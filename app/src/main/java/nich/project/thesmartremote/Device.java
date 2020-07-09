package nich.project.thesmartremote;

/*
Parts of code from:
Dr. Tom Owen: CSC306    TVYM
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

    public Device() {
    }

    public Device(String name, int bearing) {
        this.name = name;
        this.bearing = bearing;
    }

    public int getDevice_ID() { return device_ID; }

    public void setDevice_ID(int device_ID) { this.device_ID = device_ID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getBearing() { return bearing; }

    public void setBearing(int bearing) { this.bearing = bearing; }
}
