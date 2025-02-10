package object;

import entity.Entity;
import main.GamePanel;

public class ObjectMana extends Entity {

    GamePanel gamePanel;

    public ObjectMana(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        value = 1;
        name = "Mana Crystal";
        direction = "down";
        collision = true;
        down1 = setup("/resources/objects/mana", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/objects/mana_empty", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity){
        gamePanel.ui.addMessage("Mana + " + value);
        entity.mana += value;
    }
}
