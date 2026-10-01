package views;
import javax.swing.*;
import config.GameConfig;
import java.awt.*;

public class PausePanel extends JPanel{
    private MainFrame mainFrame ;
    JLabel ltitle ;
    JButton bresume , brestart, bquit ;

    public PausePanel(){
        super();
        setLayout(null);
        setPreferredSize(new Dimension(600,700));
        setOpaque(false);

        ltitle = new JLabel("PAUSE") ;
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 130f));
        ltitle.setBounds(162, 110, 276, 141);
        add(ltitle);

        bresume = new RoundedButton("Resume", 50) ;
        bresume.setBackground(GameConfig.PinkBG);
        bresume.setForeground(GameConfig.GreenBlue);
        bresume.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bresume.setBounds(112, 336, 373, 95);
        bresume.setFocusPainted(false);
        add(bresume);

        brestart = new RoundedButton("Restart", 50) ;
        brestart.setBackground(GameConfig.PinkBG);
        brestart.setForeground(GameConfig.GreenBlue);
        brestart.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        brestart.setBounds(112, 450, 373, 95);
        brestart.setFocusPainted(false);
        add(brestart);

        bquit = new RoundedButton("Quit", 50) ;
        bquit.setBackground(GameConfig.PinkBG);
        bquit.setForeground(GameConfig.GreenBlue);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bquit.setBounds(112, 564, 373, 95);
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
