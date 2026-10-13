package views;
import javax.swing.*;
import config.GameConfig;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PausePanel extends JPanel implements ActionListener{
    private MainFrame mainFrame ;
    private GamePanel gamePanel ;
    JLabel ltitle ;
    JButton bresume , brestart, bquit ;
    private JPanel cardContainer ;


    public PausePanel(MainFrame mainFrame, GamePanel gamePanel){
        super();
        this.mainFrame = mainFrame ;
        this.gamePanel = gamePanel ;
        setLayout(null);
        setOpaque(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {});
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});

        cardContainer = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                double s = Math.min(getWidth() / 600.0, getHeight() / 700.0);
                g2.scale(s, s);

                g2.setColor(GameConfig.Beige);
                g2.fillRoundRect(10, 15, 580, 680, 100, 100);
                g2.setColor(GameConfig.Yellow);
                g2.fillRoundRect(27, 51, 543, 260, 50, 50);
                g2.dispose();
                
            }
        };
        cardContainer.setLayout(null);
        cardContainer.setPreferredSize(new Dimension(600,700));
        cardContainer.setOpaque(false);
        cardContainer.setBounds(0, 0, 600, 700);
        cardContainer.addMouseListener(new java.awt.event.MouseAdapter() {});


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(660, 190, 600, 700);

        layeredPane.add(cardContainer, JLayeredPane.DEFAULT_LAYER);

        ltitle = new JLabel("PAUSE") ;
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 130f));
        ltitle.setBounds(162, 110, 276, 141);
        cardContainer.add(ltitle);

        bresume = new RoundedButton("Resume", 50) ;
        bresume.setBackground(GameConfig.PinkBG);
        bresume.setForeground(GameConfig.GreenBlue);
        bresume.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bresume.setBounds(112, 336, 373, 95);
        bresume.setFocusPainted(false);
        cardContainer.add(bresume);

        brestart = new RoundedButton("Restart", 50) ;
        brestart.setBackground(GameConfig.PinkBG);
        brestart.setForeground(GameConfig.GreenBlue);
        brestart.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        brestart.setBounds(112, 450, 373, 95);
        brestart.setFocusPainted(false);
        cardContainer.add(brestart);

        bquit = new RoundedButton("Quit", 50) ;
        bquit.setBackground(GameConfig.PinkBG);
        bquit.setForeground(GameConfig.GreenBlue);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bquit.setBounds(112, 564, 373, 95);
        bquit.setFocusPainted(false);
        cardContainer.add(bquit);


        add(layeredPane);

        bresume.addActionListener(this);
        bquit.addActionListener(this);
        brestart.addActionListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g ;

        Color dimColor = new Color(0, 0, 0, 150);
        g2.setColor(dimColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bresume) {
            gamePanel.gameStart();
            this.setVisible(false);
        } else if(e.getSource() == brestart){
            gamePanel.gameReset();
            this.setVisible(false);
        } else if (e.getSource() == bquit) {
            gamePanel.gameStop();
            mainFrame.switchView(new MainMenuPanelAfterLogin(mainFrame));
        }
    }
}
