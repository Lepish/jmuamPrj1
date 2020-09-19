import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Gift{

    public Image img;
    public int x, y;
    public Boolean act;
    Timer timerUpdate;
    private int mGameWindowWidth;
    private int mGameWindowHeight;
    private int mDifficult;

    public Gift(Image img, int gameWindowWidth, int gameWindowHeight, int difficult){
        this.mDifficult = difficult;
        this.mGameWindowWidth = gameWindowWidth;
        this.mGameWindowHeight = gameWindowHeight;
        timerUpdate = new Timer(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                moveDown();
            }
        });
        this.img = img;
        act = false;
    }

    public void start(){
        timerUpdate.start();
        y = 0;
        x = (int)(200 + Math.random() * (mGameWindowWidth - 400));
        act = true;
    }

    public void moveDown (){
        if(act == true){
            y += 1;
            if(mDifficult <= 3) {
                x = x + (int) (Math.sin(y / 20) * 5);
            }else{
                x = x + (int) (Math.sin(y / 20) * (mDifficult + 2));
            }
            if (x >= mGameWindowWidth - 100) {
                x = mGameWindowWidth - 100;
            }
            if (x <= 100) {
                x = 100;
            }
        }
        if((y + img.getHeight(null)) >= mGameWindowHeight){
            timerUpdate.stop();
        }
    }

    public void draw(Graphics gr){
        if(act == true){
            gr.drawImage(img, x, y, null);
        }
    }
}
