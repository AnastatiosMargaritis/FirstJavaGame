package object;

import entity.Entity;
import main.GamePanel;

public class ObjectLattern extends Entity {

    public ObjectLattern(GamePanel gamePanel) {
        super(gamePanel);

        type = type_light;
        name = "Lattern";
        direction = "down";
        down1 = setup("/resources/objects/latern", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\n A light that can be used to light up walls.";
        price = 200;
        lightRadius = 250;
        collision = true;
    }
}
