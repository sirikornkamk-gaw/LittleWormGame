package views;

import javax.swing.* ;
import java.awt.* ;
import config.GameConfig;

public class GameWinPanel extends JPanel {
    private MainFrame mainFrame ;
    JLabel ltitle, lsubtitle ;
    JButton bplayagain , bquit ;

    public GameWinPanel(){
        super();
        setLayout(null);
        setPreferredSize(new Dimension(600,700));
        setOpaque(false);

        ltitle = new JLabel("Yummy!") ;
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 130f));
        ltitle.setBounds(133, 65, 384, 141);
        add(ltitle);

        lsubtitle = new JLabel("You ate them all!") ;
        lsubtitle.setForeground(GameConfig.GreenBlue);
        lsubtitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        lsubtitle.setBounds(113, 206, 384, 69);
        add(lsubtitle);

        bplayagain = new RoundedButton("Play Again", 50) ;
        bplayagain.setBackground(GameConfig.PinkBG);
        bplayagain.setForeground(GameConfig.GreenBlue);
        bplayagain.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bplayagain.setBounds(112, 379, 373, 95);
        bplayagain.setFocusPainted(false);
        add(bplayagain);


        bquit = new RoundedButton("Quit", 50) ;
        bquit.setBackground(GameConfig.PinkBG);
        bquit.setForeground(GameConfig.GreenBlue);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bquit.setBounds(113, 520, 373, 95);
        bquit.setFocusPainted(false);
        add(bquit);

    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g ;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(GameConfig.Beige);
        g2.fillRoundRect(0, 0, 600, 700, 100, 100);
        g2.setColor(GameConfig.Yellow);
        g2.fillRoundRect(27, 51, 543, 260, 50, 50);


    }
}