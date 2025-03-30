package src.Util;
import src.User_interface.StartMenu;

/**
 * This class represents the Game Startup Interface.
 * Description: Game theme panel.
 * @author Lin Yujie
 * @since 1.0
 **/
public class GameDemo {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        GameSelect gameSelect = new GameSelect();
        gameSelect.stateSelection(1);
        StartMenu startmenu = new StartMenu();
        startmenu.launch();
    }
}