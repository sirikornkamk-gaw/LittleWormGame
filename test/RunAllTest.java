package test;

import test.ModelsTest.GameBoardTest;
import test.ModelsTest.UserTest;
import test.ModelsTest.WormTest;
import test.ModelsTest.ItemTest;

public class RunAllTest {
    public static void main(String[] args) {

        System.setProperty("runAll", "true");

        WormTest.main(args);
        System.out.println();
        GameBoardTest.main(args);
        System.out.println();
        UserTest.main(args);
        System.out.println();
        ItemTest.main(args);

        int passed = WormTest.passed + GameBoardTest.passed + UserTest.passed + ItemTest.passed;
        int failed = WormTest.failed + GameBoardTest.failed + UserTest.failed + ItemTest.failed;

        System.out.println("\n==============================");
        System.out.println("        ALL TESTS SUMMARY");
        System.out.println("==============================");
        System.out.println("WormTest      : " + WormTest.passed + " passed, " + WormTest.failed + " failed");
        System.out.println("GameBoardTest : " + GameBoardTest.passed + " passed, " + GameBoardTest.failed + " failed");
        System.out.println("UserTest      : " + UserTest.passed + " passed, " + UserTest.failed + " failed");
        System.out.println("ItemTest      : " + ItemTest.passed + " passed, " + ItemTest.failed + " failed");
        System.out.println("------------------------------");
        System.out.println("Total         : " + passed + " passed, " + failed + " failed");
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
