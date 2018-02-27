package pharmaceuticals.nl.peptrix.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Result2")
public class Result {

	@Id
	@GeneratedValue
	@Column(name = "esultid")
	private long resultid;

	@Column(name = "xperimentid")
	private long experimentid;

	@Column(name = "ampleid")
	private long sampleid;

	@Column(name = "roup_id")
	private long group_id;

	@Column(name = "filenumber")
	private long filenumber;

	@Column(name = "lcfraction")
	private long lcfraction;

	@Column(name = "Offset_LC_MS")
	private float offset_LC_MS;

	@Column(name = "retentiontime")
	private float retentiontime;

	@Column(name = "Quantilethreshold", length = 8)
	private String quantilethreshold;

	@Column(name = "Year", length = 4)
	private String year;

	@Column(name = "Size_KB")
	private float size_KB;

	@Column(name = "File", length = 200)
	private String file;

	@Column(name = "Type", length = 16)
	private String type;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Temporal(TemporalType.TIME)
	private Date time;

	@CreationTimestamp
	private Date created;

	@UpdateTimestamp
	private Date updated;

	public long getResultid() {
		return resultid;
	}

	public void setResultid(long resultid) {
		this.resultid = resultid;
	}

	public long getExperimentid() {
		return experimentid;
	}

	public void setExperimentid(long experimentid) {
		this.experimentid = experimentid;
	}

	public long getSampleid() {
		return sampleid;
	}

	public void setSampleid(long sampleid) {
		this.sampleid = sampleid;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getFilenumber() {
		return filenumber;
	}

	public void setFilenumber(long filenumber) {
		this.filenumber = filenumber;
	}

	public long getLcfraction() {
		return lcfraction;
	}

	public void setLcfraction(long lcfraction) {
		this.lcfraction = lcfraction;
	}

	public float getOffset_LC_MS() {
		return offset_LC_MS;
	}

	public void setOffset_LC_MS(float offset_LC_MS) {
		this.offset_LC_MS = offset_LC_MS;
	}

	public float getRetentiontime() {
		return retentiontime;
	}

	public void setRetentiontime(float retentiontime) {
		this.retentiontime = retentiontime;
	}

	public String getQuantilethreshold() {
		return quantilethreshold;
	}

	public void setQuantilethreshold(String quantilethreshold) {
		this.quantilethreshold = quantilethreshold;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public float getSize_KB() {
		return size_KB;
	}

	public void setSize_KB(float size_KB) {
		this.size_KB = size_KB;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
