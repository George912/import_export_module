package pro.semargl.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "measurement_unit")
public class MeasurementUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @OneToMany(mappedBy = "measurementUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Article> articleSet = new HashSet<>();

    public MeasurementUnit() {
    }

    public MeasurementUnit(String name) {
        this();
        this.name = name;
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
    public String toString() {
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", articleSetCount='" + articleSet.size() + '\'' +
                '}';
    }
}
