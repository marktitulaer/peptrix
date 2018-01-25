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
@Table(name = "Experiment2")
public class Experiment {

	@Id
	@GeneratedValue
	@Column(name = "experimentid")
	private long experimentid;

	@Column(name = "quipmentid")
	private long equipmentid;

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
