package models;

public class Tile {

    int x;
    int y;
    String type;
    float spawnTime;

    Tile(int x, int y, String type, float spawnTime) {
        setX(x);
        setY(y);
        setType(type);
        setSpawnTime(spawnTime);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public float getSpawnTime() {
        return spawnTime;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }
}