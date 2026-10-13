package views;
import javax.swing.*;

import config.GameConfig;


public class MainFrame extends JFrame {
    private JLayeredPane layeredPane ;
    private UIScaler scaler = new UIScaler();
    private JPanel currentPanel;


    public MainFrame(){
       

        setTitle("Little Worm Game");
        ImageIcon img = new ImageIcon("./assets/image/IconGame.png");
        setIconImage(img.getImage());
   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setOpaque(true);
        layeredPane.setBackground(GameConfig.LightGreen);
        setContentPane(layeredPane);


        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (currentPanel != null) {
                    scaler.scale(currentPanel, layeredPane.getWidth(), layeredPane.getHeight());
                }
            }
        });

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (currentPanel != null) {
                scaler.scale(currentPanel, layeredPane.getWidth(), layeredPane.getHeight());
                }
            }
        });

    setVisible(true);
        
        GameConfig.loadSession();
        if (!GameConfig.isLogin) {
            switchView(new MainMenuPanel(this));
        }else{
            switchView(new MainMenuPanelAfterLogin(this));
            
        }
       

        
    }

    public void switchView(JPanel newPanel) {
        layeredPane.removeAll();
        currentPanel = newPanel;

        UIScaler s = (UIScaler) newPanel.getClientProperty("uiScaler");
        if (s == null) {
            newPanel.setBounds(0, 0, 1920, 1080);   // ตั้งเฉพาะ panel ที่ยังไม่เคยถูก scale
            s = new UIScaler();
            newPanel.putClientProperty("uiScaler", s);
        }
        scaler = s;

        layeredPane.add(currentPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.setBackground(newPanel.getBackground());
        scaler.scale(currentPanel, layeredPane.getWidth(), layeredPane.getHeight());
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