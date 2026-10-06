package models;

import java.util.ArrayList;

import config.GameConfig;

public class User {
    private String username;    
    private String password;
    private String score;

    private ArrayList<String> usernameList;
    private ArrayList<String> passwordList;
    private ArrayList<String> scoreList;

    private ReadFile rf;
    private WriteFile wf;

    public User() {
        rf = new ReadFile();
        wf = new WriteFile();

        usernameList = rf.getUsernames();
        passwordList = rf.getPasswords();
        scoreList = rf.getScores();
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void updateScore(String username, String newScore) {
        setScore(newScore);
        wf.updateScore(username, newScore);
        GameConfig.saveSession(true, username, Integer.parseInt(newScore));
    }

    public boolean isContainUsername(String username) {
        boolean isContain = false;
        for (String u : usernameList) {
            if (u.equals(username)) isContain = true; 
        }
        return isContain;
    }

    public void createNewAccount(String username, String password) {
        wf.writeNewAccount(username, password);
    }

    public boolean loginAccount(String username, String password) {
        boolean isLoginSuccess = false;
            
        for (int i = 0; i < usernameList.size(); i++) {
            System.out.println(usernameList.get(i) + ", " + passwordList.get(i));
            if (username.equals(usernameList.get(i)) && password.equals(passwordList.get(i))) {
                setUsername(usernameList.get(i));
                setPassword(passwordList.get(i));
                setScore(scoreList.get(i));
                isLoginSuccess = true;
            }
        }
        
        return isLoginSuccess;
    }

    public void logoutAccount() {
        GameConfig.clearSession();
    }
}
