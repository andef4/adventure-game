package ch.andefgassm.adventuregame.combat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combatant {
	
    private Map<String, Integer> stats = new HashMap<String, Integer>();
    private Map<IResource, Integer> resources = new HashMap<IResource, Integer>();
	private Map<String, Skill> skills = new HashMap<String, Skill>();
	
	
	
	public List<String> getAvailableSkills() {
		return null;
	}
	

}
