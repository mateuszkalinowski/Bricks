package core;


import frames.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class Bricks {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }

    public static MainFrame mainFrame;
}
