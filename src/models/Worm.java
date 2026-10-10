package models;


import java.util.ArrayList;
import java.util.HashSet;

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


    public void move() {
        for (int i = bodys.size() - 1; i > 0; i--) {
            Point prev = bodys.get(i - 1);
            bodys.set(i, new Point(prev.x, prev.y));
        }
        bodys.set(0, new Point(getX(), getY()));

        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());
        checkRep();
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