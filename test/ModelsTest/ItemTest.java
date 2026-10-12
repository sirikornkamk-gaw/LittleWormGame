package test.ModelsTest;

import config.GameConfig;
import models.Apple;
import models.GoldApple;
import models.Item;
import models.Poison;
import models.SpeedPotion;

public class ItemTest {
    public static int passed = 0;
    public static int failed = 0;
    private static final int COLS = GameConfig.BOARD_WIDTH / GameConfig.tilesize;  
    private static final int ROWS = GameConfig.BOARD_HEIGHT / GameConfig.tilesize; 
    
    /* helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea ItemTest\n");
        }

        System.out.println("=== Item Test ===");

        runtest();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0 && System.getProperty("runAll") == null) {
            System.exit(1);
        }
    }

    // ---- runtest ----

    public static void runtest() {
        // --- constructor ---
        System.out.println("--- constructor ---");
        Item item  = new Apple(3, 4, 1.5f);
        check("new Apple(3, 4, 1.5) position is (3,4)", item.getX() == 3 && item.getY() == 4);
        check("new Apple initialSpawnTime is 1.5", item.getInitialSpawnTime() == 1.5);
        check("new Apple type is \"apple\"", item.getType().equals("apple"));
        check("new GoldApple type is \"goldApple\"", new GoldApple(0, 0, 0).getType().equals("goldApple"));
        check("new SpeedPotion type is \"speedPotion\"", new SpeedPotion(0, 0, 0).getType().equals("speedPotion"));
        check("new Poison type is \"poison\"", new Poison(0, 0, 0).getType().equals("poison"));

        // --- setX ---
        System.out.println("\n--- setX ---");
        item = new Apple(3, 4, 0);
        item.setX(0);
        check("setX(0) first column is allowed", item.getX() == 0);
        item.setX(COLS-1);
        check("setX(COLS-1) last column is allowed", item.getX() == COLS-1);
        
        item = new Apple(3, 4, 0);
        boolean threw = false;
        try {
            item.setX(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setX(-1) throw IllegalArgumentException e", threw);
        check("setX(-1) x stay 3", item.getX() == 3);

        item = new Apple(3, 3, 0);
        threw = false;
        try {
            item.setX(COLS);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setX(COLS) throw IllegalArgumentException e", threw);
        check("setX(COLS) x stay 3", item.getX() == 3);

        // --- setY ---
        System.out.println("\n--- setY ---");
        item = new Apple(3, 4, 0);
        item.setY(0);
        check("setY(0) first row is allowed", item.getY() == 0);
        item.setY(ROWS-1);
        check("setY(ROWS-1) last row is allowed", item.getY() == ROWS-1);

        item = new Apple(3, 4, 0);
        threw = false;
        try {
            item.setY(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setY(-1) throw IllegalArgumentException e", threw);
        check("setY(-1) x stay 4", item.getY() == 4);

        item = new Apple(3, 4, 0);
        threw = false;
        try {
            item.setY(ROWS);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setY(ROWS) throw IllegalArgumentException e", threw);
        check("setY(ROWS) y stay 4", item.getY() == 4);

        // --- setType ---
        System.out.println("\n--- setType ---");
        item = new Apple(3, 4, 0);
        item.setType("poison");
        check("setType(\"poison\") changes type", item.getType().equals("poison"));

        item = new Apple(3, 4, 0);
        threw = false;
        try {
            item.setType(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setType(null) throw IllegalArgumentException", threw);
        check("setType(null) type stay \"apple\"", item.getType().equals("apple"));

        item = new Apple(3, 4, 0);
        threw = false;
        try {
            item.setType("");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setType(\"\") throw IllegalArgumentException", threw);
        check("setType(\"\") type stay \"apple\"", item.getType().equals("apple"));


        item = new Apple(3, 4, 0);
        threw = false;
        try {
            item.setType("banana");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setType(\"banana\") throw IllegalArgumentException", threw);
        check("setType(\"banana\") type stay \"apple\"", item.getType().equals("apple"));


        // --- setInitialSpawnTime ---
        System.out.println("\n--- setInitialSpawnTime ---");
        item = new Apple(3, 4, 2);
        item.setInitialSpawnTime(0);
        check("setInitialSpawnTime(0) is allowed", item.getInitialSpawnTime() == 0);
        item.setInitialSpawnTime(20f);
        check("setInitialSpawnTime(20) is allowed", item.getInitialSpawnTime() == 20);

        threw = false;
        try {
            item.setInitialSpawnTime(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setInitialSpawnTime(-1) throw IllegalArgumentException", threw);
        check("setInitialSpawnTime(-1) InitialSpawnTime stay 20 ", item.getInitialSpawnTime() == 20);
    }

}