package nich.project.thesmartremote;

public class Device {

    private String name;
    private int bearing;

    public Device(String name, int bearing) {
        this.name = name;
        this.bearing = bearing;
    }

    public String getName() {
        return name;
    }

    public void setName(String m_name) {
        this.name = m_name;
    }

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int m_bearing) {
        this.bearing = m_bearing;
    }
}


