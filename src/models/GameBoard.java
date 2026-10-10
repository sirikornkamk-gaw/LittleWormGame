package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import config.GameConfig;

public class GameBoard {
    private final int boardWidth = GameConfig.BOARD_WIDTH / GameConfig.tilesize;
    private final int boardHeight = GameConfig.BOARD_HEIGHT / GameConfig.tilesize;
    private final int despawnAppleSecond = GameConfig.despawnAppleSceond;

    private Worm worm;
    private ArrayList<Item> items;
    private float second;

    private Random random;
    private boolean gameOver;
    private boolean gameWin;
    private boolean isEatItem;
    private String isEatItemType;
    private boolean isMonthOpen;
    private int isMonthOpenFrame;
    private int amountEatItem;
    private boolean isSpeedUp;
    
    public GameBoard() {
        random = new Random();
        
        worm = new Worm(5, 5);
        items = new ArrayList<>();

        
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        
        isEatItem = false;
        setAmountEatItem(0);
        isMonthOpen = false;
        setIsMonthOpenFrame(0);
        isSpeedUp = false;
        setSecond(0);
        
        setGameOver(false);
        setGameWin(false);
        checkRep();
    }

    public void checkRep() {
        assert worm != null : "worm is null";
        assert items != null : "items is null";
        assert random != null : "random is null";

        assert !items.isEmpty() : "items is empty";
        assert second >= 0 : "second is negative: " + second;
        assert amountEatItem >= 0 : "amountEatItem is negative: " + amountEatItem;
        // animation worm open the month. 
        // true if the worm eat item and after that 3 frame the month will close.
        // isMonthOpenFrame collect the current frame that have been pass if the frame is more than 2 worm month will close and go to false.
        assert isMonthOpenFrame >= 0 && isMonthOpenFrame <= 2
                : "isMonthOpenFrame out of range: " + isMonthOpenFrame;

        // use for tell what is worm eat (apple, goldapple. speedpotion, posion)
        if (isEatItem) {
            assert isEatItemType != null : "isEatItemType is null";
            assert isEatItemType.isEmpty() || isEatItemType.equals("apple")
                    || isEatItemType.equals("goldApple")
                    || isEatItemType.equals("speedPotion")
                    || isEatItemType.equals("poison")
                    : "invalid isEatItemType: " + isEatItemType;
        }

        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            assert it != null : "item " + i + " is null";
            assert it.getX() >= 0 && it.getX() < boardWidth
                    && it.getY() >= 0 && it.getY() < boardHeight
                    : "item " + i + " out of board";

            for (int j = i + 1; j < items.size(); j++) {
                assert !collision(items.get(j).getX(), items.get(j).getY(), it.getX(), it.getY())
                        : "items " + i + " and " + j + " overlap";
            }

            assert !collision(worm.getX(), worm.getY(), it.getX(), it.getY())
                    : "item " + i + " overlaps worm head";
            for (Point body : worm.getBody()) {
                assert !collision(body.x, body.y, it.getX(), it.getY())
                        : "item " + i + " overlaps worm body";
            }
        }

        if (gameOver) {
            boolean isOutOfBoard = worm.getX() < 0
                    || worm.getX() >= boardWidth
                    || worm.getY() < 0
                    || worm.getY() >= boardHeight;

            boolean isAtWall = worm.getX() == 0 || worm.getX() == boardWidth - 1
                    || worm.getY() == 0 || worm.getY() == boardHeight - 1;

            boolean isSelfCollision = false;
            for (Point body : worm.getBody()) {
                if (collision(worm.getX(), worm.getY(), body.x, body.y)) {
                    isSelfCollision = true;
                }
            }

            assert isOutOfBoard || isAtWall || isSelfCollision
                    : "gameOver is true but worm is inside the board and not self-overlapping";
        }

