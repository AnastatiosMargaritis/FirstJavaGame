package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectDoor extends SuperObject{


    public ObjectDoor(GamePanel gamePanel) {

        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/door.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
