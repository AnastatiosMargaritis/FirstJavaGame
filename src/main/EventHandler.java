package main;


public class EventHandler {

    GamePanel gamePanel;
    EventRect eventRectangle[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        eventRectangle = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            eventRectangle[map][col][row] = new EventRect();
            eventRectangle[map][col][row].x = 23;
            eventRectangle[map][col][row].y = 23;
            eventRectangle[map][col][row].width = 2;
            eventRectangle[map][col][row].height = 2;
            eventRectangle[map][col][row].eventRectangleDefaultX = eventRectangle[map][col][row].x;
            eventRectangle[map][col][row].eventRectangleDefaultY = eventRectangle[map][col][row].y;

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;

                if(row == gamePanel.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent(){
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gamePanel.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            if(hit(0, 3, 3, "up")){
                damagePit(3, 3, gamePanel.dialogueState);
            }else if(hit(0, 5, 5, "any")){
                damagePit(5, 5, gamePanel.dialogueState);
            }else if(hit(0, 38, 8, "up")){
                healingPool(gamePanel.dialogueState);
            }else if(hit(0, 11, 4, "any")){
                teleport(1, 11, 11);
            }else if(hit(1, 11, 11, "any")){
                teleport(0, 11, 5);
            }
        }
    }

    public void teleport(int map, int col, int row){
        gamePanel.gameState = gamePanel.transitionState;
        tempMap = map;
        tempRow = row;
        tempCol = col;
        canTouchEvent = false;
    }

    public boolean hit(int map, int eventCol, int eventRow, String requiredDirection){
        boolean hit = false;

        if(map == gamePanel.currentMap){
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRectangle[map][eventCol][eventRow].x = eventCol * gamePanel.tileSize + eventRectangle[map][eventCol][eventRow].x;
            eventRectangle[map][eventCol][eventRow].y = eventRow * gamePanel.tileSize + eventRectangle[map][eventCol][eventRow].y;

            if(gamePanel.player.solidArea.intersects(eventRectangle[map][eventCol][eventRow])
                    && !eventRectangle[map][eventCol][eventRow].eventDone){
                if(gamePanel.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }
            }

            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRectangle[map][eventCol][eventRow].x = eventRectangle[map][eventCol][eventRow].eventRectangleDefaultX;
            eventRectangle[map][eventCol][eventRow].y = eventRectangle[map][eventCol][eventRow].eventRectangleDefaultY;
        }

        return hit;
    }

    public void damagePit(int col, int row, int gameState){

        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You're hit. Hehehehheheheh";
        gamePanel.player.life--;
        eventRectangle[0][col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if(gamePanel.keyHandler.startDialogue){
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "HEALED BIATCH";
            gamePanel.player.life++;
            gamePanel.player.mana++;
        }
    }
}
