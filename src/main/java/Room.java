import java.io.Serializable;

public class Room implements Serializable {
    private int id;
    private String name;
    private String country;
    private boolean lightSwitched;

    public Room(int id, String name, String country, boolean lightSwitched) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lightSwitched = lightSwitched;
    }

    public Room(String name, String country) {
        this.name = name;
        this.country = country;
        this.lightSwitched = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isLightSwitched() {
        return lightSwitched;
    }

    public void setLightSwitched(boolean lightSwitched) {
        this.lightSwitched = lightSwitched;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lightSwitched=" + lightSwitched +
                '}';
    }
}
