package pharmaceuticals.nl.peptrix.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "PERSONS")
public class Person {
	@Id
	@GeneratedValue
	@Column(name = "Personid")
	private Long id;

	@Column(name = "Name", length = 200)
	private String name;
	
	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

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