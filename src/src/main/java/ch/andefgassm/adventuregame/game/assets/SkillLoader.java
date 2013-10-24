package ch.andefgassm.adventuregame.game.assets;

import java.io.File;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Skill;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkillLoader {
    
    public static void main(String[] args) throws AssetLoadException {
        CombatSystem system = new CombatSystem();
        load(system, "assets/data/skills/execute.json");
    }
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static void load(CombatSystem system, String file) throws AssetLoadException {
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readValue(new File(file), JsonNode.class);
        } catch (Exception e) {
            throw new AssetLoadException("Can't load file " + file, e);
        }
        
        if (rootNode.get("id") == null) {
            throw new AssetLoadException("id attribute of skill can't be null: " + file);
        }
        String id = rootNode.get("id").asText();
        if (rootNode.get("name") == null) {
            throw new AssetLoadException("name attribute of skill can't be null: " + file);
        }
        Skill skill = new Skill(rootNode.get("name").asText());
        
        
        
        
        
        
        system.registerSkill(id, skill);
    }

}
