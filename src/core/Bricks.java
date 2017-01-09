package core;

import stages.*;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class Bricks extends Application {
    @SuppressWarnings ("ResultOfMethodCallIgnored")
    public void start(Stage primaryStage) throws Exception {
        mainStage = new MainStage();
        mainStage.setSettings(initialBoardSize, firstPlayerColor, secondPlayerColor, isSound, volume,
                firstPlayerPath, secondPlayerPath, firstPlayerProgramType, secondPlayerProgramType,
                firstPlayerRunCommand, secondPlayerRunCommand, computerPlayerType, theme);
        mainStage.start(primaryStage);
    }

    @SuppressWarnings ("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        //EventQueue.invokeLater(() -> {
        path = System.getProperty("user.home") + "/Documents/Bricks";
        File resources = new File(path);
        try {
            if (!resources.exists()) {
                resources.mkdir();
            }
            if (!new File(path + "/options").exists()) {
                new File(path + "/options").createNewFile();
                PrintWriter createCfg = new PrintWriter(new File(path + "/options"));
                createCfg.println("BoardSize=5");
                createCfg.println("FirstColor=java.awt.Color[r=69,g=136,b=58]");
                createCfg.println("SecondColor=java.awt.Color[r=238,g=44,b=44]");
                createCfg.println("sound=true");
                createCfg.println("volume=0");
                createCfg.println("firstPlayer=");
                createCfg.println("secondPlayer=");
                createCfg.println("firstPlayerProgramType=1");
                createCfg.println("secondPlayerProgramType=1");
                createCfg.println("firstPlayerRunCommand=");
                createCfg.println("secondPlayerRunCommand=");
                createCfg.println("theme=0");
                createCfg.close();
                loadDefaultSettings();
            } else {
                try {
                    Scanner in = new Scanner(new File(path + "/options"));
                    String line = in.nextLine();
                    String[] Divided = line.split("=");
                    initialBoardSize = Integer.parseInt(Divided[1]);
                    line = in.nextLine();
                    String[] splittedLine = line.split("[=]");
                    firstPlayerColor = javafx.scene.paint.Color.web(splittedLine[1]);
                    line = in.nextLine();
                    splittedLine = line.split("[=]");
                    secondPlayerColor = javafx.scene.paint.Color.web(splittedLine[1]);
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    isSound = splittedLine[1].equals("true");
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    volume = Integer.parseInt(splittedLine[1]);
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine.length == 2) {
                        firstPlayerPath = splittedLine[1];
                    } else {
                        firstPlayerPath = "";
                    }
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine.length == 2) {
                        secondPlayerPath = splittedLine[1];
                    } else {
                        secondPlayerPath = "";
                    }
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    firstPlayerProgramType = Integer.parseInt(splittedLine[1]);
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    secondPlayerProgramType = Integer.parseInt(splittedLine[1]);
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine.length == 2) {
                        firstPlayerRunCommand = splittedLine[1];
                    } else {
                        firstPlayerRunCommand = "";
                    }
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine.length == 2) {
                        secondPlayerRunCommand = splittedLine[1];
                    } else {
                        secondPlayerRunCommand = "";
                    }
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine.length == 2) {
                        computerPlayerType = Integer.parseInt(splittedLine[1]);
                    } else {
                        computerPlayerType = 0;
                    }
                    line = in.nextLine();
                    splittedLine = line.split("=");
                    if (splittedLine[1].equals("1"))
                        theme = 1;
                    else
                        theme = 0;
                } catch (Exception ignored) {
                    loadDefaultSettings();
                }
            }
        } catch (Exception ignored) {
            loadDefaultSettings();
        }
        launch();
    }

    private static boolean isSound;
    public static boolean autoPlayRunning = false;

    private static int initialBoardSize;
    private static int volume;
    private static int firstPlayerProgramType;
    private static int secondPlayerProgramType;
    private static int computerPlayerType;

    private static javafx.scene.paint.Color firstPlayerColor;
    private static javafx.scene.paint.Color secondPlayerColor;

    private static String firstPlayerPath;
    private static String secondPlayerPath;
    private static String firstPlayerRunCommand;
    private static String secondPlayerRunCommand;
    public static String path;

    public static RobotPlayer firstRobotPlayer;
    public static RobotPlayer secondRobotPlayer;
    public static RobotPlayer singlePlayerRobotPlayer;

    public static MainStage mainStage;

    private static int theme;


    private static void loadDefaultSettings() {
        initialBoardSize = 5;
        firstPlayerColor = new javafx.scene.paint.Color(69.0 / 255, 136.0 / 255, 58.0 / 255.0, 1);
        secondPlayerColor = new javafx.scene.paint.Color(238.0 / 255, 44.0 / 255, 44.0 / 255.0, 1);
        isSound = true;
        volume = 0;
        firstPlayerProgramType = 1;
        secondPlayerProgramType = 1;

        firstPlayerRunCommand = "";
        secondPlayerRunCommand = "";

        firstPlayerPath = "";
        secondPlayerPath = "";

        computerPlayerType = 0;

        theme = 0;

    }
}
