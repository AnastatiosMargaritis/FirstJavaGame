package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class ObjectFireball extends Projectile {

    GamePanel gamePanel;

    public ObjectFireball(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;

        getImage();
    }

    public void getImage(){
        up1 = up2 = setup("/resources/projectile/fireball_up", gamePanel.tileSize, gamePanel.tileSize);
        down1 = down2 = setup("/resources/projectile/fireball_down", gamePanel.tileSize, gamePanel.tileSize);
        right1 = right2 = setup("/resources/projectile/fireball_right", gamePanel.tileSize, gamePanel.tileSize);
        left1 = left2 = setup("/resources/projectile/fireball_left", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean haveResource(Entity user){
        return user.mana >= useCost;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
}
