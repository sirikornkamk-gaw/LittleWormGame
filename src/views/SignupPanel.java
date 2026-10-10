package views;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import config.GameConfig;
import models.ReadFile;
import models.User;
import models.WriteFile;

public class SignupPanel extends JPanel implements ActionListener {
    private JPanel cardContainer ;
    private GreenTagWarnning tagwarnning ;
    private JLabel ltitle1 , ltitle2 , lsignup  ;
    private JButton bsignup, bquit ;
    private JTextField tUser ;
    private JPasswordField pf1, pf2 ;

    // ReadFile rf;
    // WriteFile wf;
    private User user;

    public SignupPanel(){
        // rf = new ReadFile();
        // wf = new WriteFile();
        user = new User();
        
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

        layeredPane.add(cardContainer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(tagwarnning, JLayeredPane.POPUP_LAYER);

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
        
        
        lsignup = new JLabel("SIGN UP") ;
        lsignup.setForeground(GameConfig.White);
        lsignup.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 128f));
        lsignup.setBounds(144, 226, 334, 118);
        cardContainer.add(lsignup);

        tUser = new JTextField() ;
        tUser.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        tUser.setBounds(77, 416, 446, 60);
        UIText.setupJTextfield(tUser, " Username");
        cardContainer.add(tUser);

        pf1 = new JPasswordField() ;
        pf1.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        pf1.setBounds(77, 513, 446, 60);
        UIText.setupJPasswordfield(pf1, " Password");
        cardContainer.add(pf1);

        pf2 = new JPasswordField() ;
        pf2.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        pf2.setBounds(77, 610, 446, 60);
        UIText.setupJPasswordfield(pf2, " Password");
        cardContainer.add(pf2);

        bsignup = new RoundedButton("SIGN UP", 50) ;
        bsignup.setBackground(GameConfig.LightGreen);
        bsignup.setForeground(GameConfig.Beige);
        bsignup.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bsignup.setBounds(190, 715, 220, 68);
        bsignup.setFocusPainted(false);
        cardContainer.add(bsignup);

        bquit = new RoundedButton("X", 25) ;
        bquit.setBackground(GameConfig.LightGreen);
        bquit.setForeground(GameConfig.PinkBG);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 36f));
        bquit.setBounds(24, 21, 53, 39);
        bquit.setFocusPainted(false);
        cardContainer.add(bquit);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        add(layeredPane, gbc);

        bquit.addActionListener(this);
        bsignup.addActionListener(this);
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
        String password1 = new String(pf1.getPassword());
        String password2 = new String(pf2.getPassword());
        if (e.getSource() == bquit) {
            this.setVisible(false);
        } else if (e.getSource() == bsignup) {
            // if (Username.trim().isEmpty() || 
            // password1.trim().isEmpty() ||
            // password2.trim().isEmpty() ||
            // Username.equals(" Username") || 
            // password1.equals(" Password")||
            // password2.equals(" Password"
            // ))
            boolean isAccountVaild = user.isUsernameAndPasswordVaild(Username, password1);
            if (!isAccountVaild)  
        {
                tagwarnning.ShowtagWarnning("Notice!", "Please Fill In\nAll Fields!");
                tagwarnning.setVisible(true);
                tagwarnning.getParent().revalidate();
                tagwarnning.getParent().repaint();
                return ;
            }  
            if (Username.length() >= 20) {
                tagwarnning.ShowtagWarnning("Oops!", "User must\nbe <= 20 char!");
                tagwarnning.setVisible(true);
                return ;
            }
            if (!password1.equals(password2)) {
                tagwarnning.ShowtagWarnning("Oops!", "Password\nDo not Match!");
                tagwarnning.setVisible(true);
                return ;
            }
            boolean isAccountExist = user.isContainUsername(Username);
            if (isAccountExist == true) {
                tagwarnning.ShowtagWarnning("Oops!", "Username\nAlready taken!");
                tagwarnning.setVisible(true);
                tagwarnning.setVisible(true);

            } else {
            
                user.createNewAccount(Username, password1);
                tagwarnning.ShowtagWarnning("Success!", "Account Created\nSuccessfully!");
                tagwarnning.setVisible(true);
                tUser.setText("");
                pf1.setText("");
                pf2.setText("");
                // this.setVisible(false);
            }
        }
    }
}
