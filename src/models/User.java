// remove the password out of this file and call the function if we will to access the password
// try to not collect the usernamelist and scorelist. just use a function that we wrtei on other file like rf or wf

package models;

import java.util.ArrayList;

import config.GameConfig;

public class User {
    private String username;    
    private String score;

    private ReadFile rf;
    private WriteFile wf;

    public User() {
        update();
        wf = new WriteFile();
        checkRep();
    }
    
    public void checkRep() {
        assert username == null || (!username.isEmpty()) : "username is empty";
        assert score == null || (!score.isEmpty())
                : "score is empty: " + score;
    }
    
    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

    public void update() {
        rf = new ReadFile();
        checkRep();
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();
        this.username = username;
        checkRep();
    }

    public void setScore(String score) {
        if (score == null || score.isEmpty()) throw new IllegalArgumentException();
        this.score = score;
        checkRep();
    }

    public void updateScore(String username, String newScore) {
        setScore(newScore);
        wf.updateScore(username, newScore);
        GameConfig.saveSession(true, username, Integer.parseInt(newScore));
    }

    public boolean isContainUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();

        update();
        boolean isContain = false;
        for (String u : rf.getUsernames()) {
            if (u.equals(username)) isContain = true; 
        }
        return isContain;
    }

    public boolean isUsernameAndPasswordVaild(String username, String password) {
        if (username.trim().isEmpty() ||
            password.trim().isEmpty() ||
            username.equals(" Username") ||
            password.equals(" Password") ||
            username.length() >= 20) {
            return false;
        }
        return true;
    }

    public void createNewAccount(String username, String password) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();
        if (password == null || password.isEmpty()) throw new IllegalArgumentException();
        wf.writeNewAccount(username, password);
    }

    public boolean loginAccount(String username, String password) {
        update();
        if (username == null || username.isEmpty()) throw new IllegalArgumentException();
        if (password == null || password.isEmpty()) throw new IllegalArgumentException();
        boolean isLoginSuccess = false;
            
        for (int i = 0; i < rf.getUsernames().size(); i++) {
            if (username.equals(rf.getUsernames().get(i)) && password.equals(rf.getPasswords().get(i))) {
                setUsername(rf.getUsernames().get(i));
                setScore(rf.getScores().get(i));
                isLoginSuccess = true;
            }
        }
        return isLoginSuccess;
    }

    public void logoutAccount() {
        GameConfig.clearSession();
    }
}
