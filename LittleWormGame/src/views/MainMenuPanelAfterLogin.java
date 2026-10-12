package views;

import javax.swing.*;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.GameConfig;
import models.User;

public class MainMenuPanelAfterLogin extends  JPanel implements ActionListener{
    private MainFrame mainFrame ;
    private JLabel ltitle1 , ltitle2, lHsc, lscore, luserN  ;
    private JButton bstart, bcon, blogout ;
    private GreenTagWarnning tagWarnning ;
    private pinkTagWarnning noLoginWarnnig ;

    private User user;


    public MainMenuPanelAfterLogin(MainFrame mainFrame){
        user = new User();
        this.mainFrame = mainFrame ;
        setLayout(null);
        setBackground(GameConfig.LightGreen);

        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        tagWarnning = new GreenTagWarnning();
        tagWarnning.setVisible(false);
        layeredPane.add(tagWarnning, JLayeredPane.POPUP_LAYER);

        noLoginWarnnig = new pinkTagWarnning();
        noLoginWarnnig.setVisible(false);
        layeredPane.add(noLoginWarnnig, JLayeredPane.POPUP_LAYER);
        add(layeredPane);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            layeredPane.setBounds(0, 0, getWidth(), getHeight());
            tagWarnning.setBounds(0, 0, getWidth(), getHeight());
            noLoginWarnnig.setBounds(0, 0, getWidth(), getHeight());
            revalidate();
            }
        });
        
        ltitle1 = new JLabel("Little Worm") ;
        ltitle1.setForeground(GameConfig.Beige);
        ltitle1.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 128f));
        ltitle1.setBounds(153, 82, 691, 143);
        add(ltitle1);

        ltitle2 = new JLabel("Game") ;
        ltitle2.setForeground(GameConfig.Beige);
        ltitle2.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 128f));
        ltitle2.setBounds(293, 225, 406, 155);
        add(ltitle2);

        bstart = new RoundedButton("Start Game", 50) ;
        bstart.setBackground(GameConfig.PinkBG);
        bstart.setForeground(GameConfig.White);
        bstart.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 96f));
        bstart.setBounds(243, 429, 487, 123);
        bstart.setFocusPainted(false);
        add(bstart);

        bcon = new RoundedButton("Continue", 50) ;
        bcon.setBackground(GameConfig.PinkBG);
        bcon.setForeground(GameConfig.White);
        bcon.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bcon.setBounds(324, 614, 326, 81);
        bcon.setFocusPainted(false);
        add(bcon);

        blogout = new RoundedButton("Log Out", 50) ;
        blogout.setBackground(GameConfig.PinkBG);
        blogout.setForeground(GameConfig.White);
        blogout.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        blogout.setBounds(324, 738, 326, 81);
        blogout.setFocusPainted(false);
        add(blogout);

        lHsc = new JLabel("High Score") ;
        lHsc.setForeground(GameConfig.White);
        lHsc.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 50f));
        lHsc.setBounds(74, 930, 450, 60);
        add(lHsc);

        lscore = new JLabel() ;
        lscore.setForeground(GameConfig.White);
        lscore.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 128f));
        lscore.setBounds(310, 915, 450, 60);
        add(lscore);

        int paddingRight = 60 ;


        luserN = new JLabel() ;
        luserN.setForeground(GameConfig.White);
        luserN.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        luserN.setHorizontalAlignment(SwingConstants.RIGHT);
        add(luserN);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int actualWidth = getWidth(); 
                int labelWidth = actualWidth - paddingRight;
                luserN.setBounds(0, 24, labelWidth, 70);
            }
        });

        bstart.addActionListener(this);
        bcon.addActionListener(this);
        blogout.addActionListener(this);

        if (GameConfig.isLogin) {
            showUserName(GameConfig.currentUser);
            showHighScore(String.valueOf(GameConfig.highScore));
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        MenuClover.drawClover(g2);
    }

    public void showHighScore(String highScore){
        int score = Integer.parseInt(highScore);
        String scoreText = String.format("%04d", score);
        this.lscore.setText(scoreText);
    }

    public void showUserName(String username){
        this.luserN.setText(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bstart) {
            if (GameConfig.isLogin == false) {
                noLoginWarnnig.setVisible(true);
                noLoginWarnnig.ShowtagWarnning("Notice!", "Please Log In\nFirst!");
                
            }else{
                GameConfig.savedGamePanel = null;
                mainFrame.switchView(new GamePanel(this.mainFrame));
            }

        }else if (e.getSource() == blogout) {
            user.logoutAccount();
            GameConfig.savedGamePanel = null;
            MainMenuPanel mainMenuPanel = new MainMenuPanel(this.mainFrame);
            mainFrame.switchView(mainMenuPanel);
        }else if (e.getSource() == bcon) {
            GamePanel gamePanel = GameConfig.savedGamePanel ;
            if (gamePanel == null) {
                gamePanel = new GamePanel(this.mainFrame);
            } else {
                gamePanel.resumeGame();
            }
            mainFrame.switchView(gamePanel);
        }
    }
}
