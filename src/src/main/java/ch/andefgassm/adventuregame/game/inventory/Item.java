package ch.andefgassm.adventuregame.game.inventory;

import java.util.HashMap;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.IStat;

public class Item {
    private Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
    private ItemType type = null;
    private String name = null;
    private String id = null;
    private String icon = null;

    public int getStat(IStat stat) {
        Integer value = stats.get(stat);
        if (value == null) {
            value = 0;
        }
        return value;
    }

    public ItemType getType() {
        return type;
    }
    public void setType(ItemType type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<IStat, Integer> getStats() {
        return stats;
    }
    public void setStats(Map<IStat, Integer> stats) {
        this.stats = stats;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
