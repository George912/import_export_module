package pro.semargl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "measurement_unit")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MeasurementUnit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "measurement_unit_sequence_generator")
    @SequenceGenerator(name = "measurement_unit_sequence_generator"
            , sequenceName = "measurement_unit_sequence"
            , allocationSize = 1)
    @JsonIgnore
    private Long id;
    @Column(name = "name", nullable = false, length = 20)
    @JacksonXmlText
    private String name;
    @OneToMany(mappedBy = "measurementUnit", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Article> articleSet = new HashSet<>();

    public MeasurementUnit() {
    }

    public MeasurementUnit(String name) {
        this();
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(Set<Article> articleSet) {
        this.articleSet = articleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementUnit)) return false;

        MeasurementUnit that = (MeasurementUnit) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", articleSetCount='" + articleSet.size() + '\'' +
                '}';
    }
}
