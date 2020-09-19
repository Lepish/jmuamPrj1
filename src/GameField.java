import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameField extends JPanel {
    public int x;
    public Timer timerUpdate, timerDraw, timerSec;
    public int giftsCount = 0;

    private Image mHatImg;
    private Image mBgImg;
    private Gift[] mGifts;
    private Image mGameOverImg;
    private int mDifficulty;
    private int mGameWindowWidth;
    private int mGameWindowHeight;
    private int mSeconds;
    private int mPoints;

    public GameField(int difficulty, int gameWindowWidth, int gameWindowHeight) {
        mGameWindowWidth = gameWindowWidth;
        mGameWindowHeight = gameWindowHeight;
        mDifficulty = difficulty;
        mSeconds = 0;
        mPoints = 0;
        x = mGameWindowWidth/2 - 100;
        try {
            mHatImg = ImageIO.read(new File("hat.png"));
            //JOptionPane.showMessageDialog(null, "Картинка для шапки успешно загружена.");
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Картинка для шапки не загружена.");
        }
        try {
            mBgImg = ImageIO.read(new File("bg.png"));
            ///JOptionPane.showMessageDialog(null, "Картинка фона игры успешно загружена.");
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Картинка фона игры не загружена.");
        }
        try {
            mGameOverImg = ImageIO.read(new File("gmovr.png"));
            //JOptionPane.showMessageDialog(null, "Картинка конца игры успешно загружена.");
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Картинка конца игры не загружена.");
        }
        mGifts = new Gift[15];
        int j = 1;
        for (int i = 0; i < 15; i++) {
            try {
                mGifts[i] = new Gift(ImageIO.read(new File("./p" + j + ".png")), mGameWindowWidth, mGameWindowHeight, mDifficulty);
                j++;
                if(j > 5){
                    j = 1;
                }
                //JOptionPane.showMessageDialog(null, "Картинка подарка  № " + i + " успешно загружена.");
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(null, "Картинка подарка  № " + i + " не загружена.");
            }
        }
        timerUpdate = new Timer(3000, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateStart();
            }
        });
        timerUpdate.start();
        timerDraw = new Timer(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                repaint();
            }
        });
        timerDraw.start();
        timerSec = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSeconds++;
            }
        });

        timerSec.start();
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        gr.drawImage(mBgImg, 0, 0, null);
        gr.drawImage(mHatImg, x, mGameWindowHeight - 120, null);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./Pixel.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = new Font("Pixel", Font.PLAIN, 20);
        String timeStr = "Seconds lived : "+ mSeconds;
        String pointsStr = "Points : "+ mPoints;
        gr.setFont(font);
        gr.setColor(Color.BLACK);
        gr.drawString(timeStr, mGameWindowWidth - 300,30);
        gr.drawString(pointsStr, mGameWindowWidth - 300, 60);

        for (int i = 0; i < 9; i++){
            mGifts[i].draw(gr);
            if (mGifts[i].act == true){
                if(mGifts[i].y + mGifts[i].img.getHeight(null) >= mGameWindowHeight - 40) {
                    if (Math.abs(mGifts[i].x - x) > 150) {
                        gr.drawImage(mGameOverImg, 0, 0, null);
                        gr.drawString(timeStr, mGameWindowWidth / 2 - 130,mGameWindowHeight / 2 + 60);
                        gr.drawString(pointsStr, mGameWindowWidth / 2 - 85, mGameWindowHeight / 2 + 90);
                        timerSec.stop();
                        timerDraw.stop();
                        timerUpdate.stop();
                        break;
                    } else {
                        mGifts[i].act = false;
                        mPoints++;
                    }
                }
                if(mGifts[i].y + mGifts[i].img.getHeight(null) >= mGameWindowHeight - 100) {
                    if (Math.abs(mGifts[i].x - x) <= 150) {
                        mGifts[i].act = false;
                        mPoints++;
                    }
                }
            }
        }
    }

    private void updateStart(){
        int giftCounter = 0;
        for(int i = 0; i < 9; i++){
            if(mGifts[i].act == false){
                if (giftCounter < mDifficulty){
                    mGifts[i].start();
                    giftsCount = giftsCount + 1;
                    break;
                }
            } else {
                giftCounter++;
            }
        }
    }
}
