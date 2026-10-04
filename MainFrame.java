package views;
import javax.swing.*;


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
        switchView(new MainMenuPanel(this));

        
    }

    public void switchView(JPanel newPanel){
        layeredPane.removeAll();
        newPanel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(newPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void loinSuccess(String username,String score){
        MainMenuPanelAfterLogin userMenuPanel = new MainMenuPanelAfterLogin(this);
        userMenuPanel.ShowUserName(username);
        userMenuPanel.ShowHighScore(score);
        switchView(userMenuPanel);
    }
}
