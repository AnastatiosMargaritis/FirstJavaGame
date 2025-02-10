package object;

import entity.Entity;
import main.GamePanel;


public class ObjectHeart extends Entity {
    GamePanel gamePanel;

    public ObjectHeart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        direction = "down";
        collision = true;
        down1 = setup("/resources/objects/heart", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("/resources/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void use(Entity entity){
        gamePanel.ui.addMessage("Life + " + value);
        entity.life += value;
    }
}
