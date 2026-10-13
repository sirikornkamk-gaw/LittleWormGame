package views;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import config.GameConfig;


public class GreenTagWarnning extends JPanel {
    private JLabel ltitle, lmessage ;

    public GreenTagWarnning(){
        setOpaque(false);
        setLayout(null);
        JPanel cardContaiPanel = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                double s = Math.min(getWidth() / 500.0, getHeight() / 400.0);
                g2.scale(s, s);

                g2.setColor(GameConfig.GreenBlue);
                g2.fillRoundRect(0, 0, 500, 400, 100, 100);
                g2.setColor(GameConfig.LightGreen);
                g2.fillRoundRect(10, 8, 480, 380, 100, 100);
                g2.dispose();
            }
        };

        cardContaiPanel.setOpaque(false);
        cardContaiPanel.setLayout(null);
        cardContaiPanel.setBounds(710, 340, 500, 400);

        JPanel headpanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                double s = Math.min(getWidth() / 450.0, getHeight() / 185.0);
                g2.scale(s, s);

                g2.setColor(GameConfig.PinkBG);
                g2.fillRoundRect(0, 0, 450, 185, 50, 50);
                g2.dispose();
            }
        };
        headpanel.setOpaque(false);
        headpanel.setLayout(null);
        headpanel.setBounds(25, 30, 450, 185);
        cardContaiPanel.add(headpanel);

        ltitle = new JLabel("", SwingConstants.CENTER);
        ltitle.setForeground(GameConfig.GreenBlue);
        ltitle.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 110f));
        ltitle.setBounds(0, 0, 450, 185);
        headpanel.add(ltitle);

        lmessage = new JLabel("", SwingConstants.CENTER);
        lmessage.setForeground(GameConfig.GreenBlue);
        lmessage.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 48f));
        lmessage.setBounds(25, 240, 450, 130);
        cardContaiPanel.add(lmessage);

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
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void ShowtagWarnning(String title, String message){
        this.ltitle.setText(title);
        this.lmessage.setText("<html><div style='text-align: center;'>" + message.replaceAll("\n", "<br>") + "</div></html>");
    }
    
    
}
