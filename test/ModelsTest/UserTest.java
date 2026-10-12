package test.ModelsTest;

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
    public static int passed = 0;
    public static int failed = 0;

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

    private static void writeSession(String content) throws IOException {
        Files.write(SESSION, content.getBytes(StandardCharsets.UTF_8));
    }

    /** true ถ้า code โยน IllegalArgumentException */
    private static boolean throwsIAE(Runnable r) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea UserTest\n");
        }

        System.out.println("=== User Test (Login / Sign up) ===");

       
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
        } catch (Exception | AssertionError e) {
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

        if (failed > 0 && System.getProperty("runAll") == null) {
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

        GameConfig.saveSession(true, "yam", 7);
        GameConfig.clearSession();
        check("clearSession alone resets isLogin", !GameConfig.isLogin);
        check("clearSession alone resets username and score",
                GameConfig.currentUser.equals("") && GameConfig.highScore == 0);

        writeSession("");
        GameConfig.isLogin = true;
        GameConfig.currentUser = "keep";
        GameConfig.highScore = 9;
        GameConfig.loadSession();
        check("loadSession with empty session file keeps current values",
                GameConfig.isLogin && GameConfig.currentUser.equals("keep") && GameConfig.highScore == 9);

        // --- ReadFile ---
        System.out.println("\n--- ReadFile (more) ---");
        writeData(SEED);
        rf = new ReadFile();
        check("isContainPassword finds existing password", rf.isContainPassword("1234"));
        check("isContainPassword returns false for unknown password", !rf.isContainPassword("nope"));
        check("isContainScore finds existing score", rf.isContainScore("52"));
        check("isContainScore returns false for unknown score", !rf.isContainScore("999"));
        check("each account row has 3 fields", rf.getAccounts().get(2).length == 3);
        writeData(SEED + "z,z,0\n");
        rf.update();
        check("update() re-reads the file (5 accounts)", rf.getAccounts().size() == 5);
        check("update() does not duplicate old rows", rf.getUsernames().size() == 5);

        // --- WriteFile ---
        System.out.println("\n--- WriteFile ---");
        writeData("");
        models.WriteFile wf = new models.WriteFile();
        wf.writeUsername("a");
        wf.writePassword("b");
        wf.writeScore("3");
        wf.writeNewLine();
        check("writeUsername + writePassword + writeScore + writeNewLine build one line",
                readData().equals("a,b,3\n"));

        writeData("");
        wf.writeNewAccount("bob", "pw");
        check("writeNewAccount writes 'bob,pw,0'", readData().equals("bob,pw,0\n"));

        writeData(SEED);
        wf.updateScore("Gong", "99");
        check("WriteFile.updateScore changes the target user's score",
                readData().equals("Gong,159357,99\nUser123456,123456,52\nkiddo,1234,5\nyam,yam190450,1\n"));

        writeData(SEED);
        wf.updateScore("ghost", "99");
        check("WriteFile.updateScore with unknown user leaves file unchanged", readData().equals(SEED));

        // --- User (setters / edge cases) ---
        System.out.println("\n--- User (setters / edge cases) ---");
        writeData(SEED);
        u = new User();
        check("new User() has no username before login", u.getUsername() == null);
        u.setUsername("tom");
        u.setScore("8");
        check("setUsername / getUsername", "tom".equals(u.getUsername()));
        check("setScore / getScore", "8".equals(u.getScore()));

        writeData(SEED);
        u = new User();
        u.updateScore("ghost", "5");
        check("User.updateScore with unknown user leaves file unchanged", readData().equals(SEED));

        writeData("");
        u = new User();
        check("empty user file: no username found", !u.isContainUsername("Gong"));
        check("empty user file: login returns false", !quietLogin(u, "Gong", "159357"));
        u.createNewAccount("first", "1");
        check("sign up into an empty file works", readData().equals("first,1,0\n"));
        check("login works after sign up into an empty file", quietLogin(new User(), "first", "1"));

        writeData(SEED);
        u = new User();
        u.createNewAccount("newbie", "pass99");
        check("new account cannot log in with wrong password", !quietLogin(new User(), "newbie", "wrong"));

        
        System.out.println("\n--- validation ---");
        writeData(SEED);
        u = new User();
        check("isUsernameAndPasswordVaild accepts normal input", u.isUsernameAndPasswordVaild("kiddo", "1234"));
        check("isUsernameAndPasswordVaild rejects empty username", !u.isUsernameAndPasswordVaild("", "1234"));
        check("isUsernameAndPasswordVaild rejects blank username", !u.isUsernameAndPasswordVaild("   ", "1234"));
        check("isUsernameAndPasswordVaild rejects empty password", !u.isUsernameAndPasswordVaild("kiddo", ""));
        check("isUsernameAndPasswordVaild rejects placeholder ' Username'", !u.isUsernameAndPasswordVaild(" Username", "1234"));
        check("isUsernameAndPasswordVaild rejects placeholder ' Password'", !u.isUsernameAndPasswordVaild("kiddo", " Password"));
        check("isUsernameAndPasswordVaild rejects username of 20 characters",
                !u.isUsernameAndPasswordVaild("12345678901234567890", "1234"));
        check("isUsernameAndPasswordVaild accepts username of 19 characters",
                u.isUsernameAndPasswordVaild("1234567890123456789", "1234"));
        check("setUsername rejects empty string", throwsIAE(() -> new User().setUsername("")));
        check("setUsername rejects null", throwsIAE(() -> ( new User()).setUsername(null)));
        check("setScore rejects empty string", throwsIAE(() -> new User().setScore("")));
        check("setScore rejects null", throwsIAE(() -> new User().setScore(null)));
        check("isContainUsername rejects empty string", throwsIAE(() -> new User().isContainUsername("")));
        check("createNewAccount rejects empty username", throwsIAE(() -> new User().createNewAccount("", "pw")));
        check("createNewAccount rejects empty password", throwsIAE(() -> new User().createNewAccount("bob", "")));
        check("rejected createNewAccount does not change the file", readData().equals(SEED));
    }
}
