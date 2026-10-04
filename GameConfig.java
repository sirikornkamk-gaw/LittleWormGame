package config;
import java.awt.* ;

public class GameConfig {
    //Color
    public static final Color GreenBlue = new Color(16, 86, 102);
    public static final Color LightGreen = new Color(131, 153, 88);
    public static final Color DarkerGreen = new Color(10, 51, 35);
    public static final Color Greentable = new Color(86, 108, 43);
    public static final Color DarkGreen = new Color(56, 78, 12);
    public static final Color Greenshadow = new Color(60, 114, 46) ;
    public static final Color GreenApple = new Color(194, 233, 116);
    public static final Color RedApple = new Color(227, 53, 53);
    public static final Color LightRedApple = new Color(254, 66, 66);
    public static final Color Applepulp = new Color(223, 226, 139);
    public static final Color Applestalk = new Color(113, 36, 36);
    public static final Color Beige = new Color(255, 252, 213);
    public static final Color wormcolor = new Color(243, 188, 175);
    public static final Color DarkPink = new Color(232, 129, 129);
    public static final Color Grey = new Color(112, 93, 93);
    public static final Color PinkBG = new Color(241, 180, 175);
    public static final Color Yellow = new Color(248, 213, 99);
    public static final Color purple = new Color(208, 115, 239);
    public static final Color Black = Color.black;
    public static final Color White = Color.WHITE;


    //Font
    public static final String Irish_Grover = "assets/font/IrishGrover-Regular.ttf";
    public static final String Jersey_20 = "assets/font/Jersey20-Regular.ttf";
    public static final String Jersey_10 = "assets/font/Jersey10-Regular.ttf";

    //Size of Board
    public static final int tilesize = 70;
    public static final int BOARD_WIDTH = 1750;
    public static final int BOARD_HEIGHT = 840;

    //ImagePath
    public static final String wormOpenMouth = "assets/image/WormOpenMouth.png";
    public static final String wormCloseMouth = "assets/image/WormCloseMouth.png";
    public static final String PoisonItem = "/assets/image/Poison.png" ;
    public static final String SpeedItem = "/assets/image/SpeedItem.png" ;

    //FilePath
    public static final String PATH_USERNAME = "../userData/usernames.txt" ;
    public static final String PATH_PASSWORD = "../userData/passwords.txt" ;
    public static final String PATH_SCORE = "../userData/scores.txt" ;

    public static boolean isLogin = false ;
}
