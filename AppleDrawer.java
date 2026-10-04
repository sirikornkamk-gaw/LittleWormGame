package views;

import java.awt.*;

import config.GameConfig;

public class AppleDrawer {
    public static void drawApple(Graphics2D g, int x, int y, int w,int h, Color appleColor) {
        g.setColor(GameConfig.Applestalk);
        int middleApple = x + (int)(w * 0.80) - ((w/15)) ;
        g.fillRect(middleApple, y-(h/8), w/15, h/5);
        g.setColor(appleColor);
        g.fillOval(x, y, w, h);
        g.fillOval(x+(w/2), y, w, h);
        
    }
    public static void RotateApple(Graphics2D g,int x, int y, int w,int h, Color appleColor,double angle) {
        Graphics2D g2 = (Graphics2D) g.create();
        double centerX = x + (w * 0.75); 
        double centerY = y + (h / 2.0);
        
        g2.rotate(Math.toRadians(angle), centerX, centerY);
        AppleDrawer.drawApple(g2, x, y, w, h, appleColor);
        g2.dispose();
    }
}


