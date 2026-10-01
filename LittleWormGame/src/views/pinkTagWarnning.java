package views;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import config.GameConfig;



public class pinkTagWarnning extends JPanel {
    private JLabel ltitle, lmessage ;

    public pinkTagWarnning(){
        setOpaque(false);
        setLayout(new GridBagLayout());
        JPanel cardContaiPanel = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g ;
                g2.setColor(GameConfig.PinkBG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 100, 100);
            }
        };

        cardContaiPanel.setOpaque(false);
        cardContaiPanel.setPreferredSize(new Dimension(500,400));
        cardContaiPanel.setLayout(new BorderLayout(15,25));
        cardContaiPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        JPanel headpanel = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g ;
                g2.setColor(GameConfig.LightGreen);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
            }
        };
        headpanel.setOpaque(false);
        headpanel.setPreferredSize(new Dimension(450,185));
        headpanel.setLayout(new GridBagLayout());

        ltitle = new JLabel() ;
        ltitle.setForeground(GameConfig.White);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 110f));
        headpanel.add(ltitle);

        lmessage = new JLabel() ;
        lmessage.setForeground(GameConfig.GreenBlue);
        lmessage.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 48f));
        lmessage.setHorizontalAlignment(SwingConstants.CENTER);

        cardContaiPanel.add(headpanel, BorderLayout.NORTH);
        cardContaiPanel.add(lmessage,BorderLayout.CENTER);

        add(cardContaiPanel) ;
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }    

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void ShowtagWarnning(String title, String message){
        this.ltitle.setText(title);
        Font customFont = FontLoader.loadFont(GameConfig.Jersey_10, 48f);
        this.lmessage.setFont(customFont);
        this.lmessage.setForeground(GameConfig.GreenBlue);
        String formattedmsg = "<html><center>" 
                        + message.replaceAll("\n", "<br>") 
                        + "</center></html>";

        this.lmessage.setText(formattedmsg);
        this.setVisible(true);
    }
    
    
}
