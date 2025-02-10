package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gamePanel);
        this.color = color;
        this.generator = generator;
        this.xd = xd;
        this.yd = yd;
        this.size = size;
        this.speed = speed;

        life = maxLife;
        int offset = (gamePanel.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update(){
        worldX += xd * speed;
        worldY += yd * speed;

        life --;
        if(life < maxLife/3){
            yd++;
        }

        if(life == 0){
            alive = false;
        }
    }

    public void draw(Graphics2D graphics2D){
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screen = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screen, size, size);
    }
}
