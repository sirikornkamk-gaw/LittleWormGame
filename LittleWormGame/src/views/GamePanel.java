package views;
import javax.swing.* ;
import java.awt.* ;
import java.util.ArrayList;
import java.util.List;
import config.GameConfig;

public class GamePanel extends JPanel {
    private WormDrawer wormDrawer ;
    private List<Point> Body ;
    private JLabel lscore ;
    private JButton bPause ;
    private Character currentDirection ;
    private Boolean isOpenMouth ;
    public GamePanel(){
        setLayout(null);
        setBackground(GameConfig.PinkBG);

        lscore = new JLabel(getScore()) ;
        lscore.setForeground(GameConfig.DarkerGreen);
        lscore.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 96f));
        lscore.setBounds(190, 50, 150, 80);
        add(lscore);

        bPause = new RoundedButton("Pause", 50) ;
        bPause.setBackground(GameConfig.Beige);
        bPause.setForeground(GameConfig.DarkerGreen);
        bPause.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 94f));
        bPause.setBounds(1559, 30, 281, 114);
        bPause.setFocusPainted(false);
        add(bPause);

        wormDrawer = new WormDrawer();
        Body = new ArrayList<>();
        int size = GameConfig.tilesize;
        int startX = 925 ;
        int startY = 525 ;
        for (int i = 0; i < 5; i++) {
        Body.add(new Point(startX - (i * size), startY));
        }
        isOpenMouth = false ;
        currentDirection = 'D';

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g ;
        g2.setColor(GameConfig.Beige);
        g2.fillRoundRect(80, 30, 281, 114, 50, 50);
        AppleDrawer.drawApple(g2, 110, 65, 41, 53, GameConfig.RedApple);
        g2.setColor(GameConfig.GreenBlue);
        g2.fillRect(65, 156, 1790, 880);
        int r = 25 , c = 12 ;
        int tileSize = GameConfig.tilesize ;
        for(int i = 0 ; i< r ; i++){
            for(int j = 0 ; j < c ; j++){
                if ((i+j) % 2 == 0) {
                    g2.setColor(GameConfig.DarkGreen);
                } else {
                    g2.setColor(GameConfig.Greentable);
                }
                g2.fillRect(85 + (i *tileSize), 176 + (j * tileSize) , tileSize, tileSize);
            }
        }
        wormDrawer.drawWorm(g2, Body, currentDirection, isOpenMouth);
    }

    public String getScore(){
        return "0000" ;
    }

}

