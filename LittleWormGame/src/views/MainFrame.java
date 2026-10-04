package views;
import javax.swing.*;

import config.GameConfig;


public class MainFrame extends JFrame {
    private JLayeredPane layeredPane ;

    public MainFrame(){
       

        setTitle("Little Worm Game");
        ImageIcon img = new ImageIcon("./assets/image/IconGame.png");
        setIconImage(img.getImage());
   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        setContentPane(layeredPane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        
        GameConfig.loadSession();
        if (!GameConfig.isLogin) {
            switchView(new MainMenuPanel(this));
        }else{
            switchView(new MainMenuPanelAfterLogin(this));
            
        }
        

        
    }

    public void switchView(JPanel newPanel){
        layeredPane.removeAll();
        newPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(newPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void loginSuccess(String username,String score){
        GameConfig.isLogin = true ;
        MainMenuPanelAfterLogin userMenuPanel = new MainMenuPanelAfterLogin(this);
        userMenuPanel.showUserName(username);
        userMenuPanel.showHighScore(score);
        switchView(userMenuPanel);
    }

}