package main;


import entity.NPC_Merchant;
import entity.NPC_OldMan;
import main.tile_interactive.DryTree;
import monster.Monster_GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject(){
        int i = 0;
        int mapNumber = 0;
        gamePanel.object[mapNumber][i] = new ObjectCoin(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 25;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 19;
        i++;
        gamePanel.object[mapNumber][i] = new ObjectMana(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 36;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 12;
        i++;
        gamePanel.object[mapNumber][i] = new ObjectHeart(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 25;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[mapNumber][i] = new ObjectAxe(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 20;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;
        i++;
        gamePanel.object[mapNumber][i] = new ObjectShieldBlue(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 18;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 21;

        i++;
        gamePanel.object[mapNumber][i] = new ObjectPotion(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 12;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 12;

        i++;
        gamePanel.object[mapNumber][i] = new ObjectDoor(gamePanel);
        gamePanel.object[mapNumber][i].worldX = gamePanel.tileSize * 26;
        gamePanel.object[mapNumber][i].worldY = gamePanel.tileSize * 12;

        // MAP 1
        mapNumber = 1;
        i = 0;
        gamePanel.npc[mapNumber][i] = new NPC_Merchant(gamePanel);
        gamePanel.npc[mapNumber][i].worldX = gamePanel.tileSize * 11;
        gamePanel.npc[mapNumber][i].worldY = gamePanel.tileSize * 8;
    }

    public void setNpc(){
        int mapNumber = 0;
        gamePanel.npc[mapNumber][0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[mapNumber][0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[mapNumber][0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster(){
        int mapNumber = 0;
        gamePanel.monster[mapNumber][0] = new Monster_GreenSlime(gamePanel);
        gamePanel.monster[mapNumber][0].worldX = gamePanel.tileSize * 18;
        gamePanel.monster[mapNumber][0].worldY = gamePanel.tileSize * 18;
    }

    public void setInteractiveTile(){
        int i = 0;
        int mapNumber = 0;
        gamePanel.interactiveTile[mapNumber][i] = new DryTree(gamePanel, 13, 13);
    }
}
