package ch.andefgassm.adventuregame.combat;

import java.util.Map.Entry;

public class ActiveEffect {
	private Effect effect = null;
	private int intervalsLeft = 0;
	private int baseLifeChange = 0;
	private Combatant caster;
	//private Combatant target;

	public ActiveEffect(Effect effect, Combatant caster, Combatant target) {
		this.effect = effect;
		this.intervalsLeft = effect.getIntervalsRunning();
		this.caster = caster;
		//this.target = target;
		calculateBaseLifeChange();
	}

	public Effect getEffect() {
		return effect;
	}

	public int getIntervalsLeft() {
		return intervalsLeft;
	}

	/**
	 * reduce interval by 1
	 * 
	 * @return true if no intervals are left
	 */
	public boolean decrementInterval() {
		intervalsLeft--;
		return intervalsLeft <= 0;
	}

	/**
	 * calculate base damage/heal based on caster stats
	 */
	private void calculateBaseLifeChange() {
		baseLifeChange = effect.getBaseLifeChange();
		for (Entry<IStat, Float> statScaling : effect.getStatScaling().entrySet()) {
			IStat stat = statScaling.getKey();
			Integer value = caster.getCurrentStats().get(stat);
			if (value != null) {
				baseLifeChange += statScaling.getValue() * value;
			}
		}
	}

	/**
	 * calculate effective life damage/heal based on the baseLifeChange value
	 * and resistances, armor and other effects on the target
	 * 
	 * @return
	 */
	public int calculateEffectiveLifeChange() {
		return baseLifeChange;
	}
}
