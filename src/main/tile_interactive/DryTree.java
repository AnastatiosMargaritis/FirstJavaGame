package main.tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DryTree extends InteractiveTile{

    GamePanel gamePanel;
    public DryTree(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/resources/tiles_interactive/destructible tree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        direction = "down";
        life = 3;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    @Override
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new Trunk(gamePanel, worldX/gamePanel.tileSize, worldY/gamePanel.tileSize);
        return tile;
    }

    public Color getParticleColor(){
        return new Color(65, 50, 30);
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
