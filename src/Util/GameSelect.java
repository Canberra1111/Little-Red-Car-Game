package src.Util;
/**
 * This class updates the parameters of the window's size and position when under different game states.
 * @author Lin Yujie
 * @since 1.0
 */

public class GameSelect {
    /**
     * This method includes parameter state.
     *
     * @param state state = 1,2,3,4 corresponds to the start menu (setting interface), pick
     * menu (rules frame), game interface (win interface), thank you interface respectively.
     */
    public void stateSelection(int state){
        switch (state){
            case 1:
                GameUtil.WINDOW_X = 500;
                GameUtil.WINDOW_Y = 350;
                GameUtil.WINDOW_WIDTH = 1600;
                GameUtil.WINDOW_HEIGHT = 800;
                break;
            case 2:
                GameUtil.WINDOW_X = 300;
                GameUtil.WINDOW_Y = 200;
                GameUtil.WINDOW_WIDTH = 2000;
                GameUtil.WINDOW_HEIGHT = 1200;
                break;
            case 3:
                GameUtil.WINDOW_X = 350;
                GameUtil.WINDOW_Y = 150;
                GameUtil.WINDOW_WIDTH = 4*GameUtil.OFFSET+
                        GameUtil.BUTTON_WIDTH2 +GameUtil.SQUARE_LENGTH;
                GameUtil.WINDOW_HEIGHT = 3*GameUtil.OFFSET+
                        GameUtil.SQUARE_LENGTH;
                break;
            case 4:
                GameUtil.WINDOW_X = 300;
                GameUtil.WINDOW_Y = 700;
                GameUtil.WINDOW_WIDTH = 800;
                GameUtil.WINDOW_HEIGHT = 800;
                break;
            default:
                break;
        }
    }
}