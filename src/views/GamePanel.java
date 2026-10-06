package views;
import javax.swing.* ;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import config.GameConfig;
import models.GameBoard;
import models.User;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private GameBoard gameBoard;
    private Timer timer;
    private int currentScore;
    private User user;

    private WormDrawer wormDrawer ;
    private List<Point> Body ;
    private JLabel lscore ;
    private JButton bPause ;
    private Character currentDirection ;
    private Boolean isOpenMouth ;
    private PausePanel pausePage ;
    private MainFrame mainFrame ;
    private GameOverPanel gameOverPage ;
    private GameWinPanel gameWinPage ;

    
    public GamePanel(MainFrame mainFrame){
        gameBoard = new GameBoard();
        timer = new Timer(100, this);
        currentScore = 0;
        user = new User();
        timer.start();        

        this.mainFrame = mainFrame ;
        setLayout(new BorderLayout());
        setBackground(GameConfig.PinkBG);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        pausePage = new PausePanel(this.mainFrame,this);
        pausePage.setVisible(false);
        layeredPane.add(pausePage, JLayeredPane.POPUP_LAYER);

        gameOverPage = new GameOverPanel(this.mainFrame,this);
        gameOverPage.setVisible(false);
        layeredPane.add(gameOverPage, JLayeredPane.POPUP_LAYER);

        gameWinPage = new GameWinPanel(this.mainFrame,this);
        gameWinPage.setVisible(false);
        layeredPane.add(gameWinPage, JLayeredPane.POPUP_LAYER);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            layeredPane.setBounds(0, 0, getWidth(), getHeight());
            pausePage.setBounds(0, 0, getWidth(), getHeight());
            gameOverPage.setBounds(0, 0, getWidth(), getHeight());
            gameWinPage.setBounds(0, 0, getWidth(), getHeight());
            }
        });

        lscore = new JLabel("0000") ;
        lscore.setForeground(GameConfig.DarkerGreen);
        lscore.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 96f));
        lscore.setBounds(190, 50, 150, 80);
        layeredPane.add(lscore);

        bPause = new RoundedButton("Pause", 50) ;
        bPause.setBackground(GameConfig.Beige);
        bPause.setForeground(GameConfig.DarkerGreen);
        bPause.setFont(FontLoader.loadFont(GameConfig.Jersey_10, 94f));
        bPause.setBounds(1559, 30, 281, 114);
        bPause.setFocusPainted(false);
        layeredPane.add(bPause);

        wormDrawer = new WormDrawer();
        Body = new ArrayList<>();

        gameReset();

        add(layeredPane, BorderLayout.CENTER);
        bPause.addActionListener(this);

        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
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
        wormDrawer.drawWorm(g2, Body, currentDirection, isOpenMouth);
        AppleDrawer.drawApple(g2, (gameBoard.getApple().getX() * tileSize) + 90, (gameBoard.getApple().getY() * tileSize) + 181, 41, 53, GameConfig.RedApple);
    }

    public void showScore(int score){
        String scoreText = String.format("%04d", score);
        this.lscore.setText(scoreText); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && gameBoard.getWorm().getVelocityY() != 1) {
            gameBoard.setWormDirection('W');
            currentDirection = 'W';     
        } else if (
            e.getKeyCode() == KeyEvent.VK_DOWN && gameBoard.getWorm().getVelocityY() != -1
        ) {
            gameBoard.setWormDirection('S');
            currentDirection = 'S';
        } else if (
            e.getKeyCode() == KeyEvent.VK_LEFT && gameBoard.getWorm().getVelocityX() != 1
        ) {
            gameBoard.setWormDirection('A');
            currentDirection = 'A';
        } else if (
            e.getKeyCode() == KeyEvent.VK_RIGHT && gameBoard.getWorm().getVelocityX() != -1
        ) {
            gameBoard.setWormDirection('D');
            currentDirection = 'D';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showScore(currentScore);
        if (e.getSource() == bPause) {
            pausePage.setVisible(true);
            gameStop();
        }

        gameBoard.update();

        if (gameBoard.getGameOver()) gameOver();
        if (gameBoard.getGameWin()) gameWin();
        if (gameBoard.getIsEatApple()) currentScore++;
        setBody();
        repaint();
    }

    public void gameStart() {
        timer.start();
    }

    public void gameStop() {
        timer.stop();
    }

    public void resumeGame() {
        pausePage.setVisible(false);
        gameOverPage.setVisible(false);
        gameWinPage.setVisible(false);
        revalidate();
        repaint();
        gameStart();
        requestFocusInWindow();
    }

    public void gameOver() {
        timer.stop();
        GameConfig.savedGamePanel = null;
        if (currentScore > GameConfig.highScore)   
            user.updateScore(GameConfig.currentUser, String.valueOf(currentScore));
        gameOverPage.setVisible(true);
        revalidate();
        repaint();
    }

    public void gameWin() {
        timer.stop();
        GameConfig.savedGamePanel = null;
        gameWinPage.setVisible(true);
        user.updateScore(GameConfig.currentUser, String.valueOf(currentScore));
        revalidate();
        repaint();
    }

    public void gameReset(){
        gameBoard = new GameBoard();
        Body.clear();
        currentScore = 0;

        setBody();
        isOpenMouth = false ;
        currentDirection = 'D';
        showScore(currentScore);

        GameConfig.savedGamePanel = this;
        gameStart();
        repaint();
        requestFocusInWindow();
    }

    public void setBody() {
        int tileSize = GameConfig.tilesize;

        Body.clear();
        Body.add(new Point((gameBoard.getWorm().getX() * tileSize) + 86, (gameBoard.getWorm().getY() * tileSize) + 176));
        for (int i = 0; i < gameBoard.getWorm().getBodySize(); i++) {
            Body.add(new Point((gameBoard.getWorm().getBody().get(i).x * tileSize) + 86, (gameBoard.getWorm().getBody().get(i).y * tileSize) + 176));
        }
    }

}

