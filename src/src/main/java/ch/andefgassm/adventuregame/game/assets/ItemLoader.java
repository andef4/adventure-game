package ch.andefgassm.adventuregame.game.assets;

import java.io.File;
import java.io.IOException;
import ch.andefgassm.adventuregame.combat.IStat;
import ch.andefgassm.adventuregame.game.Stat;
import ch.andefgassm.adventuregame.game.inventory.Item;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ItemLoader {

    public static void main(String[] args) {
    	 
	ObjectMapper mapper = new ObjectMapper();
	
	SimpleModule module = new SimpleModule("stats", Version.unknownVersion());
	module.addAbstractTypeMapping(IStat.class, Stat.class);
	mapper.registerModule(module); // important, otherwise won't have any effect on mapper's configuration

 
	try {
 
		// read from file, convert it to user class
		Item item = mapper.readValue(new File("assets/data/items/item.json"), Item.class);
 
		// display to console
		System.out.println(item);
 
	} catch (JsonGenerationException e) {
 
		e.printStackTrace();
 
	} catch (JsonMappingException e) {
 
		e.printStackTrace();
 
	} catch (IOException e) {
 
		e.printStackTrace();
 
	}
 
  }

}
