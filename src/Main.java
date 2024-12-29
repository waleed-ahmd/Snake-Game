import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 600;
        int height = width;
        JFrame frame = new JFrame("Snake Game");

        frame.setVisible(true);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        SnakeGame game = new SnakeGame(width,height);
        frame.add(game);
        game.requestFocus();

    }
}
