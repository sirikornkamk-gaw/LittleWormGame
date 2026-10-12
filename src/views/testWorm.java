package views;
import config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class testWorm extends JPanel{
    private WormDrawer wormDrawer;
    private List<Point> testBody;
    private char currentDirection = 'D';
    private boolean isOpenMouth = false;

    public testWorm() {
        wormDrawer = new WormDrawer();
        
        // 1. สร้าง mock data ลำตัวหนอน (3 ข้อ)
        testBody = new ArrayList<>();
        int size = GameConfig.tilesize;
        int startX = 400 ;
        int startY = 300 ;
        for (int i = 0; i < 5; i++) {
        testBody.add(new Point(startX - (i * size), startY));
        }
        // 2. ดักคีย์บอร์ดไว้กดทดสอบหมุนหัว/อ้าปาก
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                
                // เปลี่ยนทิศทางตามปุ่ม WASD
                if (key == KeyEvent.VK_W) currentDirection = 'W';
                if (key == KeyEvent.VK_A) currentDirection = 'A';
                if (key == KeyEvent.VK_S) currentDirection = 'S';
                if (key == KeyEvent.VK_D) currentDirection = 'D';

                // กด Spacebar เพื่อสลับ อ้าปาก / หุบปาก
                if (key == KeyEvent.VK_SPACE) {
                    isOpenMouth = !isOpenMouth;
                }

                repaint(); // วาดใหม่ทันทีที่กดปุ่ม
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // วาดพื้นหลังกระดาน
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // คำบอกวิธีเทสบนหน้าจอ
        g2.setColor(Color.BLACK);
        g2.drawString("กด W, A, S, D เพื่อทดสอบหมุนหัวและหาง", 20, 30);
        g2.drawString("กด SPACEBAR เพื่อทดสอบสลับ อ้าปาก / หุบปาก", 20, 50);

        // 3. สั่งวาดหนอนที่เราทำไว้!
        wormDrawer.drawWorm(g2, testBody, currentDirection, isOpenMouth);
        ItemDrawer.drawPoisonItem(g2,400, 200, 70, 70);
    }

    // Main สำหรับกด Run หน้าจอนี้แยกออกมา
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test WormDrawer");
        testWorm testPanel = new testWorm() ;
        
        frame.add(testPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

