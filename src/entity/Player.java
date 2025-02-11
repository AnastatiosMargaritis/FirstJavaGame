package entity;


import main.GamePanel;
import main.KeyHandler;
import object.ObjectFireball;
import object.ObjectKey;
import object.ObjectShieldWood;
import object.ObjectSwordNormal;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        super(gamePanel);
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8, 16, 20, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new ObjectKey(gamePanel));
    }

    public void getPlayerImage(){
        up1 = setup("/resources/player/hero_move_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/resources/player/hero_move_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/resources/player/move_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/resources/player/move_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/resources/player/hero_move_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/resources/player/hero_move_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/resources/player/hero_move_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/resources/player/hero_move_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getPlayerAttackImage(){

        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/resources/player/attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/resources/player/attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/resources/player/attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/resources/player/attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/resources/player/attack_left_1" ,gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/resources/player/attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/resources/player/attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/resources/player/attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }

        // FIX AXE ANIMATION SPRITES
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/resources/player/attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackUp2 = setup("/resources/player/attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown1 = setup("/resources/player/attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackDown2 = setup("/resources/player/attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
            attackLeft1 = setup("/resources/player/attack_left_1" ,gamePanel.tileSize * 2, gamePanel.tileSize);
            attackLeft2 = setup("/resources/player/attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight1 = setup("/resources/player/attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
            attackRight2 = setup("/resources/player/attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        }

    }
    
    public void setDefaultValues(){
        worldX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        worldY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        System.out.println(worldX + " " + worldY);
        defaultSpeed = 6;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;

        // DEFAULT STATS
        level = 1;
        strength = 1; // the more the strength the more the damage
        dexterity = 1; // the more the dexterity the more the defence
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new ObjectSwordNormal(gamePanel);
        currentShield = new ObjectShieldWood(gamePanel);
        attack = getAttack();
        defence = getDefence();
        projectile = new ObjectFireball(gamePanel);
    }

    public void setDefaultPositions(){
        worldX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        worldY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        direction = "down";
    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefence(){
        return  defence = dexterity * currentShield.defenceValue;
    }
    public void update(){

        if(attacking){
            handleAttack();
        }else if(keyHandler.moveUp || keyHandler.moveDown ||
                keyHandler.moveLeft || keyHandler.moveRight || keyHandler.startDialogue){
            if(keyHandler.moveUp){
                direction = "up";
            }

            if(keyHandler.moveDown){
                direction = "down";
            }

            if(keyHandler.moveLeft){
                direction = "left";
            }

            if(keyHandler.moveRight){
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            //CHECK NPC COLLISION
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int interactiveTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);

            // CHECK EVENT
            gamePanel.eventHandler.checkEvent();


            // CHECK OBJECT COLLISION
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // IF COLLISION IS FALSE MOVE
            if(!collisionOn && !keyHandler.startDialogue){
                switch (direction){
                    case "up":worldY -= speed; break;
                    case "down":worldY += speed; break;
                    case "left": worldX -= speed;; break;
                    case "right":  worldX += speed;; break;
                }
            }

            gamePanel.keyHandler.startDialogue = false;

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

        if(gamePanel.keyHandler.projectileShoot && !projectile.alive
                && shotAvailableCounter == 40
                && projectile.haveResource(this)){

            //SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);

            // ADD IT TO THE LIST
            for(int i = 0; i < gamePanel.projectileList[1].length; i++){
                if(gamePanel.projectileList[gamePanel.currentMap][i] == null){
                    gamePanel.projectileList[gamePanel.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;

            // SUBTRACT THE COST (MANA, AMMO ETC)
            projectile.subtractResource(this);
        }

        // Outside of key if statement
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 40){
            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }

        if(mana > maxMana){
            mana = maxMana;
        }

        if(life <= 0){
            gamePanel.gameState = gamePanel.gameOverState;
        }
    }

    public void handleAttack(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }

        if(spriteCounter > 5 &&spriteCounter <= 25){
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/worldY for the attackArea
            switch (direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(attack, monsterIndex, currentWeapon.knockBackPower);

            // Check interactive tile collision
            int interactiveTileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.interactiveTile);
            damageInteractiveTile(interactiveTileIndex);

            // Check projectile collision
            int projectileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.projectileList);
            damageProjectile(projectileIndex);

            // After checking collision, restore the originalData
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int index){
        if(index != 999){

            // PICKUP ONLY ITEMS
            if(gamePanel.object[gamePanel.currentMap][index].type == type_pickupOnly){
                gamePanel.object[gamePanel.currentMap][index].use(this);
                gamePanel.object[gamePanel.currentMap][index] = null;
            // OBSTACLE
            }else if(gamePanel.object[gamePanel.currentMap][index].type == type_obstacle){
                if(keyHandler.startDialogue){
                    gamePanel.object[gamePanel.currentMap][index].interact();
                }
            }else{
                String text;

                // INVENTORY ITEMS
                if(canObtainItem(gamePanel.object[gamePanel.currentMap][index])){
                    text = " Got a " + gamePanel.object[gamePanel.currentMap][index].name + "!";
                }else{
                    text = "You cannot carry anymore!";
                }
                gamePanel.ui.addMessage(text);
                gamePanel.object[gamePanel.currentMap][index] = null;
            }

        }
    }

    public void interactNPC(int index){

        if(keyHandler.startDialogue){
            if(index != 999){
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[gamePanel.currentMap][index].speak();
            }else{
                attacking = true;
            }
        }


    }
    
    public void draw(Graphics2D graphics2D){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case "up":
                if(!attacking){
                    if(spriteNum == 1){
                        image = up1;
                    }

                    if(spriteNum == 2){
                        image = up2;
                    }
                }else{
                    tempScreenY = screenY - gamePanel.tileSize;
                    if(spriteNum == 1){
                        image = attackUp1;
                    }

                    if(spriteNum == 2){
                        image = attackUp2;
                    }
                }

                break;
            case "down":
                if(!attacking){
                    if(spriteNum == 1){
                        image = down1;
                    }

                    if(spriteNum == 2){
                        image = down2;
                    }
                }else{
                    if(spriteNum == 1){
                        image = attackDown1;
                    }

                    if(spriteNum == 2){
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(!attacking){
                    if(spriteNum == 1){
                        image = left1;
                    }

                    if(spriteNum == 2){
                        image = left2;
                    }
                }else{
                    tempScreenX = screenX - gamePanel.tileSize;
                    if(spriteNum == 1){
                        image = attackLeft1;
                    }

                    if(spriteNum == 2){
                        image = attackLeft2;
                    }
                }

                break;
            case "right":
                if(!attacking){
                    if(spriteNum == 1){
                        image = right1;
                    }

                    if(spriteNum == 2){
                        image = right2;
                    }
                }else{
                    if(spriteNum == 1){
                        image = attackRight1;
                    }

                    if(spriteNum == 2){
                        image = attackRight2;
                    }
                }
                break;
        }

        if(invincible){
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2D.drawImage(image, tempScreenX, tempScreenY, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void contactMonster(int index){
        if(index != 999){
            if(!invincible){
                int damage = gamePanel.monster[gamePanel.currentMap][index].attack - defence;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int attack, int index, int knockBackPower){
        if(index != 999){
            if(!gamePanel.monster[gamePanel.currentMap][index].invincible){

                if(knockBackPower > 0){
                    knockBack(gamePanel.monster[gamePanel.currentMap][index], knockBackPower);

                }
                int damage = attack - gamePanel.monster[gamePanel.currentMap][index].defence;
                if(damage < 0){
                    damage = 0;
                }
                gamePanel.monster[gamePanel.currentMap][index].life -= damage;
                gamePanel.monster[gamePanel.currentMap][index].onPath = true;
                gamePanel.ui.addMessage(damage + " damage!");
                gamePanel.monster[gamePanel.currentMap][index].invincible = true;


                if(gamePanel.monster[gamePanel.currentMap][index].life <= 0){
                    gamePanel.ui.addMessage("Killed the monster!");
                    exp += gamePanel.monster[gamePanel.currentMap][index].exp;
                    checkLevelUp();
                    gamePanel.monster[gamePanel.currentMap][index].checkDrop();
                    gamePanel.monster[index] = null;
                }
            }
        }else{
            System.out.println("POUTSA");
        }
    }

    public void damageInteractiveTile(int index){
        if(index != 999 &&  gamePanel.interactiveTile[gamePanel.currentMap][index].destructible
            && gamePanel.interactiveTile[gamePanel.currentMap][index].isCorrectItem(this)
            && !gamePanel.interactiveTile[gamePanel.currentMap][index].invincible){
            gamePanel.interactiveTile[gamePanel.currentMap][index].life--;
            gamePanel.interactiveTile[gamePanel.currentMap][index].invincible = true;
            generateParticle(gamePanel.interactiveTile[gamePanel.currentMap][index], gamePanel.interactiveTile[gamePanel.currentMap][index]);
            if(gamePanel.interactiveTile[gamePanel.currentMap][index].life <= 0){
                gamePanel.interactiveTile[gamePanel.currentMap][index] = gamePanel.interactiveTile[gamePanel.currentMap][index].getDestroyedForm();
            }
        }
    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            life = maxLife;
            strength ++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();

            gamePanel.ui.currentDialogue = "You are level " + level + " now!\n";
        }
    }

    public void selectItem(){
        int itemIndex = gamePanel.ui.getItemIndexOnSlot(gamePanel.ui.playerSlotCol, gamePanel.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }

            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defence = getDefence();
            }

            if(selectedItem.type == type_consumable){
                // LATER
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void damageProjectile(int projectileIndex){
        if(projectileIndex != 999){
            Entity projectile = gamePanel.projectileList[gamePanel.currentMap][projectileIndex];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void knockBack(Entity entity, int knockBackPower){
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;

        // CHECK IF STACKABLE
        if(item.stackable){
            int index = searchItemInInventory(item.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }else{
                if(inventory.size() < maxIntentorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }else{
            if(inventory.size() < maxIntentorySize){
                inventory.add(item);
                canObtain = true;
            }
        }

        return canObtain;
    }
}
