package views;

import javax.swing.* ;
import java.awt.* ;

import config.GameConfig;

public class ItemDrawer {
    public static void drawSpeedItem(Graphics2D g, int x, int y, int w,int h){
        Image speedItemImage = new ImageIcon(ItemDrawer.class.getResource(GameConfig.SpeedItem)).getImage() ;
        g.drawImage(speedItemImage, x, y, w, h, null);
    }   

    public static void drawPoisonItem(Graphics2D g, int x, int y, int w,int h){
        Image poisonItemImage = new ImageIcon(ItemDrawer.class.getResource(GameConfig.PoisonItem)).getImage() ;
        g.drawImage(poisonItemImage, x, y, w, h, null);
    }  
}
