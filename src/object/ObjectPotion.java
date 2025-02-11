package object;

import entity.Entity;
import main.GamePanel;

public class ObjectPotion extends Entity {

    GamePanel gamePanel;

    public ObjectPotion(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        name = "Potion";
        direction = "down";
        down1 = setup("/resources/objects/potion", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        value = 5;

        description = "[" + name + "]\n Minor HP Restore";
        type = type_consumable;
        stackable = true;
    }

    public void use(Entity entity){
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "You drink the " + name + "!\n"
                                        + "Your life ahs been recovered by " + value + ".";
        entity.life += value;

        if(gamePanel.player.life > gamePanel.player.maxLife){
            gamePanel.player.life = gamePanel.player.maxLife;
        }
    }
}
