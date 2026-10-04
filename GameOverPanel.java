package views;


import javax.swing.* ;
import java.awt.* ;
import config.GameConfig;

public class GameOverPanel extends JPanel {
    private MainFrame mainFrame ;
    JLabel ltitle ;
    JButton btryagain , bquit ;

    public GameOverPanel(){
        super();
        setLayout(null);
        setPreferredSize(new Dimension(600,700));
        setOpaque(false);

        ltitle = new JLabel("Game Over") ;
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 128f));
        ltitle.setBounds(61, 112, 480, 137);
        add(ltitle);

        btryagain = new RoundedButton("Try Again", 50) ;
        btryagain.setBackground(GameConfig.PinkBG);
        btryagain.setForeground(GameConfig.GreenBlue);
        btryagain.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        btryagain.setBounds(112, 379, 373, 95);
        btryagain.setFocusPainted(false);
        add(btryagain);


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


