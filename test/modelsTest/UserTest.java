package test.modelsTest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import config.GameConfig;
import models.ReadFile;
import models.User;

public class UserTest {
    private static int passed = 0;
    private static int failed = 0;

    private static final Path DATA = Paths.get(GameConfig.userData);
    private static final Path SESSION = Paths.get(GameConfig.sessionFile);


    private static final String SEED =
            "Gong,159357,0\n" +
            "User123456,123456,52\n" +
            "kiddo,1234,5\n" +
            "yam,yam190450,1\n";

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    // ---------- helpers ----------

    private static void writeData(String content) throws IOException {
        if (DATA.getParent() != null) Files.createDirectories(DATA.getParent());
        Files.write(DATA, content.getBytes(StandardCharsets.UTF_8));
    }

    
    private static String readData() throws IOException {
        return new String(Files.readAllBytes(DATA), StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    private static String readSession() throws IOException {
        return new String(Files.readAllBytes(SESSION), StandardCharsets.UTF_8).trim();
    }

    /** loginAccount() มี println พิมพ์ username/password — ปิดเสียงไว้ไม่ให้รก output */
    private static boolean quietLogin(User u, String username, String password) {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
        try {
            return u.loginAccount(username, password);
        } finally {
            System.setOut(original);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea UserTest\n");
        }

        System.out.println("=== User Test (Login / Sign up) ===");

        // backup ไฟล์จริง แล้วคืนค่าให้หลังเทส
        byte[] dataBackup = null;
        byte[] sessionBackup = null;
        try {
            if (Files.exists(DATA)) dataBackup = Files.readAllBytes(DATA);
            if (Files.exists(SESSION)) sessionBackup = Files.readAllBytes(SESSION);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            runtest();
        } catch (Exception e) {
            failed++;
            System.out.println("[FAIL] unexpected exception: " + e);
            e.printStackTrace();
        } finally {
            try {
                if (dataBackup != null) Files.write(DATA, dataBackup);
                else Files.deleteIfExists(DATA);
                if (sessionBackup != null) Files.write(SESSION, sessionBackup);
                else Files.deleteIfExists(SESSION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    public static void runtest() throws IOException {

        // --- ReadFile ---
        System.out.println("--- ReadFile ---");
        writeData(SEED);
        ReadFile rf = new ReadFile();
        check("read 4 accounts from file", rf.getAccounts().size() == 4);
        check("read 4 usernames", rf.getUsernames().size() == 4);
        check("first username is Gong", rf.getUsernames().get(0).equals("Gong"));
        check("first password is 159357", rf.getPasswords().get(0).equals("159357"));
        check("second score is 52", rf.getScores().get(1).equals("52"));
        check("isContainUsername finds existing user", rf.isContainUsername("kiddo"));
        check("isContainUsername returns false for unknown user", !rf.isContainUsername("nobody"));

        // --- login: success ---
        System.out.println("\n--- login: success ---");
        writeData(SEED);
        User u = new User();
        check("login with correct username and password returns true", quietLogin(u, "kiddo", "1234"));
        check("after login, getUsername() is kiddo", "kiddo".equals(u.getUsername()));
        check("after login, getPassword() is 1234", "1234".equals(u.getPassword()));
        check("after login, getScore() is 5", "5".equals(u.getScore()));

        u = new User();
        check("login with the first account in file", quietLogin(u, "Gong", "159357"));
        u = new User();
        check("login with the last account in file", quietLogin(u, "yam", "yam190450"));
        check("after login as last account, score is 1", "1".equals(u.getScore()));

        // --- login: failure ---
        System.out.println("\n--- login: failure ---");
        u = new User();
        check("login with wrong password returns false", !quietLogin(u, "kiddo", "wrong"));
        check("failed login does not set username", u.getUsername() == null);
        check("login with unknown username returns false", !quietLogin(u, "nobody", "1234"));
        check("login with another user's password returns false", !quietLogin(u, "kiddo", "159357"));
        check("login with empty username and password returns false", !quietLogin(u, "", ""));
        check("login is case-sensitive for username", !quietLogin(u, "KIDDO", "1234"));
        check("login is case-sensitive for password", !quietLogin(u, "yam", "YAM190450"));

        // --- sign up ---
        System.out.println("\n--- sign up ---");
        writeData(SEED);
        u = new User();
        check("isContainUsername returns true for existing user", u.isContainUsername("Gong"));
        check("isContainUsername returns false for new username", !u.isContainUsername("newbie"));

        u.createNewAccount("newbie", "pass99");
        check("createNewAccount appends a line with score 0",
                readData().equals(SEED + "newbie,pass99,0\n"));

        User reloaded = new User();
        check("new User() sees the new account", reloaded.isContainUsername("newbie"));
        check("new account can log in", quietLogin(reloaded, "newbie", "pass99"));
        check("new account starts with score 0", "0".equals(reloaded.getScore()));
        check("old accounts still exist after sign up", reloaded.isContainUsername("Gong")
                && reloaded.isContainUsername("User123456")
                && reloaded.isContainUsername("kiddo")
                && reloaded.isContainUsername("yam"));
        check("file now has 5 accounts", new ReadFile().getAccounts().size() == 5);

        reloaded.createNewAccount("second", "abc");
        check("sign up two accounts in a row, file has 6 accounts", new ReadFile().getAccounts().size() == 6);
        check("second new account can log in", quietLogin(new User(), "second", "abc"));

        // --- update score ---
        System.out.println("\n--- update score ---");
        writeData(SEED);
        u = new User();
        quietLogin(u, "kiddo", "1234");
        u.updateScore("kiddo", "20");
        check("updateScore changes score in the User object", "20".equals(u.getScore()));
        check("updateScore changes only that user's line in the file",
                readData().equals("Gong,159357,0\nUser123456,123456,52\nkiddo,1234,20\nyam,yam190450,1\n"));
        check("updateScore saves session as logged in", GameConfig.isLogin);
        check("updateScore saves session username", "kiddo".equals(GameConfig.currentUser));
        check("updateScore saves session high score", GameConfig.highScore == 20);
        check("session file content is 'true,kiddo,20'", readSession().equals("true,kiddo,20"));

        User afterUpdate = new User();
        quietLogin(afterUpdate, "kiddo", "1234");
        check("login again shows the new score 20", "20".equals(afterUpdate.getScore()));

        // --- session ---
        System.out.println("\n--- session ---");
        GameConfig.saveSession(true, "yam", 7);
        check("saveSession sets isLogin", GameConfig.isLogin);
        check("saveSession writes 'true,yam,7' to file", readSession().equals("true,yam,7"));

        GameConfig.isLogin = false;
        GameConfig.currentUser = "";
        GameConfig.highScore = 0;
        GameConfig.loadSession();
        check("loadSession restores isLogin", GameConfig.isLogin);
        check("loadSession restores username", "yam".equals(GameConfig.currentUser));
        check("loadSession restores high score", GameConfig.highScore == 7);

        // --- logout ---
        System.out.println("\n--- logout ---");
        u = new User();
        u.logoutAccount();
        check("logout sets isLogin to false", !GameConfig.isLogin);
        check("logout clears current user", GameConfig.currentUser.equals(""));
        check("logout resets high score to 0", GameConfig.highScore == 0);
        check("logout writes 'false,,0' to session file", readSession().equals("false,,0"));
    }
}
