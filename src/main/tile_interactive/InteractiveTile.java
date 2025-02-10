package main.tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

    GamePanel gamePanel;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel, int col, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public void update(){
        if(invincible){
            invincibleCounter ++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
}
