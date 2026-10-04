package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class WriteFile {

    Path username_path = Path.of("username.txt");
    Path password_path = Path.of("password.txt");
    Path score_path = Path.of("score.txt");

    public void write(Path path, String content) {
        try {
            Files.writeString(
                path,
                content + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUsername(String username) {
        write(username_path, username);
    }

    public void writePassword(String password) {
        write(password_path, password);
    }

    public void writeScore(String score) {
        write(score_path, score);
    }

    public void writeAll(String username, String password, String score) {
        writeUsername(username);
        writePassword(password);
        writeScore(score);
    }
}