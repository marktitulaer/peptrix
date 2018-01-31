package pharmaceuticals.nl.peptrix.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ItemValue2")
public class ItemValue {

	@Id
	@GeneratedValue
	@Column(name = "itemValueid")
	private long itemvalueid;

	@Column(name = "systemCodeItemid")
	private long systemcodeitemid;

	@Column(name = "initid")
	private long unitid;

	@Column(name = "Itemvalue", length = 200)
	String itemvalue;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getItemvalueid() {
		return itemvalueid;
	}

	public void setItemvalueid(long itemvalueid) {
		this.itemvalueid = itemvalueid;
	}

	public long getSystemcodeitemid() {
		return systemcodeitemid;
	}

	public void setSystemcodeitemid(long systemcodeitemid) {
		this.systemcodeitemid = systemcodeitemid;
	}

	public long getUnitid() {
		return unitid;
	}

	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}

	public String getItemvalue() {
		return itemvalue;
	}

	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
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
