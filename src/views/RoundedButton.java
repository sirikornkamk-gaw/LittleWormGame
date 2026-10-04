package views;

import java.awt.*;
import javax.swing.*;

import config.GameConfig;



public class RoundedButton extends JButton{
    private int cornerRadius ;
    private Color oriTextColor ;

    public RoundedButton(String text, int radius){
        super(text) ;
        this.cornerRadius = radius ;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
    }
public void setForeground(Color fg){
    if(!getModel().isRollover()){
        this.oriTextColor = fg ;
    }
    super.setForeground(fg);
}

    public void paintComponent(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g.create();
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color baseColor = getBackground() ;
        if (getModel().isPressed()) {
            g2.setColor(baseColor.darker());
            if (oriTextColor != null) super.setForeground(oriTextColor);
        }else if(getModel().isRollover()){
            
            if (baseColor.equals(GameConfig.PinkBG)) {
                setForeground(baseColor);
            }
            g2.setColor(baseColor.brighter());

        }else {
            g2.setColor(baseColor);
            if (oriTextColor != null) super.setForeground(oriTextColor);
            
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();

        super.paintComponent(g);
    }
}
