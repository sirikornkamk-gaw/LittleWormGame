package models;
import java.util.List;

import config.GameConfig;

import java.util.ArrayList;
import java.awt.Point;

public class Worm {
    private List<Point> body ;
    private char direction ;

    public Worm(int startX, int startY){
        this.direction = 'D';
        this.body = new ArrayList<>();

        int size = GameConfig.tilesize;
        body.add(new Point(startX,startY));
        body.add(new Point(startX,startY));
        body.add(new Point(startX,startY));
    }



}
