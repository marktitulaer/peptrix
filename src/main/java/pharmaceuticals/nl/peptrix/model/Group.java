package pharmaceuticals.nl.peptrix.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Group2_")
public class Group {

	@Id
	@GeneratedValue
	@Column(name = "roup_id")
	private long groupid;

	@Column(name = "Group_code", length = 100, unique = true)
	String groupcode;

	@Column(name = "Name", length = 200)
	String name;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getGroupid() {
		return groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	public String getGroupcode() {
		return groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
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

}
