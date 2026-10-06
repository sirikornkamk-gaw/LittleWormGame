package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import config.GameConfig;

public class ReadFile {
    private String path = GameConfig.userData;
    private ArrayList<String[]> accounts;
    private ArrayList<String> usernames;
    private ArrayList<String> passwords;
    private ArrayList<String> scores;

    public ReadFile() {
        update();
    }
    
    public void readAllAccount() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String s ;
                while ((s = br.readLine()) != null) {
                    String account[] = s.split(",");
                        accounts.add(account);
                        usernames.add(account[0]);
                        passwords.add(account[1]);
                        scores.add(account[2]);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public ArrayList<String[]> getAccounts() {
        return accounts;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    
    public ArrayList<String> getPasswords() {
        return passwords;
    }
    
    
    public ArrayList<String> getScores() {
        return scores;
    }

    public boolean isContainUsername(String username) {
        boolean isContain = false; 
        for (String u : usernames) {
            if (u.equals(username)) isContain = true; 
        }
        return isContain;
    }

    public boolean isContainPassword(String password) {
        boolean isContain = false; 
        for (String p : passwords) {
            if (p.equals(password)) isContain = true; 
        }
        return isContain;
    }

    public boolean isContainScore(String score) {
        boolean isContain = false; 
        for (String s : scores) {
            if (s.equals(score)) isContain = true; 
        }
        return isContain;
    }
    
    public void update() {
        accounts = new ArrayList<>();
        usernames = new ArrayList<>();
        passwords = new ArrayList<>();
        scores = new ArrayList<>();

        readAllAccount();
    }
}