package models;

public class Item {
    int x;
    int y;
    String type;
    float initialSpawnTime;

    Item(int x, int y, String type, float initialSpawnTime) {
        setX(x);
        setY(y);
        setType(type);
        setInitialSpawnTime(initialSpawnTime);
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getInitialSpawnTime() {
        return initialSpawnTime;
    }

    public void setInitialSpawnTime(float initialSpawnTime) {
        this.initialSpawnTime = initialSpawnTime;
    }
}
