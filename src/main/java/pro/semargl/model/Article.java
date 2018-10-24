package pro.semargl.model;

public class Article {
    private long id;
    private String title;
    private String description;
    private MeasurementUnit measurementUnit;
    private double weight;

    public Article() {
    }

    public Article(String title, String description, MeasurementUnit measurementUnit, double weight) {
        this();
        this.title = title;
        this.description = description;
        this.measurementUnit = measurementUnit;
        this.weight = weight;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", measurementUnit=" + measurementUnit +
                ", weight=" + weight +
                '}';
    }
}
