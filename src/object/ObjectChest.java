package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectChest extends SuperObject{


    public ObjectChest(GamePanel gamePanel) {

        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/chest.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
