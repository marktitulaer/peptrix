package pharmaceuticals.nl.peptrix.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Systemcode")
public class Systemcode implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "systemcodeid")
	long systemcodeid;

	@Column(name = "Code", length = 16, unique = true)
	String code;

	@Column(name = "Description", length = 100)
	String description;

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

	public long getSystemcodeid() {
		return systemcodeid;
	}

	public void setSystemcodeid(long systemcodeid) {
		this.systemcodeid = systemcodeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
