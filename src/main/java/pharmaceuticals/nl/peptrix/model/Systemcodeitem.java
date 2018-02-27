package pharmaceuticals.nl.peptrix.model;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "SystemCodeItem2")
public class Systemcodeitem {

	@Id
	@GeneratedValue
	@Column(name = "ystemCodeItemid")
	long systemcodeitemid;

	@Column(name = "ystemcodeid")
	long systemcodeid;

	@Column(name = "ItemCode", length = 16, unique = true)
	String itemcode;

	@Column(name = "Description", length = 50)
	String description;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getSystemcodeitemid() {
		return systemcodeitemid;
	}

	public void setSystemcodeitemid(long systemcodeitemid) {
		this.systemcodeitemid = systemcodeitemid;
	}

	public long getSystemcodeid() {
		return systemcodeid;
	}

	public void setSystemcodeid(long systemcodeid) {
		this.systemcodeid = systemcodeid;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
