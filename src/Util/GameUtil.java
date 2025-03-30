package src.Util;

/**
 * This class includes a series of default window parameters and the paths of pictures used in the game.
 * @author Lin Yujie, Pei Yinan
 * @since 1.0
 */
public class GameUtil {
    /**
     * The parameter WINDOW_X, WINDOW_Y is the coordinate of the up-left corner of
     * the window relative to the computer screen.
     * WINDOW_WIDTH, WINDOW_HEIGHT is the width and height of the window.
     */
    public static int WINDOW_X;
    public static int WINDOW_Y;
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;


    /**
     * Parameters and pictures in the start menu
     */
    // The speed of the picture rolling in the background
    public static int BACKGROUND_SPEED = 2;

    //Load in the paths of the pictures in the start menu
    public static String RUSHHOUR_PNG = "image\\StartMenu\\Rushhour.png";
    public static String LONG_PICTURE_PNG = "image\\StartMenu\\LongPicture.png";
    public static String BUTTON_START_CAR_SMALL_PNG = "image\\StartMenu\\Button\\StartCar_small.png";
    public static String BUTTON_START_CAR_BIG_PNG = "image\\StartMenu\\Button\\StartCar_big.png";
    public static String BUTTON_EXIT_CAR_SMALL_PNG = "image\\StartMenu\\Button\\ExitCar_Small.png";
    public static String BUTTON_EXIT_CAR_BIG_PNG = "image\\StartMenu\\Button\\ExitCar_Big.png";
    public static String BUTTON_SETTING_SMALL = "image\\StartMenu\\Button\\setting_small.png";
    public static String BUTTON_SETTING_BIG = "image\\StartMenu\\Button\\setting_big.png";

    /**
     * Parameters and pictures in the thank-you interface
     */
    // Load in the paths of the pictures in the Thank-you interface
    public static String THANK_YOU_PNG = "image\\ThankYouInterface\\ThankYou.png";


    /**
     * Parameters and pictures in the setting menu
     */
    // Load in the paths of pictures in the setting menu
    public static String MUTE_BUTTON_SMALL = "image\\SettingMenu\\mute_small.png";
    public static String VOL_BUTTON_SMALL = "image\\SettingMenu\\vol_small.png";
    public static String SETTING_MENU_BACKGROUND="image\\SettingMenu\\back1.png";


    /**
     * Parameters and pictures in the pick menu
     */
    public static boolean pickMenuBackFlag = true;

    // Load in the paths of the pictures in the pick menu
    public static String PICKMENU_BACKGROUND = "image\\PickMenu\\PickMenuBackground.png";
    public static String SELECT_LEVEL = "image\\PickMenu\\selectLevel.png";
    public static String[] LEVEL_BUTTONS = {"image\\PickMenu\\easy.png","image\\PickMenu\\medm.png",
            "image\\PickMenu\\hard.png","image\\PickMenu\\exert.png"};
    public static String[] GAME_INTERFACE = {"image\\PickMenu\\GameInterface1.png",
            "image\\PickMenu\\GameInterface2.png","image\\PickMenu\\GameInterface3.png",
            "image\\PickMenu\\GameInterface4.png"};
    public static String I_BUTTON = "image\\PickMenu\\AdditionalInformation.png";
    public static String BACK1 = "image\\PickMenu\\back.png";


    /**
     * Parameters and pictures in the rules frame
     */
    public static String RULESFRAME_BACKGROUND = "image\\RulesFrame\\RulesFrameBackground.png";


    /**
     * Parameters and pictures in the game interface
     */
    public static boolean gameInterfaceBackFlag = false;

    // The space between different components as well as the edge
    public static int OFFSET = 120;

    // The length of the 6*6 grid
    public static int SQUARE_LENGTH = 900;
    public static int BUTTON_WIDTH1 = 120;
    public static int BUTTON_HEIGHT1 = 132;
    public static int BUTTON_WIDTH2 = 500;
    public static int BUTTON_HEIGHT2 = 250;
    public static int level = 0;
    public static boolean isHintMode = false;
    public static int answerIndex = 0;
    public static boolean restartFlag = false;

    // Load in the paths of the pictures in the game interface
    public static String GAMEINTERFACE_BACKGROUND = "image\\GameInterface\\GameInterfaceBackground.png";
    public static String PARKING_LOT = "image\\GameInterface\\ParkingLots\\parkingLot.png";
    public static String PARKING_LOT_FRAME = "image\\GameInterface\\ParkingLots\\parkingLotFrame.png";
    public static String RED_LIGHT = "image\\GameInterface\\ParkingLots\\redLight.png";
    public static String GREEN_LIGHT = "image\\GameInterface\\ParkingLots\\greenLight.png";
    public static String BACK2 = "image\\GameInterface\\Buttons\\back.png";
    public static String RESTART = "image\\GameInterface\\Buttons\\restart.png";
    public static String NEXT = "image\\GameInterface\\Buttons\\next.png";
    public static String NO_NEXT = "image\\GameInterface\\Buttons\\noNext.png";
    public static String HINT = "image\\GameInterface\\Buttons\\hint.png";
    public static String BLUE_CAR_VERTICAL = "image\\GameInterface\\Vehicles\\blue_car_vertical.png";
    public static String BLUE_CAR_HORIZONTAL = "image\\GameInterface\\Vehicles\\blue_car_horizontal.png";
    public static String RED_CAR = "image\\GameInterface\\Vehicles\\red_car.png";
    public static String TRUCK_VERTICAL = "image\\GameInterface\\Vehicles\\truck_vertical.png";
    public static String TRUCK_HORIZONTAL = "image\\GameInterface\\Vehicles\\truck_horizontal.png";
    public static String LEVEL_DISPLAY = "image\\GameInterface\\Recorders\\level.png";
    public static String MOVES_DISPLAY = "image\\GameInterface\\Recorders\\moves.png";
    public static String TIMER_DISPLAY = "image\\GameInterface\\Recorders\\time.png";
    public static String HINT_LEFT = "image\\GameInterface\\HintMode\\hintLeft.png";
    public static String HINT_RIGHT = "image\\GameInterface\\HintMode\\hintRight.png";
    public static String HINT_UP = "image\\GameInterface\\HintMode\\hintUp.png";
    public static String HINT_DOWN = "image\\GameInterface\\HintMode\\hintDown.png";
}