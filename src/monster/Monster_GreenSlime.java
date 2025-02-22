package monster;

import entity.Entity;
import main.GamePanel;
import object.ObjectCoin;
import object.ObjectKey;
import object.ObjectMana;
import object.ObjectRock;

import java.awt.*;
import java.util.Random;

public class Monster_GreenSlime extends Entity {
        GamePanel gamePanel;

        public Monster_GreenSlime(GamePanel gamePanel){
            super(gamePanel);

            this.gamePanel = gamePanel;
            type = type_monster;
            defaultSpeed = 1;
            speed = defaultSpeed;
            maxLife = 4;
            life = maxLife;
            attack = 5;
            defence = 0;
            exp = 6;
            solidArea = new Rectangle(8, 16, 20, 32);
            projectile = new ObjectRock(gamePanel);

            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
            direction = "down";

            getImage();
        }

        public void getImage(){
            up1 = setup("/resources/monster/monster_down_1", gamePanel.tileSize, gamePanel.tileSize);
            up2 = setup("/resources/monster/monster_down_2", gamePanel.tileSize, gamePanel.tileSize);
            down1 = setup("/resources/monster/monster_down_1", gamePanel.tileSize, gamePanel.tileSize);
            down2 = setup("/resources/monster/monster_down_2", gamePanel.tileSize, gamePanel.tileSize);
            left1 = setup("/resources/monster/monster_down_1", gamePanel.tileSize, gamePanel.tileSize);
            left2 = setup("/resources/monster/monster_down_2", gamePanel.tileSize, gamePanel.tileSize);
            right1 = setup("/resources/monster/monster_down_1", gamePanel.tileSize, gamePanel.tileSize);
            right2 = setup("/resources/monster/monster_down_2", gamePanel.tileSize, gamePanel.tileSize);
        }

        public void setAction(){
            if(onPath){
                searchPath(getGoalCol(gamePanel.player), getGoalCol(gamePanel.player));

                int i = new Random().nextInt(200) + 1;
                if(i > 197 && !projectile.alive && shotAvailableCounter == 40){
                    projectile.set(worldX, worldY, direction, true, this);

                    for(int j = 0; j < gamePanel.projectileList[1].length; j ++){
                        if(gamePanel.projectileList[gamePanel.currentMap][j] == null){
                            gamePanel.projectileList[gamePanel.currentMap][j] = projectile;
                            break;
                        }
                    }
                    shotAvailableCounter = 0;
                }
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
