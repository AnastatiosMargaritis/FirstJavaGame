package entity;

import main.GamePanel;
import object.ObjectKey;
import object.ObjectSwordNormal;

public class NPC_Merchant extends Entity{


    public NPC_Merchant(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 0;

        getNPCMerchantImage();
        setDialogue();
        setItems();
    }

    public void getNPCMerchantImage(){
        down1 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/resources/npc/merchant", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Looking for the good stuff?";
    }

    public void setItems(){
        inventory.add(new ObjectKey(gamePanel));
        inventory.add(new ObjectSwordNormal(gamePanel));
    }

    public void speak(){
        super.speak();
        gamePanel.gameState = gamePanel.tradeState;

        gamePanel.ui.merchant = this;
    }
}
