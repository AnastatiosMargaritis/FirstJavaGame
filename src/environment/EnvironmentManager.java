package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {

    GamePanel gamePanel;
    Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D graphics2D){
        lighting.draw(graphics2D);
    }

    public void setup(){
        lighting = new Lighting(gamePanel);
    }

    public void update(){
        lighting.update();
    }
}
