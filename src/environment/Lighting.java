package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gamePanel;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setLightSource();
    }

    public void draw(Graphics2D graphics2D){
        graphics2D.drawImage(darknessFilter, 0, 0, null);
    }

    public void setLightSource(){
        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) darknessFilter.getGraphics();

        if(gamePanel.player.currentLight == null){
            graphics2D.setColor(new Color(0, 0, 0, .98f));
        }else{
            int centerX = gamePanel.player.screenX + (gamePanel.tileSize) / 2;
            int centerY = gamePanel.player.screenY + (gamePanel.tileSize) / 2;

            double x = centerX - (350 / 2);
            double y = centerY - (350 / 2);

            Shape circleShape = new Ellipse2D.Double(x, y, 350, 350);

            Color color[] = new Color[5];
            float fraction[] = new float[5];

            color[0] = new Color(0, 0, 0, 0f);
            color[1] = new Color(0, 0, 0, 0.25f);
            color[2] = new Color(0, 0, 0, 0.5f);
            color[3] = new Color(0, 0, 0, 0.75f);
            color[4] = new Color(0, 0, 0, 0.98f);

            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = .98f;

            RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY, gamePanel.player.currentLight.lightRadius, fraction, color);
            graphics2D.setPaint(gradientPaint);
        }

        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        graphics2D.dispose();
    }

    public void update(){
        if(gamePanel.player.ligthUpdated){
            setLightSource();
            gamePanel.player.ligthUpdated = false;
        }
    }
}
