package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Main {
    JFrame frame;
    Main(){
        frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);

        GamePanel panel= new GamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
          Main s1 = new Main();
    }
}
