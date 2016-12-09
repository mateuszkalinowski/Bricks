package core;


import frames.MainFrame;
import logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                    createCfg.println("firstPlayer=");
                    createCfg.println("secondPlayer=");
                    createCfg.println("firstPlayerProgramType=1");
                    createCfg.println("secondPlayerProgramType=1");
                    createCfg.println("firstPlayerRunCommand=");
                    createCfg.println("secondPlayerRunCommand=");
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
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine.length==2) {
                            firstPlayerPath = splittedLine[1];
                        }
                        else {
                            firstPlayerPath = "";
                        }
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine.length==2) {
                            secondPlayerPath = splittedLine[1];
                        }
                        else {
                            secondPlayerPath = "";
                        }
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        firstPlayerProgramType = Integer.parseInt(splittedLine[1]);
                        line = in.nextLine();
                        splittedLine = line.split("=");
                        secondPlayerProgramType = Integer.parseInt(splittedLine[1]);
                        line=in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine.length==2) {
                            firstPlayerRunCommand=splittedLine[1];
                        }
                        else {
                            firstPlayerRunCommand = "";
                        }
                        line=in.nextLine();
                        splittedLine = line.split("=");
                        if(splittedLine.length==2) {
                            secondPlayerRunCommand=splittedLine[1];
                        }
                        else {
                            secondPlayerRunCommand = "";
                        }
                    } catch (Exception ignored) {
                        loadDefaultSettings();
                    }
                }
            } catch (Exception ignored) {
                loadDefaultSettings();
            }
            mainFrame = new MainFrame(initialBoardSize,firstPlayerColor,secondPlayerColor,isSound,volume,debugMode,
                    firstPlayerPath,secondPlayerPath,firstPlayerProgramType,secondPlayerProgramType,
                    firstPlayerRunCommand,secondPlayerRunCommand);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    if(firstRobotPlayer!=null)
                       firstRobotPlayer.killRobot();
                    if(secondRobotPlayer!=null)
                        secondRobotPlayer.killRobot();
                }
            });
        });
    }
    private static int initialBoardSize;
    private static Color firstPlayerColor;
    private static Color secondPlayerColor;
    private static boolean isSound;
    private static int volume;
    private static boolean debugMode;
    private static String firstPlayerPath;
    private static String secondPlayerPath;

    private static int firstPlayerProgramType;
    private static int secondPlayerProgramType;

    private static String firstPlayerRunCommand;
    private static String secondPlayerRunCommand;

    public static MainFrame mainFrame;

    public static String path;

    private static void loadDefaultSettings(){
        initialBoardSize=5;
        firstPlayerColor = new Color(69,136,58);
        secondPlayerColor = new Color(238,44,44);
        isSound = true;
        volume = 0;
        firstPlayerProgramType=1;
        secondPlayerProgramType=1;

        firstPlayerRunCommand="";
        secondPlayerRunCommand="";

        firstPlayerPath="";
        secondPlayerPath="";
    }

    public static RobotPlayer firstRobotPlayer;
    public static RobotPlayer secondRobotPlayer;

}
