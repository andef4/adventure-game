package ch.andefgassm.adventuregame.game.assets;

import java.io.IOException;

import ch.andefgassm.adventuregame.combat.IDamageType;
import ch.andefgassm.adventuregame.combat.IResource;
import ch.andefgassm.adventuregame.combat.IStat;
import ch.andefgassm.adventuregame.game.DamageType;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.Stat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class AssetLoader {
	
	
	private static ObjectMapper objectMapper = null;
	
	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
	        SimpleModule module = new SimpleModule();
	        module.addAbstractTypeMapping(IResource.class, Resource.class);
	        module.addAbstractTypeMapping(IDamageType.class, DamageType.class);
	        module.addAbstractTypeMapping(IStat.class, Stat.class);
	        module.addKeyDeserializer(IResource.class, new KeyDeserializer() {
				@Override
				public Object deserializeKey(String key, DeserializationContext context) throws IOException, JsonProcessingException {
					for (Resource s : Resource.values()) {
						if (key.toLowerCase() == s.name().toLowerCase()) {
							return s;
						}
					}
					throw new IllegalArgumentException("Unknown Resource key");
				}
	        });
	        module.addKeyDeserializer(IStat.class, new KeyDeserializer() {
				@Override
				public Object deserializeKey(String key, DeserializationContext context) throws IOException, JsonProcessingException {
					for (Stat s : Stat.values()) {
						if (key.toLowerCase() == s.name().toLowerCase()) {
							return s;
						}
					}
					throw new IllegalArgumentException("Unknown Stat key");
				}
	        });
	        objectMapper.registerModule(module);
		}
		return objectMapper;
	}
}
