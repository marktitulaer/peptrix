package pharmaceuticals.nl.peptrix.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Sample")
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sampleid")
    private long sampleid;
    @Column(name = "Sample_code", length = 100, unique = true)
    private String samplecode;
    @Column(name = "Name", length = 200)
    private String name;
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;

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

    public long getSampleid() {
        return sampleid;
    }

    public void setSampleid(long sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
