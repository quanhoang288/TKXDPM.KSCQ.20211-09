package eco.bike.entity;

public class Bike {
    private int id;
    private String type;
    private String licensePlate;
    private String batteryPercent;
    private String value;
    private int dockId;

    public Bike(int id, String type, String licensePlate, String value, int dockId) {
        this.id = id;
        this.type = type;
        this.licensePlate = licensePlate;
        this.value = value;
        this.dockId = dockId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(String batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDockId() {
        return dockId;
    }

    public void setDockId(int dockId) {
        this.dockId = dockId;
    }
}
