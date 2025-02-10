package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Entity {

    GamePanel gamePanel;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    String dialogues[] = new String[20];


    // STATE
    public int worldX, worldY;
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean collision = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive;
    public boolean onPath = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;

    // CHARACTER STATUS
    public int maxLife;
    public int life;
    public int speed;
    public String name;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public int maxMana;
    public int mana;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;
    public int value;
    public int price;

    // TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;

    public List<Entity> inventory = new ArrayList<>();
    public final int maxIntentorySize = 20;


    public Entity(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void setAction(){};
    public void checkDrop(){}
    public void dropItem(Entity item){

        for(int i = 0; i < gamePanel.object.length; i++){
            if(gamePanel.object[gamePanel.currentMap][i] == null){
                gamePanel.object[gamePanel.currentMap][i] = item;
                gamePanel.object[gamePanel.currentMap][i].worldX = worldX;
                gamePanel.object[gamePanel.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void update(){
        setAction();

       checkCollision();

        // IF COLLISION IS FALSE MOVE
        if(!collisionOn){
            switch (direction){
                case "up":worldY -= speed; break;
                case "down":worldY += speed; break;
                case "left": worldX -= speed;; break;
                case "right":  worldX += speed;; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 40){
            shotAvailableCounter++;
        }
    };

    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }

                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }

                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }

                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }

                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

            if(invincible){
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            graphics2D.drawImage(image, screenX, screenY, null);
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Monster HP Bar
        if(type == type_monster){
            double oneScale = (double)gamePanel.tileSize / maxLife;
            double hpBarValue = oneScale * life;

            graphics2D.setColor(new Color(35, 35, 35));
            graphics2D.fillRect(screenX -1, screenY - 16, gamePanel.tileSize + 2, 12);

            graphics2D.setColor(new Color(255, 0, 30));
            graphics2D.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
        }
    }

    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;

        }
    }

    public void use(Entity entity){}

    public void damagePlayer(int attack){
        if(!gamePanel.player.invincible){
            int damage = attack - gamePanel.player.defence;
            if(damage < 0){
                damage = 0;
            }
            gamePanel.player.life -= damage;
            gamePanel.player.invincible = true;
        }
    }

    public Color getParticleColor(){
        return null;
    }

    public int getParticleSize(){
        return 0;
    }

    public int getParticleSpeed(){
        return 0;
    }

    public int getParticleMaxLife(){
        return 0;
    }

    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle particle1 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, -1);
        Particle particle2 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, -1);
        Particle particle3 = new Particle(gamePanel, target, color, size, speed, maxLife, -2, 1);
        Particle particle4 = new Particle(gamePanel, target, color, size, speed, maxLife, 2, 1);

        particle1.alive = true;
        particle2.alive = true;
        particle3.alive = true;
        particle4.alive = true;

        gamePanel.particleList.addAll(List.of(particle1, particle2, particle3, particle4));
    }

    public void checkCollision(){
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){
            damagePlayer(attack);
        }
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gamePanel.pathFinder.search()){
            // Next worldX & worldY
            int nextX = gamePanel.pathFinder.pathList.getFirst().col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.getFirst().row * gamePanel.tileSize;

            // Entity's solidArea position
            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            if(entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize){
                direction= " up";
            }else if(entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize){
                direction= " down";
            }else if(entityTopY >= nextY && entityBottomY < nextY + gamePanel.tileSize){
                // left or right
                if(entityLeftX > nextX){
                    direction = "left";
                }

                if(entityLeftX < nextX){
                    direction = " right";
                }
            }else if(entityTopY > nextY && entityLeftX > nextX){
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }else if(entityTopY > nextY && entityLeftX < nextX){
                //up or right
                direction = " up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }else if(entityTopY < nextY && entityLeftX > nextX){
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }else if(entityTopY < nextY && entityLeftX < nextX){
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }

//            int nextCol = gamePanel.pathFinder.pathList.getFirst().col;
//            int nextRow = gamePanel.pathFinder.pathList.getFirst().row;
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
        }
    }
}
