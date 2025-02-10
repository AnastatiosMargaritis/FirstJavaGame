package object;

import entity.Entity;
import main.GamePanel;

public class ObjectShieldBlue extends Entity {
    public ObjectShieldBlue(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Blue Shield";
        direction = "down";
        down1 = setup("/resources/objects/blue_shield", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        defenceValue = 2;
    }
}
