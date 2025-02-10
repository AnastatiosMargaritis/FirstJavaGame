package object;

import entity.Entity;
import main.GamePanel;

public class ObjectCoin extends Entity {

    GamePanel gamePanel;

    public ObjectCoin(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = type_pickupOnly;
        name = "Bronze Coin";
        direction = "down";
        down1 = setup("/resources/objects/coin", gamePanel.tileSize /2 , gamePanel.tileSize / 2);
        value = 1;
        collision = true;
    }

    public void use(Entity entity){
        gamePanel.ui.addMessage("Coin +" + value);
        gamePanel.player.coin += value;
    }
}
