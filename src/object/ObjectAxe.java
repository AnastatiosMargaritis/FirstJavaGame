package object;

import entity.Entity;
import main.GamePanel;

public class ObjectAxe extends Entity {

    public ObjectAxe(GamePanel gamePanel) {
        super(gamePanel);

        name = "Axe";
        direction = "down";
        down1 = setup("/resources/objects/axe", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        attackValue = 2;

        attackArea.width = 30;
        attackArea.height = 30;

        description = "[" + name + "]\n A simple axe.";
        type = type_axe;
    }
}