        if (gameWin) {
            assert worm.getBodySize() == boardWidth * boardHeight
                    : "gameWin flag but board is not full";
        }
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
        this.isEatItem = value;
    }

    public boolean getIsEatItem() {
        return isEatItem;
    }

    public void setIsEatItemType (String value) {
        this.isEatItemType  = value;
    }

    public String getIsEatItemType () {
        return isEatItemType ;
    }

    public void setIsMonthOpen(boolean value) {
        this.isMonthOpen = value;
    }

    public boolean getIsMonthOpen() {
        return isMonthOpen;
    }

    public boolean getIsSpeedUp() {
        return isSpeedUp;
    }

    public void setIsSpeedUp(boolean value) {
        this.isSpeedUp = value;
        checkRep();
    }

    public int getAmountEatItem() {
        return amountEatItem;
    }

    public void setAmountEatItem(int value) {
        if (value < 0) throw new IllegalArgumentException();
        this.amountEatItem = value;
    }

    public float getSecond() {
        return second;
    }

    public void setSecond(float value) {
        if (value < 0) throw new IllegalArgumentException();
        this.second = value;
    }

    public int getIsMonthOpenFrame() {
        return isMonthOpenFrame;
    }

    public void setIsMonthOpenFrame(int value) {
        if (isMonthOpenFrame > 2) throw new IllegalArgumentException();
        this.isMonthOpenFrame = value;
    }


    public void addItem(float initialSpawnTime) {
        int x;
        int y;
        boolean isCollisionWormBody;
        boolean isCollisionItem;

        while (true) {
            x = random.nextInt(boardWidth);
            y = random.nextInt(boardHeight);
            isCollisionWormBody = false;
            isCollisionItem = false;

            if (collision(worm.getX(), worm.getY(), x, y)) {
                isCollisionWormBody = true;
            }
            for (int i = 0; i < worm.getBodySize(); i++) {
                if (collision(worm.getBody(i).x, worm.getBody(i).y, x, y)) {
                    isCollisionWormBody = true;
                }
            }

            for (int i = 0; i < items.size(); i++) {
                if (collision(items.get(i).x, items.get(i).y, x, y)) {
                    isCollisionItem = true;
                }
            }


            if (isCollisionWormBody == false && isCollisionItem == false) break;
        }

        int randomItemType = random.nextInt(4);
        if (randomItemType == 0)
            items.add(new Apple(x, y, initialSpawnTime));

        if (randomItemType == 1)
            items.add(new GoldApple(x, y, initialSpawnTime));

        if (randomItemType == 2)
            items.add(new SpeedPotion(x, y, initialSpawnTime));
            
        if (randomItemType == 3)
            items.add(new Poison(x, y, initialSpawnTime));
        
    }

    public void eatItem(int index) {
        if (collision(worm.getX(), worm.getY(), items.get(index).getX(), items.get(index).getY())) {
            String type = items.get(index).getType();
            setIsEatItem(true);
            setIsMonthOpen(true);
            setAmountEatItem(getAmountEatItem() + 1);
            items.remove(index);
            addItem(getSecond());
            setIsEatItemType(type);
        }
    }
    
    public void update() {
        
        isEatItem = false;
        isEatItemType = "";

        if (isMonthOpen) {
            setIsMonthOpenFrame(getIsMonthOpenFrame() + 1);
        }

        if (getIsMonthOpenFrame() >= 2) {
            isMonthOpen = false;
            setIsMonthOpenFrame(0);
        }

        // if wrom cannot move
        // that meaning the worm will out of the board and gameover
        if (!worm.move()) {
            setGameOver(true);
        }
        
        for (int i = 0; i < items.size(); i++) {
            if (getSecond() - items.get(i).getInitialSpawnTime() > despawnAppleSecond) {   
                items.remove(i);
                addItem(getSecond());
            }
            eatItem(i);
        }
        
        // check is worm are on the same tile as a body
        // if yes gamever
        // we do not check the head of the worm is that out of the board because we check that on the move function
        isGameOver();
        isGameWin();

        if (isSpeedUp)
            setSecond(getSecond() + (float)0.05);
        else 
            setSecond(getSecond() + (float)0.1);
        checkRep();
    }

    public boolean collision(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }


    public void reset() {
        worm = new Worm(5, 5);

        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());
        addItem(getSecond());

        setSecond(0);
        setGameOver(false);
        setGameWin(false);
        checkRep();
    }

    public void setGameOver(boolean value) {
        this.gameOver = value;
    } 

    public boolean getGameOver() {
        return gameOver;
    }

    public void isGameOver() {
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
        if (worm.getBody().size() == boardWidth * boardHeight) setGameWin(true);
    }   
}