package main.tile_interactive;

import main.GamePanel;

public class Trunk extends InteractiveTile{

    public Trunk(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);
        this.gamePanel = gamePanel;

        this.worldX = gamePanel.tileSize * col;
        this.worldY = gamePanel.tileSize * row;

        down1 = setup("/resources/tiles_interactive/trunk", gamePanel.tileSize, gamePanel.tileSize);
        direction = "down";
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
