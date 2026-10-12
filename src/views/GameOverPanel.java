package views;


import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import config.GameConfig;

public class GameOverPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame ;
    private GamePanel gamePanel ;
    private JPanel cardContainer ;
    JLabel ltitle ;
    JButton btryagain , bquit ;

    public GameOverPanel(MainFrame mainFrame, GamePanel gamePanel){
        super();
        this.mainFrame = mainFrame ;
        this.gamePanel = gamePanel ;

        setLayout(new GridBagLayout());
        setOpaque(false);

        this.addMouseListener(new java.awt.event.MouseAdapter() {});
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {});

        cardContainer = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GameConfig.Beige);
                g2.fillRoundRect(10, 15, 580, 680, 100, 100);
                g2.setColor(GameConfig.Yellow);
                g2.fillRoundRect(27, 51, 543, 260, 50, 50);
                
            }
        };
        cardContainer.setLayout(null);
        cardContainer.setPreferredSize(new Dimension(600,700));
        cardContainer.setOpaque(false);
        cardContainer.setBounds(0, 0, 600, 700);
        cardContainer.addMouseListener(new java.awt.event.MouseAdapter() {});


        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(600,900));

        layeredPane.add(cardContainer, JLayeredPane.DEFAULT_LAYER);

        ltitle = new JLabel("Game Over") ;
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 128f));
        ltitle.setBounds(61, 112, 480, 137);
        cardContainer.add(ltitle);

        btryagain = new RoundedButton("Try Again", 50) ;
        btryagain.setBackground(GameConfig.PinkBG);
        btryagain.setForeground(GameConfig.GreenBlue);
        btryagain.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        btryagain.setBounds(112, 379, 373, 95);
        btryagain.setFocusPainted(false);
        cardContainer.add(btryagain);


        bquit = new RoundedButton("Quit", 50) ;
        bquit.setBackground(GameConfig.PinkBG);
        bquit.setForeground(GameConfig.GreenBlue);
        bquit.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 64f));
        bquit.setBounds(113, 520, 373, 95);
        bquit.setFocusPainted(false);
        cardContainer.add(bquit);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0 ;
        gbc.gridy = 0 ;
        gbc.anchor = GridBagConstraints.CENTER ;
        add(layeredPane, gbc);

        bquit.addActionListener(this);
        btryagain.addActionListener(this);
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
        if (e.getSource() == bquit) {
            mainFrame.switchView(new MainMenuPanelAfterLogin(this.mainFrame));
        } else if(e.getSource() == btryagain){
            this.setVisible(false);
            gamePanel.gameReset();
        }
    }

    
}


