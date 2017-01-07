package stages;

import core.Bricks;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.RobotPlayer;

import java.beans.EventHandler;
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
        singleGamesButton = new Button("Gra Pojedyńcza");
        singleGameButtonHBox.setAlignment(Pos.CENTER);
        singleGameButtonHBox.getChildren().add(singleGamesButton);

        singleGamesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                singleGamesBorder = true;
                drawFrame();
            }
        });
        singleGamesButton.addEventHandler(MouseEvent.MOUSE_EXITED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                singleGamesBorder = false;
                drawFrame();
            }
        });

        singleGamesButton.setOnAction(event -> {
            boolean checkFirstComputerPlayer = false;
            boolean checkSecondComputerPlayer = false;
            if (Bricks.mainStage.firstPlayerProgramType == 0) {
                if (Bricks.mainStage.playerFirstFullPath.substring(Bricks.mainStage.playerFirstFullPath.length() - 3).equals("out") || Bricks.mainStage.playerFirstFullPath.substring(Bricks.mainStage.playerFirstFullPath.length() - 3).equals("exe")) {
                    try {
                        Bricks.firstRobotPlayer = new RobotPlayer(Bricks.mainStage.playerFirstFullPath, Bricks.mainStage.BoardSize);
                        checkFirstComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                } else if (Bricks.mainStage.playerFirstFullPath.substring(Bricks.mainStage.playerFirstFullPath.length() - 3).equals("jar")) {
                    try {
                        Bricks.firstRobotPlayer = new RobotPlayer("java -jar " + Bricks.mainStage.playerFirstFullPath, Bricks.mainStage.BoardSize);
                        checkFirstComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                } else if (Bricks.mainStage.playerFirstFullPath.substring(Bricks.mainStage.playerFirstFullPath.length() - 5).equals("class")) {
                    try {
                        Bricks.firstRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainStage.pathToPlayerOne + " " + Bricks.mainStage.playerFirstProgramName,Bricks.mainStage.BoardSize);
                        checkFirstComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                }

            }
            if (Bricks.mainStage.firstPlayerProgramType == 1) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(Bricks.mainStage.firstPlayerRunCommand, Bricks.mainStage.BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }
            if (Bricks.mainStage.secondPlayerProgramType == 0) {
                if (Bricks.mainStage.playerSecondFullPath.substring(Bricks.mainStage.playerSecondFullPath.length() - 3).equals("out") || Bricks.mainStage.playerSecondFullPath.substring(Bricks.mainStage.playerSecondFullPath.length() - 3).equals("exe")) {
                    try {
                        Bricks.secondRobotPlayer = new RobotPlayer(Bricks.mainStage.playerSecondFullPath, Bricks.mainStage.BoardSize);
                        checkSecondComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                } else if (Bricks.mainStage.playerSecondFullPath.substring(Bricks.mainStage.playerSecondFullPath.length() - 3).equals("jar")) {
                    try {
                        Bricks.secondRobotPlayer = new RobotPlayer("java -jar " + Bricks.mainStage.playerSecondFullPath, Bricks.mainStage.BoardSize);
                        checkSecondComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                } else if (Bricks.mainStage.playerSecondFullPath.substring(Bricks.mainStage.playerSecondFullPath.length() - 5).equals("class")) {
                    try {
                        Bricks.secondRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainStage.pathToPlayerTwo + " " + Bricks.mainStage.playerSecondProgramName, Bricks.mainStage.BoardSize);
                        checkSecondComputerPlayer = true;
                    } catch (Exception ignored) {
                    }
                }

            }
            if (Bricks.mainStage.secondPlayerProgramType == 1) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(Bricks.mainStage.secondPlayerRunCommand, Bricks.mainStage.BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
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
                alert.setContentText("Sprawdź podaną w ustawienaich ścieżkę.");
                alert.showAndWait();
            } else if (checkFirstComputerPlayer) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Drugi program grający nie działa.");
                alert.setContentText("Sprawdź podaną w ustawienaich ścieżkę.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Oba programy grające nie działają.");
                alert.setContentText("Sprawdź podane w ustawienaich ścieżki.");
                alert.showAndWait();
            }


        });

        mainGridPane.add(singleGameButtonHBox, 0,5);

        HBox gamesGameButtonHBox = new HBox();
        gamesGamesButton = new Button("Rozgrywki");
        gamesGameButtonHBox.setAlignment(Pos.CENTER);
        gamesGameButtonHBox.getChildren().add(gamesGamesButton);

        gamesGamesButton.setOnAction(event -> {
            gamesPane = new GamesPane(Bricks.mainStage.sceneOfChoice.getWidth(), Bricks.mainStage.sceneOfChoice.getHeight());
            Scene gamesScene = new Scene(gamesPane, Bricks.mainStage.sceneOfChoice.getWidth(), Bricks.mainStage.sceneOfChoice.getHeight());
            gamesScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
            Bricks.mainStage.mainStage.setScene(gamesScene);
            Bricks.mainStage.mainStage.show();
        });

        gamesGamesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamesGamesBorder = true;
                drawFrame();
            }
        });
        gamesGamesButton.addEventHandler(MouseEvent.MOUSE_EXITED, new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gamesGamesBorder = false;
                drawFrame();
            }
        });

        mainGridPane.add(gamesGameButtonHBox, 1,5);

        HBox backButtonHBox = new HBox();
        backButton = new Button("Cofnij");
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

            singleGameCanvas.setHeight(height);
            singleGameCanvas.setWidth(width);

            gamesGameCanvas.setHeight(height);
            gamesGameCanvas.setWidth(width);


            GraphicsContext gc1 = singleGameCanvas.getGraphicsContext2D();
            gc1.clearRect(0,0,width,height);
            gc1.drawImage(singleGameImage,0, 0, width, height);

            if(singleGamesBorder) {
                gc1.setStroke(Color.BLUE);
                gc1.setLineWidth(3);
                gc1.strokeRect(0,0,width,height);
            }

            GraphicsContext gc2 = gamesGameCanvas.getGraphicsContext2D();
            gc2.clearRect(0,0,width,height);
            gc2.drawImage(gamesGameImage,0,0,width,height);
            if(gamesGamesBorder) {
                gc2.setStroke(Color.BLUE);
                gc2.setLineWidth(3);
                gc2.strokeRect(0,0,width,height);
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

    public GamesPane gamesPane;

}
