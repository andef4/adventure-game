package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combatant {
	
	
	
	private Map<String, Integer> stats = new HashMap<String, Integer>();
	
	private Map<IResource, Integer> resources = new HashMap<IResource, Integer>();
	private List<Skill> skills = new ArrayList<Skill>();
	
	
	
	public List<Skill> getAvailableSkills() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
