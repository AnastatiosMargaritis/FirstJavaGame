package monster;

import entity.Entity;
import main.GamePanel;
import object.ObjectCoin;
import object.ObjectKey;
import object.ObjectMana;

import java.awt.*;
import java.util.Random;

public class AttackingMonster extends Entity {

    GamePanel gamePanel;

    public AttackingMonster(GamePanel gamePanel){
        super(gamePanel);

        this.gamePanel = gamePanel;
        type = type_monster;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defence = 1;
        exp = 10;
        solidArea = new Rectangle(8, 16, 20, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 36;
        attackArea.height = 36;
        direction = "down";

        getImage();
//        getAttackImage();
    }

    public void getImage(){
        up1 = setup("/resources/monster/attacking_monster/monster_move_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/resources/monster/attacking_monster/monster_move_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/resources/monster/attacking_monster/monster_move_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/monster/attacking_monster/monster_move_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/resources/monster/attacking_monster/monster_move_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/resources/monster/attacking_monster/monster_move_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/resources/monster/attacking_monster/monster_move_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/resources/monster/attacking_monster/monster_move_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getAttackImage(){
        up1 = setup("/resources/monster/attacking_monster/monster_attack_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/resources/monster/attacking_monster/monster_attack_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/resources/monster/attacking_monster/monster_attack_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/monster/attacking_monster/monster_attack_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/resources/monster/attacking_monster/monster_attack_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/resources/monster/attacking_monster/monster_attack_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/resources/monster/attacking_monster/monster_attack_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/resources/monster/attacking_monster/monster_attack_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void setAction(){
        if(onPath){
            searchPath(getGoalCol(gamePanel.player), getGoalCol(gamePanel.player));

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

        if(!attacking){
            checkAttack(2, gamePanel.tileSize * 4, gamePanel.tileSize);
        }
    }

    public void checkAttack(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDistance = getXDistance(gamePanel.player);
        int yDistance = getYDistance(gamePanel.player);

        switch (direction){
            case "up":
                if(gamePanel.player.worldY < worldY
                        && yDistance < straight
                        && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gamePanel.player.worldY > worldY
                        && yDistance < straight
                        && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gamePanel.player.worldX < worldX
                        && xDistance < straight
                        && yDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gamePanel.player.worldX > worldX
                        && xDistance < straight
                        && yDistance < horizontal){
                    targetInRange = true;
                }
                break;
        }

        if(targetInRange){
            int i = new Random().nextInt(rate);

            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void checkDrop(){

        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if(i < 50){
            dropItem(new ObjectCoin(gamePanel));
        }

        if(i >= 50 && i < 75){
            dropItem(new ObjectKey(gamePanel));
        }

        if(i > 75){
            dropItem(new ObjectMana(gamePanel));
        }
    }
}
