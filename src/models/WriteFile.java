package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import config.GameConfig;

public class WriteFile {
    private String path = GameConfig.userData;

    public void writeUsername(String username) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(username + ",");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writePassword(String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(password + ",");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeScore(String score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateScore(String username, String newScore) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] account = line.split(",");

                if (account[0].equals(username)) {
                    account[2] = newScore;
                    line = account[0] + "," + account[1] + "," + account[2];
                }

                lines.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeNewLine() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeNewAccount(String Username, String password) {
        writeUsername(Username);
        writePassword(password);
        writeScore("0");
        writeNewLine();
    }
}

