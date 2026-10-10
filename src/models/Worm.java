package models;


import java.util.ArrayList;

import config.GameConfig;

import java.awt.Point;


public class Worm {

    private final int boardWidth = GameConfig.BOARD_WIDTH / GameConfig.tilesize;
    private final int boardHeight = GameConfig.BOARD_HEIGHT / GameConfig.tilesize;
    private final int boardAllTile = boardWidth * boardHeight;

    private int x;
    private int y;

    private int velocityX;
    private int velocityY;

    private ArrayList<Point> bodys;

    public Worm(int x, int y) {
        this.x = x;
        this.y = y;

        this.velocityX = 1;
        this.velocityY = 0;

        bodys = new ArrayList<>();
        bodys.add(new Point(x - 1, y));
        bodys.add(new Point(x - 2, y));
        bodys.add(new Point(x - 3, y));
    }

    public void checkRep() {
        assert bodys != null : "bodys is null";

        assert x >= 0 && x < boardWidth : "worm head x out of board: " + x;
        assert y >= 0 && y < boardHeight : "worm head y out of board: " + y;

        assert velocityX >= -1 && velocityX <= 1
                : "velocityX out of range: " + velocityX;
        assert velocityY >= -1 && velocityY <= 1
                : "velocityY out of range: " + velocityY;

        assert bodys.size() + 1 <= boardAllTile
                : "worm length " + (bodys.size() + 1)
                        + " exceeds board tiles " + boardAllTile;

        for (int i = 0; i < bodys.size(); i++) {
            Point body = bodys.get(i);
            assert body != null : "body " + i + " is null";
            assert body.x >= 0 && body.x < boardWidth
                    && body.y >= 0 && body.y < boardHeight
                    : "body " + i + " out of board: (" + body.x + "," + body.y + ")";

            assert !(body.x == x && body.y == y)
                    : "body " + i + " overlaps head at (" + x + "," + y + ")";
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocityX() {
        return this.velocityX;
    }

    public int getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityX(int x) {
        if ( x < -1 || x > 1 ) throw new IllegalArgumentException();
        this.velocityX = x;
    }

    public void setVelocityY(int y) {
        if ( y < -1 || y > 1 ) throw new IllegalArgumentException();
        this.velocityY = y;
    }

    public ArrayList<Point> getBody() {
        return bodys;
    }

    public Point getBody(int index) {
        return bodys.get(index);
    }
    
    public int getBodySize() {
        return bodys.size();
    }

    public void setBody(int x, int y, int index) {
        bodys.set(index, new Point(x, y));
    }


    public boolean move() {
        int nextX = getX() + velocityX;
        int nextY = getY() + velocityY;

        if (nextX < 0 || nextX >= boardWidth || nextY < 0 || nextY >= boardHeight) {
            return false;
        }

        for (int i = bodys.size() - 1; i > 0; i--) {
            Point prev = bodys.get(i - 1);
            bodys.set(i, new Point(prev.x, prev.y));
        }
        bodys.set(0, new Point(getX(), getY()));

        setX(nextX);
        setY(nextY);
        checkRep();
        return true;
    }

    public Point getLastBody() {
        Point last_body = bodys.get(bodys.size() - 1);
        return new Point(last_body.x, last_body.y);
    }

    public void addTail() {
        if (boardAllTile - 1 == getBodySize()) throw new IllegalArgumentException();
        bodys.add(getLastBody());
    }
}