package old;

/**
 * 
 * @author andef4
 */
public interface ICombatant {
    // defensive stats
    /**
     * @return change to fully avoid an attack
     */
    public abstract int getDodgePercent();
    
    /**
     * @return reduced magic damage in percent
     */
    public abstract int getMagicResistancePercent();
    
    /**
     * @return chance to block in percent
     */
    public abstract int getBlockPercent();
    
    /**
     * @return how much damage is adverted on a successful block
     */
    public abstract int getBlockValue();
    
    /**
     * @return the life of the combatant
     */
    public abstract int getLife();
    
    // offensive stats
    /**
     * @return spellpower increases magic damage done
     */
    public abstract int getSpellpower();
    
    /**
     * @return strength increases the physical damage done
     */
    public abstract int getStrength();
    
    /**
     * @return chance to make a critical hit = 2x the base damage
     */
    public abstract int getCritPercent();
}
