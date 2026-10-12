package views;

import javax.swing.* ;
import java.util.List;
import java.awt.* ;
import java.awt.geom.AffineTransform;

import config.GameConfig;

public class WormDrawer {
    private Image headOpenImage ;
    private Image headCloseImage ;
    
    public WormDrawer(){
        this.headOpenImage = new ImageIcon(GameConfig.wormOpenMouth).getImage();
        this.headCloseImage = new ImageIcon(GameConfig.wormCloseMouth).getImage();
    }

    public void drawWorm(Graphics2D g, List<Point> body,char direction, boolean isOpenMouth){
        if (body == null || body.isEmpty()) return ;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        for(int i=0 ; i < body.size() ; i++){
            Point p = body.get(i);
            if (i == 0) {
                Image currentHead ;
                if (isOpenMouth == true) {
                    currentHead = headOpenImage;   
                } else {
                    currentHead = headCloseImage;
                }
                drawRotatedImg(g, currentHead, p.x, p.y, direction);
            }else if(i == body.size()-1){
                drawTail(g, p, body.get(i-1));
            }else{
                g.setColor(GameConfig.wormcolor);
                g.fillOval(p.x, p.y, GameConfig.tilesize, GameConfig.tilesize);
            }
        }
    }

    public void drawRotatedImg(Graphics2D g, Image img ,int x, int y , char direction){
        if(img == null) return ;
        int tileSize = GameConfig.tilesize ;
        AffineTransform oldTransform = g.getTransform();
        int centerX = x + tileSize / 2 ;
        int centerY = y + tileSize / 2 ;

        double angle = 0 ;
        switch (Character.toUpperCase(direction)) {
            case 'D': angle = 0 ; break;
            case 'S': angle = Math.toRadians(90) ; break;
            case 'A': angle = Math.toRadians(180) ; break;
            case 'W': angle = Math.toRadians(270) ; break;
        }
        int drawSize = tileSize+ tileSize / 4 ;
        int offset = (drawSize - tileSize) / 2 ;
        g.rotate(angle, centerX, centerY);
        g.drawImage(img, x-offset, y-offset, drawSize, drawSize, null);
        g.setTransform(oldTransform);
    }

    private void drawTail(Graphics2D g, Point tailPoint, Point prevPoint){
        int tileSize = GameConfig.tilesize ;
        g.setColor(GameConfig.wormcolor);
        int centerX = tailPoint.x + tileSize / 2 ;
        int centerY = tailPoint.y + tileSize / 2 ; 

        double angle = 0;
        if (prevPoint.x > tailPoint.x) angle =  0;
        else if (prevPoint.y > tailPoint.y) angle = Math.toRadians(90);
        else if (prevPoint.x < tailPoint.x) angle = Math.toRadians(180);
        else if (prevPoint.y < tailPoint.y) angle = Math.toRadians(270);

        AffineTransform olTransform = g.getTransform();
        g.rotate(angle, centerX, centerY);

        g.fillOval(tailPoint.x, tailPoint.y, tileSize, tileSize);

    
         int tailLength = (int) (tileSize * 0.85); // ความยาวปลายหาง
        int[] xPoints = {
            centerX - tailLength,
            centerX,
            centerX 
        };
        int[] yPoints = {
            centerY,                      
            centerY - (tileSize / 2),
            centerY + (tileSize / 2) 
        };
        g.fillPolygon(xPoints, yPoints, 3);

        int tipSize = (int) (tileSize * 0.35);
        g.fillOval(centerX - tailLength - (tipSize / 2), centerY - (tipSize / 2), tipSize, tipSize);
        g.setTransform(olTransform);
    }

}
