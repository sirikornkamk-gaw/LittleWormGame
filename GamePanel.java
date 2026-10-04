package views;
import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import java.util.ArrayList;
import config.GameConfig;
import models.User;
import models.Worm;
import models.WriteFile;
import models.Apple;
import models.GameBoard;
import models.ReadFile;


public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private WormDrawer wormDrawer ;
    private JLabel lscore ;
    private JButton bPause ;
    private Character currentDirection ;
    private Boolean isOpenMouth ;
    private User user;
    private ReadFile rf ;
    private WriteFile wf ;
    
    private GameBoard gameBoard ;
    private Worm worm ;
    private Apple apple ;
    
    private ArrayList<Point> Body ;
    
    private Timer gameloop ;
    private float seconds = 0;
    private float despawnTimeSeconds = 10;

    public GamePanel() {
        user = new User();
        rf = new ReadFile();
        wf = new WriteFile();
        
        gameBoard = new GameBoard();
        worm = new Worm(5,5);
        apple = new Apple(7,5, 0);
        gameloop = new Timer(100, this);
     
        setLayout(null);
        setBackground(GameConfig.PinkBG);
        
        lscore = new JLabel(getScore()) ;
        lscore.setForeground(GameConfig.DarkerGreen);
        lscore.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 96f));
        lscore.setBounds(190, 50, 150, 80);
        add(lscore);
        
        bPause = new RoundedButton("Pause", 50) ;
        bPause.setBackground(GameConfig.Beige);
        bPause.setForeground(GameConfig.DarkerGreen);
        bPause.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 94f));
        bPause.setBounds(1559, 30, 281, 114);
        bPause.setFocusPainted(false);
        add(bPause);

        wormDrawer = new WormDrawer();
        Body = new ArrayList<>();
        int tileSize = GameConfig.tilesize;
       
        Body.add(new Point(worm.getX() * tileSize, worm.getY() * tileSize));
        // System.out.println(worm.getX() + ", " + worm.getY() );
        for (int i = 0; i < worm.getBodySize(); i++) {
            // System.out.println(worm.getBody().get(i).x + ", " +  (worm.getBody().get(i).y));
            Body.add(new Point(worm.getBody().get(i).x * tileSize, (worm.getBody().get(i).y * tileSize)));
        }
        
        isOpenMouth = false;
        currentDirection = worm.getDirection();
      
        gameStart();
       
        setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g ;
        g2.setColor(GameConfig.Beige);
        g2.fillRoundRect(80, 30, 281, 114, 50, 50);
        AppleDrawer.drawApple(g2, 110, 65, 41, 53, GameConfig.RedApple);
        g2.setColor(GameConfig.GreenBlue);
        g2.fillRect(65, 156, 1790, 880);
        int r = 25 , c = 12 ;
        int tileSize = GameConfig.tilesize ;
        for(int i = 0 ; i< r ; i++){
            for(int j = 0 ; j < c ; j++){
                if ((i+j) % 2 == 0) {
                    g2.setColor(GameConfig.DarkGreen);
                } else {
                    g2.setColor(GameConfig.Greentable);
                }
                g2.fillRect(85 + (i *tileSize), 176 + (j * tileSize) , tileSize, tileSize);
            }
        }
        // wormDrawer.drawWorm(g2, Body, currentDirection, isOpenMouth);
        wormDrawer.drawWorm(g2, Body, worm.getDirection(), false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
    
    public void gameStart(){
        gameloop.start();
    }
    
    public void gameStop(){
        gameloop.stop();
    }
    
    public void update(){
        worm.move();
        
        Body = new ArrayList<>();
        int tileSize = GameConfig.tilesize;
       
        Body.add(new Point(worm.getX() * tileSize, worm.getY() * tileSize));
        for (int i = 0; i < worm.getBodySize(); i++) {
            Body.add(new Point(worm.getBody().get(i).x * tileSize, (worm.getBody().get(i).y * tileSize)));
        }
        
        // isOpenMouth = false;
        currentDirection = worm.getDirection();
    }
    
    public void clear(){     
        gameBoard = new GameBoard();
        worm = new Worm(5, 5);
        apple = new Apple(7, 5, 0);
         
        currentDirection = worm.getDirection();
        isOpenMouth = false;
        //  user;
        //   rf ;
        //   wf ;
        
        gameloop = new Timer(100, this);
        seconds = 0;
   }

    public String getScore(){
        return "0000" ;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && worm.getVelocityY() != 1) {
            worm.setVelocityX(0);
            worm.setVelocityY(-1);
        } else if (
            e.getKeyCode() == KeyEvent.VK_DOWN && worm.getVelocityY() != -1
        ) {
            worm.setVelocityX(0);
            worm.setVelocityY(1);
        } else if (
            e.getKeyCode() == KeyEvent.VK_LEFT && worm.getVelocityX() != 1
        ) {
            worm.setVelocityX(-1);
            worm.setVelocityY(0);
        } else if (
            e.getKeyCode() == KeyEvent.VK_RIGHT && worm.getVelocityX() != -1
        ) {
            worm.setVelocityX(1);
            worm.setVelocityY(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

