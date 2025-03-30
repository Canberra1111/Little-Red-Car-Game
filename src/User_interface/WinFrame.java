package src.User_interface;
import src.Util.MovesRecord;
import src.Util.GameSelect;
import src.Util.GameUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import static src.MusicPlay.SoundControlUnits.Mouse_Click;
import static src.MusicPlay.SoundControlUnits.Mouse_hover;
import static src.User_interface.GameInterface.stopMusic;

/**
 * The WinFrame class represents the window displayed when the player wins a game level.
 * It includes information about the completion of the level, the player's moves, and the best moves achieved.
 * The WinFrame class represents the window displayed when the player wins a game level.
 * It includes information about the completion of the level, the player's moves, and the best moves achieved.
 * This class handles the graphical user interface for the win screen, displaying relevant information such as
 * the completed level, the number of moves made by the player, and the best moves achieved. It also provides
 * options to go back to the game menu, restart the current level, or proceed to the next level.
 * The WinFrame class extends JPanel and manages the layout and rendering of components within the window.
 * @author Gong Yihang
 * @since 1.0
 */
public class WinFrame extends JPanel{
    private final Image background;
    private final Image[] starList = new Image[3];
    private final JFrame jFrame;

    /**
     * HashMap to store level objects mapped by their level numbers.
     */
    private final HashMap<Integer, MovesRecord> levelMap = new HashMap<Integer, MovesRecord>(){
        {
            put(1, new MovesRecord(21, 10));
            put(2, new MovesRecord(25, 20));
            put(3, new MovesRecord(21, 30));
            put(4, new MovesRecord(37, 30));
        }
    };

    /**
     * HashMap to store level objects mapped by their level numbers.
     */
    private final HashMap<Integer, ArrayList<Integer>> levelList = new HashMap<>();

    /**
     * HashMap to store level moves data.
     */
    private int record;



    public WinFrame(JFrame jFrame) {
        this.jFrame = jFrame;
        setLayout(null);
        setOpaque(false);
        setVisible(false);
        setBounds(GameUtil.WINDOW_WIDTH / 3,GameUtil.WINDOW_HEIGHT / 5, 600,600);
        // Load in the pictures
        background = new ImageIcon("image/WinFrame/winFrameBackground.png").getImage();
        for (int i = 0; i < 3; i++) {
            starList[i] = new ImageIcon("image/WinFrame/star" + (i + 1) + ".png").getImage();
        }
        initGameBack();
        initRestartButton();
        initNextButton();
        loadLevelMovesData();
        saveMove();
    }
    /**
     * Loads level moves data from files.
     */

