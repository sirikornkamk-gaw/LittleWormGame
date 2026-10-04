package views;
import javax.swing.*;

public class testUI {
   
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        // 1. อยากดู Panel ไหน เอามาวางตรงนี้เลย
        // frame.add(new MainMenuFrame()); 
        // frame.add(new GameWinPanel()); 

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

