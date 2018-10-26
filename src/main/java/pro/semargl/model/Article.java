package pro.semargl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "article_sequence_generator")
    @SequenceGenerator(name = "article_sequence_generator"
            , sequenceName = "article_sequence"
            , allocationSize = 1)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private long id;
    @Column(name = "title", nullable = false, length = 200)
    @JacksonXmlProperty(localName = "Title")
    private String title;
    @Column(name = "description", nullable = false, length = 400)
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measurement_unit")
    @JacksonXmlProperty(localName = "UnitOfMeasurement")
    private MeasurementUnit measurementUnit;
    @Column(name = "weight", nullable = false)
    @JacksonXmlProperty(localName = "Weight")
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

    public long getId() {
        return id;
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

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        return getTitle().equals(article.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode();
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
