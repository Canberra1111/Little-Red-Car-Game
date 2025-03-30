package src.User_interface;
import src.Util.GameSelect;
import src.Util.GameUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.MusicPlay.SoundControlUnits.*;

/**
 * This class implements the function of selecting the 4 different challenges.
 * The interface also includes a back button and an "i" button that displays the
 * rules in the rules frame.
 * @author Lin Yujie: initialize the layered pane, set the background pictures,
 *                    implement the 4 level buttons, complete the back button.
 * @author Yang Zetianhao: implement the "i" button.
 * @author Bai Yunpeng: control the music
 * @since 1.0
 */
public class PickMenu extends JFrame {
    public void select(){
        // control the music (Bai YunPeng)
        if (GameUtil.gameInterfaceBackFlag){
            Start_Menu_Music();
        }
        GameUtil.gameInterfaceBackFlag = false;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Stop_Start_Menu_Music();
            }
        });

        // Initialize the layered pane (Lin Yujie)
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        setLayout(null);
        setBounds(GameUtil.WINDOW_X,GameUtil.WINDOW_Y,
                GameUtil.WINDOW_WIDTH,GameUtil.WINDOW_HEIGHT);

        // Load in pickMenu background and fill the whole window (Lin Yujie)
        ImageIcon pickMenuBackground = new ImageIcon(GameUtil.PICKMENU_BACKGROUND);
        pickMenuBackground.setImage(pickMenuBackground.getImage().getScaledInstance(GameUtil.WINDOW_WIDTH,
                GameUtil.WINDOW_HEIGHT, Image.SCALE_SMOOTH));
        JLabel backgroundLabel = new JLabel(pickMenuBackground);
        backgroundLabel.setVisible(true);
        backgroundLabel.setBounds(0, 0, GameUtil.WINDOW_WIDTH,GameUtil.WINDOW_HEIGHT);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setTitle("Pick menu");
        setVisible(true);

        // Load in the "Select Level" title in the middle (Lin Yujie)
        ImageIcon selectLevelIcon = new ImageIcon(GameUtil.SELECT_LEVEL);
        selectLevelIcon.setImage(selectLevelIcon.getImage().getScaledInstance(GameUtil.WINDOW_WIDTH/3,
                GameUtil.WINDOW_HEIGHT/10, Image.SCALE_SMOOTH));
        JLabel selectLevelLabel = new JLabel(selectLevelIcon);
        selectLevelLabel.setVisible(true);
        selectLevelLabel.setBounds(GameUtil.WINDOW_WIDTH/3, GameUtil.WINDOW_HEIGHT/10,
                GameUtil.WINDOW_WIDTH/3, GameUtil.WINDOW_HEIGHT/10);
        layeredPane.add(selectLevelLabel, JLayeredPane.PALETTE_LAYER);
        setContentPane(layeredPane);
        layeredPane.setVisible(true);
        setVisible(true);

        for (int i = 0; i < 4; i++) {
            // set the level
            final int level = i + 1;

            // Create 4 level buttons, corresponding to the 4 challenges
            JButton button1 = new JButton();
            ImageIcon buttonIcon1 = new ImageIcon(GameUtil.LEVEL_BUTTONS[i]);
            buttonIcon1.setImage(buttonIcon1.getImage().
                    getScaledInstance(10*GameUtil.WINDOW_WIDTH/91,
                            10*GameUtil.WINDOW_WIDTH/91, Image.SCALE_SMOOTH));
            button1.setIcon(buttonIcon1);
            button1.setBounds(GameUtil.WINDOW_WIDTH/25 + i * 6*GameUtil.WINDOW_WIDTH/25,
                    15*GameUtil.WINDOW_HEIGHT/25, GameUtil.WINDOW_WIDTH/5,
                    GameUtil.WINDOW_WIDTH/5);
            button1.setBorder(null);
            button1.setContentAreaFilled(false);
            button1.setFocusPainted(false);
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            layeredPane.add(button1, JLayeredPane.PALETTE_LAYER);
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button1.setIcon(new ImageIcon(buttonIcon1.getImage().
                            getScaledInstance(GameUtil.WINDOW_WIDTH/8,
                                    GameUtil.WINDOW_WIDTH/8, Image.SCALE_SMOOTH)));
                    Mouse_hover();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button1.setIcon(new ImageIcon(buttonIcon1.getImage().
                            getScaledInstance(10*GameUtil.WINDOW_WIDTH/91,
                                    10*GameUtil.WINDOW_WIDTH/91, Image.SCALE_SMOOTH)));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    Mouse_Click();
                    dispose();
                    GameUtil.level = level;
                    GameSelect gameSelect = new GameSelect();
                    gameSelect.stateSelection(3);
                    GameInterface gameInterface = new GameInterface();
                    gameInterface.play();
                }
            });

            // Create 4 pictures, corresponding to the thumbnail of the 4 game interfaces (Lin Yujie)
            JButton button2 = new JButton();
            ImageIcon buttonIcon2 = new ImageIcon(GameUtil.GAME_INTERFACE[i]);
            buttonIcon2.setImage(buttonIcon2.getImage().getScaledInstance
                    (GameUtil.WINDOW_WIDTH/5,
                            GameUtil.WINDOW_WIDTH/5, Image.SCALE_SMOOTH));
            button2.setIcon(buttonIcon2);
            button2.setBounds(GameUtil.WINDOW_WIDTH/25 + i * 6*GameUtil.WINDOW_WIDTH/25, 7*GameUtil.WINDOW_HEIGHT/25,
                    11*GameUtil.WINDOW_WIDTH/50, 11*GameUtil.WINDOW_WIDTH/50);
            button2.setBorder(null);
            button2.setContentAreaFilled(false);
            button2.setFocusPainted(false);
            button2.setBorderPainted(false);
            button2.setOpaque(false);
            layeredPane.add(button2, JLayeredPane.PALETTE_LAYER);
            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button2.setIcon(new ImageIcon(buttonIcon2.getImage().
                            getScaledInstance(11*GameUtil.WINDOW_WIDTH/50,
                                    11*GameUtil.WINDOW_WIDTH/50, Image.SCALE_SMOOTH)));
                    Mouse_hover();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button2.setIcon(new ImageIcon(buttonIcon2.getImage().getScaledInstance
                            (GameUtil.WINDOW_WIDTH/5, GameUtil.WINDOW_WIDTH/5, Image.SCALE_SMOOTH)));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    Mouse_Click();
                    dispose();
                    GameUtil.level = level;
                    GameSelect gameSelect = new GameSelect();
                    gameSelect.stateSelection(3);
                    GameInterface gameInterface = new GameInterface();
                    gameInterface.play();
                }
            });
        }

        // Create an "i" button on the up-right position of the window (Yang Zetianhao)
        JButton button3 = new JButton();
        ImageIcon buttonIcon3 = new ImageIcon(GameUtil.I_BUTTON);
        buttonIcon3.setImage(buttonIcon3.getImage().getScaledInstance(GameUtil.WINDOW_WIDTH / 20,
                GameUtil.WINDOW_WIDTH / 20, Image.SCALE_SMOOTH));
        ImageIcon rulesIcon = new ImageIcon(GameUtil.RULESFRAME_BACKGROUND);
        JLabel rulesLabel = new JLabel(rulesIcon);
        rulesLabel.setBounds(25* GameUtil.WINDOW_WIDTH/120, 2* GameUtil.WINDOW_HEIGHT/13,
                2*GameUtil.WINDOW_WIDTH/3,  GameUtil.WINDOW_WIDTH/2);
        button3.setIcon(buttonIcon3);
        button3.setBounds(35 * GameUtil.WINDOW_WIDTH/40, GameUtil.WINDOW_HEIGHT/10,
                3 * GameUtil.WINDOW_WIDTH/50, 3 * GameUtil.WINDOW_WIDTH/50);
        button3.setBorder(null);
        button3.setContentAreaFilled(false);
        button3.setFocusPainted(false);
        button3.setBorderPainted(false);
        button3.setOpaque(false);
        layeredPane.add(button3, JLayeredPane.PALETTE_LAYER);
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setIcon(new ImageIcon(buttonIcon3.getImage().getScaledInstance
                        (3 * GameUtil.WINDOW_WIDTH / 50,
                                3 * GameUtil.WINDOW_WIDTH / 50, Image.SCALE_SMOOTH)));
                layeredPane.add(rulesLabel, JLayeredPane.MODAL_LAYER);
                rulesLabel.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button3.setIcon(new ImageIcon(buttonIcon3.getImage().getScaledInstance
                        (GameUtil.WINDOW_WIDTH / 20,
                                GameUtil.WINDOW_WIDTH / 20, Image.SCALE_SMOOTH)));
                rulesLabel.setVisible(false);
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
        });

        // Create a "back" button on the up-left position of the window (Lin Yujie)
        JButton button4 = new JButton();
        ImageIcon buttonIcon4 = new ImageIcon(GameUtil.BACK1);
        buttonIcon4.setImage(buttonIcon4.getImage().getScaledInstance(120, 132, Image.SCALE_SMOOTH));
        button4.setIcon(buttonIcon4);
        button4.setBounds(GameUtil.WINDOW_WIDTH/15, 3*GameUtil.WINDOW_HEIGHT/40, 144, 158);
        button4.setBorder(null);
        button4.setContentAreaFilled(false);
        button4.setFocusPainted(false);
        button4.setBorderPainted(false);
        button4.setOpaque(false);
        layeredPane.add(button4, JLayeredPane.PALETTE_LAYER);
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setIcon(new ImageIcon(buttonIcon4.getImage().
                        getScaledInstance(144, 158, Image.SCALE_SMOOTH)));
                Mouse_hover();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button4.setIcon(new ImageIcon(buttonIcon4.getImage().
                        getScaledInstance(120, 132, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                Mouse_Click();
                GameSelect gameSelect = new GameSelect();
                gameSelect.stateSelection(1);
                GameUtil.pickMenuBackFlag = false;
                dispose();
                StartMenu startMenu = new StartMenu();
                startMenu.launch();
            }
        });
        setVisible(true);
    }
}