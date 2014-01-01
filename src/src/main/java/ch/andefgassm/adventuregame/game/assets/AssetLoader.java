package ch.andefgassm.adventuregame.game.assets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.andefgassm.adventuregame.combat.IDamageType;
import ch.andefgassm.adventuregame.combat.IResource;
import ch.andefgassm.adventuregame.combat.IStat;
import ch.andefgassm.adventuregame.game.DamageType;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.Stat;
import ch.andefgassm.adventuregame.game.inventory.ItemType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class AssetLoader {

	private ObjectMapper objectMapper = null;

	private AssetLoader() {
		objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(IResource.class, Resource.class);
        module.addAbstractTypeMapping(IDamageType.class, DamageType.class);
        module.addAbstractTypeMapping(IStat.class, Stat.class);
        module.addKeyDeserializer(IResource.class, new KeyDeserializer() {
			@Override
			public Object deserializeKey(String key, DeserializationContext context) throws IOException, JsonProcessingException {
				for (Resource resource : Resource.values()) {
					if (key.toLowerCase().equals(resource.name().toLowerCase())) {
						return resource;
					}
				}
				throw new IllegalArgumentException(String.format("Unknown Resource key %s", key));
			}
        });
        module.addKeyDeserializer(IStat.class, new KeyDeserializer() {
			@Override
			public Object deserializeKey(String key, DeserializationContext context) throws IOException, JsonProcessingException {
				for (Stat stat : Stat.values()) {
					if (key.toLowerCase().equals(stat.name().toLowerCase()) ) {
						return stat;
					}
				}
				throw new IllegalArgumentException(String.format("Unknown Stat key %s", key));
			}
        });
        module.addDeserializer(ItemType.class, new JsonDeserializer<ItemType>() {
			@Override
			public ItemType deserialize(JsonParser parser, DeserializationContext context)
					throws IOException, JsonProcessingException {
				String itemType = parser.getText();
				for (ItemType type : ItemType.values()) {
					if (itemType.toLowerCase().equals(type.name().toLowerCase()) ) {
						return type;
					}
				}
				throw new IllegalArgumentException(String.format("Unknown ItemType %s", itemType));
			}
		});
        objectMapper.registerModule(module);
	}

	public <T> List<T> load(String path, Class<T> clazz) {
		File directory = new File(path);

		List<File> filePaths = new ArrayList<File>();
		findJsonFiles(directory, filePaths);

		List<T> list = new ArrayList<T>();

		for (File file : filePaths) {
			try {
				T value = objectMapper.readValue(file, clazz);
				list.add(value);
			} catch (Exception ex) {
				throw new AssetLoadException("Error loading file " + file.getAbsolutePath(), ex);
			}
		}

		return list;
	}

	private void findJsonFiles(File directory, List<File> filePaths) {
		for (File file : directory.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".json")) {
				filePaths.add(file);
			} else if(file.isDirectory()) {
				findJsonFiles(file, filePaths);
			}
		}
	}

	private static AssetLoader instance = null;
	public static AssetLoader getInstance() {
		if (instance == null) {
			instance = new AssetLoader();
		}
		return instance;
	}
}
