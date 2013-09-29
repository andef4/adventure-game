package old;

public class Combat {
    private Player player;
    private InCombat player_combat;
    
    private Enemy enemy;
    private InCombat enemy_combat;
    
    public Combat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.player_combat = new InCombat(player);
        this.enemy_combat = new InCombat(enemy);
    }
    
    
    public void fight() {
        
        //Action action = null;        
        //action = this.player.getNextAction();
        
        
        
    }
    
    
    

}
