package views;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.GameConfig;
import models.WriteFile;

public class SignupPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame ;
    private JPanel cardContainer ;
    JLabel ltitle1 , ltitle2 , lsignup  ;
    JButton bsignup, bquit ;
    JTextField tUser ;
    JPasswordField pf1, pf2 ;
    WriteFile wf;

    public SignupPanel(){
        wf = new WriteFile();
        setLayout(new GridBagLayout());
        setOpaque(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {});
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});
        
        cardContainer = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(GameConfig.PinkBG);
                g2.fillRect(0, 0, 600, 900);
                AppleDrawer.RotateApple(g2, -68, 836, 235, 273, GameConfig.RedApple,14.43);
                AppleDrawer.RotateApple(g2, 332, 810, 235, 273, GameConfig.GreenApple,-20.12);
                AppleDrawer.RotateApple(g2, -84, 181, 93, 134, GameConfig.GreenApple,35.58);
                AppleDrawer.RotateApple(g2, 485, -20, 93, 134, GameConfig.RedApple,-132.27);
            }
        };
        cardContainer.setLayout(null);
        cardContainer.setPreferredSize(new Dimension(600,900));
        cardContainer.setOpaque(false);

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
        add(cardContainer, gbc);

        bquit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bquit) {
            this.setVisible(false);
        } else if (e.getSource() == bsignup) {
        }
    }
}
