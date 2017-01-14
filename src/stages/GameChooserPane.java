package stages;

import XClasses.XRobotPlayer;
import core.Bricks;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.BoardLogic;

import java.io.File;
import java.util.Optional;

/**
 * Created by Mateusz on 07.01.2017.
 * Project Bricks
 */
class GameChooserPane extends Pane {
    GameChooserPane(){
        mainGridPane = new GridPane();
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(14.28);
        for (int i = 0; i < 7; i++) {
            mainGridPane.getRowConstraints().add(row);
        }
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        for (int i = 0; i < 2; i++) {
            mainGridPane.getColumnConstraints().add(column);
        }

        Label chooseGameTypeLabel = new Label("Wybierz typ gry");
        chooseGameTypeLabel.setAlignment(Pos.CENTER);
        chooseGameTypeLabel.setMaxWidth(Double.MAX_VALUE);
        chooseGameTypeLabel.setFont(Font.font("Comic Sans MS",50));

        mainGridPane.add(chooseGameTypeLabel,0,0,2,1);

        singleGameCanvas = new Canvas();
        gamesGameCanvas = new Canvas();

        mainGridPane.add(singleGameCanvas,0,1,1,4);
        mainGridPane.add(gamesGameCanvas,1,1,1,4);

        HBox singleGameButtonHBox = new HBox();
        singleGamesButton = new Button("1 vs 1");
        singleGameButtonHBox.setAlignment(Pos.CENTER);
        singleGameButtonHBox.getChildren().add(singleGamesButton);
        singleGamesButton.setMinWidth(150);
        singleGameButtonHBox.setMinWidth(150);

        singleGamesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            singleGamesBorder = true;
            drawFrame();
        });
        singleGamesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            singleGamesBorder = false;
            drawFrame();
        });

        singleGamesButton.setOnAction(event -> {
            boolean checkFirstComputerPlayer = false;
            boolean checkSecondComputerPlayer = false;
            if (Bricks.mainStage.firstPlayerProgramType == 0) {
                XRobotPlayer newSinglePlayerRobotPlayer = new XRobotPlayer("Plik class/exe/jar/out/py", Bricks.mainStage.playerFirstFullPath);
                Bricks.firstRobotPlayer = newSinglePlayerRobotPlayer.getRobotPlayer();
                try {
                    Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                }
                catch (Exception ignored){}
                if (Bricks.firstRobotPlayer != null) {
                    checkFirstComputerPlayer = true;
                }
            }
            if (Bricks.mainStage.firstPlayerProgramType == 1) {
                XRobotPlayer newSinglePlayerRobotPlayer = new XRobotPlayer("Własny",Bricks.mainStage.firstPlayerRunCommand);
                Bricks.firstRobotPlayer = newSinglePlayerRobotPlayer.getRobotPlayer();
                try {
                    Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                }
                catch (Exception ignored){}
                if(Bricks.firstRobotPlayer!=null) {
                    checkFirstComputerPlayer = true;
                }
            }
            if (Bricks.mainStage.secondPlayerProgramType == 0) {
                XRobotPlayer newSinglePlayerRobotPlayer = new XRobotPlayer("Plik class/exe/jar/out/py", Bricks.mainStage.playerSecondFullPath);
                Bricks.secondRobotPlayer = newSinglePlayerRobotPlayer.getRobotPlayer();
                try {
                    Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                }
                catch (Exception ignored){}
                if (Bricks.secondRobotPlayer != null) {
                    checkSecondComputerPlayer = true;
                }

            }
            if (Bricks.mainStage.secondPlayerProgramType == 1) {
                XRobotPlayer newSinglePlayerRobotPlayer = new XRobotPlayer("Własny", Bricks.mainStage.secondPlayerRunCommand);
                Bricks.secondRobotPlayer = newSinglePlayerRobotPlayer.getRobotPlayer();
                try {
                    Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                }
                catch (Exception ignored){}
                if (Bricks.secondRobotPlayer != null) {
                    checkSecondComputerPlayer = true;
                }
            }
            if(checkFirstComputerPlayer&&checkSecondComputerPlayer) {
                int gametype = 2;
                BoardLogic board = new BoardLogic(Bricks.mainStage.BoardSize);
                Bricks.mainStage.gamePane = new GamePane(board, gametype);
                Bricks.mainStage.sceneOfTheGame = new Scene(Bricks.mainStage.gamePane, Bricks.mainStage.sceneOfChoice.getWidth(), Bricks.mainStage.sceneOfChoice.getHeight());
                Bricks.mainStage.sceneOfTheGame.getStylesheets().add(Bricks.mainStage.selectedTheme);
                Bricks.mainStage.gamePane.drawFrame();
                Bricks.mainStage.mainStage.setScene(Bricks.mainStage.sceneOfTheGame);
                Bricks.mainStage.mainStage.show();
                Bricks.mainStage.sceneOfTheGame.setOnKeyReleased(event13 -> {
                    if (event13.getCode() == KeyCode.ESCAPE) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Potwierdzenie Wyjścia");
                        alert.setHeaderText("Chcesz wrócić do menu głównego?");
                        alert.setContentText("Obecna rozgrywka nie zostanie zapisana.");
                        ButtonType buttonYes = new ButtonType("Tak");
                        ButtonType resetBoard = new ButtonType("Zresetuj Grę");
                        ButtonType buttonNo = new ButtonType("Anuluj");
                        alert.getButtonTypes().setAll(buttonNo, resetBoard, buttonYes);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonYes) {
                            Bricks.mainStage.backToMenu();
                        } else if (result.isPresent() && result.get() == resetBoard) {
                            Bricks.mainStage.gamePane.resetBoard();
                        }
                    }
                });
            } else if (!checkFirstComputerPlayer && checkSecondComputerPlayer) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Pierwszy program grający nie działa.");
                alert.setContentText("Sprawdź podaną w ustawieniach ścieżkę.");
                alert.showAndWait();
            } else if (checkFirstComputerPlayer) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Drugi program grający nie działa.");
                alert.setContentText("Sprawdź podaną w ustawieniach ścieżkę.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Oba programy grające nie działają.");
                alert.setContentText("Sprawdź podane w ustawieniach ścieżki.");
                alert.showAndWait();
            }


        });

        mainGridPane.add(singleGameButtonHBox, 0,5);

        HBox gamesGameButtonHBox = new HBox();
        gamesGamesButton = new Button("Liga");
        gamesGameButtonHBox.setAlignment(Pos.CENTER);
        gamesGameButtonHBox.getChildren().add(gamesGamesButton);
        gamesGamesButton.setMinWidth(150);
        gamesGameButtonHBox.setMinWidth(150);

        gamesGamesButton.setOnAction(event -> {
            gamesPane = new GamesPane(Bricks.mainStage.sceneOfChoice.getWidth(), Bricks.mainStage.sceneOfChoice.getHeight());
            gamesScene = new Scene(gamesPane, Bricks.mainStage.sceneOfChoice.getWidth(), Bricks.mainStage.sceneOfChoice.getHeight());
            gamesScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
            Bricks.mainStage.mainStage.setScene(gamesScene);
            Bricks.mainStage.mainStage.show();
        });

        gamesGamesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            gamesGamesBorder = true;
            drawFrame();
        });
        gamesGamesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            gamesGamesBorder = false;
            drawFrame();
        });

        mainGridPane.add(gamesGameButtonHBox, 1,5);

        HBox backButtonHBox = new HBox();
        backButton = new Button("Powrót");
        backButtonHBox.setAlignment(Pos.CENTER);
        backButtonHBox.getChildren().add(backButton);

        backButton.setOnAction(event -> Bricks.mainStage.backToMenu());

        mainGridPane.add(backButtonHBox, 0,6,2,1);

        singleGameImage = new Image(MainStage.class.getResourceAsStream("resources/podgladPojedynczej.png"));
        gamesGameImage = new Image(MainStage.class.getResourceAsStream("resources/podgladRozgrywek.png"));

        getChildren().add(mainGridPane);

        heightProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefHeight(newValue.doubleValue());
            singleGamesButton.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/40));
            gamesGamesButton.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/40));
            backButton.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/40));
            drawFrame();
        });
        widthProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefWidth(newValue.doubleValue());
            drawFrame();
        });
    }

    void drawFrame(){
        try {
            int[] rozmiar = Bricks.mainStage.getGameChooserSizeAsArray();
            double width = rozmiar[0]*1.0 / 2.0;
            double height = rozmiar[1]*1.0 / 7.0 * 4.0;
            double difference=0;
            if(width>height) {
                difference = width-height;
                if(difference<10)
                    difference=10;
            }
            else {
                difference=10;
            }
            singleGameCanvas.setHeight(height);
            singleGameCanvas.setWidth(width);

            gamesGameCanvas.setHeight(height);
            gamesGameCanvas.setWidth(width);


            GraphicsContext gc1 = singleGameCanvas.getGraphicsContext2D();
            gc1.clearRect(0,0,width,height);
            gc1.drawImage(singleGameImage,difference/2.0, 0, width-difference, height);

            if(singleGamesBorder) {
                gc1.setStroke(Color.BLUE);
                gc1.setLineWidth(3);
                gc1.strokeRect(difference/2.0,0,width-difference,height);
            }

            GraphicsContext gc2 = gamesGameCanvas.getGraphicsContext2D();
            gc2.clearRect(0,0,width,height);
            gc2.drawImage(gamesGameImage,difference/2.0,0,width-difference,height);
            if(gamesGamesBorder) {
                gc2.setStroke(Color.BLUE);
                gc2.setLineWidth(3);
                gc2.strokeRect(difference/2.0,0,width-difference,height);
            }
        }
        catch(Exception ignored) {}
    }

    private Button singleGamesButton;
    private Button gamesGamesButton;
    private Button backButton;

    private Canvas singleGameCanvas;
    private Canvas gamesGameCanvas;

    private GridPane mainGridPane;

    private Image singleGameImage;
    private Image gamesGameImage;

    private boolean singleGamesBorder = false;

    private boolean gamesGamesBorder = false;

    GamesPane gamesPane;

    Scene gamesScene;

}
