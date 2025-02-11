package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectKey extends Entity {

    public GamePanel gamePanel;

    public ObjectKey(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        name = "Key";
        direction = "down";
        down1 = setup("/resources/objects/key", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;
        price = 75;

        type = type_consumable;
        stackable = true;
    }

    public void use(Entity entity){
        gamePanel.gameState =  gamePanel.dialogueState;
        int objectIndex = getDetected(entity, gamePanel.object, "Door");

        if(objectIndex != 999){
            gamePanel.ui.currentDialogue = " You use the key to open the door.";
            gamePanel.object[gamePanel.currentMap][objectIndex] = null;
        }else{
            gamePanel.ui.currentDialogue = " You can't use the key.";
        }
    }
}
