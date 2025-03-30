package src.User_interface;
import src.Util.GameSelect;
import src.Util.GameUtil;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static src.MusicPlay.SoundControlUnits.*;

/**
 * This class implements the start menu. The start menu includes a scrolling background
 * picture, a title, and two buttons -- "start" and "exit".
 * Calls class to scroll the background
 * @author Yuan Jiayi
 * @since 1.0
 */
public class StartMenu extends JPanel implements MouseListener,ActionListener {
    private Image foreground;
    private Image background;
    private int backgroundPosition;
    private final JButton gameButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton settingButton = new JButton();
    boolean chosen;
    private Timer timer;
    public void launch() {
        // control the music
        if (GameUtil.pickMenuBackFlag){
            Start_Menu_Music();
        }
        GameUtil.pickMenuBackFlag = true;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Stop_Start_Menu_Music();
            }
        });

        /**
         * load the pictures in the start menu and set them in a preferred size
         */
        ImageIcon foregroundIcon = new ImageIcon(GameUtil.RUSHHOUR_PNG);
        foreground = foregroundIcon.getImage();
        ImageIcon backgroundIcon = new ImageIcon(GameUtil.LONG_PICTURE_PNG);
        background = backgroundIcon.getImage();
        setPreferredSize(new Dimension(GameUtil.WINDOW_WIDTH, GameUtil.WINDOW_HEIGHT));

        /**
         * put the pictures into the window
         */
        JFrame frame = new JFrame("Game Background");
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.setLayout(null);

        /**
         * Create the "start" and "exit" buttons and add them to the frame,
         * both of which are magnified when the mouse is in the button area.
         */
        gameButton.setLayout(new BorderLayout());
        gameButton.setIcon(new ImageIcon(GameUtil.BUTTON_START_CAR_SMALL_PNG));
        gameButton.setRolloverIcon(new ImageIcon(GameUtil.BUTTON_START_CAR_BIG_PNG));
        gameButton.addMouseListener(this);
        gameButton.setHorizontalAlignment(SwingConstants.LEFT);
        gameButton.setFocusPainted(false);
        gameButton.setBorderPainted(false);
        gameButton.setContentAreaFilled(false);
        gameButton.setBounds(GameUtil.WINDOW_WIDTH/5, 3*GameUtil.WINDOW_HEIGHT/5, 414, 180);
        gameButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(gameButton);

        exitButton.setLayout(new BorderLayout());
        exitButton.setIcon(new ImageIcon(GameUtil.BUTTON_EXIT_CAR_SMALL_PNG));
        exitButton.setRolloverIcon(new ImageIcon(GameUtil.BUTTON_EXIT_CAR_BIG_PNG));
        exitButton.addMouseListener(this);
        exitButton.setHorizontalAlignment(SwingConstants.LEFT);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(3*GameUtil.WINDOW_WIDTH/5, 3*GameUtil.WINDOW_HEIGHT/5, 414, 180);
        exitButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(exitButton);

        settingButton.setLayout(new BorderLayout());
        settingButton.setIcon(new ImageIcon(GameUtil.BUTTON_SETTING_SMALL));
        settingButton.setRolloverIcon(new ImageIcon(GameUtil.BUTTON_SETTING_BIG));
        settingButton.addMouseListener(this);
        settingButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingButton.setFocusPainted(false);
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setBounds(GameUtil.WINDOW_WIDTH/10, GameUtil.WINDOW_HEIGHT/10, 200, 200);
        settingButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(settingButton);

        timer = new Timer(10,this);
        timer.start();
    }

    /**
     * Adjust the size and position of the background and picture
     * You can draw backgrounds, buttons, and other graphical elements on the panel by calling methods on the g object.
     * @param g 'g' is the parameter to the paintComponent method, which is the Graphics object used to draw graphics on the panel.
     * @author Yuan Jiayi
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x1 = backgroundPosition % GameUtil.WINDOW_WIDTH;
        int x2 = (backgroundPosition - GameUtil.WINDOW_WIDTH) % GameUtil.WINDOW_WIDTH;
        g.drawImage(background, x1, 0, GameUtil.WINDOW_WIDTH, GameUtil.WINDOW_HEIGHT, null);
        g.drawImage(background, x2, 0, GameUtil.WINDOW_WIDTH, GameUtil.WINDOW_HEIGHT, null);
        int foregroundWidth = GameUtil.WINDOW_WIDTH / 2;
        int foregroundHeight = GameUtil.WINDOW_HEIGHT / 2;
        int foregroundX = (GameUtil.WINDOW_WIDTH - foregroundWidth) / 2;
        int foregroundY = (GameUtil.WINDOW_HEIGHT - foregroundHeight) / 8;
        g.drawImage(foreground, foregroundX, foregroundY, foregroundWidth, foregroundHeight, null);
    }

    /**
     * Update the starting position of the image scroll loop
     * @author Yuan Jiayi
     */
    private void updateBackgroundPosition() {
        backgroundPosition += GameUtil.BACKGROUND_SPEED;
        if(backgroundPosition>GameUtil.WINDOW_WIDTH){
            backgroundPosition = 0;
        }
        repaint();
    }

    /**
     * Use the listener to update the background position
     * @author Yuan Jiayi
     * @param e 'e' is an ActionEvent, KeyEvent, and MouseEvent parameter that passes event information to the method when the corresponding event occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        updateBackgroundPosition();
    }

    /**
     * the control of window switching when button is clicked
     */
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getSource() == gameButton){
            Mouse_Click();
            gameButton.setEnabled(false);
            chosen = true;
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            GameSelect gameSelect = new GameSelect();
            gameSelect.stateSelection(2);
            PickMenu pickmenu = new PickMenu();
            pickmenu.select();
        }
        else if(e.getSource() == exitButton){
            Mouse_Click();
            exitButton.setEnabled(false);
            chosen = true;
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            GameSelect gameSelect = new GameSelect();
            gameSelect.stateSelection(4);
            ThankYouInterface thankYouInterface = new ThankYouInterface();
            thankYouInterface.setVisible(true);
            thankYouInterface.setLocationRelativeTo(null);
        }
        else if(e.getSource() == settingButton){
            Mouse_Click();
            SettingInterface setting = new SettingInterface();
        }
    }

    /**
     * @author Yuan Jiayi
     * Use the mouse position to determine whether the picture magnifies or not
     * The changing size of the image reflects the dynamic effect of the button
     * @param e 'e' is an ActionEvent, KeyEvent, and MouseEvent parameter that passes event information to the method when the corresponding event occurs.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        chosen=false;
        if (e.getSource() instanceof JButton)
            if(e.getSource() == gameButton){
                if(!chosen) {Mouse_hover();}
            }
            else if(e.getSource() == exitButton) {
                if (!chosen) {Mouse_hover();}
            }
            else {System.err.println();}
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}