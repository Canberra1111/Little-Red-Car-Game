package src.User_interface;
import src.Object.Car;
import src.Util.GameSelect;
import src.Util.GameUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.String;

import static src.MusicPlay.SoundControlUnits.*;

/**
 * This class defines the game interface of the 4 levels.
 * It includes the buttons (back, restart, next, hint),
 * the implementation of the board (background, parking lot and its frame),
 * control of vehicles (positioning, mouse and key operation for movement),
 * pedometers and timers,
 * background music (different in every level),
 * and proper execution of the game (win judgement).
 *
 * @author Lin Yujie
 * 1. set the background as well as back, restart, next button.
 * 2. Design level 1 and level 2, including the vehicle's position and answer.
 * 3. Judge when the game wins
 *    (1) implement the automatic move of the car towards the exit when there are no vehicles in the front
 *    (2) make the red light turn green at the exit
 * 4. Implement the hint mode -- make the flashing arrow appear on the correct vehicle in the corresponding step
 *    while limiting the moves of other vehicles
 * @author Pei YiNan
 * 1. Design level 3 and level 4, including the vehicle's position and answer.
 * 2. Implement the pedometer, including the update and reset of the steps.
 * 3. hint mode -- make the flashing arrow appear on the correct vehicle in the corresponding step while limiting the
 *    moves of other vehicles
 * @author Yang Zetianhao
 * 1. Write the mouse and key control of the vehicles by calling the judgements functions of when to move (which
 *    is encapsulated in the car class).
 * 2. Implement the timer, including the update and reset of the time.
 * 3. Display the correct number under different levels.
 * @author Yuan Jiayi -- responsible for finding beautifying the pictures
 * @author Bai Yunpeng -- responsible for adding the sound effects (moving the car, game wins) as well as the
 * background music in different levels
 *
 * @since 1.0
 */

public class GameInterface extends JFrame implements KeyListener {
    // create a layered Pane to draw the interface
    JLayeredPane layeredPane;

    // create an array to store all the cars
    Car[] cars;

    // create the necessary variables that is used to display the level, moves, and time
    JLabel levelLabel = new JLabel(""+ GameUtil.level);
    JLabel stepsLabel = new JLabel("" + moves);
    public static int moves = 0;
    JLabel timeLabel = new JLabel();
    Timer timer;
    int secondsPassed;

    // create variables that is used for the hint mode
    int carIndex, direction;
    public String[] answer;
    public static String[] answer1 = {"3L","3L","0L","4U","4U","4U","2U","2U","2U",
            "7L","7L","7L","5L","5L","5L","2D","2D","2D","1D","1D","1D"};
    public static String[] answer2 = {"2L","0L","0L","3D","4L","4L","1U","6R","6R","7U",
            "8U","9L","9L","9L","9L","7D","8D","6L","6L","1D","1D","1D","4R","4R","3U"};
    public static String[] answer3 = {"8U","8U","4L","4L","5L","1D","1D","1D","0L","0L",
            "3L","3L","7U","7U","7U","7U","6L","9L","2D","2D","2D"};
    public static String[] answer4 = {"9D","2R","2R","3R","1R","7U","7U","10U","12U",
            "0L","11U","4R","4R","4R","11D","0R","7D","7D","7D","0L","3L","1L","11U",
            "11U","11U","6L","4L","4L","12D","12D","5L","5L","5L","8D","8D","9D","9D"};


