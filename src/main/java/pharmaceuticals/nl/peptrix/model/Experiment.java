package pharmaceuticals.nl.peptrix.model;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Experiment")
public class Experiment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experimentid")
    private long experimentid;
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipmentid")
    private Equipment equipment;
    @Column(name = "Name", length = 200)
    String name;
    @Column(name = "Date")
    Date date;
    @Column(name = "Year", length = 4)
    String year;
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;

    public long getExperimentid() {
        return experimentid;
    }

    public void setExperimentid(long experimentid) {
        this.experimentid = experimentid;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
