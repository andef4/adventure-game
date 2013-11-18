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
	private String name = null;
	private boolean harmful;
	private Map<IResource, Integer> requiredResources = new HashMap<IResource, Integer>();
	private List<Effect> targetEffects = new ArrayList<Effect>();
	private List<Effect> casterEffects = new ArrayList<Effect>();
	
	
	@JsonCreator
	public Skill(@JsonProperty("name") String name, @JsonProperty("harmful") boolean harmful) {
		this.name = name;
		this.harmful= harmful;
	}
	public String getName() {
		return name;
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
}