    public void loadLevelMovesData() {
        File dataFile = new File("data");
        if (!dataFile.exists()) dataFile.mkdir();
        File file = new File("data/level"+GameUtil.level + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        levelList.put(GameUtil.level, new ArrayList<>());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    levelList.get(GameUtil.level).add((int) c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Saves the player's moves to files.
     */
    public void saveMove() {
        int moves = GameInterface.moves;
        levelList.get(GameUtil.level).add(moves);
        // preserve the record in the data directory as txt files
        File file = new File("data/level"+GameUtil.level + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            StringBuilder sb = new StringBuilder();
            for (int move : levelList.get(GameUtil.level)) {
                sb.append((char) move);
            }
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        setSize(600, 600);
        // Add the background picture
        g.drawImage(background, 0, 0, getWidth(),getHeight(),this);
        // set font size and style
        Font font = new Font("Arial", Font.BOLD, 32);
        g.setFont(font);

        // set the colors
        Color color = new Color(0, 0, 0);
        g.setColor(color);
        // set the hint information
        g.drawString("LEVEL COMPLETED", 150, 180);

        // draw the steps
        g.drawString("MOVES: " + GameInterface.moves,210, 350);
        // implement the record steps
        if (levelList.isEmpty()) {
            record = GameInterface.moves;
        } else {
            record = levelList.get(GameUtil.level).stream().min(Integer::compareTo).orElse(GameInterface.moves);
        }
        g.drawString("RECORD: " + record, 208, 400);

        // Add the star picture
        int score = score();
        g.drawImage(starList[score], 150, 200, 300, 100, this);
    }

    /**
     * This method sets a grading standard. If the user gets less than or
     * equal to the standard, he or she gets 3 stars. If the user make
     *  <=10 steps more than the standard, then two stars are obtained.
     *  In other cases, only 1 star is displayed.
     * @return the number of stars
     */
    private int score(){
        // obtain current level information
        MovesRecord level = levelMap.get(GameUtil.level);
        int moves = GameInterface.moves;
        // judge whether to give 1-3 stars
        if(moves <= level.getBestMoves()){
            return 2;
        }
        if (moves > level.getBestMoves() &&  moves <= level.getBestMoves() + 10){
            return 1;
        }
        return 0;
    }
    /**
     * Saves the player's moves to files.
     */
    private void initGameBack() {
        JButton gameBack = new JButton();
        ImageIcon IconBack = new ImageIcon(GameUtil.BACK2);
        IconBack.setImage(IconBack.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameBack.setIcon(IconBack);
        gameBack.setBounds(50,getHeight() - 150,6*GameUtil.BUTTON_WIDTH1/5, 6*GameUtil.BUTTON_HEIGHT1/5);
        gameBack.setBorder(null);
        gameBack.setContentAreaFilled(false);
        gameBack.setFocusPainted(false);
        gameBack.setBorderPainted(false);
        gameBack.setOpaque(false);
        add(gameBack, JLayeredPane.PALETTE_LAYER);
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
                Mouse_Click();
                GameSelect gameSelect = new GameSelect();
                gameSelect.stateSelection(2);
                jFrame.dispose();
                GameInterface.moves = 0;
                GameUtil.gameInterfaceBackFlag = true;
                stopMusic();
                PickMenu pickMenu = new PickMenu();
                pickMenu.select();
            }
        });
    }

    /**
     * Initializes the "Restart" button, allowing the player to restart the current level.
     */
    private void initRestartButton() {
        ImageIcon IconRestart = new ImageIcon(GameUtil.RESTART);
        IconRestart.setImage(IconRestart.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        JButton gameRestart1 = new JButton();
        gameRestart1.setIcon(IconRestart);
        gameRestart1.setBounds(230,getHeight()-150,6*GameUtil.BUTTON_WIDTH1/5,
                6*GameUtil.BUTTON_HEIGHT1/5);
        gameRestart1.setBorder(null);
        gameRestart1.setContentAreaFilled(false);
        gameRestart1.setOpaque(false);
        add(gameRestart1);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                gameRestart1.setIcon(new ImageIcon(IconRestart.getImage().
                        getScaledInstance(6*GameUtil.BUTTON_WIDTH1/5,
                                6*GameUtil.BUTTON_HEIGHT1/5, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameRestart1.setIcon(new ImageIcon(IconRestart.getImage().getScaledInstance
                        (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                Mouse_Click();
                GameSelect gameSelect = new GameSelect();
                gameSelect.stateSelection(3);
                GameUtil.restartFlag = true;
                jFrame.dispose();
                GameInterface.moves = 0;
                GameInterface gameInterface = new GameInterface();
                gameInterface.play();
            }
        };
        gameRestart1.addMouseListener(mouseAdapter);

    }
    /**
     * Initializes the "Next" button, allowing the player to proceed to the next level.
     */
    private void initNextButton() {
        JButton gameNext = new JButton();
        ImageIcon IconNext = new ImageIcon(GameUtil.NEXT);
        IconNext.setImage(IconNext.getImage().getScaledInstance
                (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH));
        gameNext.setIcon(IconNext);
        gameNext.setBounds(400,getHeight()-150,6*GameUtil.BUTTON_WIDTH1/5,
                6*GameUtil.BUTTON_HEIGHT1/5);
        gameNext.setBorder(null);
        gameNext.setContentAreaFilled(false);
        gameNext.setFocusPainted(false);
        gameNext.setBorderPainted(false);
        gameNext.setOpaque(false);
        add(gameNext, JLayeredPane.PALETTE_LAYER);
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
                    gameNoNext.setBounds(412,getHeight()-138,GameUtil.BUTTON_WIDTH1,
                            GameUtil.BUTTON_HEIGHT1);
                    gameNoNext.setBorder(null);
                    gameNoNext.setContentAreaFilled(false);
                    gameNoNext.setFocusPainted(false);
                    gameNoNext.setBorderPainted(false);
                    gameNoNext.setOpaque(false);
                    add(gameNoNext, JLayeredPane.PALETTE_LAYER);
                    gameNoNext.setVisible(true);
                    setVisible(true);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gameNext.setIcon(new ImageIcon(IconNext.getImage().getScaledInstance
                        (GameUtil.BUTTON_WIDTH1, GameUtil.BUTTON_HEIGHT1, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (GameUtil.level < 4){
                    GameInterface.moves = 0;
                    stopMusic();
                    GameUtil.level += 1;
                    jFrame.dispose();
                    Mouse_Click();
                    GameSelect gameSelect = new GameSelect();
                    gameSelect.stateSelection(3);
                    GameUtil.isHintMode = false;
                    GameInterface gameInterface = new GameInterface();
                    gameInterface.play();
                }
            }
        });
    }
}