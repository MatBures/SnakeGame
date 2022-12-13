package org.example;

import javax.swing.JFrame;
import java.awt.*;

//Class that sets basics "parameters" and adding panel.
public class GameFrame extends JFrame {

    GameFrame() {
        GamePanel panel = new GamePanel();
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jakub\\eclipse-workspace\\SnakeGame\\src\\main\\java\\org\\example\\snakeimage.jpg");
        this.setIconImage(icon);
        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }
}
