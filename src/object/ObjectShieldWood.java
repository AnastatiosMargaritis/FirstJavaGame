package object;

import entity.Entity;
import main.GamePanel;

public class ObjectShieldWood extends Entity {

    public ObjectShieldWood(GamePanel gamePanel) {
        super(gamePanel);

        type = type_shield;
        name = "Wood Shield";
        down1 = setup("/resources/objects/shield", gamePanel.tileSize, gamePanel.tileSize);
        defenceValue = 1;
        description = "[" + name + "]\n An old shield.";
    }
}
