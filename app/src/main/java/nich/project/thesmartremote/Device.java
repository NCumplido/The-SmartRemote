package nich.project.thesmartremote;

public class Device {



    public Device(String name, int bearing) {
        this.name = name;
        this.bearing = bearing;
    }

    private String name;
    private int bearing;

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
