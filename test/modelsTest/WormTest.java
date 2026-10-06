package test.modelsTest;

import models.Worm;

public class WormTest {
    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
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
                    + " - re-run with: java -ea PlaylistTest\n");
        }

        System.out.println("=== Worm Test ===");

        runtest();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    public static boolean getBodyPosition(Worm w, int segment, int x, int y) {
        return w.getBody(segment).getX() == x && w.getBody(segment).getY() == y ;
    }

    public static void  runtest() {

        // --- initial status ---
        System.out.println("--- initial status ---");
        Worm w = new Worm(5, 5);
        check("initial of the head is (5, 5)", w.getX() == 5 && w.getY() == 5);
        check("initial velocity is (1, 0)", w.getVelocityX() == 1 && w.getVelocityY() == 0);
        check("initial length of body is 3 segments", w.getBodySize() == 3);
        check("initail position of first body segment", getBodyPosition(w, 0, 4, 5));
        check("initail position of second body segment", getBodyPosition(w, 1, 3, 5));
        check("initail position of third body segment", getBodyPosition(w, 2, 2, 5));

        // --- move ---
        System.out.println("\n--- move ---");
        w = new Worm(5, 5);
        w.move();
        check("move right 1 step, change head to (6,5)", w.getX() == 6 && w.getY() == 5);
        check("move right 1 step, change first body segment position to (5,5)", getBodyPosition(w, 0, 5, 5));
        check("move right 1 step, change second body segment position to (4,5)", getBodyPosition(w, 1, 4, 5));
        check("move right 1 step, change third body segment position to (3,5)", getBodyPosition(w, 2, 3, 5));

        w = new Worm(5, 5);
        w.setVelocityX(0);
        w.setVelocityY(-1);
        w.move();
        check("move up 1 step, change head position to (5,4)", w.getX() == 5 && w.getY() == 4);
        check("move up 1 step, change first body segment position to (5,5)", getBodyPosition(w, 0, 5, 5));
        check("move up 1 step, change second body segment position to (4,5)", getBodyPosition(w, 1, 4, 5));
        check("move up 1 step, change third body segment position to (3,5)", getBodyPosition(w, 2, 3, 5));

        w = new Worm(5,5);
        w.setVelocityX(0);
        w.setVelocityY(1);
        w.move();
        check("move down 1 step, change head position to (5,6)", w.getX() == 5 && w.getY() == 6);
        check("move down 1 step, change first body segment position to (5,5)", getBodyPosition(w, 0, 5, 5));
        check("move down 1 step, change second body segment position to (4,5)", getBodyPosition(w, 1, 4, 5));
        check("move down 1 step, change third body segment position to (3,5)", getBodyPosition(w, 2, 3, 5));

        w = new Worm(5, 5);
        w.setVelocityX(0);
        w.setVelocityY(1);
        w.move();
        w.setVelocityX(-1);
        w.setVelocityY(0);
        w.move();
        check("move down and move left, change head position to (4,6)", w.getX() == 4 && w.getY() == 6);
        check("move down and move left, change first body segment position to (5,6)", getBodyPosition(w, 0, 5, 6));
        check("move down and move left, change second body segment position to (5,5)", getBodyPosition(w, 1, 5, 5));
        check("move down and move left, change third body segment position to (4,5)", getBodyPosition(w, 2, 4, 5));

        w = new Worm(5, 5);
        for(int i=0 ; i<5 ; i++) {
            w.move();
            i++;
        }
        check("move right 5 times, length remains the same", w.getBodySize() == 3);

        // --- getter/setter ---
        System.out.println("\n--- getter/setter ---");
        w = new Worm(5, 5);
        w.setX(10);
        w.setY(9);
        check("can change head position", w.getX() == 10 && w.getY() == 9 );
        w.setBody(2, 2, 0);
        check("can change body position", getBodyPosition(w, 0, 2, 2));
        check("call getLastBody() returns the tail position (2,5)", w.getLastBody().getX() == 2 && w.getLastBody().getY() == 5);
        

        // --- addtail ---
        System.out.println("\n--- addtail ---");
        w = new Worm(5, 5);
        w.addTail();
        check("call addTail(), body has lengthened", w.getBodySize() == 4);
        check("call addTail(), add the new node at the end", getBodyPosition(w, 3, 2, 5));
        check("call addTail(), no body segment overlaps the head", !getBodyPosition(w, 3, w.getX(), w.getY()));

        w.move();
        check("call addTail() and moving one step, the body segments are correctly aligned ", 
            getBodyPosition(w, 0, 5, 5) && getBodyPosition(w, 1, 4, 5) && 
            getBodyPosition(w, 2, 3, 5) && getBodyPosition(w, 3, 2, 5));

        w = new Worm(5, 5);
        w.addTail();
        w.addTail();
        w.move();
        w.move();
        check("call addTail() two times and moving two step, the body segments are correctly aligned ", 
            getBodyPosition(w, 0, 6, 5) && getBodyPosition(w, 1, 5, 5) && getBodyPosition(w, 2, 4, 5) && 
            getBodyPosition(w, 3, 3, 5) && getBodyPosition(w, 4, 2, 5));

    }
}