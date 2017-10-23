package biodiv.observation;
// Generated 31 Jul, 2017 7:18:53 AM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Annotation generated by hbm2java
 */
@Entity
@Table(name = "annotation", schema = "public")
public class Annotation implements java.io.Serializable {

	private long id;
	private long version;
	private Observation observation;
    //order of annotation in source required for checklist
	private int columnOrder;
	private String key;
    //source of annotation in case of checklist source type will be Checklist.class 
	private String sourceType;
	//TODO: value can be text
	private String value;

	public Annotation() {
	}

	public Annotation(long id, Observation observation, int columnOrder, String key) {
		this.id = id;
		this.observation = observation;
		this.columnOrder = columnOrder;
		this.key = key;
	}

	public Annotation(long id, Observation observation, int columnOrder, String key, String sourceType, String value) {
		this.id = id;
		this.observation = observation;
		this.columnOrder = columnOrder;
		this.key = key;
		this.sourceType = sourceType;
		this.value = value;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Version
	@Column(name = "version", nullable = false)
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "observation_id", nullable = false)
	public Observation getObservation() {
		return this.observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}

	@Column(name = "column_order", nullable = false)
	public int getColumnOrder() {
		return this.columnOrder;
	}

	public void setColumnOrder(int columnOrder) {
		this.columnOrder = columnOrder;
	}

	@Column(name = "key", nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "source_type")
	public String getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "value", columnDefinition="text")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Annotation [id=" + id + ", key=" + key + ", value=" + value + "]";
	}

}