package views;
import javax.swing.*;
import config.GameConfig;
import models.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;



public class LoginPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame ;
    private SignupPanel signupPage ;
    private GreenTagWarnning tagwarnning ;
    private JPanel cardContainer ;
    private JLabel ltitle1 , ltitle2 , llogin , lwarning ;
    private JButton blogin, bsignup, bquit ;
    private JTextField tUser ;
    private JPasswordField pf ;
    
    private User user;
    
    public LoginPanel(MainFrame mainFrame, SignupPanel signupPage){
        user = new User();
        this.mainFrame = mainFrame ;
        this.signupPage = signupPage;
        setLayout(new GridBagLayout());
        setOpaque(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {});
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});

        

        cardContainer = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(GameConfig.GreenBlue);
                g2.fillRect(0, 0, 600, 900);
                g2.setColor(GameConfig.PinkBG);
                g2.fillRect(5, 4, 590, 890);
                AppleDrawer.RotateApple(g2, -68, 836, 235, 273, GameConfig.RedApple,14.43);
                AppleDrawer.RotateApple(g2, 332, 810, 235, 273, GameConfig.GreenApple,-20.12);
                AppleDrawer.RotateApple(g2, -84, 181, 93, 134, GameConfig.GreenApple,35.58);
                AppleDrawer.RotateApple(g2, 485, -20, 93, 134, GameConfig.RedApple,-132.27);
            }
        };
        cardContainer.setLayout(null);
        cardContainer.setPreferredSize(new Dimension(600,900));
        cardContainer.setOpaque(false);
        cardContainer.setBounds(0, 0, 600, 900);
        cardContainer.addMouseListener(new java.awt.event.MouseAdapter() {});

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(600,900));

        tagwarnning = new GreenTagWarnning();
        tagwarnning.setBounds(0, 0, 600, 900);
        tagwarnning.setVisible(false);

        signupPage = new SignupPanel();
        signupPage.setVisible(false);
        signupPage.setBounds(0, 0, 600, 900);

        
        
        layeredPane.add(cardContainer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(tagwarnning, JLayeredPane.POPUP_LAYER);
        layeredPane.add(signupPage, JLayeredPane.POPUP_LAYER);

        ltitle1 = new JLabel("Little Worm") ;
        ltitle1.setForeground(GameConfig.GreenBlue);
        ltitle1.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 64f));
        ltitle1.setBounds(133, 72, 340, 77);
        cardContainer.add(ltitle1);

        ltitle2 = new JLabel("Game") ;
        ltitle2.setForeground(GameConfig.GreenBlue);
        ltitle2.setFont(FontLoader.loadFont(GameConfig.Irish_Grover, 64f));
        ltitle2.setBounds(212, 153, 200, 77);
        cardContainer.add(ltitle2);
        
        
        llogin = new JLabel("LOG IN") ;
        llogin.setForeground(GameConfig.White);
        llogin.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 128f));
        llogin.setBounds(176, 238, 270, 118);
        cardContainer.add(llogin);

        tUser = new JTextField() ;
        tUser.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        tUser.setBounds(77, 426, 445, 60);
        UIText.setupJTextfield(tUser, " Username");
        cardContainer.add(tUser);

        pf = new JPasswordField() ;
        pf.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        pf.setBounds(77, 518, 445, 60);
        UIText.setupJPasswordfield(pf, " Password");
        cardContainer.add(pf);

        blogin = new RoundedButton("LOG IN", 50) ;
        blogin.setBackground(GameConfig.LightGreen);
        blogin.setForeground(GameConfig.White);
        blogin.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        blogin.setBounds(190, 604, 220, 68);
        blogin.setFocusPainted(false);
        cardContainer.add(blogin);

        lwarning = new JLabel("If you Don't have an account, Please");
        lwarning.setForeground(GameConfig.White);
        lwarning.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 32f));
        lwarning.setBounds(96, 684, 421, 34);
        cardContainer.add(lwarning);
        
        bsignup = new RoundedButton("SIGN UP", 50) ;
        bsignup.setBackground(GameConfig.Beige);
        bsignup.setForeground(GameConfig.LightGreen);
        bsignup.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 40f));
        bsignup.setBounds(216, 734, 173, 46);
        bsignup.setFocusPainted(false);
        cardContainer.add(bsignup);

        bquit = new RoundedButton("X", 25) ;
        bquit.setBackground(GameConfig.LightGreen);
        bquit.setForeground(GameConfig.PinkBG);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        bquit.setBounds(24, 21, 53, 39);
        bquit.setFocusPainted(false);
        cardContainer.add(bquit);

        bquit.addActionListener(this);
        blogin.addActionListener(this);
        bsignup.addActionListener(this);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        add(layeredPane, gbc);

        
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
        String Username = tUser.getText();
        String password = new String(pf.getPassword());
        if (e.getSource() == bquit) {
            this.setVisible(false);
        } else if (e.getSource() == blogin) {
            if (Username.trim().isEmpty() || 
            password.trim().isEmpty() ||
            Username.equals(" Username") || 
            password.equals(" Password")) {
                tagwarnning.ShowtagWarnning("Notice!", "Please Fill In\nAll Fields!");
                tagwarnning.setVisible(true);
                tagwarnning.getParent().revalidate();
                tagwarnning.getParent().repaint();
                return ;
            }

            boolean isAccountExist = user.loginAccount(Username, password);
            if (isAccountExist) {
                tagwarnning.ShowtagWarnning("Success!", "Welcome to\nlittle Worm Game!");
                this.setVisible(false);
                int userHighscore = Integer.parseInt(user.getScore());
                GameConfig.saveSession(true, Username,userHighscore);
                mainFrame.loginSuccess(Username, String.valueOf(userHighscore));
            } else {
                tagwarnning.ShowtagWarnning("Failed!", "Invalid\nUsername or Password");
                tagwarnning.setVisible(true);
            }

            // try (BufferedReader br = new BufferedReader(new FileReader(GameConfig.userData))) {
            //     String s ;
            //     boolean isAc = false ;
            //     while ((s = br.readLine()) != null) {
            //         String arr[] = s.split(",");
            //         if (Username.equals(arr[0]) && password.equals(arr[1])) {
            //             tagwarnning.ShowtagWarnning("Success!", "Welcome to\nlittle Worm Game!");
            //             isAc = true ;
            //             this.setVisible(false);
            //             int userHighscore = Integer.parseInt(arr[2].trim());
            //             GameConfig.saveSession(true, Username,userHighscore);
            //             mainFrame.loginSuccess(Username, String.valueOf(userHighscore));
            //             break;
            //         }
            //     }
            // br.close();
                
                // if (isAccountExist == false) {
                // }
                // } catch (Exception a) {
            //     a.printStackTrace();
            // }
        }else if (e.getSource() == bsignup) {
            this.setVisible(false);
            signupPage.setVisible(true);
        }

    }

}


