package pro.semargl.model;

public class MeasurementUnit {
    private long id;
    private String name;

    public MeasurementUnit() {
    }

    public MeasurementUnit(String name) {
        this();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
