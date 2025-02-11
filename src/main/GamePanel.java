package main;

import AI.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import main.tile.TileManager;
import main.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 scale
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Map Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FOR FULL SCREEN
    int fullScreenWidth = screenWidth;
    int fullScreenHeight = screenHeight;
    BufferedImage tempScreen;
    Graphics2D graphics2D;
    EnvironmentManager environmentManager = new EnvironmentManager(this);


    //FPS
    int FPS = 60;

    // System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public PathFinder pathFinder = new PathFinder(this);

    // Entity and Object
    public Player player = new Player(this, keyHandler);
    public Entity object[][] = new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public Entity projectileList[][] = new Entity[maxMap][20];
//    public List<Entity> projectileList = new ArrayList<>();
    public InteractiveTile interactiveTile[][] = new InteractiveTile[maxMap][50];
    public List<Entity> particleList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5; // for future use -> implementing options menu
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNpc();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        gameState = playState;

        environmentManager.setup();

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) tempScreen.getGraphics();
        setFullScreen();
    }

    public void setFullScreen(){
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null){
            double drawInterval = 1000000000d / FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;

            long timer = 0;
            int drawCount = 0;

            while(gameThread != null){
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;



                if(delta >= 1){
                    update();
                    drawToTempScreen();
                    drawToScreen();
                    delta--;
                    drawCount++;
                }
            }
        }
    }

    public void update(){
        if(gameState == playState){
            player.update();
            for(Entity entity: npc[currentMap]){
                if(entity != null){
                    entity.update();
                }
            }

            if(monster[currentMap] != null){
                for(Entity entity: monster[currentMap]){
                    if(entity != null){
                        entity.update();
                    }
                }
            }


            for(int i = 0; i < projectileList[1].length; i++){
                if(projectileList[currentMap][i] != null){
                    projectileList[currentMap][i].update();
                }

                if(projectileList[currentMap][i]!= null && !projectileList[currentMap][i].alive){
                    projectileList[currentMap][i] = null;
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    particleList.get(i).update();
                }

                if(particleList.get(i) != null && !particleList.get(i).alive){
                    particleList.remove(i);
                }
            }

            for(InteractiveTile interactiveTile1: interactiveTile[currentMap]){
                if(interactiveTile1 != null){
                    interactiveTile1.update();
                }
            }

            environmentManager.update();
        }


        if(gameState == pauseState){

        }
    }

    public void drawToTempScreen(){
        tileManager.draw(graphics2D);

        for(InteractiveTile interactiveTile1: interactiveTile[currentMap]){
            if(interactiveTile1 != null){
                interactiveTile1.draw(graphics2D);
            }
        }

        for (Entity superObject : object[currentMap]) {
            if(superObject != null){
                superObject.draw(graphics2D);
            }
        }

        // NPC
        for(Entity entity: npc[currentMap]){
            if(entity != null){
                entity.draw(graphics2D);
            }
        }

        // MONSTER
        if(monster[currentMap] != null){
            for(Entity entity: monster[currentMap]){
                if(entity != null){
                    entity.draw(graphics2D);
                }
            }
        }


        // PROJECTILE
        for(int i = 0; i < projectileList[1].length; i++){
            if(projectileList[currentMap][i] != null){
                projectileList[currentMap][i].draw(graphics2D);
            }
        }

        // PARTICLE
        for(Entity entity: particleList){
            if(entity != null){
                entity.draw(graphics2D);
            }
        }

        player.draw(graphics2D);
        environmentManager.draw(graphics2D);

        // UI
        ui.draw(graphics2D);
    }

    public void drawToScreen(){
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        graphics.dispose();
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        assetSetter.setNpc();
        assetSetter.setMonster();
    }
}
