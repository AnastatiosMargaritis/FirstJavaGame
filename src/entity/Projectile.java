package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update(){

        if(user == gamePanel.player){
            int monsterIndex = 999;
            try{
                monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            }catch (NoSuchFieldError e){
                e.printStackTrace();
            }

            if(monsterIndex != 999){
                gamePanel.player.damageMonster(attack, monsterIndex);
                alive = false;
            }
        }

        if(user != gamePanel.player){
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if(!gamePanel.player.invincible && contactPlayer){
                damagePlayer(attack);
                alive = false;
            }
        }

        switch (direction){
            case "up":worldY -= speed; break;
            case "down":worldY += speed; break;
            case "left": worldX -= speed;; break;
            case "right":  worldX += speed;; break;
        }

        life--;
        if(life <= 0){
            alive = false;
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
    }

    public boolean haveResource(Entity user){
        return user.mana >= useCost;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
}
