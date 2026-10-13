package views;

import javax.swing.*;
import config.GameConfig;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ActionListener{
    private MainFrame mainFrame ;
    private JLabel ltitle1 , ltitle2  ;
    private JButton bstart, blogin, bsignup ;
    private pinkTagWarnning noLoginWarnnig ;

    private LoginPanel loginPage ;
    private SignupPanel signupPage ;

    public MainMenuPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame ;
        setLayout(null);                               
        setBackground(GameConfig.LightGreen);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, 1920, 1080);          

        noLoginWarnnig = new pinkTagWarnning();
        noLoginWarnnig.setBounds(0, 0, 1920, 1080);       
        noLoginWarnnig.setVisible(false);
        layeredPane.add(noLoginWarnnig, JLayeredPane.POPUP_LAYER);

        signupPage = new SignupPanel();
        signupPage.setBounds(0, 0, 1920, 1080);
        signupPage.setVisible(false);
        layeredPane.add(signupPage, JLayeredPane.POPUP_LAYER);

        loginPage = new LoginPanel(this.mainFrame, this.signupPage);
        loginPage.setBounds(0, 0, 1920, 1080);
        loginPage.setVisible(false);
        layeredPane.add(loginPage, JLayeredPane.DRAG_LAYER);
        
        
        ltitle1 = new JLabel("Little Worm") ;
        ltitle1.setForeground(GameConfig.Beige);
        ltitle1.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 128f));
        ltitle1.setBounds(128, 178, 691, 143);
        layeredPane.add(ltitle1);

        ltitle2 = new JLabel("Game") ;
        ltitle2.setForeground(GameConfig.Beige);
        ltitle2.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 128f));
        ltitle2.setBounds(268, 321, 406, 155);
        layeredPane.add(ltitle2);

        bstart = new RoundedButton("Start Game", 50) ;
        bstart.setBackground(GameConfig.PinkBG);
        bstart.setForeground(GameConfig.White);
        bstart.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 96f));
        bstart.setBounds(218, 525, 487, 123);
        bstart.setFocusPainted(false);
        layeredPane.add(bstart);

        blogin = new RoundedButton("Log In", 50) ;
        blogin.setBackground(GameConfig.PinkBG);
        blogin.setForeground(GameConfig.White);
        blogin.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        blogin.setBounds(302, 705, 326, 81);
        blogin.setFocusPainted(false);
        layeredPane.add(blogin);

        bsignup = new RoundedButton("Sign Up", 50) ;
        bsignup.setBackground(GameConfig.PinkBG);
        bsignup.setForeground(GameConfig.White);
        bsignup.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bsignup.setBounds(302, 826, 326, 81);
        bsignup.setFocusPainted(false);
        layeredPane.add(bsignup);

        add(layeredPane);

        bstart.addActionListener(this);
        blogin.addActionListener(this);
        bsignup.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        double s = Math.min(getWidth() / 1920.0, getHeight() / 1080.0);
        g2.scale(s, s);
        MenuClover.drawClover(g2);
        g2.dispose();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == bstart) {
        if (GameConfig.isLogin == false) {
            noLoginWarnnig.setVisible(true);
            noLoginWarnnig.ShowtagWarnning("Notice!", "Please Log In\nFirst!");
        }else{
            mainFrame.switchView(new GamePanel(this.mainFrame)) ;
        }
            
       } else if (e.getSource() == blogin) {
            loginPage.setVisible(true);
       } else if(e.getSource() == bsignup){
            signupPage.setVisible(true);
       }
    }
}
