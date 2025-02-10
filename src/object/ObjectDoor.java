package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectDoor extends Entity {

    GamePanel gamePanel;

    public ObjectDoor(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        name = "Door";
        type = 8;
            direction = "down";
            down1 = setup("/resources/objects/door", gamePanel.tileSize, gamePanel.tileSize);
            collision = true;

            solidArea.x = 0;
            solidArea.y = 0;
            solidArea.width = 48;
            solidArea.height = 32;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;

    }

    public void interact(){
        gamePanel.gameState = gamePanel.dialogueState;
        gamePanel.ui.currentDialogue = "You need a key to open the door.";
    }
}
