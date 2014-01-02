package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A skill usable by players an enemies
 * @author andef4
 */
public class Skill {
	private String id = null;
	private String name = null;
	private String icon = null;
	private boolean harmful;
	private Map<IResource, Integer> requiredResources = new HashMap<IResource, Integer>();
	private List<Effect> targetEffects = new ArrayList<Effect>();
	private List<Effect> casterEffects = new ArrayList<Effect>();
	private String description;

	@JsonCreator
	public Skill(@JsonProperty("id") String id,
			@JsonProperty("name") String name,
			@JsonProperty("harmful") boolean harmful) {
		this.id = id;
		this.name = name;
		this.harmful= harmful;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isHarmful() {
		return harmful;
	}
	public Map<IResource, Integer> getRequiredResources() {
		return requiredResources;
	}
	public List<Effect> getTargetEffects() {
		return targetEffects;
	}
	public List<Effect> getCasterEffects() {
		return casterEffects;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
