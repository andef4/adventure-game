package ch.andefgassm.adventuregame.combat;

public class CombatEvent {

    private Combatant caster = null;
    private Combatant target = null;
    private String skillName = null;
    private int healOrDamage = 0;


    public CombatEvent(Combatant caster, Combatant target, String skillName, int healOrDamage) {
        this.caster = caster;
        this.target = target;
        this.skillName = skillName;
        this.healOrDamage = healOrDamage;
    }

    public Combatant getCaster() {
        return caster;
    }

    public Combatant getTarget() {
        return target;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getHealOrDamage() {
        return healOrDamage;
    }
}
