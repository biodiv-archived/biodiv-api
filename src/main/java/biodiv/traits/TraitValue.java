package biodiv.traits;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import biodiv.common.CommonMethod;

/**
 * TraitValue generated by hbm2java
 */
@Entity
@Table(name = "trait_value", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "value",
		"trait_id" }))
public class TraitValue extends CommonMethod implements java.io.Serializable {

	private long id;
	private long version;
	private long traitId;
	private String description;
	private String icon;
	private String source;
	private String value;
	private boolean isDeleted;
	//private Set facts = new HashSet(0);

	public TraitValue() {
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "icon")
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "source", nullable = false)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "value", nullable = false)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	@Column(name = "trait_id", nullable = false)
	public long getTraitId() {
		return traitId;
	}

	public void setTraitId(long traitId) {
		this.traitId = traitId;
	}
	@Column(name = "is_deleted", nullable = false)
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "traitValue")
//	public Set getFacts() {
//		return this.facts;
//	}
//
//	public void setFacts(Set facts) {
//		this.facts = facts;
//	}

}
