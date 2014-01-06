package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CombatSystem {

    private Map<String, Skill> skills = new HashMap<String, Skill>();
    private List<IStatProcessor> statProcessors = new ArrayList<IStatProcessor>();
    private Map<String, ISpellModifier> spellModifiers = new HashMap<String, ISpellModifier>();

    public void registerSkill(String name, Skill skill) {
        skills.put(name, skill);
    }

    public Skill getSkill(String skill) {
        return skills.get(skill);
    }

    public List<IStatProcessor> getStatProcessors() {
        return statProcessors;
    }


    public Map<String, ISpellModifier> getSpellModifiers() {
        return spellModifiers;
    }
}
