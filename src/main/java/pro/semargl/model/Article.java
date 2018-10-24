package pro.semargl.model;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @Column(name = "description", nullable = false, length = 400)
    private String description;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;
    @Column(name = "weight", nullable = false)
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
