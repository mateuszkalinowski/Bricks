package core;


import frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class Bricks {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            path = System.getProperty("user.home") + "/Documents/Bricks";
            File resources = new File(path);
            try {
                if(!resources.exists()) {
                    resources.mkdir();
                }
                if(!new File(path+"/options").exists()) {
                    new File(path+"/options").createNewFile();
                    PrintWriter createCfg = new PrintWriter(new File(path+"/options"));
                    createCfg.println("BoardSize=5");
                    createCfg.println("FirstColor=java.awt.Color[r=69,g=136,b=58]");
                    createCfg.println("SecondColor=java.awt.Color[r=238,g=44,b=44]");
                    createCfg.println("sound=true");
                    createCfg.println("volume=0");
                    createCfg.println("debugMode=false");
                    createCfg.close();
                    loadDefaultSettings();
                }
                else {
                    try {
                        Scanner in = new Scanner(new File(path + "/options"));
                        String line = in.nextLine();
                        String []Divided = line.split("=");
                        initialBoardSize = Integer.parseInt(Divided[1]);
                        line = in.nextLine();
                        String []splittedLine = line.split("[=,/]");
                        int r = Integer.parseInt(splittedLine[2]);
                        int g = Integer.parseInt(splittedLine[4]);
                        int b = Integer.parseInt(splittedLine[6].substring(0,splittedLine[6].length()-1));
                        firstPlayerColor = new Color(r,g,b);
                        line = in.nextLine();
                        splittedLine = line.split("[=,/]");
                        r = Integer.parseInt(splittedLine[2]);
                        g = Integer.parseInt(splittedLine[4]);
                        b = Integer.parseInt(splittedLine[6].substring(0,splittedLine[6].length()-1));
                        secondPlayerColor = new Color(r,g,b);
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine[1].equals("true"))
                            isSound = true;
                        else
                            isSound=false;
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        volume = Integer.parseInt(splittedLine[1]);

                        line = in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine[1].equals("true"))
                            debugMode = true;
                        else
                            debugMode = false;

                    } catch (Exception ignored) {
                        loadDefaultSettings();
                    }
                }
            } catch (Exception ignored) {
                loadDefaultSettings();
            }

            mainFrame = new MainFrame(initialBoardSize,firstPlayerColor,secondPlayerColor,isSound,volume,debugMode);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }
    private static int initialBoardSize;
    private static Color firstPlayerColor;
    private static Color secondPlayerColor;
    private static boolean isSound;
    private static int volume;
    private static boolean debugMode;

    public static MainFrame mainFrame;

    public static String path;

    private static void loadDefaultSettings(){
        initialBoardSize=5;
        firstPlayerColor = new Color(69,136,58);
        secondPlayerColor = new Color(238,44,44);
        isSound = true;
        volume = 0;
    }

}
