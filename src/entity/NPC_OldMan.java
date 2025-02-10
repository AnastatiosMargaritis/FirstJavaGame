package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        getNPCOldManImage();
        setDialogue();
    }

    public void getNPCOldManImage(){
        up1 = setup("/resources/npc/npc_move_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/resources/npc/npc_move_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/resources/npc/npc_move_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/npc/npc_move_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/resources/npc/npc_move_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/resources/npc/npc_move_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/resources/npc/npc_move_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/resources/npc/npc_move_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Gamw tin mana sou tin kariola";
        dialogues[1] = "Ti koitas 8eleis 3ulo a";
        dialogues[2] = "Sou milaw vre gamhmeno paidaki";
        dialogues[3] = "Ainte geia";
    }

    public void setAction(){
        if(onPath){
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
//            int goalCol = 27;
//            int goalRow = 14;

            searchPath(goalCol, goalRow);
        }else{
            actionLockCounter ++;

            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick a number from 1 to 100
                if(i <= 25){
                    direction = "up";
                }

                if(i > 25 && i <= 50){
                    direction = "down";
                }

                if(i > 50 && i <= 75){
                    direction = "left";
                }

                if(i > 75){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void speak(){
        super.speak();
        onPath = true;
    }

}
