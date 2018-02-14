package pharmaceuticals.nl.peptrix.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Equipment")
public class Equipment  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "equipmentid")
	private long equipmentid;

	@Column(name = "Name", length = 200)
	private String name;

	@Column(name = "Code", length = 16, unique = true)
	private String code;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(long equipmentid) {
		this.equipmentid = equipmentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Equipment2 [id=" + equipmentid + ", name=" + name + ", code=" + code + "]";
	}

}
