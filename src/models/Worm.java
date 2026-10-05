package models;
import java.util.List;

import config.GameConfig;

import java.util.ArrayList;
import java.awt.Point;

import java.util.ArrayList;

public class Worm {

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

    // public void checkRep() {}

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
        // checkRep();
    }

    public void setY(int y) {
        this.y = y;

        // checkRep();
    }

    public int getVelocityX() {
        return this.velocityX;
    }

    public int getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityX(int x) {
        this.velocityX = x;
        // checkRep();
    }

    public void setVelocityY(int y) {
        this.velocityY = y;
        // checkRep();
    }

    public ArrayList<Point> getBody() {
        return bodys;
    }

    public Point getBody(int index) {
        return bodys.get(index);
    }
    
    public int getBodySize() {
        return bodys.size();
        // checkRep();
    }

    public void setBody(int x, int y, int index) {
        bodys.set(index, new Point(x, y));
        // checkRep();
    }


    public void move() {
        int pre_x = 0;
        int pre_y = 0;
        int current_index = 0;

        for (Point position : bodys) {
            if (current_index == 0) {
                setBody(getX(), getY(), current_index);
                pre_x = position.x;
                pre_y = position.y;
                current_index++;
            } else {
                setBody(pre_x, pre_y, current_index);
                pre_x = position.x;
                pre_y = position.y;
                current_index++;
            }
        }

        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());
    }

    public Point getLastBody() {
        Point last_body = bodys.get(bodys.size() - 1);
        return new Point(last_body.x, last_body.y);
    }

    public void addTail() {
        bodys.add(getLastBody());
    }
}