    /**
     * The play method is the main function of the game Interface.
     * It calls other paint methods defined by our group members to
     * draw the interface and set the game logic
     * @author Lin Yujie
     */
    public void play(){
        //Initialize the layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        setLayout(null);
        setBounds(GameUtil.WINDOW_X,GameUtil.WINDOW_Y,
                GameUtil.WINDOW_WIDTH,GameUtil.WINDOW_HEIGHT);

        // control music (play and stop)
        playMusic();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopMusic();
            }
        });

        // paint the interface, buttons, and set the cars' logic
        paintInterface();
        paintButtons();
        paintCars();

        // display the level, moves and timer on the interface
        level();
        moves();
        timer();

        // Hint mode is implemented
        setString();
        if(GameUtil.isHintMode){
            hintMode();
        }
    }

    /**
     * This method plays the music in different levels
     * @author Bai Yunpeng
     */
    public void playMusic() {
        Stop_Start_Menu_Music();
        if (!GameUtil.restartFlag){
            switch(GameUtil.level){
                case 1:
                    Level1_Music();
                    break;
                case 2:
                    Level2_Music();
                    break;
                case 3:
                    Level3_Music();
                    break;
                case 4:
                    Level4_Music();
                    break;
            }
        }
        GameUtil.restartFlag = false;
    }

    /**
     * This method stops the music in different levels
     * @author Bai Yunpeng
     */
    public static void stopMusic() {
        switch(GameUtil.level){
            case 1:
                Stop_Level1_Music();
                break;
            case 2:
                Stop_Level2_Music();
                break;
            case 3:
                Stop_Level3_Music();
                break;
            case 4:
                Stop_Level4_Music();
                break;
        }
    }


    /**
     * This method paint the interface, including the background picture, the parking lot,
     * the parking lot's frame and the red light at the exit
     * @author Lin Yujie
     */
    public void paintInterface(){
        // Load in GameInterface background and fill the whole window
        ImageIcon gameInterfaceBackground = new ImageIcon(GameUtil.GAMEINTERFACE_BACKGROUND);
        gameInterfaceBackground.setImage(gameInterfaceBackground.getImage().
                getScaledInstance(GameUtil.WINDOW_WIDTH,
                        GameUtil.WINDOW_HEIGHT, Image.SCALE_SMOOTH));
        JLabel backgroundLabel = new JLabel(gameInterfaceBackground);
        backgroundLabel.setVisible(true);
        backgroundLabel.setBounds(0, 0, GameUtil.WINDOW_WIDTH,GameUtil.WINDOW_HEIGHT);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setTitle("Game Interface");
        setVisible(true);

        // Load in the parking lot image and put it in the right position
        ImageIcon parkingLot = new ImageIcon(GameUtil.PARKING_LOT);
        parkingLot.setImage(parkingLot.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH,GameUtil.SQUARE_LENGTH,Image.SCALE_SMOOTH));
        JLabel parkingLotLabel = new JLabel(parkingLot);
        parkingLotLabel.setVisible(true);
        parkingLotLabel.setBounds
                (3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2, 2*GameUtil.OFFSET,
                        GameUtil.SQUARE_LENGTH,GameUtil.SQUARE_LENGTH);
        layeredPane.add(parkingLotLabel, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        // Load in the parking lot's frame
        int frameWidth = 32;
        ImageIcon parkingLotFrame = new ImageIcon(GameUtil.PARKING_LOT_FRAME);
        parkingLotFrame.setImage(parkingLotFrame.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH+frameWidth*2+6,GameUtil.SQUARE_LENGTH+frameWidth*2,Image.SCALE_SMOOTH));
        JLabel parkingLotFrame_Label = new JLabel(parkingLotFrame);
        parkingLotFrame_Label.setVisible(true);
        parkingLotFrame_Label.setBounds
                (3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2-frameWidth, 2*GameUtil.OFFSET-frameWidth,
                        GameUtil.SQUARE_LENGTH+frameWidth*2+10,GameUtil.SQUARE_LENGTH+frameWidth*2);
        layeredPane.add(parkingLotFrame_Label, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        // Load in the exit picture with red lights
        int lightWidth = 40;
        ImageIcon redLights = new ImageIcon(GameUtil.RED_LIGHT);
        redLights.setImage(redLights.getImage().getScaledInstance
                (lightWidth,GameUtil.SQUARE_LENGTH/6+lightWidth*2,Image.SCALE_SMOOTH));
        JLabel redLight_Label = new JLabel(redLights);
        redLight_Label.setVisible(true);
        redLight_Label.setBounds
                (3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2+GameUtil.SQUARE_LENGTH-2,
                        2*GameUtil.OFFSET+GameUtil.SQUARE_LENGTH/3-lightWidth+5,
                        lightWidth,GameUtil.SQUARE_LENGTH/6+lightWidth*2);
        layeredPane.add(redLight_Label, JLayeredPane.MODAL_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);
    }



    /**
     * This method paint the buttons on the interface, including the back,
     * restart, next, and hint button
     * @author Lin Yujie
     */
    public void paintButtons(){
        //Create a back button
        JButton gameBack = new JButton();
        ImageIcon IconBack = new ImageIcon(GameUtil.BACK2);
        IconBack.setImage(IconBack.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameBack.setIcon(IconBack);
        gameBack.setBounds(GameUtil.OFFSET, GameUtil.OFFSET/2,
                6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
        gameBack.setBorder(null);
        gameBack.setContentAreaFilled(false);
        gameBack.setFocusPainted(false);
        gameBack.setBorderPainted(false);
        gameBack.setOpaque(false);
        layeredPane.add(gameBack, JLayeredPane.PALETTE_LAYER);
        gameBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                gameBack.setIcon(new ImageIcon(IconBack.getImage().
                        getScaledInstance(6*GameUtil.BUTTON_WIDTH1/5,
                                6*GameUtil.BUTTON_HEIGHT1/5, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameBack.setIcon(new ImageIcon(IconBack.getImage().
                        getScaledInstance(GameUtil.BUTTON_WIDTH1,
                                GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                secondsPassed = 0;
                updateTimeLabel();
                moves=0;
                stepsLabel.setText("0");
                GameUtil.answerIndex = 0;
                Mouse_Click();
                GameSelect gameSelect = new GameSelect();
                gameSelect.stateSelection(2);
                stopMusic();
                GameUtil.gameInterfaceBackFlag = true;
                dispose();
                GameUtil.isHintMode = false;
                PickMenu pickMenu = new PickMenu();
                pickMenu.select();
            }
        });

        //Create a restart button
        JButton gameRestart = new JButton();
        ImageIcon IconRestart = new ImageIcon(GameUtil.RESTART);
        IconRestart.setImage(IconRestart.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameRestart.setIcon(IconRestart);
        int gameRestart_x = GameUtil.OFFSET+(GameUtil.BUTTON_WIDTH2-GameUtil.BUTTON_WIDTH1)/2;
        gameRestart.setBounds(gameRestart_x, GameUtil.OFFSET/2,
                6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
        gameRestart.setBorder(null);
        gameRestart.setContentAreaFilled(false);
        gameRestart.setFocusPainted(false);
        gameRestart.setBorderPainted(false);
        gameRestart.setOpaque(false);
        layeredPane.add(gameRestart, JLayeredPane.PALETTE_LAYER);
        gameRestart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                gameRestart.setIcon(new ImageIcon(IconRestart.getImage().
                        getScaledInstance(6*GameUtil.BUTTON_WIDTH1/5,
                                6*GameUtil.BUTTON_HEIGHT1/5, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameRestart.setIcon(new ImageIcon(IconRestart.getImage().getScaledInstance
                        (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                secondsPassed = 0;
                updateTimeLabel();
                moves=0;
                stepsLabel.setText("0");
                GameUtil.answerIndex = 0;
                Mouse_Click();
                GameSelect gameSelect = new GameSelect();
                gameSelect.stateSelection(3);
                GameUtil.restartFlag = true;
                dispose();
                GameUtil.isHintMode = false;
                GameInterface gameInterface = new GameInterface();
                gameInterface.play();
            }
        });

        // Create a next button
        JButton gameNext = new JButton();
        ImageIcon IconNext = new ImageIcon(GameUtil.NEXT);
        IconNext.setImage(IconNext.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameNext.setIcon(IconNext);
        int gameNext_x = GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2-GameUtil.BUTTON_WIDTH1;
        gameNext.setBounds(gameNext_x, GameUtil.OFFSET/2,
                6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
        gameNext.setBorder(null);
        gameNext.setContentAreaFilled(false);
        gameNext.setFocusPainted(false);
        gameNext.setBorderPainted(false);
        gameNext.setOpaque(false);
        layeredPane.add(gameNext, JLayeredPane.PALETTE_LAYER);
        gameNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (GameUtil.level < 4){
                    gameNext.setIcon(new ImageIcon(IconNext.getImage().
                            getScaledInstance(6*GameUtil.BUTTON_WIDTH1/5,
                                    6*GameUtil.BUTTON_HEIGHT1/5, Image.SCALE_SMOOTH)));
                    Mouse_hover();
                }
                else {
                    remove(gameNext);
                    JButton gameNoNext = new JButton();
                    ImageIcon IconNoNext = new ImageIcon(GameUtil.NO_NEXT);
                    IconNoNext.setImage(IconNoNext.getImage().getScaledInstance
                            (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
                    gameNoNext.setIcon(IconNoNext);
                    gameNoNext.setBounds(gameNext_x-1, GameUtil.OFFSET/2-3,
                            6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
                    gameNoNext.setBorder(null);
                    gameNoNext.setContentAreaFilled(false);
                    gameNoNext.setFocusPainted(false);
                    gameNoNext.setBorderPainted(false);
                    gameNoNext.setOpaque(false);
                    layeredPane.add(gameNoNext, JLayeredPane.MODAL_LAYER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameNext.setIcon(new ImageIcon(IconNext.getImage().getScaledInstance
                        (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                secondsPassed = 0;
                updateTimeLabel();
                moves = 0;
                stepsLabel.setText("0");
                GameUtil.answerIndex = 0;
                if (GameUtil.level < 4){
                    stopMusic();
                    GameUtil.level += 1;
                    dispose();
                    Mouse_Click();
                    GameSelect gameSelect = new GameSelect();
                    gameSelect.stateSelection(3);
                    GameUtil.isHintMode = false;
                    GameInterface gameInterface = new GameInterface();
                    gameInterface.play();
                }
            }
        });

        // Create a hint button
        JButton gameHint = new JButton();
        ImageIcon IconHint = new ImageIcon(GameUtil.HINT);
        IconHint.setImage(IconHint.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameHint.setIcon(IconHint);
        gameHint.setBounds(GameUtil.OFFSET*3+GameUtil.BUTTON_WIDTH2+GameUtil.SQUARE_LENGTH/2
                        -3*GameUtil.BUTTON_WIDTH1/5, GameUtil.OFFSET/2,
                6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
        gameHint.setBorder(null);
        gameHint.setContentAreaFilled(false);
        gameHint.setFocusPainted(false);
        gameHint.setBorderPainted(false);
        gameHint.setOpaque(false);
        layeredPane.add(gameHint, JLayeredPane.PALETTE_LAYER);
        gameHint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                gameHint.setIcon(new ImageIcon(IconHint.getImage().
                        getScaledInstance(6*GameUtil.BUTTON_WIDTH1/5,
                                6*GameUtil.BUTTON_HEIGHT1/5, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameHint.setIcon(new ImageIcon(IconHint.getImage().getScaledInstance
                        (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                timer.stop();
                secondsPassed = 0;
                updateTimeLabel();
                moves=0;
                stepsLabel.setText("0");
                GameUtil.answerIndex = 0;
                GameUtil.restartFlag = true;
                Mouse_Click();
                dispose();
                GameUtil.isHintMode = true;
                GameInterface gameInterface = new GameInterface();
                gameInterface.play();
            }
        });
    }



    /**
     * This method paint the cars on the interface according to different levels
     * and add listener to them.
     * @author Lin Yujie, Pei Yinan
     */
    public void paintCars(){
        // Create the object "car"
        cars = new Car[16];
        String[] name = {"1","2","3","4","5","6","7","8",
                "9","10","11","12","13","14","15","16"};
        for (int i = 0; i < name.length; i++) {
            cars[i] = new Car(i,name[i]);
            cars[i].setOpaque(false);
            cars[i].setBorder(null);
            cars[i].addKeyListener(this);
            cars[i].setBackground(new Color(0, 0, 0, 0));
            layeredPane.add(cars[i],JLayeredPane.POPUP_LAYER);
            // Add KeyListener to the JFrame
            addKeyListener(this);
            setFocusable(true);
            requestFocusInWindow();
        }

        // Load the car pictures into the button with an appropriate size
        ImageIcon blueVertical = new ImageIcon(GameUtil.BLUE_CAR_VERTICAL);
        blueVertical.setImage(blueVertical.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH/6,GameUtil.SQUARE_LENGTH/3,Image.SCALE_SMOOTH));

        ImageIcon blueHorizontal = new ImageIcon(GameUtil.BLUE_CAR_HORIZONTAL);
        blueHorizontal.setImage(blueHorizontal.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH/3,GameUtil.SQUARE_LENGTH/6, Image.SCALE_SMOOTH));

        ImageIcon red = new ImageIcon(GameUtil.RED_CAR);
        red.setImage(red.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH/3,GameUtil.SQUARE_LENGTH/6, Image.SCALE_SMOOTH));

        ImageIcon truckVertical = new ImageIcon(GameUtil.TRUCK_VERTICAL);
        truckVertical.setImage(truckVertical.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH/6, GameUtil.SQUARE_LENGTH/2, Image.SCALE_SMOOTH));

        ImageIcon truckHorizontal = new ImageIcon(GameUtil.TRUCK_HORIZONTAL);
        truckHorizontal.setImage(truckHorizontal.getImage().getScaledInstance
                (GameUtil.SQUARE_LENGTH/2,GameUtil.SQUARE_LENGTH/6,Image.SCALE_SMOOTH));

        // Put different vehicles in the corresponding position according to different levels
        int x = 3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2;
        int y = 2*GameUtil.OFFSET;
        switch (GameUtil.level){
            case 1:
                cars[0].setBounds(x+GameUtil.SQUARE_LENGTH/6,
                        y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[0].setIcon(red);
                cars[1].setBounds(x+5*GameUtil.SQUARE_LENGTH/6,y,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[1].setIcon(truckVertical);
                cars[2].setBounds(x+GameUtil.SQUARE_LENGTH/2,
                        y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[2].setIcon(truckVertical);
                cars[3].setBounds(x+GameUtil.SQUARE_LENGTH/3,y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[3].setIcon(blueHorizontal);
                cars[4].setBounds(x+GameUtil.SQUARE_LENGTH/3,
                        y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[4].setIcon(blueVertical);
                cars[5].setBounds(x+2*GameUtil.SQUARE_LENGTH/3,
                        y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[5].setIcon(blueHorizontal);
                cars[6].setBounds(x, y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[6].setIcon(blueVertical);
                cars[7].setBounds(x+2*GameUtil.SQUARE_LENGTH/3,
                        y+5*GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[7].setIcon(blueHorizontal);
                break;
            case 2:
                cars[0].setBounds(x+GameUtil.SQUARE_LENGTH/3,
                        y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[0].setIcon(red);
                cars[1].setBounds(x+2*GameUtil.SQUARE_LENGTH/3,
                        y+GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[1].setIcon(truckVertical);
                cars[2].setBounds(x+GameUtil.SQUARE_LENGTH/6, y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[2].setIcon(blueHorizontal);
                cars[3].setBounds(x+GameUtil.SQUARE_LENGTH/2, y,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[3].setIcon(blueVertical);
                cars[4].setBounds(x+2*GameUtil.SQUARE_LENGTH/3,y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[4].setIcon(blueHorizontal);
                cars[5].setBounds(x,y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[5].setIcon(blueHorizontal);
                cars[6].setBounds(x+GameUtil.SQUARE_LENGTH/3,
                        y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[6].setIcon(blueHorizontal);
                cars[7].setBounds(x+GameUtil.SQUARE_LENGTH/3,
                        y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[7].setIcon(blueVertical);
                cars[8].setBounds(x+GameUtil.SQUARE_LENGTH/2,
                        y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[8].setIcon(blueVertical);
                cars[9].setBounds(x+2*GameUtil.SQUARE_LENGTH/3,
                        y+5*GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[9].setIcon(blueHorizontal);
                break;
            case 3:
                cars[0].setBounds(x+GameUtil.SQUARE_LENGTH/2,
                        y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[0].setIcon(red);
                cars[1].setBounds(x+GameUtil.SQUARE_LENGTH/3,y,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[1].setIcon(truckVertical);
                cars[2].setBounds(x+5*GameUtil.SQUARE_LENGTH/6,y,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[2].setIcon(truckVertical);
                cars[3].setBounds(x+GameUtil.SQUARE_LENGTH/2, y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[3].setIcon(blueHorizontal);
                cars[4].setBounds(x+GameUtil.SQUARE_LENGTH/3, y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[4].setIcon(blueHorizontal);
                cars[5].setBounds(x+GameUtil.SQUARE_LENGTH/6, y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[5].setIcon(blueHorizontal);
                cars[6].setBounds(x+2*GameUtil.SQUARE_LENGTH/3, y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[6].setIcon(blueHorizontal);
                cars[7].setBounds(x+GameUtil.SQUARE_LENGTH/2,y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[7].setIcon(blueVertical);
                cars[8].setBounds(x,y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2);
                cars[8].setIcon(truckVertical);
                cars[9].setBounds(x+2*GameUtil.SQUARE_LENGTH/3, y+5*GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[9].setIcon(blueHorizontal);
                break;
            case 4:
                cars[0].setBounds(x+GameUtil.SQUARE_LENGTH/6,
                        y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[0].setIcon(red);
                cars[1].setBounds(x, y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[1].setIcon(blueHorizontal);
                cars[2].setBounds(x+GameUtil.SQUARE_LENGTH/3, y,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[2].setIcon(blueHorizontal);
                cars[3].setBounds(x, y+GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[3].setIcon(blueHorizontal);
                cars[4].setBounds(x, y+2*GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[4].setIcon(blueHorizontal);

                cars[5].setBounds(x+2*GameUtil.SQUARE_LENGTH/3, y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6);
                cars[5].setIcon(blueHorizontal);
                cars[6].setBounds(x+GameUtil.SQUARE_LENGTH/6,
                        y+5*GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/6);
                cars[6].setIcon(truckHorizontal);
                cars[7].setBounds(x, y+GameUtil.SQUARE_LENGTH/3,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[7].setIcon(blueVertical);
                cars[8].setBounds(x+2*GameUtil.SQUARE_LENGTH/3, y+GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[8].setIcon(blueVertical);
                cars[9].setBounds(x+5*GameUtil.SQUARE_LENGTH/6, y,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[9].setIcon(blueVertical);
                cars[10].setBounds(x+GameUtil.SQUARE_LENGTH/2, y+GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[10].setIcon(blueVertical);
                cars[11].setBounds(x+GameUtil.SQUARE_LENGTH/3, y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[11].setIcon(blueVertical);
                cars[12].setBounds(x+GameUtil.SQUARE_LENGTH/2, y+GameUtil.SQUARE_LENGTH/2,
                        GameUtil.SQUARE_LENGTH/6,
                        GameUtil.SQUARE_LENGTH/3);
                cars[12].setIcon(blueVertical);
                break;
            default:
                break;
        }
        setContentPane(layeredPane);
        setVisible(true);
    }



    /**
     * In the common mode, when the keys ↑, ↓, ←, → is pressed, the vehicle moves under
     * certain condition, which is logically determined in the car class. In the hint
     * mode, only the car with the flash arrow can be moved in the hint direction.
     * @param e Key event
     * @author Yang Zetianhao (common mode)
     * @author Pei Yinan (hint mode)
     * @author Lin Yujie (whether success)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            Car car = (Car) e.getSource();
            int keyCode = e.getKeyCode();
            int x = car.getX();
            int y = car.getY();
            setIndexAndDirection();
            if (GameUtil.isHintMode) {
                if (car.equals(cars[carIndex])) {
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            if (direction == 'U') {
                                car.moveUpward(x, y, cars);
                                stepsLabel.setText("" + moves);
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction == 'D') {
                                car.moveDownward(x, y, cars);
                                stepsLabel.setText("" + moves);
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            if (direction == 'L') {
                                car.moveLeft(x, y, cars);
                                stepsLabel.setText("" + moves);
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction == 'R') {
                                car.moveRight(x, y, cars);
                                stepsLabel.setText("" + moves);
                            }
                            break;
                    }
                }
            }
            else{
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        car.moveUpward(x, y, cars);
                        stepsLabel.setText("" + moves);
                        break;
                    case KeyEvent.VK_DOWN:
                        car.moveDownward(x, y, cars);
                        stepsLabel.setText("" + moves);
                        break;
                    case KeyEvent.VK_LEFT:
                        car.moveLeft(x, y, cars);
                        stepsLabel.setText("" + moves);
                        break;
                    case KeyEvent.VK_RIGHT:
                        car.moveRight(x, y, cars);
                        stepsLabel.setText("" + moves);
                        break;
                }
            }
        } catch (Exception ex) {}

        if(moves==1){
            secondsPassed = 0;
            updateTimeLabel();
            timer.start();
        }

        // Every time the player pressed the key, a judgement is needed to determine whether the game wins.
        if (winJudgement()){
            // Obtain all components and determine one by one whether they need to be removed
            removeFlashArrow();
            timer.stop();
            removeKeyListener(this);
            lightTurnGreen();
            moveOutOfTheExit();
            Win_Music();
            GameUtil.isHintMode = false;
            WinFrame winFrame = new WinFrame(this);
            layeredPane.add(winFrame, JLayeredPane.DRAG_LAYER);
            winFrame.setVisible(true);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}



    /**
     * This method judges whether the game wins. If there are no
     * other cars in front of the red car, the game wins.
     * @return flag
     * @author Lin Yujie
     */
    public boolean winJudgement(){
        boolean flag = true;
        for (Car car : cars) {
            if (car != cars[0] && car.getBounds().intersects
                    (cars[0].getX()+cars[0].getWidth(), cars[0].getY(),
                            GameUtil.OFFSET*3+GameUtil.BUTTON_WIDTH2+
                                    GameUtil.SQUARE_LENGTH-cars[0].getX()-
                                    cars[0].getWidth(),
                            cars[0].getHeight())) {
                flag = false;
            }
        }
        return flag;
    }



    /**
     * This method is implemented when the game wins. It turns the red lights into green.
     * @author Lin Yujie
     */
    public void lightTurnGreen(){
        //Load in the exit picture with green lights
        int lightWidth = 40;
        ImageIcon greenLights = new ImageIcon(GameUtil.GREEN_LIGHT);
        greenLights.setImage(greenLights.getImage().getScaledInstance
                (lightWidth+2,GameUtil.SQUARE_LENGTH/6
                        +lightWidth*2+3,Image.SCALE_SMOOTH));
        JLabel greenLight_Label = new JLabel(greenLights);
        greenLight_Label.setVisible(true);
        greenLight_Label.setBounds
                (3*GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2+ GameUtil.SQUARE_LENGTH-3,
                        2*GameUtil.OFFSET +GameUtil.SQUARE_LENGTH/3-lightWidth+3,
                        lightWidth+2,GameUtil.SQUARE_LENGTH/6+lightWidth*2+3);
        layeredPane.add(greenLight_Label, JLayeredPane.POPUP_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);
    }



    /**
     * This method handles the automatic movement of the car when the game wins.
     * @author Lin Yujie
     */
    public void moveOutOfTheExit() {
        int initialX = cars[0].getX();
        int targetX = initialX + GameUtil.SQUARE_LENGTH*2;
        int steps = 30;
        int distancePerStep = (targetX - initialX) / steps;
        int delay = 20;

        // Create a Timer to control the animation
        Timer timer = new Timer(delay, new ActionListener() {
            int currentX = initialX;
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move the car by one step
                currentX += distancePerStep;
                cars[0].setLocation(currentX, cars[0].getY());

                // Check if the target position is reached
                if (currentX >= targetX) {
                    // Stop the Timer when the target position is reached
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        // Start the Timer
        timer.start();
    }



    /**
     * This method displays the corresponding level on the game interface
     * @author Yang Zetianhao
     */
    public void level(){
        // load in the level display picture
        ImageIcon selectLevelIcon = new ImageIcon(GameUtil.LEVEL_DISPLAY);
        selectLevelIcon.setImage(selectLevelIcon.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2,Image.SCALE_SMOOTH));
        JLabel selectLevelLabel = new JLabel(selectLevelIcon);
        selectLevelLabel.setVisible(true);
        selectLevelLabel.setBounds(GameUtil.OFFSET, 2*GameUtil.OFFSET,
                GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2);
        layeredPane.add(selectLevelLabel, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        // display and update the level
        levelLabel.setVisible(true);
        layeredPane.add(levelLabel, JLayeredPane.MODAL_LAYER);
        Font labelFont = new Font("Arial", Font.BOLD, 60);
        levelLabel.setFont(labelFont);
        levelLabel.setBounds(GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2/2-20,
                2*GameUtil.OFFSET+GameUtil.BUTTON_HEIGHT2/2-10,50,50);
        levelLabel.setForeground(Color.WHITE);
    }



    /**
     * This method calculates the steps that the cars move
     * @author Pei Yinan
     */
    public void moves(){
        // load in the moves display picture
        ImageIcon selectLevelIcon = new ImageIcon(GameUtil.MOVES_DISPLAY);
        selectLevelIcon.setImage(selectLevelIcon.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2,Image.SCALE_SMOOTH));
        JLabel selectLevelLabel = new JLabel(selectLevelIcon);
        selectLevelLabel.setVisible(true);
        selectLevelLabel.setBounds(GameUtil.OFFSET, 2*GameUtil.OFFSET+
                        GameUtil.SQUARE_LENGTH/2-GameUtil.BUTTON_HEIGHT2/2,
                GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2);
        layeredPane.add(selectLevelLabel, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        // display and update the moves
        stepsLabel.setVisible(true);
        layeredPane.add(stepsLabel, JLayeredPane.MODAL_LAYER);
        Font labelFont = new Font("Arial", Font.BOLD, 60);
        stepsLabel.setFont(labelFont);
        stepsLabel.setBounds(GameUtil.OFFSET+GameUtil.BUTTON_WIDTH2/2-15,
                2*GameUtil.OFFSET+GameUtil.SQUARE_LENGTH/2-10,
                100,50);
        stepsLabel.setForeground(Color.WHITE);
    }



    /**
     * This method display the time when the game starts
     * @author Yang Zetianhao
     */
    public void timer(){
        // load in the time display picture
        ImageIcon selectLevelIcon = new ImageIcon(GameUtil.TIMER_DISPLAY);
        selectLevelIcon.setImage(selectLevelIcon.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2,Image.SCALE_SMOOTH));
        JLabel selectLevelLabel = new JLabel(selectLevelIcon);
        selectLevelLabel.setVisible(true);
        selectLevelLabel.setBounds(GameUtil.OFFSET, 2*GameUtil.OFFSET+
                        GameUtil.SQUARE_LENGTH-GameUtil.BUTTON_HEIGHT2,
                GameUtil.BUTTON_WIDTH2, GameUtil.BUTTON_HEIGHT2);
        layeredPane.add(selectLevelLabel, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        // display and update the time
        timeLabel.setVisible(true);
        layeredPane.add(timeLabel,JLayeredPane.MODAL_LAYER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        timeLabel.setBounds(GameUtil.OFFSET+3*GameUtil.BUTTON_WIDTH2/10,
                2*GameUtil.OFFSET+GameUtil.SQUARE_LENGTH-GameUtil.BUTTON_HEIGHT2/2-80,
                500,200);
        timeLabel.setForeground(Color.WHITE);
        //Initialize the time
        updateTimeLabel();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                updateTimeLabel();
            }
        });
        secondsPassed = 0;
    }



    /**
     * This method transfers the seconds into hour+minutes+seconds
     * @author Yang Zetianhao
     */
    private void updateTimeLabel() {
        int hours = secondsPassed / 3600;
        int minutes = (secondsPassed % 3600) / 60;
        int seconds = secondsPassed % 60;
        String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(timeText);
    }



    /**
     * This method set the answer string according to different levels
     * @author Lin Yujie
     */
    public void setString() {
        switch (GameUtil.level){
            case 1:
                answer = new String[answer1.length];
                System.arraycopy(answer1, 0, answer, 0, answer1.length);
                break;
            case 2:
                answer = new String[answer2.length];
                System.arraycopy(answer2, 0, answer, 0, answer2.length);
                break;
            case 3:
                answer = new String[answer3.length];
                System.arraycopy(answer3, 0, answer, 0, answer3.length);
                break;
            case 4:
                answer = new String[answer4.length];
                System.arraycopy(answer4, 0, answer, 0, answer4.length);
                break;
            default:
                break;
        }
    }



    /**
     * This method sets the car index and direction, so as to extract them from the answer strings.
     * @author Lin Yujie
     */
    public void setIndexAndDirection(){
        try {
            if (answer[GameUtil.answerIndex].length()==2) {
                carIndex = Integer.parseInt(answer[GameUtil.answerIndex].substring(0, 1));
                direction = answer[GameUtil.answerIndex].charAt(1);
            }
            else {
                carIndex = Integer.parseInt(answer[GameUtil.answerIndex].substring(0, 2));
                direction = answer[GameUtil.answerIndex].charAt(2);
            }
        } catch (Exception e) {

        }
    }



    /**
     * This method implements the hint mode recursively. If the given answer equals to
     *  the pressed key, the flashed arrow is updated.
     *  @author Lin Yujie
     */
    public void hintMode(){
        setIndexAndDirection();
        trackFlashArrow(carIndex, direction);
        cars[carIndex].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if ((keyCode == KeyEvent.VK_UP && direction == 'U')||
                        (keyCode == KeyEvent.VK_DOWN && direction == 'D')||
                        (keyCode == KeyEvent.VK_LEFT && direction == 'L')||
                        (keyCode == KeyEvent.VK_RIGHT && direction == 'R')){
                    cars[carIndex].removeKeyListener(this);
                    GameUtil.answerIndex++;
                    hintMode();
                }
            }
        });
    }



    /**
     * This method implements the flashing effect of the arrow. Every time the
     * hint car moves, the previous arrow will be removed.
     * @param index the index of the car is used to decide which position the car should be fitted in
     * @param direction the direction of the car includes "L"(left), "R"(right), "U"(up), "D"(down)
     * @author Lin Yujie
     */
    public void trackFlashArrow(int index, int direction){
        // Before displaying the next flash arrow, remove the previous one
        removeFlashArrow();

        // load in the array icon on the middle top of the targeted car
        JLabel arrowLabel = new JLabel();
        arrowLabel.setName("arrowLabel");
        String HINT;
        if (direction == 'L') {HINT = GameUtil.HINT_LEFT;}
        else if (direction == 'R') {HINT = GameUtil.HINT_RIGHT;}
        else if (direction == 'U') {HINT = GameUtil.HINT_UP;}
        else if (direction == 'D') {HINT = GameUtil.HINT_DOWN;}
        else HINT = "0";
        ImageIcon flashArrow = new ImageIcon(HINT);
        int flashArrowWidth = 120, flashArrowHeight = 132;
        flashArrow.setImage(flashArrow.getImage().getScaledInstance
                (flashArrowWidth,flashArrowHeight,Image.SCALE_SMOOTH));
        arrowLabel.setIcon(flashArrow);
        arrowLabel.setBounds(cars[index].getX()+cars[index].getWidth()/2-flashArrowWidth/2,
                cars[index].getY()+cars[index].getHeight()/2-flashArrowHeight/2,
                flashArrowWidth, flashArrowHeight);
        layeredPane.add(arrowLabel, JLayeredPane.DRAG_LAYER);
        layeredPane.setVisible(true);

        // Implement flashing effect for the arrow icon
        Timer flashTimer = new Timer(500, new ActionListener() {
            boolean isVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                arrowLabel.setVisible(isVisible);
                isVisible = !isVisible;
            }
        });
        flashTimer.start();
    }



    /**
     * This method remove the previous flash arrow in the game interface
     * @author Lin Yujie
     */
    public void removeFlashArrow() {
        // Obtain all components and determine one by one whether they need to be removed
        Component[] components = layeredPane.getComponents();
        for (Component component : components) {
            // Only remove the component named "arrowLabel"
            if (component instanceof JLabel && ((JLabel) component).getName() != null &&
                    ((JLabel) component).getName().equals("arrowLabel")) {
                layeredPane.remove(component);
                break;
            }
        }
    }
}