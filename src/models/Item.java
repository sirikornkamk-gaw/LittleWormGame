package models;

import config.GameConfig;

public class Item {
    private final int boardWidth = GameConfig.BOARD_WIDTH / GameConfig.tilesize;
    private final int boardHeight = GameConfig.BOARD_HEIGHT / GameConfig.tilesize;

    int x;
    int y;
    String type;
    float initialSpawnTime;

    Item(int x, int y, String type, float initialSpawnTime) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.initialSpawnTime = initialSpawnTime;
        checkRep();
    }

    public void checkRep() {
        assert x >= 0 && x < boardWidth
                : "item x out of board: " + x;
        assert y >= 0 && y < boardHeight
                : "item y out of board: " + y;
        assert type != null && !type.isEmpty()
                : "type is null or empty";
        assert type.equals("apple") || type.equals("goldApple")
                || type.equals("speedPotion") || type.equals("poison")
                : "unknown item type: " + type;
        assert initialSpawnTime >= 0
                : "initialSpawnTime invalid: " + initialSpawnTime;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0 || x >= boardWidth) throw new IllegalArgumentException();
        this.x = x;
        checkRep();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0 || y >= boardHeight) throw new IllegalArgumentException();
        this.y = y;
        checkRep();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.isEmpty()) throw new IllegalArgumentException();
        // if (!type.equals("apple") && !type.equals("goldApple")
        //         && !type.equals("speedPotion") && !type.equals("poison")) throw new IllegalArgumentException();
        
        this.type = type;
        checkRep();
    }

    public float getInitialSpawnTime() {
        return initialSpawnTime;
    }

    public void setInitialSpawnTime(float initialSpawnTime) {
        if (initialSpawnTime < 0) throw new IllegalArgumentException();
        this.initialSpawnTime = initialSpawnTime;
        checkRep();
    }
}
