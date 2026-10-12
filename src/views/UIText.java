package views;

import javax.swing.* ;
import config.GameConfig;
import java.awt.event.*;

public class UIText {
    public static void setupJTextfield(JTextField tf, String text ){
        tf.setText(text);
        tf.setForeground(GameConfig.Grey);
        tf.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(text)) {
                    tf.setText("");
                    tf.setForeground(GameConfig.Black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().trim().isEmpty()) {
                    tf.setText(text);
                    tf.setForeground(GameConfig.Grey);
                }
            }

        });
    }
    public static void setupJPasswordfield(JPasswordField pw, String text ){
        pw.setText(text);
        pw.setEchoChar((char)0);
        pw.setForeground(GameConfig.Grey);

        pw.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                String pass = new String(pw.getPassword());
                if (pass.equals(text)) {
                    pw.setText("");
                    pw.setEchoChar('*');
                    pw.setForeground(GameConfig.Black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String pass = new String(pw.getPassword());
                if (pass.trim().isEmpty()) {
                    pw.setText(text);
                    pw.setEchoChar((char) 0);
                    pw.setForeground(GameConfig.Grey);
                }
            }

        });
    }
}
