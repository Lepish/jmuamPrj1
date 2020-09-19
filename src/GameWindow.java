import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    public int gameWindowWidth;
    public int gameWindowHeight;

    private GameField mGField;
    private int mDifficulty;

    public GameWindow(int difficulty) {
        this.mDifficulty = difficulty;
        addKeyListener(new cKeyPressedListener());
        setFocusable(true);
        gameWindowWidth = 1440;
        gameWindowHeight = 810;
        setBounds(0,0,gameWindowWidth,gameWindowHeight);
        setTitle("Game");
        mGField = new GameField(mDifficulty, gameWindowWidth, gameWindowHeight);
        Container con = getContentPane();
        con.add(mGField);
        setVisible(true);
    }

    private class cKeyPressedListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == 27) {
                System.exit(0);
            } else if(keyCode == 37) {
                if (mGField.x - 30 > -50){
                    mGField.x -= 30;
                } else {
                    mGField.x = gameWindowWidth;
                }
            }
            else if(keyCode == 39) {
                if (mGField.x + 30 < gameWindowWidth){
                    mGField.x += 30;
                } else {
                    mGField.x = 0;
                }
            }
        }
        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
    }
}
