package test.ModelsTest;

import config.GameConfig;
import models.GameBoard;
import models.Item;
import models.Worm;

import java.awt.*;
import java.util.ArrayList;

public class GameBoardTest {
    public static int passed = 0;
    public static int failed = 0;

    private static final int COLS = GameConfig.BOARD_WIDTH / GameConfig.tilesize ;
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
                    + " - re-run with: java -ea GameBoardTest\n");
        }

        System.out.println("=== GameBoard Test ===");

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

    // ---- helper ----

    private static boolean head(GameBoard b, int x, int y) {
        return b.getWorm().getX() == x && b.getWorm().getY() == y;
    }

    private static boolean itemOnWorm(GameBoard b, Item item) {
        if(item.getX() == b.getWorm().getX() && item.getY() == b.getWorm().getY())
            return true;
        for(Point p : b.getWorm().getBody())
            if(p.x == item.getX() && p.y == item.getY())
                return true;
        return false;
    }

    private static boolean velocity(GameBoard b, int x, int y) {
        return  b.getWorm().getVelocityX() == x && b.getWorm().getVelocityY() == y;
    }

    private static void place(GameBoard b, int hx, int hy, int vx, int vy, int[][] body) {
        makeASpaceForTest(b);
        Worm w = b.getWorm();
        w.setX(hx);
        w.setY(hy);
        w.setVelocityX(vx);
        w.setVelocityY(vy);
        for(int i = 0; i < body.length ; i++) {
            if(i < w.getBodySize()) 
                w.setBody(body[i][0], body[i][1], i);
            else {
                // w.addTail();
                // w.setBody(body[i][0], body[i][1], i);
                w.getBody().add(new Point(body[i][0], body[i][1]));
            }
        }
        
    }

    private  static void makeASpaceForTest(GameBoard b) {
        for(int i = 0 ; i < b.getItems().size() ; i++) {
            b.getItem(i).setX(i+6);
            b.getItem(i).setY(ROWS - 1);
        }

    }

    private static Throwable updateSafely(GameBoard b) {
        try {
            b.update();
            return null;
        } catch (Throwable e) {
            return e;
        }
    }

    private static Item putItemInFront(GameBoard b, int index, String type) {
        makeASpaceForTest(b);
        Item tarket = b.getItem(index);
        tarket.setX(6);
        tarket.setY(5);
        tarket.setType(type);
        return tarket;
    }

    private static boolean hasSameItem(GameBoard b, Item target) {
        for (Item it : b.getItems()) {
            if (it == target) return true;
        }
        return false;
    }

    // ---- runtest ----

    public static void  runtest() {
        // --- Initial State ---
        System.out.println("--- Initial State ---");
        GameBoard b = new GameBoard();
        check("initial head position is (5,5)", head(b, 5, 5));
        check("initial body has 3 segments", b.getWorm().getBodySize() == 3);
        check("initial gamOver is false", !b.getGameOver());
        check("initial gamWin is false", !b.getGameWin());
        check("initial isEatApple is false", !b.getIsEatItem());
        check("initial isEatAppleType is empty", "".equals(b.getIsEatItemType()));
        check("initial amountEatItem is 0", b.getAmountEatItem() == 0);
        check("initial mount is closed", !b.getIsMonthOpen());
        check("board start with 8 items", b.getItems().size() == 8);

        boolean allInside = true;
        boolean notOnWorm = true;
        for(Item item : b.getItems()) {
            if(item.getX() < 0 || item.getX() >= COLS || item.getY() < 0 || item.getY() >= ROWS) allInside = false;
            if(itemOnWorm(b, item)) notOnWorm = false;
        }
        check("all of item is inside board", allInside);
        check("no item spawns on worm", notOnWorm);

        boolean noOverlap = true;
        for(int game = 0 ; game < 2000 ; game++) {
            GameBoard g = new GameBoard();
            ArrayList<Item> items = g.getItems();
            for(int i = 0 ; i < items.size() ; i++)
                for(int j = i+1 ; j < items.size() ; j++)
                    if(items.get(i).getX() == items.get(j).getX() && items.get(i).getY() == items.get(j).getY())
                        noOverlap = false;
        }
        check("no item share on the same cell", noOverlap);

        // --- setWormDirection ---
        System.out.println("\n---- setWormDirection ---");
        b = new GameBoard();
        b.setWormDirection('W');
        check("press W, worm turn up (0,-1) ", velocity(b, 0, -1));  

        b = new GameBoard();
        b.setWormDirection('S');
        check("press S, worm turn down (0,1)", velocity(b, 0, 1));

        b = new GameBoard();
        b.setWormDirection('D');
        check("press D (same direction), worm still right (1,0)", velocity(b, 1, 0));

        b = new GameBoard();
        b.setWormDirection('A');
        check("press A (opposite), worm still right (1,0)", velocity(b,1, 0));

        b = new GameBoard();
        b.setWormDirection('X');
        check("press non-direction key, still right", velocity(b, 1, 0));

        b = new GameBoard();
        b.setWormDirection('W');
        b.update();
        b.setWormDirection('A');
        check("press W,worm going up, press A, worm turn left", velocity(b, -1, 0));

        b = new GameBoard();
        b.setWormDirection('W');
        b.update();
        b.setWormDirection('S');
        check("press W, worm going up, press S (opposite), worm still up", velocity(b, 0, -1));

        // --- Wall Collision ---
        System.out.println("\n--- Wall Collision ---");
        b = new GameBoard();
        place(b, 1, 5, -1, 0, new int[][] {{2, 5}, {3, 5}, {4, 5}});
        b.update();;
        check("move into the first colomn, not game over", !b.getGameOver() && head(b, 0, 5));

        b = new GameBoard();
        place(b, COLS-2, 5, 1, 0, new int[][] {{COLS-3, 5}, {COLS-4, 5}, {COLS-5, 5}});
        b.update();
        check("move into the last column, not game over", !b.getGameOver() && head(b, COLS-1, 5));

        b = new GameBoard();
        place(b, 5, 1, 0, -1, new int[][] {{5, 2}, {5, 3}, {5, 4}});
        b.update();
        check("move into the first row, not game over", !b.getGameOver() && head(b, 5, 0));

        b = new GameBoard();
        place(b, 5, ROWS-2, 0, 1, new int[][] {{5, ROWS-3}, {5, ROWS-4}, {5, ROWS-5}});
        b.update();
        check("move into the last row, not game over", !b.getGameOver() && head(b, 5, ROWS-1));

        b = new GameBoard();
        place(b, COLS-1, 5, 1, 0, new int[][] {{COLS-2, 5}, {COLS-3, 5}, {COLS-4, 5}});
        b.update();
        check("hit the right wall, game over", b.getGameOver());
        check("head must be inside the board (x="+(COLS-1)+")", head(b, COLS-1, 5));

        b = new GameBoard();
        place(b, 0, 5, -1, 0, new int[][] {{1, 5}, {2, 5}, {3, 5}});
        b.update();
        check("hit the left wall, game over", b.getGameOver());
        check("head must be inside the board (x=0)", head(b, 0, 5));

        b = new GameBoard();
        place(b, 5, ROWS-1, 0, 1, new int[][] {{5, ROWS-2}, {5, ROWS-3}, {5, ROWS-4}});
        b.update();
        check("hit the bottom wall, game over", b.getGameOver());
        check("head must be inside the board (y="+(ROWS-1)+")", head(b, 5, ROWS-1));

        b = new GameBoard();
        place(b, 5, 0, 0, -1, new int[][] {{5, 1}, {5, 2}, {5, 3}});
        b.update();
        check("hit the top wall, game over", b.getGameOver());
        check("head must be inside the board (y=0)", head(b, 5, 0) );
        
        // --- Self Collision ---
        System.out.println("\n--- Self Collision ---");
        b = new GameBoard();
        place(b, 5, 5, 1, 0, new int[][] {{4, 5}, {4, 6}, {5, 6}, {6, 6}, {6, 5}, {6, 4}, {6, 3}});
        Throwable err = updateSafely(b);
        check("head hit own body, game over", b.getGameOver());
        if(!b.getGameOver())System.out.println("error = " + err);

        b = new GameBoard();
        place(b, 5, 5, 1, 0, new int[][] {{4, 5}, {4, 6}, {5, 6}, {6, 6}, {6, 5}});
        err = updateSafely(b);
        check("head move into the cell the tail just left, not game over", err == null && !b.getGameOver());
        if(b.getGameOver())System.out.println("error = " + err);

        b = new GameBoard();
        b.getWorm().addTail();
        b.isGameOver();
        check("addTail() alone does not cause game over", !b.getGameOver());

        // --- isGamewin ---
        System.out.println("\n--- isGameWin ---");
        b = new GameBoard();
        b.isGameWin();
        check("starting worm has not won", !b.getGameWin());

        b = new GameBoard();
        while(b.getWorm().getBodySize() < COLS*ROWS - 1)
            b.getWorm().addTail();
        b.isGameWin();
        check("worm fills the whole board (head + body = " + (COLS * ROWS) + "), game win", b.getGameWin());

        b = new GameBoard();
         while(b.getWorm().getBodySize() < COLS*ROWS - 2)
            b.getWorm().addTail();
        b.isGameWin();
        check("call isGameWin when worm is one short of full board, getGamewin retur false", !b.getGameWin());

        // --- addItem ---
        System.out.println("\n--- addItem ---");
        b = new GameBoard();
        b.addItem(3.5f);
        Item added = b.getItem(b.getItems().size()-1);
        String t = added.getType();
        check("new item has valid type", 
            t.equals("apple") || t.equals("goldApple") || 
            t.equals("speedPotion") || t.equals("poison"));
        check("new item spawn time = 3.5f ", added.getInitialSpawnTime() == 3.5f);

        b = new GameBoard();
        boolean inside = true;
        boolean notOnWormNew = true;
        boolean noOverlapNew = true;
        boolean sawApple = false, sawGold = false, sawSpeed = false, sawPoison = false;
        for(int i = 0 ; i < 500 ; i++) {
            b.addItem(0);
            Item item = b.getItem(b.getItems().size()-1);
            if(item.getX() < 0 || item.getX() >= COLS || item.getY() < 0 || item.getY() >= ROWS )
                inside = false;
            if(itemOnWorm(b, item))
                notOnWormNew = false;
            for(int j = 0 ; j < b.getItems().size()-1 ; j++) {
                if(b.getItem(j).getX() == item.getX() && b.getItem(j).getY() == item.getY())
                    noOverlapNew = false;
            }
            if(item.getType().equals("apple"))
                sawApple = true;
            if(item.getType().equals("goldApple"))
                sawGold = true;
            if(item.getType().equals("speedPotion"))
                sawSpeed = true;
            if(item.getType().equals("poison"))
                sawPoison = true;
            b.getItems().remove(b.getItems().size()-1);
        } 
        check("new item is always inside the board", inside);
        check("new item never spawn on worm", notOnWormNew);
        check("new item never overlap on the same cell", noOverlapNew);
        check("all 4 item types can spawn", sawApple && sawGold && sawPoison && sawSpeed);

        // --- reset ---
        System.out.println("\n--- reset ---");
        b = new GameBoard();
        makeASpaceForTest(b);
        b.getItem(0).setX(6);
        b.getItem(0).setY(5);
        b.update();
        makeASpaceForTest(b);
        b.setIsSpeedUp(true);
        b.setGameOver(true);
        b.setGameWin(true);
        b.getWorm().setVelocityX(1);
        b.getWorm().setVelocityY(0);
        b.getWorm().addTail();
        b.reset();
        check("after reset, gameOver is false", !b.getGameOver());
        check("after reset, gameWin is false", !b.getGameOver());
        check("after reset, head is back at (5,5)", head(b, 5, 5));
        check("after reset, body is back to 3 segments", b.getWorm().getBodySize() == 3);
        check("after reset, worm move right (1,0)", velocity(b, 1,0));
        check("after reset, second is back to 0", b.getSecond() == 0);
        check("after reset, board has 8 items again", b.getItems().size() == 8);
        check("after reset, amountEatItem is back to 0", b.getAmountEatItem() == 0);
        check("after reset, mouth is closed", !b.getIsMonthOpen());
        check("after reset, speed up is off", !b.getIsSpeedUp());

        // --- collision ---
        System.out.println("\n--- collision ---");
        b = new GameBoard();
        check("collision(3,4,3,4) is true", b.collision(3, 4, 3, 4));
        check("collision(3,4,4,3) is false", !b.collision(3, 4, 4, 3));
        check("collision(3,4,3,5) is false", !b.collision(3, 4, 3, 5));

        // --- eatItem ---
        System.out.println("\n--- eatItem ---");
        b = new GameBoard();
        Item eaten = putItemInFront(b, 0, "apple");
        b.update();
        check("eat apple, isEatItem is true", b.getIsEatItem());
        check("eat apple, eaten type is \"apple\"", "apple".equals(b.getIsEatItemType()));
        check("eat apple, amountEatItem is 1", b.getAmountEatItem() == 1);
        check("eat apple, the eaten item is removed", !b.getItems().contains(eaten));
        check("eat apple, a new item replace (still 8 items)", b.getItems().size() == 8);
        check("eat apple, mouth open",b.getIsMonthOpen());

        makeASpaceForTest(b);
        b.update();
        check("next frame, isEatItem goes back to false", !b.getIsEatItem());
        check("next frame, eaten type goes back to empty", b.getIsEatItemType().equals(""));
        check("next frame, mouth still open", b.getIsMonthOpen());
        b.update();
        check("two frames after eating, mouth close", !b.getIsMonthOpen());

        b = new GameBoard();
        putItemInFront(b, 0, "goldApple");
        b.update();
        check("eat gold apple, eaten type is \"goldApple\"", "goldApple".equals(b.getIsEatItemType()));

        b = new GameBoard();
        putItemInFront(b, 0, "speedPotion");
        b.update();
        check("eat speed potion, eaten type is \"speedPotion\"", "speedPotion".equals(b.getIsEatItemType()));

        b = new GameBoard();
        putItemInFront(b, 0, "poison");
        b.update();
        check("eat poison, eaten type is \"poison\"", "poison".equals(b.getIsEatItemType()));

        b = new GameBoard();
        makeASpaceForTest(b);
        b.update();
        check("nothing in front, isEatItem stays false", !b.getIsEatItem() && b.getAmountEatItem() == 0);

        b = new GameBoard();
        putItemInFront(b, 0, "apple");
        b.update();
        makeASpaceForTest(b);
        b.getItem(0).setX(7);
        b.getItem(0).setY(5);
        b.update();
        check("eat two items in a row, amountEatItem is 2", b.getAmountEatItem() == 2);

        // --- update ---
        System.out.println("\n--- update ---");
        b = new GameBoard();
        makeASpaceForTest(b);
        b.update();
        check("one normal frame is 0.1 second", Math.abs(b.getSecond() - 0.1f) < 0.0001);

        b = new GameBoard();
        makeASpaceForTest(b);
        b.setIsSpeedUp(true);
        b.update();
        check("one speed up frame is 0.05 second", Math.abs(b.getSecond() - 0.05f) <0.0001);

        b = new GameBoard();
        makeASpaceForTest(b);
        Item old = b.getItem(0);
        Item fresh = b.getItem(7);
        fresh.setInitialSpawnTime(15);
        b.setSecond(20);
        b.update();
        check("item order than "+GameConfig.despawnAppleSceond+"s is removed", !b.getItems().contains(old));
        check("expired items are replaced (still 8)", b.getItems().size() == 8);
        check("younger item stay on the board", b.getItems().contains(fresh));

        b = new GameBoard();
        makeASpaceForTest(b);
        Item old1 = b.getItem(0);
        Item old2 = b.getItem(1);
        b.setSecond(20);
        b.update();
        check("two expired items must removed at the same frame", !hasSameItem(b, old1) && !hasSameItem(b, old2));

        // --- throw setter ---
        System.out.println("\n--- throw setter ---");
        b = new GameBoard();
        boolean threw;
        
        threw = false;
        try {
            b.setAmountEatItem(-1);
        }catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setAmountEatItem(-1) throws IllegalArgumentException", threw);

        threw = false;
        try {
            b.setSecond(-1);
        }catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setSecond(-1) throws IllegalArgumentException", threw);

        threw = false;
        try {
            b.setIsMonthOpenFrame(3);
        }catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setIsMonthOpenFrame(3) throws IllegalArgumentException", threw);   

        threw = false;
        try {
            b.setIsMonthOpenFrame(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setIsMonthOpenFrame(-1) throws IllegalArgumentException", threw);

        threw = false;
        try {
            b.setIsMonthOpenFrame(0);
            b.setIsMonthOpenFrame(1);
            b.setIsMonthOpenFrame(2);
        } catch (Exception e) {
            threw = true;
        }
        check("setIsMonthOpenFrame(0), (1), (2) is allowed", !threw);

        // --- current score ---
        System.out.println("\n--- current score ---");
        b = new GameBoard();
        check("initial currentScore is 0", b.getCurrentScore() == 0);

        b.setCurrentScore(5);
        check("setCurrentScore(5) changes score to 5", b.getCurrentScore() == 5);

        b.setCurrentScore(0);
        check("setCurrentScore(0) is allowed", b.getCurrentScore() == 0);

        b.setCurrentScore(7);
        threw = false;
        try {
            b.setCurrentScore(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("setCurrentScore(-1) throws IllegalArgumentException", threw);
        check("setCurrentScore(-1) score stay 7", b.getCurrentScore() == 7);

        b.setCurrentScore(12);
        b.reset();
        check("after reset, currentScore is back to 0", b.getCurrentScore() == 0);
    }
}