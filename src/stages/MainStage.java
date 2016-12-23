package stages;

import core.Bricks;
import core.Settings;
import gfx.BoardPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.BoardLogic;
import scenes.GamePane;
import scenes.OptionsPane;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class MainStage extends Application implements Runnable {
    public void start(Stage primaryStage) {
        BorderPane mainBorderPane = new BorderPane();
        GridPane mainGridPane = new GridPane();
        RowConstraints rowInMainMenu = new RowConstraints();
        rowInMainMenu.setPercentHeight(12);
        RowConstraints logoRowInMainMenu = new RowConstraints();
        logoRowInMainMenu.setPercentHeight(40);
        mainGridPane.getRowConstraints().add(logoRowInMainMenu);
        for(int i = 0; i < 5; i++)
            mainGridPane.getRowConstraints().add(rowInMainMenu);

        ColumnConstraints columnInMainMenu = new ColumnConstraints();
        columnInMainMenu.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(columnInMainMenu);

        Label bricksTitleLabel = new Label();
        bricksTitleLabel.setText("Bricks");
        bricksTitleLabel.setFont(new Font("Comic Sans MS",126));
        bricksTitleLabel.setMaxWidth(Double.MAX_VALUE);
        bricksTitleLabel.setAlignment(Pos.CENTER);

        HBox singlePlayerGameButtonHBox = new HBox();
        singlePlayerGameButtonHBox.setAlignment(Pos.CENTER);
        Button singlePlayerGameButton = new Button("Gra Jednoosobowa");
        singlePlayerGameButton.setMaxWidth(300);
        singlePlayerGameButton.setMaxHeight(180);
        singlePlayerGameButton.setFont(new Font("Comic Sans MS",22));
        singlePlayerGameButtonHBox.getChildren().add(singlePlayerGameButton);
        HBox.setMargin(singlePlayerGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(singlePlayerGameButton,Priority.ALWAYS);
        singlePlayerGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BorderPane gameBorderPane = new BorderPane();
                int gametype=0;
                BoardLogic board = new BoardLogic(5);
                gamePane = new GamePane(board,gametype);
                sceneOfTheGame = new Scene(gamePane,mainScene.getWidth(),mainScene.getHeight());
                mainStage.setScene(sceneOfTheGame);
                mainStage.show();
                Thread game = new Thread(Bricks.mainStage);
                running = true;
                game.start();


            }
        });

        HBox twoPlayersGameButtonHBox = new HBox();
        twoPlayersGameButtonHBox.setAlignment(Pos.CENTER);
        Button twoPlayersGameButton = new Button("Gra Dwuosobowa");
        twoPlayersGameButton.setMaxWidth(300);
        twoPlayersGameButton.setMaxHeight(180);
        twoPlayersGameButton.setFont(new Font("Comic Sans MS",22));
        twoPlayersGameButtonHBox.getChildren().add(twoPlayersGameButton);
        HBox.setMargin(twoPlayersGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(twoPlayersGameButton,Priority.ALWAYS);

        HBox robotWarsButtonHBox = new HBox();
        robotWarsButtonHBox.setAlignment(Pos.CENTER);
        Button robotWarsButton = new Button("Wojna Robotów");
        robotWarsButton.setMaxWidth(300);
        robotWarsButton.setMaxHeight(180);
        robotWarsButton.setFont(new Font("Comic Sans MS",22));
        robotWarsButtonHBox.getChildren().add(robotWarsButton);
        HBox.setMargin(robotWarsButton, new Insets(10,0,10,0));
        HBox.setHgrow(robotWarsButton,Priority.ALWAYS);

        HBox optionsButtonHBox = new HBox();
        optionsButtonHBox.setAlignment(Pos.CENTER);
        Button optionsButton = new Button("Opcje");
        optionsButton.setMaxWidth(300);
        optionsButton.setMaxHeight(180);
        optionsButton.setFont(new Font("Comic Sans MS",22));
        optionsButtonHBox.getChildren().add(optionsButton);
        HBox.setMargin(optionsButton, new Insets(10,0,10,0));
        HBox.setHgrow(optionsButton,Priority.ALWAYS);
        optionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                optionsPane = new OptionsPane(mainScene.getWidth(),mainScene.getHeight(),new Settings(BoardSize, firstPlayerColor, secondPlayerColor,
                        isSound, volume, debugMode, playerFirstFullPath, playerSecondFullPath,
                        firstPlayerProgramType, secondPlayerProgramType, firstPlayerRunCommand, secondPlayerRunCommand, computerPlayerType));
                sceneOfSettings = new Scene(optionsPane,mainScene.getWidth(),mainScene.getHeight());
                mainStage.setScene(sceneOfSettings);
                mainStage.show();
            }
        });

        HBox exitButtonHBox = new HBox();
        exitButtonHBox.setAlignment(Pos.CENTER);
        Button exitButton = new Button("Wyjście");
        exitButton.setMaxWidth(300);
        exitButton.setMaxHeight(180);
        exitButton.setFont(new Font("Comic Sans MS",22));
        exitButtonHBox.getChildren().add(exitButton);
        HBox.setMargin(exitButton, new Insets(10,0,10,0));
        HBox.setHgrow(exitButton,Priority.ALWAYS);
        exitButton.setOnAction(event -> {
            running=false;
            System.exit(0);
        });

        mainGridPane.add(bricksTitleLabel,0,0);
        mainGridPane.add(singlePlayerGameButtonHBox,0,1);
        mainGridPane.add(twoPlayersGameButtonHBox,0,2);
        mainGridPane.add(robotWarsButtonHBox,0,3);
        mainGridPane.add(optionsButtonHBox,0,4);
        mainGridPane.add(exitButtonHBox,0,5);
        mainBorderPane.setCenter(mainGridPane);

        Label programInfoLabel = new Label();
        programInfoLabel.setMaxWidth(Double.MAX_VALUE);
        programInfoLabel.setAlignment(Pos.CENTER);
        programInfoLabel.setText("Autorzy: Mateusz Kalinowski, Michał Romaszko \nWersja 1.3.0");
        programInfoLabel.setTextAlignment(TextAlignment.CENTER);

        mainBorderPane.setBottom(programInfoLabel);
        mainScene = new Scene(mainBorderPane, 500, 650);
        mainStage = primaryStage;
        mainStage.setTitle("Bricks");
        mainStage.setScene(mainScene);
        mainStage.setMinWidth(400);
        mainStage.setMinHeight(640);
        mainStage.show();


        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                running=false;
                System.exit(0);
            }
        });

    }
    public void getSize(){
        height = (int)Bricks.mainStage.sceneOfTheGame.getHeight();
        widht = (int)Bricks.mainStage.sceneOfTheGame.getWidth();
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();

        int FRAMERATE = 60;
        double nsPerTick = 1000000000D / FRAMERATE;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        boolean shouldRender = false;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                shouldRender = true;
                delta -= 1;
            }
            if (shouldRender) {
                getSize();
                gamePane.drawFrame(widht,height);
                shouldRender = false;
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
            }
        }
    }
    public void backToMenu(){
        mainStage.setScene(mainScene);
    }
    public void setSettings(int initialBoardSize, Color firstPlayerColor, Color secondPlayerColor, boolean isSound, int volume, boolean debugModeInitialize, String firstPlayerPath, String secondPlayerPath,
                                  int firstPlayerProgramTypeArgument, int secondPlayerProgramTypeArgument,
                                  String firstPlayerRunCommandArgument, String secondPlayerRunCommandArgument, int computerPlayerType) {

        this.BoardSize = initialBoardSize;
        this.firstPlayerColor = firstPlayerColor;
        this.secondPlayerColor = secondPlayerColor;
        this.isSound = isSound;
        this.volume = volume;
        this.debugMode = debugModeInitialize;
        this.playerFirstFullPath = firstPlayerPath;
        this.playerSecondFullPath = secondPlayerPath;
        this.firstPlayerProgramType = firstPlayerProgramTypeArgument;
        this.secondPlayerProgramType = secondPlayerProgramTypeArgument;
        this.firstPlayerRunCommand = firstPlayerRunCommandArgument;
        this.secondPlayerRunCommand = secondPlayerRunCommandArgument;
        this.computerPlayerType = computerPlayerType;

        if (playerFirstFullPath.length() > 7) {
            int i = playerFirstFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerFirstFullPath.charAt(i) == '/' || playerFirstFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerOne = playerFirstFullPath.substring(0, i);
            playerFirstProgramName = playerFirstFullPath.substring(i + 1, playerFirstFullPath.length());
            playerFirstProgramName = playerFirstProgramName.substring(0, playerFirstProgramName.length() - 6);

        }
        if (playerSecondFullPath.length() > 7) {
            int i = playerSecondFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerSecondFullPath.charAt(i) == '/' || playerSecondFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerTwo = playerSecondFullPath.substring(0, i);
            playerSecondProgramName = playerSecondFullPath.substring(i + 1, playerSecondFullPath.length());
            playerSecondProgramName = playerSecondProgramName.substring(0, playerSecondProgramName.length() - 6);
        }
        exportSettings();
    }
    private void exportSettings() {
        try {
            PrintWriter createCfg = new PrintWriter(new File(Bricks.path + "/options"));
            createCfg.println("BoardSize=" + BoardSize);
            createCfg.println("FirstColor=" + firstPlayerColor.toString());
            createCfg.println("SecondColor=" + secondPlayerColor.toString());
            createCfg.println("sound=" + isSound);
            createCfg.println("volume=" + volume);
            createCfg.println("debugMode=" + debugMode);
            createCfg.println("firstPlayer=" + playerFirstFullPath);
            createCfg.println("secondPlayer=" + playerSecondFullPath);
            createCfg.println("firstPlayerProgramType=" + firstPlayerProgramType);
            createCfg.println("secondPlayerProgramType=" + secondPlayerProgramType);
            createCfg.println("firstPlayerRunCommand=" + firstPlayerRunCommand);
            createCfg.println("secondPlayerRunCommand=" + secondPlayerRunCommand);
            createCfg.println("computerPlayerType=" + computerPlayerType);
            createCfg.close();
        } catch (Exception ignored) {

        }
    }
    private int height=100;
    private int widht=100;
    public Stage mainStage;
    private Scene mainScene;
    private boolean running = false;

    private Scene sceneOfTheGame;
    private Scene sceneOfSettings;

    private GamePane gamePane;
    private OptionsPane optionsPane;

    public BoardLogic board;

    public BoardPanel boardPanel;

    //USTAWIENIA GRY:
    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    public int volume;
    public int computerPlayer;
    private int BoardSize;
    private int computerPlayerType;

    public boolean isSound;
    private boolean debugMode;

    public javafx.scene.paint.Color firstPlayerColor;
    public javafx.scene.paint.Color secondPlayerColor;

    private String playerFirstFullPath = "";
    private String playerSecondFullPath = "";
    private String pathToPlayerOne = "";
    private String pathToPlayerTwo = "";
    private String playerFirstProgramName = "";
    private String playerSecondProgramName = "";
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

}
