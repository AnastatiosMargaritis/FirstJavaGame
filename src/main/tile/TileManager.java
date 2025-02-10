package main.tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNumber[][][];
    boolean drawPath = true;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNumber = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/resources/maps/world_map.txt",0);
        loadMap("/resources/maps/house_map.txt", 1);
    }

    public void getTileImage(){
            setup(0, "grass", false);
            setup(1, "wall", true);
            setup(2, "water", true);
            setup(3, "earth", false);
            setup(4, "tree", true);
            setup(5, "sand", false);
            setup(6, "house_floor", false);
            setup(7, "house_transition", false);
    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool utilityTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + imagePath + ".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){

        }
    }

    public void loadMap(String path, int map){
        try{
            InputStream inputStream = getClass().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
                String line = bufferedReader.readLine();

                while(col < gamePanel.maxWorldCol){
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[map][col][row] = num;
                    col++;
                }

                if(col == gamePanel.maxWorldCol){
                    col = 0;
                    row ++;
                }
            }

            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){

            int tileNum = mapTileNumber[gamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            //draw boundary
            if(worldX > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY < gamePanel.player.worldY + gamePanel.player.screenY){
            }
            graphics2D.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            worldCol++;

            if(worldCol == gamePanel.maxWorldCol){
                worldCol = 0;
                worldRow ++;
            }
        }

        if(drawPath){
            graphics2D.setColor(new Color(255, 0, 0, 70));
            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++){
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                graphics2D.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }
    }
}
