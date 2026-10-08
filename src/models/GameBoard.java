package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import config.GameConfig;

public class GameBoard {
    private Worm worm;
    private ArrayList<Item> items;
    private float second;

    private Random random;
    private boolean gameOver;
    private boolean gameWin;
    private boolean isEatApple;
    private String isEatAppleType;
    private boolean isMonthOpen;
    private int isMonthOpenFrame;
    private int amountEatItem;
    
    public GameBoard() {
        random = new Random();
        
        worm = new Worm(5, 5);
        items = new ArrayList<>();

        
        addItem("apple", second);
        addItem("apple", second);
        
        addItem("goldApple", second);
        addItem("goldApple", second);
        
        addItem("speedPotion", second);
        addItem("speedPotion", second);
        
        
        addItem("poison", second);
        addItem("poison", second);
        
        isEatApple = false;
        isEatAppleType = "";
        amountEatItem = 0;
        isMonthOpen = false;
        isMonthOpenFrame = 0;
        second = 0;

        setGameOver(false);
        setGameWin(false);
    }

    public Worm getWorm() {
        return worm;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItem(int x) {
        return items.get(x);
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
 
    public void setIsEatItem(boolean value) {
        this.isEatApple = value;
    }

    public boolean getIsEatApple() {
        return isEatApple;
    }

    public void setIsEatAppleType (String value) {
        this.isEatAppleType  = value;
    }

    public String getIsEatAppleType () {
        return isEatAppleType ;
    }

    public void setIsMonthOpen(boolean value) {
        this.isMonthOpen = value;
    }

    public boolean getIsMonthOpen() {
        return isMonthOpen;
    }

    public int getAmountEatItem() {
        return amountEatItem;
    }


    public void addItem(String type, float initialSpawnTime) {
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

        if (type.equals("apple"))
            items.add(new Apple(x, y, initialSpawnTime));

        if (type.equals("goldApple"))
            items.add(new GoldApple(x, y, initialSpawnTime));

        if (type.equals("speedPotion"))
            items.add(new SpeedPotion(x, y, initialSpawnTime));

            
        if (type.equals("poison"))
            items.add(new Poison(x, y, initialSpawnTime));


        
    }

    public void eatItem(int index) {
        if (collision(worm.getX(), worm.getY(), items.get(index).getX(), items.get(index).getY())) {
            String type = items.get(index).getType();
            setIsEatItem(true);
            setIsMonthOpen(true);
            amountEatItem++;
            items.remove(index);
            addItem(type, second);
            setIsEatAppleType(type);
        }
    }
    
    public void update() {
        isEatApple = false;
        isEatAppleType = "";

        if (isMonthOpen) {
            isMonthOpenFrame++;
        }

        if (isMonthOpenFrame >= 2) {
            isMonthOpen = false;
            isMonthOpenFrame = 0;
        }

        worm.move();

        for (int i = 0; i < items.size(); i++) {
            if (second - items.get(i).getInitialSpawnTime() > GameConfig.despawnAppleSceond) {   
                String type = items.get(i).getType();
                
                items.remove(i);
                addItem(type, second);
            }
            eatItem(i);
        }
        
        isGameOver();
        isGameWin();
        second += 0.1;
    }

    public boolean collision(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }
    
    public void reset() {
        worm = new Worm(5, 5);

        addItem("apple", second);
        addItem("apple", second);

        addItem("goldApple", second);
        addItem("goldApple", second);

        addItem("speedPotion", second);
        addItem("speedPotion", second);

        addItem("poison", second);
        addItem("poison", second);
        
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