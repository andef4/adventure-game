package ch.andefgassm.adventuregame.game.state;

import java.util.Map.Entry;

import ch.andefgassm.adventuregame.combat.IStat;
import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.Player;

public class InventoryMenuState extends AbstractConsoleGameState {

	private GameStateContext context = null;
	private Player player = null;

	public void init(GameStateContext context, String param) {
		this.context = context;
		this.player = context.getPlayer();

	    clear();
		println("Inventory Menu");
		println("=========");
		println("");
		println("Inventory (press button to equip item)");
		println("--------------------------------------");

		for(int i=1; i <= player.getInventory().size(); i++)
		{
			String itemId = player.getInventory().get(i-1);
			Item item = context.getItem(itemId);
			StringBuilder sb = new StringBuilder(i);
			sb.append(String.format("%s ) ", i));
			sb.append(String.format("[%s] ", isItemEquipped(itemId)));
			sb.append(item.getType());
			sb.append(" ");
			sb.append(item.getName());
			sb.append(" ");

			for (Entry<IStat, Integer> entry : item.getStats().entrySet()) {
				sb.append(String.format("[%s %s] ", entry.getKey(), entry.getValue()));
			}

			println(sb.toString());
		}

		println("--------------------------------------");
		println("0 ) Close inventory");

	}

	private String isItemEquipped(String itemId) {
		if (player.getEquipment().contains(itemId)) {
			return "X";
		} else {
			return "";
		}
	}

	@Override
	public void handleInput(int input) {
		if(input == 0){
			context.changeState(GameStateContext.MAP);
		}
		else if(input <= player.getInventory().size()){
			player.equip(player.getInventory().get(input-1));
			init(context, null);
		}
	}
}
