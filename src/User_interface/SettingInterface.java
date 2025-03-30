package src.User_interface;
import src.MusicPlay.SoundControlUnits;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import src.Util.GameUtil;
import static src.MusicPlay.SoundControlUnits.Mouse_Click;
import static src.MusicPlay.SoundControlUnits.Mouse_hover;

/**
 * This class defined a setting menu
 * Includes change the volume of the background music,play or mute the game sound effect
 * @author Bai Yunpeng
 * @since 1.0
 */
public class SettingInterface extends JFrame implements ChangeListener, MouseListener {
    private final JSlider slider = new JSlider();
    private final JLabel BottomcenterjlabelMusic = new JLabel("Volume:  "+slider.getValue()+"%");
    private final JLabel BottomcenterjlabelSound = new JLabel("Sound:");
    private final Font f1 = new Font("Arial", Font.BOLD,25);


    /**
     * This method created set the background of the setting menu
     * and create a slider to control the volume of the music and two buttons.
     */

    public SettingInterface() {
        init();
        volumeSlider();
        muteButton();
        playButton();
        setVisible(true);
    }

    /**
     * This method initializes the setting menu
     * and set the background
     */
    public void init() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        ImageIcon settingMenuBackground = new ImageIcon(GameUtil.SETTING_MENU_BACKGROUND);
        settingMenuBackground.setImage(settingMenuBackground.getImage().getScaledInstance(800,
                600, Image.SCALE_SMOOTH));
        JLabel backgroundLabel = new JLabel(settingMenuBackground);
        backgroundLabel.setBounds(0, 0, 800,600);
        this.getContentPane().add(backgroundLabel);
        backgroundLabel.setVisible(true);

        setLayout(null);
        setBounds(GameUtil.WINDOW_X, GameUtil.WINDOW_Y, 4 * GameUtil.WINDOW_WIDTH / 8, 3 * GameUtil.WINDOW_HEIGHT / 4);
        setLayout(null);
        setResizable(false);

        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setTitle("Setting menu");
    }

    /**
     * This method add the slider to change the volume of the music
     */
    public void volumeSlider() {
        // set the volume of the music
        add(BottomcenterjlabelMusic);
        BottomcenterjlabelMusic.setBounds(80, 160, 200, 50);
        BottomcenterjlabelMusic.setFont(f1);

        BottomcenterjlabelSound.setBounds(80, 260, 200, 50);
        BottomcenterjlabelSound.setFont(f1);

        slider.setBounds(270, 160, 260, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setOpaque(false);
        slider.addChangeListener(this);

        getLayeredPane().add(slider);
        getLayeredPane().add(BottomcenterjlabelMusic);
        getLayeredPane().add(BottomcenterjlabelSound);

    }

    /**
     * This method add a button to mute the game sound effect
     */
    //mute button
    public void muteButton() {
        JButton button1 = new JButton();
        ImageIcon buttonIcon1 = new ImageIcon(GameUtil.MUTE_BUTTON_SMALL);
        buttonIcon1.setImage(buttonIcon1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        button1.setIcon(buttonIcon1);
        button1.setBounds(260, 230, 100, 100);
        button1.setBorder(null);
        button1.setContentAreaFilled(false);
        button1.setFocusPainted(false);
        button1.setBorderPainted(false);
        button1.setOpaque(false);
        add(button1, JLayeredPane.PALETTE_LAYER);
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundControlUnits.muteSound();
                Mouse_Click();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setIcon(new ImageIcon(buttonIcon1.getImage().
                        getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setIcon(new ImageIcon(buttonIcon1.getImage().
                        getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            }
        });
    }

    /**
     * This method add a button to play the game sound effect.
     */
    public void playButton() {
        // play button
        JButton button2 = new JButton();
        ImageIcon buttonIcon2 = new ImageIcon(GameUtil.VOL_BUTTON_SMALL);
        buttonIcon2.setImage(buttonIcon2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        button2.setIcon(buttonIcon2);
        button2.setBounds(420,230,100,100);
        button2.setBorder(null);
        button2.setContentAreaFilled(false);
        button2.setFocusPainted(false);
        button2.setBorderPainted(false);
        button2.setOpaque(false);
        add(button2, JLayeredPane.PALETTE_LAYER);
        button2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                SoundControlUnits.playSound();
                Mouse_Click();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setIcon(new ImageIcon(buttonIcon2.getImage().
                        getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setIcon(new ImageIcon(buttonIcon2.getImage().
                        getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            }
        });
    }

    /**
     * change the volume and display the music size
     * @param e  a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e){
        if(e.getSource() == slider){
            BottomcenterjlabelMusic.setText("Volume:  "+slider.getValue()+"%");
            SoundControlUnits.changeVolumn(slider.getValue()*0.01f);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}