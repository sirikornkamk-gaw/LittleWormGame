package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import config.GameConfig;

public class ReadFile {

    Path username_path = Path.of(GameConfig.PATH_USERNAME);
    Path password_path = Path.of(GameConfig.PATH_PASSWORD);
    Path score_path = Path.of(GameConfig.PATH_SCORE);

    private ArrayList<String> username_list = new ArrayList<>();
    private ArrayList<String> password_list = new ArrayList<>();
    private ArrayList<String> score_list = new ArrayList<>();

    public ReadFile() {
        readAll();
    }

    public void read(Path path, ArrayList<String> arrayList) {
        try {
            for (String line : Files.readAllLines(path)) {
                // System.out.println(line);
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                // System.out.println();
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readUsername() {
        read(username_path, username_list);
    }

    public void readPassword() {
        read(password_path, password_list);
    }

    public void readScore() {
        read(score_path, score_list);
    }

    public void readAll() {
        readUsername();
        readPassword();
        readScore();
    }

    public ArrayList<String> getUsername() {
        return username_list;
    }

    public ArrayList<String> getPassword() {
        return password_list;
    }

    public ArrayList<String> getScore() {
        return score_list;
    }
    
    public String getUsername(int index) {
        return username_list.get(index);
    }

    public String getPassword(int index) {
        return password_list.get(index);
    }

    public String getScore(int index) {
        return score_list.get(index);
    }
}