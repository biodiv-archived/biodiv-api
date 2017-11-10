package biodiv.resource;
// Generated 31 Jul, 2017 7:18:53 AM by Hibernate Tools 3.5.0.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import biodiv.common.Language;
import biodiv.common.License;
import biodiv.user.User;

/**
 * Resource generated by hbm2java
 */
@Entity
@Table(name = "resource", schema = "public")
public class Resource implements java.io.Serializable {

	private long id;
	private long version;
	private Language language;
	private License license;
	private User user;
	private String description;
	private String fileName;
	private String mimeType;
	private String type;
	private String url;
	private Integer rating;
	private Date uploadTime;
	private String context;
	private String accessRights;
	private String annotations;
	private Long gbifid;
	
	public Resource() {
	}

	public Resource(long id, Language language, License license, String fileName, String type) {
		this.id = id;
		this.language = language;
		this.license = license;
		this.fileName = fileName;
		this.type = type;
	}

	public Resource(long id, Language language, License license, User user, String description, String fileName,
			String mimeType, String type, String url, Integer rating, Date uploadTime, String context,
			String accessRights, String annotations, Long gbifid) {
		this.id = id;
		this.language = language;
		this.license = license;
		this.user = user;
		this.description = description;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.type = type;
		this.url = url;
		this.rating = rating;
		this.uploadTime = uploadTime;
		this.context = context;
		this.accessRights = accessRights;
		this.annotations = annotations;
		this.gbifid = gbifid;
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
	@JoinColumn(name = "language_id", nullable = false)
	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "license_id", nullable = false)
	public License getLicense() {
		return this.license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploader_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "file_name", nullable = false)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "mime_type")
	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "rating")
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "upload_time", length = 29)
	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "context")
	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Column(name = "access_rights", length = 2055)
	public String getAccessRights() {
		return this.accessRights;
	}

	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}

	@Column(name = "annotations")
	public String getAnnotations() {
		return this.annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	@Column(name = "gbifid")
	public Long getGbifid() {
		return this.gbifid;
	}

	public void setGbifid(Long gbifid) {
		this.gbifid = gbifid;
	}
	
	
}
