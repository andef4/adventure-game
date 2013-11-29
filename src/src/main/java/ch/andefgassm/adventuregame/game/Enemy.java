package ch.andefgassm.adventuregame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.IStat;

public class Enemy {
	
    class Drop {
		private String itemId;
		private float dropRate;
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public float getDropRate() {
			return dropRate;
		}
		public void setDropRate(float dropRate) {
			this.dropRate = dropRate;
		}
	}
	
    private String id = null;
	private String name = null;
	private int life = 0;
	private String aiClassName = null;
	private List<Drop> drops = new ArrayList<Drop>();
	private Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
	
	public Enemy(String id, String name, int life, String aiClassName) {
		this.id = id;
		this.name = name;
		this.life = life;
		this.aiClassName = aiClassName;
	}
	public List<Drop> getDrops() {
		return drops;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getLife() {
		return life;
	}
	public String getAiClassName() {
		return aiClassName;
	}
	public Map<IStat, Integer> getStats() {
		return stats;
	}
}
