package stages;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import core.Bricks;
import core.Settings;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.RobotPlayer;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class MainStage extends Application {
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
        HBox bricksTitleHBox = new HBox();
        bricksTitleHBox.setAlignment(Pos.CENTER);
        bricksTitleLabel = new Text();
        bricksTitleLabel.setText("Bricks");
        bricksTitleLabel.setId("logo");
        bricksTitleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,151));
        bricksTitleHBox.getChildren().add(bricksTitleLabel);

        HBox singlePlayerGameButtonHBox = new HBox();
        singlePlayerGameButtonHBox.setAlignment(Pos.CENTER);
        singlePlayerGameButton = new Button("Gra Jednoosobowa");
        singlePlayerGameButton.setId("buttonInMainMenu");
        singlePlayerGameButton.setMaxWidth(300);
        singlePlayerGameButton.setMaxHeight(180);
        singlePlayerGameButton.setFont(new Font("Comic Sans MS",22.1));
        singlePlayerGameButtonHBox.getChildren().add(singlePlayerGameButton);
        HBox.setMargin(singlePlayerGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(singlePlayerGameButton,Priority.ALWAYS);
        singlePlayerGameButton.setOnAction(event -> {
            boolean computerPlayerFound = false;
            if (computerPlayerType == 1) {
                if (firstPlayerProgramType == 0) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(playerFirstFullPath, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
                if (firstPlayerProgramType == 1) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer("java -cp " + pathToPlayerOne + " " + playerFirstProgramName, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
                if (firstPlayerProgramType == 2) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(firstPlayerRunCommand, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
            }
            if (computerPlayerType == 2) {
                if (secondPlayerProgramType == 0) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(playerSecondFullPath, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
                if (secondPlayerProgramType == 1) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer("java -cp " + pathToPlayerTwo + " " + playerSecondProgramName, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
                if (secondPlayerProgramType == 2) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(secondPlayerRunCommand, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
            }

            if (computerPlayerType == 0 || (computerPlayerType != 0 && computerPlayerFound)) {
                int gametype = 0;
                BoardLogic board1 = new BoardLogic(BoardSize);
                gamePane = new GamePane(board1, gametype);
                sceneOfTheGame = new Scene(gamePane, mainScene.getWidth(), mainScene.getHeight());
                sceneOfTheGame.getStylesheets().add(selectedTheme);
                gamePane.drawFrame();
                mainStage.setScene(sceneOfTheGame);
                mainStage.show();
                sceneOfTheGame.setOnKeyReleased(event12 -> {
                    if (event12.getCode() == KeyCode.ESCAPE) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Potwierdzenie Wyjścia");
                        alert.setHeaderText("Chcesz wrócić do menu głównego?");
                        alert.setContentText("Obecna rozgrywka nie zostanie zapisana.");
                        ButtonType buttonYes = new ButtonType("Tak");
                        ButtonType resetBoard = new ButtonType("Zresetuj Grę");
                        ButtonType buttonNo = new ButtonType("Anuluj");
                        alert.getButtonTypes().setAll(buttonNo,resetBoard,buttonYes);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonYes) {
                            Bricks.mainStage.backToMenu();
                        }
                        else if(result.isPresent() && result.get() == resetBoard) {
                            gamePane.resetBoard();
                        }

                    }
                });
            }
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Błąd uruchamiania gry");
                error.setHeaderText("Gracz komputerowy nie mógł zostać uruchomiony.");
                error.setContentText("Sprawdź podaną w ustawieniach ścieżkę do pliku.");
                error.showAndWait();
            }
        });

        HBox twoPlayersGameButtonHBox = new HBox();
        twoPlayersGameButtonHBox.setAlignment(Pos.CENTER);
        twoPlayersGameButton = new Button("Gra Dwuosobowa");
        twoPlayersGameButton.setId("buttonInMainMenu");
        twoPlayersGameButton.setMaxWidth(300);
        twoPlayersGameButton.setMaxHeight(180);
        twoPlayersGameButton.setFont(new Font("Comic Sans MS",22.1));
        twoPlayersGameButtonHBox.getChildren().add(twoPlayersGameButton);
        HBox.setMargin(twoPlayersGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(twoPlayersGameButton,Priority.ALWAYS);
        twoPlayersGameButton.setOnAction(event -> {
            int gametype=1;
            BoardLogic board12 = new BoardLogic(BoardSize);
            gamePane = new GamePane(board12,gametype);
            sceneOfTheGame = new Scene(gamePane,mainScene.getWidth(),mainScene.getHeight());
            sceneOfTheGame.getStylesheets().add(selectedTheme);
            gamePane.drawFrame();
            mainStage.setScene(sceneOfTheGame);
            mainStage.show();
            sceneOfTheGame.setOnKeyReleased(event1 -> {
                if(event1.getCode()== KeyCode.ESCAPE) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Potwierdzenie Wyjścia");
                    alert.setHeaderText("Chcesz wrócić do menu głównego?");
                    alert.setContentText("Obecna rozgrywka nie zostanie zapisana.");
                    ButtonType buttonYes = new ButtonType("Tak");
                    ButtonType resetBoard = new ButtonType("Zresetuj Grę");
                    ButtonType buttonNo = new ButtonType("Anuluj");
                    alert.getButtonTypes().setAll(buttonNo,resetBoard,buttonYes);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == buttonYes) {
                        Bricks.mainStage.backToMenu();
                    }
                    else if(result.isPresent() && result.get() == resetBoard) {
                        gamePane.resetBoard();
                    }
                }
            });
        });

        HBox robotWarsButtonHBox = new HBox();
        robotWarsButtonHBox.setAlignment(Pos.CENTER);
        robotWarsButton = new Button("Wojna Robotów");
        robotWarsButton.setId("buttonInMainMenu");
        robotWarsButton.setMaxWidth(300);
        robotWarsButton.setMaxHeight(180);
        robotWarsButton.setFont(new Font("Comic Sans MS",22.1));
        robotWarsButtonHBox.getChildren().add(robotWarsButton);
        HBox.setMargin(robotWarsButton, new Insets(10,0,10,0));
        HBox.setHgrow(robotWarsButton,Priority.ALWAYS);
        robotWarsButton.setOnAction(event -> {
            boolean checkFirstComputerPlayer = false;
            boolean checkSecondComputerPlayer = false;
            if (firstPlayerProgramType == 0) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(playerFirstFullPath, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if (firstPlayerProgramType == 1) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainStage.pathToPlayerOne + " " + Bricks.mainStage.playerFirstProgramName, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }
            if (firstPlayerProgramType == 2) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(firstPlayerRunCommand, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }

            if (secondPlayerProgramType == 0) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(playerSecondFullPath, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if (secondPlayerProgramType == 1) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainStage.pathToPlayerTwo + " " + Bricks.mainStage.playerSecondProgramName, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }
            if (secondPlayerProgramType == 2) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(secondPlayerRunCommand, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if(checkFirstComputerPlayer && checkSecondComputerPlayer) {
                int gametype = 2;
                BoardLogic board13 = new BoardLogic(BoardSize);
                gamePane = new GamePane(board13, gametype);
                sceneOfTheGame = new Scene(gamePane, mainScene.getWidth(), mainScene.getHeight());
                sceneOfTheGame.getStylesheets().add(selectedTheme);
                gamePane.drawFrame();
                mainStage.setScene(sceneOfTheGame);
                mainStage.show();
                sceneOfTheGame.setOnKeyReleased(event13 -> {
                    if (event13.getCode() == KeyCode.ESCAPE) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Potwierdzenie Wyjścia");
                        alert.setHeaderText("Chcesz wrócić do menu głównego?");
                        alert.setContentText("Obecna rozgrywka nie zostanie zapisana.");
                        ButtonType buttonYes = new ButtonType("Tak");
                        ButtonType resetBoard = new ButtonType("Zresetuj Grę");
                        ButtonType buttonNo = new ButtonType("Anuluj");
                        alert.getButtonTypes().setAll(buttonNo,resetBoard,buttonYes);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonYes) {
                            Bricks.mainStage.backToMenu();
                        }
                        else if(result.isPresent() && result.get() == resetBoard) {
                            gamePane.resetBoard();
                        }
                    }
                });
            }
            else if (!checkFirstComputerPlayer && checkSecondComputerPlayer) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Pierwszy program grający nie działa.");
                alert.setContentText("Sprawdź podaną w ustawienaich ścieżkę.");
                alert.showAndWait();
            } else  if(checkFirstComputerPlayer) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Drugi program grający nie działa.");
                alert.setContentText("Sprawdź podaną w ustawienaich ścieżkę.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Oba programy grające nie działają.");
                alert.setContentText("Sprawdź podane w ustawienaich ścieżki.");
                alert.showAndWait();
            }
        });

        HBox optionsButtonHBox = new HBox();
        optionsButtonHBox.setAlignment(Pos.CENTER);
        optionsButton = new Button("Opcje");
        optionsButton.setId("buttonInMainMenu");
        optionsButton.setMaxWidth(300);
        optionsButton.setMaxHeight(180);
        optionsButton.setFont(new Font("Comic Sans MS",22.1));
        optionsButtonHBox.getChildren().add(optionsButton);
        HBox.setMargin(optionsButton, new Insets(10,0,10,0));
        HBox.setHgrow(optionsButton,Priority.ALWAYS);
        optionsButton.setOnAction(event -> {
            optionsPane = new OptionsPane(mainScene.getWidth(),mainScene.getHeight(),new Settings(BoardSize, firstPlayerColor, secondPlayerColor,
                    isSound, volume, debugMode, playerFirstFullPath, playerSecondFullPath,
                    firstPlayerProgramType, secondPlayerProgramType, firstPlayerRunCommand, secondPlayerRunCommand, computerPlayerType,theme));
            sceneOfSettings = new Scene(optionsPane,mainScene.getWidth(),mainScene.getHeight());
            sceneOfSettings.getStylesheets().add(selectedTheme);
            mainStage.setScene(sceneOfSettings);
            mainStage.show();
        });

        HBox exitButtonHBox = new HBox();
        exitButtonHBox.setAlignment(Pos.CENTER);
        exitButton = new Button("Wyjście");
        exitButton.setId("buttonInMainMenu");
        exitButton.setMaxWidth(300);
        exitButton.setMaxHeight(180);
        exitButton.setFont(new Font("Comic Sans MS",22.1));
        exitButtonHBox.getChildren().add(exitButton);
        HBox.setMargin(exitButton, new Insets(10,0,10,0));
        HBox.setHgrow(exitButton,Priority.ALWAYS);
        exitButton.setOnAction(event -> {
            if(Bricks.firstRobotPlayer!=null)
                Bricks.firstRobotPlayer.killRobot();
            if(Bricks.secondRobotPlayer!=null)
            Bricks.secondRobotPlayer.killRobot();
            System.exit(0);
        });

        mainGridPane.add(bricksTitleHBox,0,0);
        mainGridPane.add(singlePlayerGameButtonHBox,0,1);
        mainGridPane.add(twoPlayersGameButtonHBox,0,2);
        mainGridPane.add(robotWarsButtonHBox,0,3);
        mainGridPane.add(optionsButtonHBox,0,4);
        mainGridPane.add(exitButtonHBox,0,5);
        mainBorderPane.setCenter(mainGridPane);

        Label programInfoLabel = new Label();
        programInfoLabel.setMaxWidth(Double.MAX_VALUE);
        programInfoLabel.setAlignment(Pos.CENTER);
        programInfoLabel.setText("Autorzy: Mateusz Kalinowski, Michał Romaszko \nWersja 1.5.0");
        programInfoLabel.setTextAlignment(TextAlignment.CENTER);

        mainBorderPane.setBottom(programInfoLabel);
        mainScene = new Scene(mainBorderPane, 500, 670);
        mainStage = primaryStage;
        mainStage.setTitle("Bricks");
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add(selectedTheme);
        mainStage.setMinWidth(520);
        mainStage.setMinHeight(710);
        mainStage.show();


        mainStage.setOnCloseRequest(event -> {
            if(Bricks.firstRobotPlayer!=null)
                Bricks.firstRobotPlayer.killRobot();
            if(Bricks.secondRobotPlayer!=null)
                Bricks.secondRobotPlayer.killRobot();
            System.exit(0);
        });
        mainStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));

        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> {
                bricksTitleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 4.7));

                singlePlayerGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 32));
                twoPlayersGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 32));
                robotWarsButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 32));
                optionsButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 32));
                exitButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 32));
                FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
                buttonWidth = fontLoader.computeStringWidth(bricksTitleLabel.getText(), bricksTitleLabel.getFont()) * (2.0 / 3.0);
                if (buttonWidth > 300) {
                    singlePlayerGameButton.setMaxWidth(buttonWidth);
                    twoPlayersGameButton.setMaxWidth(buttonWidth);
                    robotWarsButton.setMaxWidth(buttonWidth);
                    optionsButton.setMaxWidth(buttonWidth);
                    exitButton.setMaxWidth(buttonWidth);
                } else {
                    singlePlayerGameButton.setMaxWidth(300);
                    twoPlayersGameButton.setMaxWidth(300);
                    robotWarsButton.setMaxWidth(300);
                    optionsButton.setMaxWidth(300);
                    exitButton.setMaxWidth(300);
                }
                mainStage.setMinWidth(newValue.doubleValue()*0.7);
        });

    }
    int[] getSizeAsArray() throws NullPointerException{
        int[] size = new int[2];
        if(Bricks.mainStage.sceneOfTheGame==null) {
            throw new NullPointerException();
        }
        size[0] = (int)Bricks.mainStage.sceneOfTheGame.getWidth();
        size[1] = (int)Bricks.mainStage.sceneOfTheGame.getHeight();
        return size;
    }

    void backToMenu(){
        if(gamePane!=null) {
            gamePane.resetBoard();
        }
        double width = mainStage.getWidth();
        double height = mainStage.getHeight();
        bricksTitleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/4.7));

        singlePlayerGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/32));
        twoPlayersGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/32));
        robotWarsButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/32));
        optionsButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/32));
        exitButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,height/32));
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        buttonWidth = fontLoader.computeStringWidth(bricksTitleLabel.getText(),bricksTitleLabel.getFont())*(2.0/3.0);
        if(buttonWidth>300) {
            singlePlayerGameButton.setMaxWidth(buttonWidth);
            twoPlayersGameButton.setMaxWidth(buttonWidth);
            robotWarsButton.setMaxWidth(buttonWidth);
            optionsButton.setMaxWidth(buttonWidth);
            exitButton.setMaxWidth(buttonWidth);
        }
        else {
            singlePlayerGameButton.setMaxWidth(300);
            twoPlayersGameButton.setMaxWidth(300);
            robotWarsButton.setMaxWidth(300);
            optionsButton.setMaxWidth(300);
            exitButton.setMaxWidth(300);
        }
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        mainStage.setScene(mainScene);
    }
    public void setSettings(int initialBoardSize, Color firstPlayerColor, Color secondPlayerColor, boolean isSound, int volume, boolean debugModeInitialize, String firstPlayerPath, String secondPlayerPath,
                                  int firstPlayerProgramTypeArgument, int secondPlayerProgramTypeArgument,
                                  String firstPlayerRunCommandArgument, String secondPlayerRunCommandArgument, int computerPlayerType,int theme) {

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
        this.theme = theme;

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
        if(mainScene!=null) {
            mainScene.getStylesheets().removeAll(classicTheme,minecraftTheme);
           if (theme == 0) {
               mainScene.getStylesheets().add(classicTheme);
               selectedTheme = classicTheme;
           }
            if (theme == 1) {
                mainScene.getStylesheets().add(minecraftTheme);
                selectedTheme = minecraftTheme;
            }
        }
        else {
            if(theme == 0) {
                selectedTheme = classicTheme;
            }
            if(theme == 1)
                selectedTheme = minecraftTheme;
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
            createCfg.println("theme=" + theme);
            createCfg.close();
        } catch (Exception ignored) {

        }
    }
    boolean possibleMove(int x1, int y1, int x2, int y2, int[][] board) {
        if (board[x1][y1] != 0 || board[x2][y2] != 0)
            return false;
        boolean flag = false;

        if (y1 == y2) {
            if (x1 + 1 == x2)
                flag = true;
            if (x1 - 1 == x2)
                flag = true;
        }
        if (x1 == x2) {
            if (y1 + 1 == y2)
                flag = true;
            if (y1 - 1 == y2)
                flag = true;
        }

        return flag;
    }
    public String getFirstPlayerProgramName(){
        return playerFirstProgramName;
    }
    public String getSecondPlayerProgramName(){
        return playerSecondProgramName;
    }
    Stage mainStage;
    private Scene mainScene;

    public Scene sceneOfTheGame;
    private Scene sceneOfSettings;

    GamePane gamePane;
    private OptionsPane optionsPane;

    //USTAWIENIA GRY:
    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    int volume;
    int BoardSize;
    int computerPlayerType;

    private double buttonWidth;

    boolean isSound;
    private boolean debugMode;

    javafx.scene.paint.Color firstPlayerColor;
    javafx.scene.paint.Color secondPlayerColor;

    private String playerFirstFullPath = "";
    private String playerSecondFullPath = "";
    private String pathToPlayerOne = "";
    private String pathToPlayerTwo = "";
    String playerFirstProgramName = "";
    String playerSecondProgramName = "";
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    int theme;

    private String classicTheme = MainStage.class.getResource("style.css").toExternalForm();
    private String minecraftTheme = MainStage.class.getResource("style2.css").toExternalForm();
    String selectedTheme;

    private Button singlePlayerGameButton;
    private Button twoPlayersGameButton;
    private Button robotWarsButton;
    private Button optionsButton;
    private Button exitButton;

    private Text bricksTitleLabel;

}
