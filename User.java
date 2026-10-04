package models;

public class User {
    private String username;
    private String password;
    private String score;

    public User() {}

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setScore(String score) {
        this.score = score;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getScore() {
        return this.score;
    }
}
