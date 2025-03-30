package src.MusicPlay;

/**
 * This class creates some methods to controls the background music and the game sound effect.
 * The sound effects include mouse click, mouse hover, car move, and win bgm
 * The background music include start menu bgm, thank you bgm, and the 4 level bgms.
 * @author Bai Yunpeng
 * @since 1.0
 */

public class SoundControlUnits {
    public static MusicPlayer localMusicPlayer;
    static boolean playSound = true;
    static MusicPlayer musicPlayer1 = new MusicPlayer("music/start_menu_back.WAV",true);
    static MusicPlayer musicPlayer2 = new MusicPlayer("music/Mouse_Click.wav",false);
    static MusicPlayer musicPlayer3 = new MusicPlayer("music/Mouse_hover.wav",false);
    static MusicPlayer musicPlayer4 = new MusicPlayer("music/level1_back.WAV",false);
    static MusicPlayer musicPlayer5 = new MusicPlayer("music/level2_back.WAV",false);
    static MusicPlayer musicPlayer6 = new MusicPlayer("music/level3_back.WAV",false);
    static MusicPlayer musicPlayer7 = new MusicPlayer("music/level4_back.WAV",false);
    static MusicPlayer musicPlayer8 = new MusicPlayer("music/move.WAV",false);
    static MusicPlayer musicPlayer9 = new MusicPlayer("music/win_bgm.WAV",false);
    static MusicPlayer musicPlayer10 = new MusicPlayer("music/thanks_back.WAV",false);

    /**
     * This method control the volume of the music
     * @param volumn the volume of the music
     */
    public static void changeVolumn(float volumn){
        musicPlayer1.setVolume(volumn);
        musicPlayer4.setVolume(volumn);
        musicPlayer5.setVolume(volumn);
        musicPlayer6.setVolume(volumn);
        musicPlayer7.setVolume(volumn);
        musicPlayer10.setVolume(volumn);

    }

    /**
     * This method mute the game sound effect
     */
    public static void muteSound(){
        playSound=false;
    }

    /**
     * This method play the game sound effect
     */
    public static void playSound(){
        playSound=true;
    }

    /**
     * This method causes the background music from the Start menu to start playing
     */
    public static void Start_Menu_Music(){
        musicPlayer1.playMusic();
        localMusicPlayer = musicPlayer1;
    }

    /**
     * This method stops the background music from the Start menu
     */
    public static void Stop_Start_Menu_Music(){
        musicPlayer1.stopMusic();
        localMusicPlayer = musicPlayer1;
    }

    /**
     * This method causes the background music from the first level to start playing
     */
    public static void Level1_Music(){
        musicPlayer4.playMusic();
        localMusicPlayer = musicPlayer4;
    }

    /**
     * This method stops the background music from the first level
     */
    public static void Stop_Level1_Music(){
        musicPlayer4.stopMusic();
        localMusicPlayer = musicPlayer4;
    }

    /**
     * This method causes the background music from the second level to start playing
     */
    public static void Level2_Music(){
        musicPlayer5.playMusic();
        localMusicPlayer = musicPlayer5;
    }

    /**
     * This method stops the background music from the second level
     */
    public static void Stop_Level2_Music(){
        musicPlayer5.stopMusic();
        localMusicPlayer = musicPlayer5;
    }

    /**
     * This method causes the background music from the third level to start playing
     */
    public static void Level3_Music(){
        musicPlayer6.playMusic();
        localMusicPlayer = musicPlayer6;
    }

    /**
     * This method stops the background music from the third level
     */
    public static void Stop_Level3_Music(){
        musicPlayer6.stopMusic();
        localMusicPlayer = musicPlayer6;
    }

    /**
     * This method causes the background music from the forth level to start playing
     */
    public static void Level4_Music(){
        musicPlayer7.playMusic();
        localMusicPlayer = musicPlayer7;
    }

    /**
     * This method stops the background music from the forth level
     */
    public static void Stop_Level4_Music(){
        musicPlayer7.stopMusic();
        localMusicPlayer = musicPlayer7;
    }

    /**
     * This method causes the background music from the thank-you interface to start playing
     */
    public static void Thanks_Menu_Music(){
        musicPlayer10.playMusic();
        localMusicPlayer = musicPlayer10;
    }

    /**
     * This method stops the background music from the thank-you interface
     */
    public static void Stop_Thanks_Menu_Music(){
        musicPlayer10.stopMusic();
        localMusicPlayer = musicPlayer10;
    }

    /**
     *This method play the game sound effect of "move the car"
     */
    public static void Car_Move_Music(){
        if(playSound) {
            musicPlayer8.playMusic();
            localMusicPlayer = musicPlayer8;
        }
    }

    /**
     * This method play the game sound effect of "win"
     */
    public static void Win_Music(){
        if(playSound) {
            musicPlayer9.playMusic();
            localMusicPlayer = musicPlayer9;
        }

    }

    /**
     * This method play the game sound effect of "click"
     */
    public static void Mouse_Click(){
        if(playSound) {
            musicPlayer2.playMusic();
            localMusicPlayer = musicPlayer2;
        }
    }

    /**
     * This method play the game sound effect of "touch"
     */
    public static void Mouse_hover(){
        if(playSound) {
            musicPlayer3.playMusic();
            localMusicPlayer = musicPlayer3;
        }
    }
}