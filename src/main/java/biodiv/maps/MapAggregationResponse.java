package biodiv.maps;

import java.util.HashMap;
import java.util.List;

public class MapAggregationResponse {

	private HashMap<Object, Long> groupSpeciesName;
	private HashMap<Object, Long> groupStatus;
	private HashMap<Object, Long> groupTaxonIDExists;
	private HashMap<Object, Long> groupUserGroupName;
	private HashMap<Object, Long> groupIdentificationNameExists;
	private HashMap<Object, Long> groupFlag;
	private HashMap<Object, Long> groupValidate;
	private Long groupAudio;
	private Long groupVideo;
	private Long groupImages;
	private Long groupNoMedia;
	private HashMap<Object, Long> groupMonth;
	private MapTraitsAggregation traits;

	public HashMap<Object, Long> getGroupSpeciesName() {
		return groupSpeciesName;
	}

	public void setGroupSpeciesName(HashMap<Object, Long> groupSpeciesName) {
		this.groupSpeciesName = groupSpeciesName;
	}

	public HashMap<Object, Long> getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(HashMap<Object, Long> groupStatus) {
		this.groupStatus = groupStatus;
	}

	public HashMap<Object, Long> getGroupTaxonIDExists() {
		return groupTaxonIDExists;
	}

	public void setGroupTaxonIDExists(HashMap<Object, Long> groupTaxonIDExists) {
		this.groupTaxonIDExists = groupTaxonIDExists;
	}

	public HashMap<Object, Long> getGroupUserGroupName() {
		return groupUserGroupName;
	}

	public void setGroupUserGroupName(HashMap<Object, Long> groupUserGroupName) {
		this.groupUserGroupName = groupUserGroupName;
	}

	public HashMap<Object, Long> getGroupIdentificationNameExists() {
		return groupIdentificationNameExists;
	}

	public void setGroupIdentificationNameExists(HashMap<Object, Long> groupIdentificationNameExists) {
		this.groupIdentificationNameExists = groupIdentificationNameExists;
	}

	public HashMap<Object, Long> getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(HashMap<Object, Long> groupFlag) {
		this.groupFlag = groupFlag;
	}

	public HashMap<Object, Long> getGroupValidate() {
		return groupValidate;
	}

	public void setGroupValidate(HashMap<Object, Long> groupValidate) {
		this.groupValidate = groupValidate;
	}

	public Long getGroupAudio() {
		return groupAudio;
	}

	public void setGroupAudio(Long groupAudio) {
		this.groupAudio = groupAudio;
	}

	public Long getGroupVideo() {
		return groupVideo;
	}

	public void setGroupVideo(Long groupVideo) {
		this.groupVideo = groupVideo;
	}

	public Long getGroupImages() {
		return groupImages;
	}

	public void setGroupImages(Long groupImages) {
		this.groupImages = groupImages;
	}

	public Long getGroupNoMedia() {
		return groupNoMedia;
	}

	public void setGroupNoMedia(Long groupNoMedia) {
		this.groupNoMedia = groupNoMedia;
	}

	public HashMap<Object, Long> getGroupMonth() {
		return groupMonth;
	}

	public void setGroupMonth(HashMap<Object, Long> groupMonth) {
		this.groupMonth = groupMonth;
	}

	public MapTraitsAggregation getTraits() {
		return traits;
	}

	public void setTraits(MapTraitsAggregation traits) {
		this.traits = traits;
	}

}
