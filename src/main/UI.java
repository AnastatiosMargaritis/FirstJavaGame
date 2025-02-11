package main;

import entity.Entity;
import object.ObjectCoin;
import object.ObjectHeart;
import object.ObjectMana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UI {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_blank, mana_full, mana_empty, coin;
    List<String> messages = new ArrayList<>();
    List<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int commandNum = 0;
    int counter = 0;
    int substate = 0;
    public Entity merchant;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // CREATE HUD OBJECT
        Entity heart = new ObjectHeart(gamePanel);
        heart_full = heart.down1;
        heart_half = heart.down2;
        heart_blank = heart.up1;
        Entity mana = new ObjectMana(gamePanel);
        this.mana_full = mana.down1;
        this.mana_empty = mana.down2;
        Entity bronzeCoin = new ObjectCoin(gamePanel);
        coin = bronzeCoin.down1;
    }


    public void draw(Graphics2D graphics2D){
        this.graphics2D = graphics2D;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.WHITE);

        // PLAY STATE
        if(gamePanel.gameState == gamePanel.playState){
            drawPlayerLife();
            drawMessage();
        }


        // PAUSE STATE
        if(gamePanel.gameState == gamePanel.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gamePanel.gameState == gamePanel.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

        // CHARACTER STATE
        if(gamePanel.gameState == gamePanel.characterState){
            drawCharacterScreen();
            drawInventory(gamePanel.player, true);
        }

        // GAME OVER STATE
        if(gamePanel.gameState == gamePanel.gameOverState){
            drawGameOverScreen();
        }

        // TRANSITION STATE
        if(gamePanel.gameState == gamePanel.transitionState){
            transitionScreen();
        }

        // TRADE STATE
        if(gamePanel.gameState == gamePanel.tradeState){
            drawTradeScreen();
        }
    }

    public void drawPlayerLife(){
        int x = gamePanel.tileSize/2;
        int y = gamePanel.tileSize/2;
        int i = 0;

        // DRAW MAX LIFE
        while(i < gamePanel.player.maxLife/2){
            graphics2D.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }

        //RESET
        x = gamePanel.tileSize/2;
        y = gamePanel.tileSize/2;
        i = 0;

        // DRAW CURRENT LIFE
        while(i < gamePanel.player.life){
            graphics2D.drawImage(heart_half, x, y, null);
            i++;

            if(i < gamePanel.player.life){
                graphics2D.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }

        // DRAW MANA
        x = gamePanel.tileSize / 2;
        y = (int) (gamePanel.tileSize * 1.5);
        i = 0;

        while(i < gamePanel.player.mana){
            graphics2D.drawImage(mana_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawPauseScreen(){

        graphics2D.setFont(arial_80B);
        graphics2D.setColor(Color.WHITE);
        String text = "PAUSED";
        int x = getXForCenteredText(text);

        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 32));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        graphics2D.drawString(currentDialogue, x, y);
    }

    public void drawCharacterScreen(){

        // CREATE A FRAME
        final int frameX = gamePanel.tileSize * 2;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);


        // TEXT
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        // NAMES
        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Life", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Defence", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Exp", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Next Lvl", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        graphics2D.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        graphics2D.drawString("Shield", textX, textY);
        textY += lineHeight;


        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.mana + "/" + gamePanel.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defence);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        graphics2D.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 24, null);
        textY += gamePanel.tileSize;

        graphics2D.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 24, null);
    }

    public void drawGameOverScreen(){
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 110f));

        text = "YOU DIED";
        graphics2D.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);

        //Main
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x - 4, y - 4);

        // Retry
        graphics2D.setFont(graphics2D.getFont().deriveFont(50f));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gamePanel.tileSize * 4;
        graphics2D.drawString(text, x, y);
        if(commandNum == 0){
            graphics2D.drawString(">", x - 40, y);
        }

    }

    public void drawInventory(Entity inventoryEntity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(inventoryEntity == gamePanel.player){
            frameX = gamePanel.tileSize * 9;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;


        }else{
            frameX = gamePanel.tileSize * 2;
            frameY = gamePanel.tileSize;
            frameWidth = gamePanel.tileSize * 6;
            frameHeight = gamePanel.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }

        // FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);


        // SLOT
        final int slotXStart = frameX + 20;
        final  int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;

        // DRAW PLAYER'S ITEMS
        int counter = 0;
        for(Entity entity: inventoryEntity.inventory){

            // EQUIP CURSOR
            if(entity == gamePanel.player.currentWeapon ||
                entity == gamePanel.player.currentShield){
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);
            }

            graphics2D.drawImage(entity.down1, slotX, slotY, null);

            // DISPLAY AMOUNT
            if(entity.amount > 1){
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 32F));
                int amountX;
                int amountY;

                String s = "" + entity .amount;
                amountX = getXForAlignToRightText(s, slotX + 44);
                amountY = slotY + gamePanel.tileSize;
                graphics2D.setColor(new Color(60, 60, 60));
                graphics2D.drawString(s, amountX, amountY);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(s, amountX - 3, amountY - 3);
            }
            slotX += slotSize;
            counter++;

            if(counter == 5 || counter == 10 || counter == 15){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if(cursor){
            int cursorX = slotXStart + (slotSize * playerSlotCol);
            int cursorY = slotYStart + (slotSize * playerSlotRow);
            int cursorWidth = gamePanel.tileSize;
            int cursorHeight = gamePanel.tileSize;

            // DRAW CURSOR
            graphics2D.setColor(Color.WHITE);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DESCRIPTION FRAME
            int descriptionFrameX = frameX;
            int descriptionFrameY = frameY + frameHeight;
            int descriptionFrameWidth = frameWidth;
            int descriptionFrameHeight = gamePanel.tileSize * 3;


            // DRAW DESCRIPTION TEXT
            int textX = descriptionFrameX + 20;
            int textY = descriptionFrameY + gamePanel.tileSize;
            graphics2D.setFont(graphics2D.getFont().deriveFont(28F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < inventoryEntity.inventory.size()){
                drawSubWindow(descriptionFrameX, descriptionFrameY, descriptionFrameWidth, descriptionFrameHeight);
                for(String line: inventoryEntity.inventory.get(itemIndex).description.split("\n")){
                    graphics2D.drawString(line, textX, textY);

                    textY += 32;
                }
            }
        }

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color color = new Color(0, 0, 0, 220);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXForCenteredText(String text){
        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.screenHeight/ 2 - length / 2;
    }

    public int getXForAlignToRightText(String text, int tailX){
        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = tailX - length;
        return gamePanel.screenHeight/ 2 - length / 2;
    }

    public void addMessage(String text){
        messages.add(text);
        messageCounter.add(0);
    }

    public void drawMessage(){
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < messages.size(); i++){
            if (messages.get(i) != null){
                graphics2D.setColor(Color.black);
                graphics2D.drawString(messages.get(i), messageX+2, messageY+2);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(messages.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    messages.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void transitionScreen(){
        counter++;
        graphics2D.setColor(new Color(0, 0, 0, counter * 5));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter == 50){
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
            gamePanel.currentMap = gamePanel.eventHandler.tempMap;
            gamePanel.player.worldX = gamePanel.tileSize * gamePanel.eventHandler.tempCol;
            gamePanel.player.worldY = gamePanel.tileSize * gamePanel.eventHandler.tempRow;
            gamePanel.eventHandler.previousEventX = gamePanel.player.worldX;
            gamePanel.eventHandler.previousEventY = gamePanel.player.worldY;
        }
    }

    public void drawTradeScreen(){

        switch (substate){
            case 0:
                tradeSelect();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }
    }

    public void tradeSelect(){
        drawDialogueScreen();
        // DRAW WINDOW
        int x = gamePanel.tileSize * 15;
        int y = gamePanel.tileSize * 5;
        int width = gamePanel.tileSize * 3;
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        // DRAW TEXTS
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;
        graphics2D.drawString("Buy", x, y);

        if(commandNum == 0){
            graphics2D.drawString(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                substate = 1;
                gamePanel.keyHandler.enterPressed = false;
            }
        }

        y += gamePanel.tileSize;
        graphics2D.drawString("Sell", x, y);
        if(commandNum == 1){
            graphics2D.drawString(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                substate = 2;
                gamePanel.keyHandler.enterPressed = false;
            }
        }

        y += gamePanel.tileSize;
        graphics2D.drawString("Leave", x, y);
        if(commandNum == 2){
            graphics2D.drawString(">", x - 24, y);
            if(gamePanel.keyHandler.enterPressed){
                commandNum = 0;
                gamePanel.gameState = gamePanel.dialogueState;
                currentDialogue = "Come again, hehe!";
            }
        }
    }

    public void tradeBuy(){
        // DRAW PLAYER INVENTORY
        drawInventory(gamePanel.player, false);

        // DRAW MERCHANT INVENTORY
        drawInventory(merchant, true);

        // DRAW HINT WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gamePanel.tileSize * 12;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Coins: " + gamePanel.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < merchant.inventory.size()){
            x = (int) (gamePanel.tileSize * 5.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x + 10, y + 9, 24, 24, null);

            int price = merchant.inventory.get(itemIndex).price;
            String text =  "" + price;
            x = getXForAlignToRightText(text, gamePanel.tileSize * 8);
            graphics2D.drawString(text, x + 10, y + 32);

            // BUY
            if(gamePanel.keyHandler.enterPressed){
                if(merchant.inventory.get(itemIndex).price > gamePanel.player.coin){
                    substate = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "Not enough coins";
                }else if(gamePanel.player.inventory.size() == gamePanel.player.maxIntentorySize){
                    substate = 0;
                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You cannot carry anymore!";
                }else{
                    gamePanel.player.coin -= merchant.inventory.get(itemIndex).price;
                    gamePanel.player.inventory.add(merchant.inventory.get(itemIndex));
                    gamePanel.keyHandler.enterPressed = false;
                }
            }
        }
    }
    public void tradeSell(){
        // DRAW PLAYER INVENTORY
        drawInventory(gamePanel.player, true);

        // DRAW MERCHANT INVENTORY
        drawInventory(merchant, false);

        // DRAW HINT WINDOW
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 9;
        int width = gamePanel.tileSize * 6;
        int height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COIN WINDOW
        x = gamePanel.tileSize * 12;
        y = gamePanel.tileSize * 9;
        width = gamePanel.tileSize * 6;
        height = gamePanel.tileSize * 2;
        drawSubWindow(x, y, width, height);
        graphics2D.drawString("Coins: " + gamePanel.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gamePanel.player.inventory.size()){
            x = (int) (gamePanel.tileSize * 15.5);
            y = (int) (gamePanel.tileSize * 5.5);
            width = (int) (gamePanel.tileSize * 2.5);
            height = gamePanel.tileSize;
            drawSubWindow(x, y, width, height);
            graphics2D.drawImage(coin, x + 10, y + 9, 24, 24, null);

            int price = gamePanel.player.inventory.get(itemIndex).price / 2;
            String text =  "" + price;
            x = getXForAlignToRightText(text, gamePanel.tileSize * 18);
            graphics2D.drawString(text, x + 10, y + 32);

            // SELL
            if(gamePanel.keyHandler.enterPressed){
                if(gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentWeapon ||
                gamePanel.player.inventory.get(itemIndex) == gamePanel.player.currentShield){
                    commandNum = 0;
                    substate = 0;

                    gamePanel.gameState = gamePanel.dialogueState;
                    currentDialogue = "You cannot sell equiped items.";
                }else{
                    gamePanel.player.inventory.remove(itemIndex);
                    gamePanel.player.coin += price;
                    gamePanel.keyHandler.enterPressed = false;
                }
            }
        }
    }
}
