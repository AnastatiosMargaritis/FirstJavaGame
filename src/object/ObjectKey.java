package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectKey extends Entity {


    public ObjectKey(GamePanel gamePanel) {
        super(gamePanel);

        name = "Key";
        direction = "down";
        down1 = setup("/resources/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        price = 75;
    }
}
