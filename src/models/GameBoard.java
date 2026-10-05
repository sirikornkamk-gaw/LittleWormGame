package models;

import java.awt.Point;
import java.util.Random;

import config.GameConfig;

public class GameBoard {
    private Worm worm;
    private Apple apple;
    private float second;

    private Random random;
    private boolean gameOver;
    private boolean gameWin;
    private boolean isEatApple;
    
    public GameBoard() {
        random = new Random();
        
        worm = new Worm(5, 5);
        addApple(second);
        addApple(0);
        second = 0;

        setGameOver(false);
        setGameWin(false);
    }

    public Worm getWorm() {
        return worm;
    }

    public Apple getApple() {
        return apple;
    }

    public void setWormDirection(char c) {
        if (c == 'W' && worm.getVelocityY() != 1) {
            worm.setVelocityX(0);
            worm.setVelocityY(-1);
        } else if (
            c == 'S' && worm.getVelocityY() != -1
        ) {
            worm.setVelocityX(0);
            worm.setVelocityY(1);
        } else if (
            c == 'A' && worm.getVelocityX() != 1
        ) {
            worm.setVelocityX(-1);
            worm.setVelocityY(0);
        } else if (
            c == 'D' && worm.getVelocityX() != -1
        ) {
            worm.setVelocityX(1);
            worm.setVelocityY(0);
        }
    }
 
    public void setIsEatApple(boolean value) {
        this.isEatApple = value;
    }

    public boolean getIsEatApple() {
        return isEatApple;
    }

    public void addApple(float initialSpawnTime) {
        int x;
        int y;
        boolean isCollisionWormBody;

        while (true) {
            x = random.nextInt(GameConfig.BOARD_WIDTH / GameConfig.tilesize);
            y = random.nextInt(GameConfig.BOARD_HEIGHT / GameConfig.tilesize);
            isCollisionWormBody = false;

            if (collision(worm.getX(), worm.getY(), x, y)) {
                isCollisionWormBody = true;
            }
            for (int i = 0; i < worm.getBodySize(); i++) {
                if (collision(worm.getBody(i).x, worm.getBody(i).y, x, y)) {
                    isCollisionWormBody = true;
                }
            }

            if (isCollisionWormBody == false) break;
        }
        
        apple = new Apple(x, y, initialSpawnTime);
    }

    public void eatApple() {
        if (collision(worm.getX(), worm.getY(), apple.getX(), apple.getY())) {
            worm.addTail();
            setIsEatApple(true);
            addApple(second);
        }
    }
    
    public void update () {
        setIsEatApple(false);
        worm.move();
        if (second - apple.getInitialSpawnTime() > GameConfig.despawnAppleSceond) addApple(second);
        eatApple();
        isGameOver();
        isGameWin();
        second += 0.1;
    }

    public boolean collision(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }
    
    public void reset() {
        worm = new Worm(5, 5);
        addApple(0);
        second = 0;
        setGameOver(false);
        setGameWin(false);
    }

    public void setGameOver(boolean value) {
        this.gameOver = value;
    } 

    public boolean getGameOver() {
        return gameOver;
    }

    public void isGameOver() {
        if (
            worm.getX() < 0 || 
            worm.getX() >= GameConfig.BOARD_WIDTH / GameConfig.tilesize || 
            worm.getY() < 0 || 
            worm.getY() >= GameConfig.BOARD_HEIGHT / GameConfig.tilesize) 
            setGameOver(true);
        for (Point body : worm.getBody()) {
            if (collision(worm.getX(), worm.getY(), (int)body.getX(),(int)body.getY())) {
                setGameOver(true);
            }
        }
    }

    public void setGameWin(boolean value) {
        this.gameWin = value;
    } 

    public boolean getGameWin() {
        return gameWin;
    }

    public void isGameWin() {
        int boardWidth = GameConfig.BOARD_WIDTH / GameConfig.tilesize;
        int boardHeight = GameConfig.BOARD_HEIGHT / GameConfig.tilesize;
        if (worm.getBody().size() == boardWidth * boardHeight) setGameWin(true);
        
        
        // testing only
        // if (worm.getBody().size() == 6) setGameWin(true);
    }   
}