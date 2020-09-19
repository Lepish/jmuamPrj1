import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        String res = JOptionPane.showInputDialog(null, "Choose difficulty 1..9\n 1..3: number of gifts\n 4..9: changing the trajectory", "Difficulty", 1);
        int difficulty = Integer.parseInt(res);
        if ((difficulty >= 1) && (difficulty <= 9)){
            GameWindow window = new GameWindow(difficulty);
        }
    }
}
