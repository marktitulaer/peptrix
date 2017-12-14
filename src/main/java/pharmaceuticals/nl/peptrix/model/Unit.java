package pharmaceuticals.nl.peptrix.model;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Unit2")
public class Unit {

	@Id
	@GeneratedValue
	@Column(name = "nitid")
	private long unitid;

	@Column(name = "Type", length = 16)
	private String type;

	@Column(name = "UnitValue", length = 16)
	private String unitvalue;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getUnitid() {
		return unitid;
	}

	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnitvalue() {
		return unitvalue;
	}

	public void setUnitvalue(String unitvalue) {
		this.unitvalue = unitvalue;
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
