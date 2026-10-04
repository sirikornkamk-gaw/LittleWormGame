package models;

import java.util.ArrayList;

public class GameBoard {
    private int width;
    private int height;
    private Worm worm;
    private ArrayList<Item> items;

    public GameBoard() {
        this.width = 25;
        this.height = 12;
        this.worm = new Worm(5, 5);
        this.items = new ArrayList<>();

        addApples();
    }

   public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.worm = new Worm(5, 5);
        this.items = new ArrayList<>();

        addApples();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Worm getWorm() {
        return worm;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public Item getItems(int index) {
        return items.get(index);
    }
    
    public void addApples() {
        items.add(new Apple(7, 5, 0)); 
    }

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
}

