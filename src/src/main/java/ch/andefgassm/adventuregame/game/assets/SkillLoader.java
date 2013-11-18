package ch.andefgassm.adventuregame.game.assets;

import java.io.File;

import ch.andefgassm.adventuregame.combat.Skill;

public class SkillLoader {

	public static void save(Skill skill, String file) throws Exception {
		AssetLoader.getObjectMapper().writeValue(new File(file), skill);
	}

	public static Skill load(String file) throws AssetLoadException {
        try {
			return AssetLoader.getObjectMapper().readValue(new File("skill.json"), Skill.class);
		} catch (Exception ex) {
			throw new AssetLoadException(ex);
		}
    }
}
