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

	@ManyToOne(optional = true)
	@JoinColumn(name = "systemcodeitemid")
	private Systemcodeitem systemcodeitem;

	@ManyToOne(optional = true)
	@JoinColumn(name = "unitid")
	private Unit unit;

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

	public Systemcodeitem getSystemcodeitem() {
		return systemcodeitem;
	}

	public void setSystemcodeitem(Systemcodeitem systemcodeitem) {
		this.systemcodeitem = systemcodeitem;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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
