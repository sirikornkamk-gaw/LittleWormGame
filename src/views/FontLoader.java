package views ;

import java.io.*;
import java.awt.* ;

public class FontLoader {
    public static Font loadFont(String path, float size) {
        try (InputStream is = FontLoader.class.getResourceAsStream(path)){
            if (is == null) {
                System.err.println("Font not found at path : " + path);
                return new Font("SansSerif", Font.BOLD, (int) size);
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont ;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size);
        }
    }
}