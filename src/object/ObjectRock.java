package object;

import entity.Projectile;
import main.GamePanel;

public class ObjectRock extends Projectile {
    GamePanel gamePanel;

    public ObjectRock(GamePanel gamePanel) {
        super(gamePanel);

        this.gamePanel = gamePanel;
        name = "Rock";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;

        getImage();
    }

    public void getImage(){
        up1 = up2 = setup("/resources/projectile/rock", gamePanel.tileSize, gamePanel.tileSize);
        down1 = down2 = setup("/resources/projectile/rock", gamePanel.tileSize, gamePanel.tileSize);
        right1 = right2 = setup("/resources/projectile/rock", gamePanel.tileSize, gamePanel.tileSize);
        left1 = left2 = setup("/resources/projectile/rock", gamePanel.tileSize, gamePanel.tileSize);
    }

    public int getParticleSize(){
        return 6; // 6 pixels
    }

    public int getParticleSpeed(){
        return 1;
    }

    public int getParticleMaxLife(){
        return 20;
    }
}
