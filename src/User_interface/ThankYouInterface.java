package src.User_interface;
import src.Util.GameUtil;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static src.MusicPlay.SoundControlUnits.*;

/**
 * This class implements the ThankYou interface. The interface includes a background
 * and the scrolling subtitles of the instructor and the team members (implemented
 * in the inner class).
 * @author Yuan Jiayi
 * @since 1.0
 */
public class ThankYouInterface extends JFrame {

    // Create frame object
    private final Image img;
    public ThankYouInterface() {
        // load in the ThankYou picture
        super();
        Stop_Start_Menu_Music();
        Thanks_Menu_Music();
        this.setResizable(false);
        img = Toolkit.getDefaultToolkit().getImage(GameUtil.THANK_YOU_PNG); // Obtain image resources
        // Declares the image panel object
        thankYouPanel thankYouPanel = new thankYouPanel(); // Create an image panel object
        this.setLocationRelativeTo(null);
        this.setBounds(GameUtil.WINDOW_X, GameUtil.WINDOW_Y,
                GameUtil.WINDOW_WIDTH, GameUtil.WINDOW_HEIGHT); // Sets the form size and position
        this.add(thankYouPanel); // Adds an image panel object to the form
        Thread thread = new Thread(thankYouPanel);
        thread.start();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("Thank you!");

        // Stop music when closing window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Stop_Thanks_Menu_Music();
            }
        });
    }

    /** inner class
     * This inner class creates a panel, define the subtitles of instructor and group members,
     * and place them in the appropriate position
     * @author Yuan Jiayi
     * @since 1.0
     */

    class thankYouPanel extends JPanel implements Runnable {
        int[] positionX = {190,190,190,190,190,190,190,190};
        int[] positionY = {100,170,240,310,380,450,520,590};
        String thanks = "Instructor: Hasan Ahmed";
        String team = "Lab 1, Group 6";
        String developer1 = "Lin Yujie";
        String developer2 = "Yang Zetianhao";
        String developer3 = "Pei Yinan";
        String developer4 = "Yuan Jiayi";
        String developer5 = "Bai Yunpeng";
        String developer6 = "Gong Yihang";

        /**
         * This method adjusts the size and position of the subtitles and picture
         * @param g 'g' is the parameter to the paintComponent method, which is the Graphics object used
         *          to draw graphics on the panel.
         * @author Yuan Jiayi
         */
        public void paint(Graphics g) {
            g.clearRect(0, 0, 800, 800);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            Font font = new Font("TimesRoman", Font.BOLD, 36);
            g.setFont(font);// Specified font
            g.setColor(Color.black);// Specify color
            g.drawString(thanks,positionX[0],positionY[0]);
            g.drawString(team, positionX[1], positionY[1]);
            g.drawString(developer1,positionX[2],positionY[2]);
            g.drawString(developer2,positionX[3],positionY[3]);
            g.drawString(developer3,positionX[4],positionY[4]);
            g.drawString(developer4,positionX[5],positionY[5]);
            g.drawString(developer5,positionX[6],positionY[6]);
            g.drawString(developer6,positionX[7],positionY[7]);
        }

        /**
         * This method updates the starting position of the subtitle scroll loop
         * @author Yuan Jiayi
         */
        public void run() {
            try {
                while (true) {
                    Thread.sleep(30); // The current thread sleeps for 1 second
                    for (int i = 0; i < positionY.length; i++) {
                        if (positionY[i] <= 100) {
                            positionY[i] =700;
                        }
                        else {
                            positionY[i]-=2.2;
                        }
                    }
                    repaint();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}