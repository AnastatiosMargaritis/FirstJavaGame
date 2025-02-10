package object;

import entity.Entity;
import main.GamePanel;

public class ObjectSwordNormal extends Entity {

    public ObjectSwordNormal(GamePanel gamePanel) {
        super(gamePanel);

        name = "Normal Sword";
        down1 = setup("/resources/objects/sword", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
        description = "[" + name + "]\n An old sword.";

        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;
        price = 36;
    }
}
