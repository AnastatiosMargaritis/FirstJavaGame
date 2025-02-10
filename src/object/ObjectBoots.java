package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ObjectBoots extends SuperObject{

    public ObjectBoots(GamePanel gamePanel) {

        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/boot.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
