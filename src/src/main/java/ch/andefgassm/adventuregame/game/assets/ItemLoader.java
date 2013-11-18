package ch.andefgassm.adventuregame.game.assets;

import java.io.File;
import java.io.IOException;

import ch.andefgassm.adventuregame.game.inventory.Item;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemLoader {

    public static void main(String[] args) {
    	 
	ObjectMapper mapper = AssetLoader.getObjectMapper();
 
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
