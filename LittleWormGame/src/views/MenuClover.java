package views;

import config.GameConfig;
import java.awt.* ;

public class MenuClover  {
    public static void drawClover(Graphics2D g) {
        g.setColor(GameConfig.Greenshadow);
        g.fillOval(860, 793, 1113, 210);
        
        g.setColor(GameConfig.RedApple);
        g.fillOval(999, 145, 651, 753);
        g.fillOval(1331, 154, 651, 753);
        g.setColor(GameConfig.LightRedApple);
        g.fillOval(1162, 187, 192, 159);
        g.setColor(GameConfig.PinkBG);
        g.fillOval(1215, 192, 89, 62);
        g.setColor(GameConfig.Applepulp);
        g.fillOval(1380, 541, 215, 214);
        g.fillOval(1491, 508, 215, 214);
        g.fillOval(1507, 691, 103, 107);
        g.fillOval(1561, 685, 178, 140);
        g.fillOval(1647, 602, 121, 143);
        g.setColor(GameConfig.Applestalk);
        g.fillRect(1490, 100, 52, 150);

        g.setColor(GameConfig.wormcolor);
        g.fillOval(1438, 797, 157, 165);
        g.fillOval(1340, 834, 157, 165);
        g.fillOval(1261, 776, 157, 165);
        g.fillOval(1163, 797, 157, 165);
        g.fillOval(1072, 772, 157, 165);
        g.fillOval(985, 819, 157, 165);
        g.fillOval(903, 768, 157, 165);
        g.fillOval(833, 599, 227, 235);

        g.fillRect(909, 529, 20, 80);
        g.fillRect(980, 534, 20, 80);
        g.fillOval(877, 503, 52, 49);
        g.fillOval(980, 503, 52, 49);

        g.setColor(GameConfig.White);
        g.fillOval(946, 654, 79, 98);
        g.fillOval(868, 654, 79, 98);

        g.setColor(GameConfig.Black);
        g.fillOval(880, 697, 52, 49);
        g.fillOval(959, 697, 52, 49);

        g.setColor(GameConfig.DarkPink);
        g.fillOval(934, 763, 23, 25);
    }
}
