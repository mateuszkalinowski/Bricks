package stages;

import core.*;
import exceptions.InvalidMoveException;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
class GamePane extends Pane {
    GamePane(BoardLogic board, int gametype) {
        this.board = board;
        this.gamemode = gametype;
        canvas = new Canvas();
        mainGridPane = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints rowWithGame = new RowConstraints();
        RowConstraints rowWithMenu = new RowConstraints();
        rowWithGame.setPercentHeight(95);
        rowWithMenu.setPercentHeight(5);
        mainGridPane.getRowConstraints().add(rowWithGame);
        mainGridPane.getRowConstraints().add(rowWithMenu);
        this.board = board;
        mainGridPane.add(canvas, 0, 0);

        randomBackground = new int[board.width + 100];

        Random rnd = new Random();
        boardStyle = rnd.nextInt(3);

        for (int i = 0; i < board.width + 100; i++) {
            randomBackground[i] = rnd.nextInt(2);
        }

        getChildren().add(mainGridPane);

        movesStorage = new MovesStorage();
        movesStorage.reset();

        if (gamemode == 0 && Bricks.mainStage.computerPlayerType == 0)
            comp = new ComputerPlayer();

        if (gamemode == 0) {
            HBox gamemodeOnePlayerHBox = new HBox();
            backToMenuButton = new Button("Powrót");
            gamemodeOnePlayerHBox.getChildren().add(backToMenuButton);
            gamemodeOnePlayerHBox.setAlignment(Pos.CENTER);
            gamemodeOnePlayerHBox.setPadding(new Insets(0, 20, 0, 0));
            gamemodeOnePlayerHBox.setAlignment(Pos.TOP_RIGHT);
            mainGridPane.add(gamemodeOnePlayerHBox, 0, 1);
        }

        if (gamemode == 1) {
            HBox gamemodeTwoPlayerHBox = new HBox();
            undoMoveButton = new Button("Cofnij Ruch");
            gamemodeTwoPlayerHBox.getChildren().add(undoMoveButton);
            gamemodeTwoPlayerHBox.setAlignment(Pos.CENTER);
            gamemodeTwoPlayerHBox.setPadding(new Insets(0, 20, 0, 0));
            gamemodeTwoPlayerHBox.setAlignment(Pos.TOP_RIGHT);
            gamemodeTwoPlayerHBox.setSpacing(10);
            backToMenuButton = new Button("Powrót");
            gamemodeTwoPlayerHBox.getChildren().add(backToMenuButton);

            mainGridPane.add(gamemodeTwoPlayerHBox, 0, 1);

            undoMoveButton.setDisable(true);

            undoMoveButton.setOnAction(event -> {
                int[] move = movesStorage.returnMoveLikeArray();

                board.board[move[0]][move[1]] = 0;
                board.board[move[2]][move[3]] = 0;

                drawFrame();
                if (actualPlayer == 1)
                    actualPlayer = 2;
                else if (actualPlayer == 2)
                    actualPlayer = 1;

                if (movesStorage.isEmpty())
                    undoMoveButton.setDisable(true);
            });


        }

        if (gamemode == 2) {
            HBox gamemodeRobotsWarsHBox = new HBox();

            speedDownButton = new Button("-");
            gamemodeRobotsWarsHBox.getChildren().add(speedDownButton);
            speedDownButton.setOnAction(event -> {
                int i = Integer.parseInt(speedTextField.getText());
                if (i > 1) {
                    i--;
                    speedTextField.setText(i + "");
                }
            });

            int autoGameSpeed = 5;
            speedTextField = new TextField(autoGameSpeed + "");
            speedTextField.setMaxWidth(32);
            speedTextField.setMinWidth(32);
            speedTextField.setEditable(false);
            gamemodeRobotsWarsHBox.getChildren().add(speedTextField);

            speedUpButton = new Button("+");
            gamemodeRobotsWarsHBox.getChildren().add(speedUpButton);
            speedUpButton.setOnAction(event -> {
                int i = Integer.parseInt(speedTextField.getText());
                if (i < 20) {
                    i++;
                    speedTextField.setText(i + "");
                }
            });
            computerPlayer = 1;
            nextMoveButton = new Button("Ruch");
            gamemodeRobotsWarsHBox.getChildren().add(nextMoveButton);
            nextMoveButton.setOnAction(event -> {
                boolean gameFinished = false;
                nextMoveButton.setDisable(true);
                try {
                    int move[] = new int[4];
                    if (computerPlayer == 1) {
                        if (movesStorage.isEmpty()) {
                            try {
                                move = Bricks.firstRobotPlayer.makeMove("ZACZYNAJ");
                            } catch (InvalidMoveException exception) {
                                walkover(computerPlayer, "BadMove");
                                gameFinished = true;
                            } catch (TimeoutException exception) {
                                walkover(computerPlayer, "Timeout");
                                gameFinished = true;
                            }
                        } else {
                            try {
                                move = Bricks.firstRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                            } catch (InvalidMoveException exception) {
                                walkover(computerPlayer, "BadMove");
                                gameFinished = true;
                            } catch (TimeoutException exception) {
                                walkover(computerPlayer, "Timeout");
                                gameFinished = true;
                            }
                        }
                    }
                    if (computerPlayer == 2) {
                        if (movesStorage.isEmpty()) {
                            try {
                                move = Bricks.secondRobotPlayer.makeMove("ZACZYNAJ");
                            } catch (InvalidMoveException exception) {
                                walkover(computerPlayer, "BadMove");
                                gameFinished = true;
                            } catch (TimeoutException exception) {
                                walkover(computerPlayer, "Timeout");
                                gameFinished = true;
                            }
                        } else {
                            try {
                                move = Bricks.secondRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                            } catch (InvalidMoveException exception) {
                                walkover(computerPlayer, "BadMove");
                                gameFinished = true;
                            } catch (TimeoutException exception) {
                                walkover(computerPlayer, "Timeout");
                                gameFinished = true;
                            }
                        }
                    }

                    int x1 = move[0];
                    int y1 = move[1];
                    int x2 = move[2];
                    int y2 = move[3];


                    if (possibleMove(x1, y1, x2, y2) && !gameFinished) {
                        board.board[x1][y1] = computerPlayer;
                        board.board[x2][y2] = computerPlayer;
                        playSound();
                        drawFrame();
                        movesStorage.addMove(x1, y1, x2, y2);
                        if (computerPlayer == 1) {
                            computerPlayer = 2;

                        } else if (computerPlayer == 2) {
                            computerPlayer = 1;
                        }
                    } else if (!gameFinished) {
                        walkover(computerPlayer, "InvalidMove");
                    }
                    nextMoveButton.setDisable(false);
                    checkNoMoves();

                } catch (Exception ignored) {
                    nextMoveButton.setDisable(false);
                }
            });

            autoPlayButton = new Button("Automatyczna Gra");
            autoPlayButton.setMinWidth(110);
            gamemodeRobotsWarsHBox.getChildren().add(autoPlayButton);
            autoPlayButton.setOnAction(event -> {
                if (autoPlayButton.getText().equals("Automatyczna Gra")) {
                    controlAutoPlayButtons(false);
                    Bricks.autoPlayRunning = true;
                    cancelReason = 0;
                    isGameFinished = false;
                    autoGame = new Task<Void>() {
                        @Override
                        protected Void call() {
                            while (Bricks.autoPlayRunning) {
                                int computerPlayer1 = Bricks.mainStage.gamePane.computerPlayer;
                                int move[] = new int[4];
                                boolean isCanceled = false;
                                if (computerPlayer1 == 1) {
                                    if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                                        try {
                                            move = Bricks.firstRobotPlayer.makeMove("ZACZYNAJ");
                                        } catch (InvalidMoveException exception) {
                                            cancelReason = 1;
                                            cancel();
                                            isCanceled = true;
                                        } catch (TimeoutException exception) {
                                            cancelReason = 2;
                                            cancel();
                                            isCanceled = true;
                                        }
                                    } else {
                                        try {
                                            move = Bricks.firstRobotPlayer.makeMove(Bricks.mainStage.gamePane.movesStorage.getLastMoveAsString());
                                        } catch (InvalidMoveException exception) {
                                            cancelReason = 1;
                                            cancel();
                                            isCanceled = true;
                                        } catch (TimeoutException exception) {
                                            cancelReason = 2;
                                            cancel();
                                            isCanceled = true;
                                        }
                                    }
                                }
                                if (computerPlayer1 == 2) {
                                    if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                                        try {
                                            move = Bricks.secondRobotPlayer.makeMove("ZACZYNAJ");
                                        } catch (InvalidMoveException exception) {
                                            cancelReason = 1;
                                            cancel();
                                            isCanceled = true;
                                        } catch (TimeoutException exception) {
                                            cancelReason = 2;
                                            cancel();
                                            isCanceled = true;
                                        }
                                    } else {
                                        try {
                                            move = Bricks.secondRobotPlayer.makeMove(Bricks.mainStage.gamePane.movesStorage.getLastMoveAsString());
                                        } catch (InvalidMoveException exception) {
                                            cancelReason = 1;
                                            cancel();
                                            isCanceled = true;
                                        } catch (TimeoutException exception) {
                                            cancelReason = 2;
                                            cancel();
                                            isCanceled = true;
                                        }
                                    }
                                }

                                int x1 = move[0];
                                int y1 = move[1];
                                int x2 = move[2];
                                int y2 = move[3];
                                if (Bricks.mainStage.gamePane.possibleMove(x1, y1, x2, y2)) {
                                    Bricks.mainStage.gamePane.board.board[x1][y1] = computerPlayer1;
                                    Bricks.mainStage.gamePane.board.board[x2][y2] = computerPlayer1;
                                    Bricks.mainStage.gamePane.playSound();
                                    Bricks.mainStage.gamePane.drawFrame();
                                    Bricks.mainStage.gamePane.movesStorage.addMove(x1, y1, x2, y2);

                                    if (computerPlayer1 == 1) {
                                        computerPlayer1 = 2;

                                    } else if (computerPlayer1 == 2) {
                                        computerPlayer1 = 1;
                                    }
                                    Bricks.mainStage.gamePane.computerPlayer = computerPlayer1;
                                } else if(!isCanceled) {
                                    cancelReason = 1;
                                    cancel();
                                }
                                if (!Bricks.mainStage.gamePane.board.anyMoves()) {
                                    isGameFinished = true;
                                    break;
                                }
                                try {
                                    Thread.sleep(1000 / Integer.parseInt(speedTextField.getText()));
                                } catch (InterruptedException e) {
                                    cancel();
                                }
                            }
                            return null;
                        }

                    };
                    Thread autoGameThread = new Thread(autoGame);
                    autoGameThread.start();
                    autoGame.setOnCancelled(event1 -> {
                        System.out.println(cancelReason);
                        Optional<ButtonType> result;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Koniec Gry");
                        alert.setContentText("Co chcesz zrobić?");
                        ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
                        ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");
                        alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
                        if (cancelReason == 1) {
                            if (computerPlayer == 1) {
                                Bricks.firstRobotPlayer.sendEndingMessages(false);
                                Bricks.secondRobotPlayer.sendEndingMessages(true);
                                alert.setHeaderText("Komputer pierwszy wykonał błędny ruch, wygrał komputer drugi.");
                            }
                            if (computerPlayer == 2) {
                                Bricks.firstRobotPlayer.sendEndingMessages(true);
                                Bricks.secondRobotPlayer.sendEndingMessages(false);
                                alert.setHeaderText("Komputer drugi wykonał błędny ruch, wygrał komputer pierwszy.");
                            }
                        }
                        if (cancelReason == 2) {
                            if (computerPlayer == 1) {
                                Bricks.firstRobotPlayer.sendEndingMessages(false);
                                Bricks.secondRobotPlayer.sendEndingMessages(true);
                                alert.setHeaderText("Komputer pierwszy przekroczył czas ruchu, wygrał komputer drugi.");
                            }
                            if (computerPlayer == 2) {
                                Bricks.firstRobotPlayer.sendEndingMessages(true);
                                Bricks.secondRobotPlayer.sendEndingMessages(false);
                                alert.setHeaderText("Komputer drugi przekroczył czas ruchu, wygrał komputer pierwszy.");
                            }
                        }
                        if (cancelReason == 1 || cancelReason == 2) {
                            result = alert.showAndWait();
                            resetBoard();
                            controlAutoPlayButtons(true);
                            if (result.isPresent() && result.get() == buttonExitToMenu)
                                Bricks.mainStage.backToMenu();
                        }
                    });
                    autoGame.setOnSucceeded(event12 -> {
                        if (isGameFinished) {
                            Optional<ButtonType> result;
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                            alert.setTitle("Koniec Gry");
                            alert.setContentText("Co chcesz zrobić?");
                            ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
                            ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");

                            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
                            if (computerPlayer == 1) {
                                Bricks.firstRobotPlayer.sendEndingMessages(false);
                                Bricks.secondRobotPlayer.sendEndingMessages(true);
                                alert.setHeaderText("Koniec możliwych ruchów, wygrał program drugi.");
                            } else {
                                Bricks.firstRobotPlayer.sendEndingMessages(true);
                                Bricks.secondRobotPlayer.sendEndingMessages(false);
                                alert.setHeaderText("Koniec możliwych ruchów, wygrał program pierwszy.");
                            }
                            result = alert.showAndWait();
                            resetBoard();
                            controlAutoPlayButtons(true);
                            if (result.isPresent() && result.get() == buttonExitToMenu)
                                Bricks.mainStage.backToMenu();
                        }
                    });
                } else {
                    Bricks.autoPlayRunning = false;
                    controlAutoPlayButtons(true);
                }
            });
            gamemodeRobotsWarsHBox.setSpacing(10);
            gamemodeRobotsWarsHBox.setAlignment(Pos.CENTER);
            backToMenuButton = new Button("Powrót");
            gamemodeRobotsWarsHBox.getChildren().add(backToMenuButton);

            mainGridPane.add(gamemodeRobotsWarsHBox, 0, 1);
        }
        backToMenuButton.setTooltip(new Tooltip("Możesz też użyć klawisza \"Escape\""));
        backToMenuButton.setOnAction(event -> {
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
                resetBoard();
            }
        });
        setOnMouseClicked(e -> {
            if ((gamemode == 0 || gamemode == 1) && e.getY() < getHeight() * 0.95) {
                if (!isSelected) {
                    if ((e.getX() > marginX && e.getX() < getWidth() - marginX) && (e.getY() > marginY && e.getY() < getHeight() - marginY)) {

                        double intOneFieldWidth = (oneFieldWidth);
                        double intOneFieldHeight = (oneFieldHeight);

                        for (int i = 0; i < board.width - 1; i++) {
                            if (e.getX() >= (marginX + i * intOneFieldWidth) && e.getX() < marginX + (i + 1) * intOneFieldWidth) {
                                selectedX = i;
                                break;
                            }
                            if (e.getX() >= marginX + (board.width - 1) + intOneFieldWidth) {
                                selectedX = board.width - 1;
                            }
                        }

                        for (int i = 0; i < board.height - 1; i++) {
                            if (e.getY() >= (marginY + i * intOneFieldHeight) && e.getY() < marginY + (i + 1) * intOneFieldHeight) {
                                selectedY = i;
                                break;
                            }
                            if (e.getY() >= marginY + (board.height - 1) + intOneFieldHeight) {
                                selectedY = board.height - 1;
                            }
                        }

                        try {
                            if (board.board[selectedX][selectedY] == 0) {
                                isSelected = true;
                                directions = board.possibleDirections(selectedX, selectedY);
                            }
                        } catch (Exception ignored) {

                        }

                    }
                } else {
                    if ((e.getX() > marginX && e.getX() < getWidth() - marginX) && (e.getY() > marginY && e.getY() < getHeight() - marginY)) {
                        int tempSelectedX;
                        int tempSelectedY;
                        double intOneFieldWidth = (oneFieldWidth);
                        double intOneFieldHeight = (oneFieldHeight);
                        tempSelectedX = 0;
                        tempSelectedY = 0;

                        for (int i = 0; i < board.width - 1; i++) {
                            if (e.getX() >= (marginX + i * intOneFieldWidth) && e.getX() < marginX + (i + 1) * intOneFieldWidth) {
                                tempSelectedX = i;
                                break;
                            }
                            if (e.getX() >= marginX + (board.width - 1) + intOneFieldWidth) {
                                tempSelectedX = board.width - 1;
                            }
                        }

                        for (int i = 0; i < board.height - 1; i++) {
                            if (e.getY() >= (marginY + i * intOneFieldHeight) && e.getY() < marginY + (i + 1) * intOneFieldHeight) {
                                tempSelectedY = i;
                                break;
                            }
                            if (e.getY() >= marginY + (board.height - 1) + intOneFieldHeight) {
                                tempSelectedY = board.height - 1;
                            }
                        }
                        if ((tempSelectedX == selectedX + 1) && tempSelectedY == selectedY) {
                            if (directions[1]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                if (gamemode == 1) {
                                    undoMoveButton.setDisable(false);
                                }
                                drawFrame();
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType == 0) {
                                            comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType != 0) {
                                            singlePlayerComputerMakeMove();
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                    }
                                } else {
                                    actualPlayer = 1;
                                }
                                checkNoMoves();
                            }
                        } else if ((tempSelectedX == selectedX - 1) && tempSelectedY == selectedY) {
                            if (directions[3]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                if (gamemode == 1) {
                                    undoMoveButton.setDisable(false);
                                }
                                drawFrame();
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType == 0) {
                                            comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType != 0) {
                                            singlePlayerComputerMakeMove();
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                    }
                                } else {
                                    actualPlayer = 1;
                                }
                                checkNoMoves();
                            }
                        } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY + 1) {
                            if (directions[2]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                if (gamemode == 1) {
                                    undoMoveButton.setDisable(false);
                                }
                                drawFrame();
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType == 0) {
                                            comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType != 0) {
                                            singlePlayerComputerMakeMove();
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                    }
                                } else {
                                    actualPlayer = 1;
                                }
                                checkNoMoves();
                            }
                        } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY - 1) {
                            if (directions[0]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                if (gamemode == 1) {
                                    undoMoveButton.setDisable(false);
                                }
                                drawFrame();
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType == 0) {
                                            comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                        if (gametype == 0 && Bricks.mainStage.computerPlayerType != 0) {
                                            singlePlayerComputerMakeMove();
                                            actualPlayer = 1;
                                            drawFrame();
                                        }
                                    }
                                } else {
                                    actualPlayer = 1;
                                }
                                checkNoMoves();
                            }
                        } else {
                            isSelected = false;
                        }

                    } else
                        isSelected = false;
                }
            }
            drawFrame();
        });

        firstPlayerImage = new Image(MainStage.class.getResourceAsStream("resources/brick_grey.png"));
        secondPlayerImage = new Image(MainStage.class.getResourceAsStream("resources/brick_red.png"));
        normalBackground = new Image(MainStage.class.getResourceAsStream("resources/dirt.png"));
        grassBackground = new Image(MainStage.class.getResourceAsStream("resources/dirt_grass.png"));
        sandBackground = new Image(MainStage.class.getResourceAsStream("resources/dirt_sand.png"));
        snowBackground = new Image(MainStage.class.getResourceAsStream("resources/dirt_snow.png"));
        skyboxtop = new Image(MainStage.class.getResourceAsStream("resources/skybox_top.png"));
        skyboxsideclouds = new Image(MainStage.class.getResourceAsStream("resources/skybox_sideClouds.png"));
        skyboxsidehills = new Image(MainStage.class.getResourceAsStream("resources/skybox_sideHills.png"));

        widthProperty().addListener((observable, oldValue, newValue) -> drawFrame());
        heightProperty().addListener((observable, oldValue, newValue) -> {
            drawFrame();
            if (gamemode == 1) {
                undoMoveButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            }
            if (gamemode == 2) {
                speedUpButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
                speedDownButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
                nextMoveButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
                autoPlayButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
            }
            if (gamemode == 0 || gamemode == 1)
                backToMenuButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            if (gamemode == 2) {
                backToMenuButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
            }
        });
    }

    @SuppressWarnings ("ConstantConditions")
    void drawFrame() {
        try {
            int[] rozmiar = Bricks.mainStage.getSizeAsArray();
            int width = rozmiar[0];
            int height = (int) mainGridPane.getRowConstraints().get(0).getPercentHeight() * rozmiar[1] / 100;
            if (width > height) {
                double difference = (width - height);
                if (difference / 2.0 > 20)
                    marginX = difference / 2.0;
                else
                    marginX = 20;
            } else {
                marginX = 20;
            }
            if (height > width) {
                double difference = (height - width);
                if (difference / 2.0 > 20)
                    marginY = difference / 2.0;
                else
                    marginY = 20;
            } else
                marginY = 20;
            canvas.setHeight(height);
            canvas.setWidth(width);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);
            oneFieldWidth = (width - marginX * 2.0) / (board.width * 1.0);
            oneFieldHeight = (height - marginY * 2.0) / (board.height * 1.0);
            int inFieldMargin = 0;
            if (Bricks.mainStage.theme == 0) {
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                gc.strokeRect(marginX, marginY, width - marginX * 2, height - marginY * 2);
            }
            javafx.scene.paint.Color firstPlayerColor = Bricks.mainStage.firstPlayerColor;

            javafx.scene.paint.Color secondPlayerColor = Bricks.mainStage.secondPlayerColor;

            if (Bricks.mainStage.theme == 1) {

                for (int i = -10; i < board.height; i++) {
                    for (int j = -50; j <= board.width + 49; j++) {
                        if (i < board.height / 2)
                            gc.drawImage(skyboxtop, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin + 1);
                        else if (i == board.height / 2) {
                            if (randomBackground[j + 50] == 0)
                                gc.drawImage(skyboxsideclouds, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin + 1, (oneFieldHeight) - 2 * inFieldMargin + 1);
                            if (randomBackground[j + 50] == 1)
                                gc.drawImage(skyboxsidehills, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin + 1, (oneFieldHeight) - 2 * inFieldMargin + 1);
                        } else if (i == board.height / 2 + 1) {
                            if (boardStyle == 0)
                                gc.drawImage(grassBackground, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                            if (boardStyle == 1)
                                gc.drawImage(sandBackground, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                            if (boardStyle == 2)
                                gc.drawImage(snowBackground, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                        } else
                            gc.drawImage(normalBackground, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                    }
                }

                for (int i = 0; i < board.height; i++) {
                    for (int j = 0; j < board.width; j++) {
                        if (board.board[j][i] == 1) {
                            gc.drawImage(firstPlayerImage, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                        }
                        if (board.board[j][i] == 2) {
                            gc.drawImage(secondPlayerImage, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                        }
                    }
                }
                if (Bricks.mainStage.theme == 1) {
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(marginX, marginY, width - marginX * 2, height - marginY * 2);
                }
                if (actualPlayer == 1) {
                    if (isSelected) {
                        int j = selectedX;
                        int i = selectedY;
                        gc.drawImage(firstPlayerImage, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                    }
                }
                if (actualPlayer == 2) {
                    if (isSelected) {
                        int j = selectedX;
                        int i = selectedY;
                        gc.drawImage(secondPlayerImage, j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                    }
                }
                for (int i = 0; i < board.height; i++) {
                    if (i > 0) {
                        gc.setStroke(javafx.scene.paint.Color.BLACK);
                        gc.strokeLine(marginX, marginY + i * oneFieldHeight, width - marginX, marginY + i * oneFieldHeight);
                    }

                    for (int j = 0; j < board.width; j++) {
                        if (j > 0) {
                            gc.setStroke(javafx.scene.paint.Color.BLACK);
                            gc.strokeLine(marginX + j * oneFieldWidth, marginY, marginX + j * oneFieldWidth, height - marginY);
                        }
                    }
                }
            }
            if (Bricks.mainStage.theme == 0) {

                if (actualPlayer == 1) {
                    if (isSelected) {
                        gc.setFill(firstPlayerColor);
                        int j = selectedX;
                        int i = selectedY;
                        if (j != board.width - 1 && i != board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                        else if (j == board.width - 1 && i != board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), (oneFieldHeight) - 2 * inFieldMargin);
                        else if (j != board.width - 1 && i == board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, height - marginY * 2 - i * (oneFieldHeight));
                        else
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), height - marginY * 2 - i * (oneFieldHeight));
                    }
                }
                if (actualPlayer == 2) {
                    if (isSelected) {
                        gc.setFill(secondPlayerColor);
                        int j = selectedX;
                        int i = selectedY;
                        if (j != board.width - 1 && i != board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                        else if (j == board.width - 1 && i != board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), (oneFieldHeight) - 2 * inFieldMargin);
                        else if (j != board.width - 1 && i == board.height - 1)
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, height - marginY * 2 - i * (oneFieldHeight));
                        else
                            gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), height - marginY * 2 - i * (oneFieldHeight));
                    }
                }

                for (int i = 0; i < board.height; i++) {
                    for (int j = 0; j < board.width; j++) {
                        if (board.board[j][i] == 1) {
                            gc.setFill(firstPlayerColor);
                            if (j != board.width - 1 && i != board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                            else if (j == board.width - 1 && i != board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), (oneFieldHeight) - 2 * inFieldMargin);
                            else if (j != board.width - 1 && i == board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, height - marginY * 2 - i * (oneFieldHeight));
                            else
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), height - marginY * 2 - i * (oneFieldHeight));
                        }
                        if (board.board[j][i] == 2) {
                            gc.setFill(secondPlayerColor);
                            if (j != board.width - 1 && i != board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, (oneFieldHeight) - 2 * inFieldMargin);
                            else if (j == board.width - 1 && i != board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), (oneFieldHeight) - 2 * inFieldMargin);
                            else if (j != board.width - 1 && i == board.height - 1)
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, (oneFieldWidth) - 2 * inFieldMargin, height - marginY * 2 - i * (oneFieldHeight));
                            else
                                gc.fillRect(j * (oneFieldWidth) + marginX + inFieldMargin, i * (oneFieldHeight) + marginY + inFieldMargin, width - marginX * 2 - j * (oneFieldWidth), height - marginY * 2 - i * (oneFieldHeight));
                        }
                    }
                }
                for (int i = 0; i < board.height; i++) {
                    if (i > 0) {
                        gc.setStroke(javafx.scene.paint.Color.BLACK);
                        gc.strokeLine(marginX, marginY + i * oneFieldHeight, width - marginX, marginY + i * oneFieldHeight);
                    }

                    for (int j = 0; j < board.width; j++) {
                        if (j > 0) {
                            gc.setStroke(javafx.scene.paint.Color.BLACK);
                            gc.strokeLine(marginX + j * oneFieldWidth, marginY, marginX + j * oneFieldWidth, height - marginY);
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }//JESZCZE NIE ZOSTALA STWORZONA MAINGAMESCENE w MAINSTAGE, NIE PROBLEM

    }

    private Canvas canvas = new Canvas();
    private GridPane mainGridPane;

    private void playSound() {
        try {
            URL putBrickURL = this.getClass().getResource("resources/putbrick.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(putBrickURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(Bricks.mainStage.volume);
            if (Bricks.mainStage.isSound) {
                clip.start();
            }
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private boolean checkNoMoves() {
        if (!board.anyMoves()) {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");

            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            if (gamemode == 2) {
                if (computerPlayer == 1) {
                    Bricks.firstRobotPlayer.sendEndingMessages(false);
                    Bricks.secondRobotPlayer.sendEndingMessages(true);
                    alert.setHeaderText("Koniec możliwych ruchów, wygrał program drugi.");
                } else {
                    Bricks.firstRobotPlayer.sendEndingMessages(true);
                    Bricks.secondRobotPlayer.sendEndingMessages(false);
                    alert.setHeaderText("Koniec możliwych ruchów, wygrał program pierwszy.");
                }
                computerPlayer = 1;

            }
            if (gamemode == 1) {
                if (actualPlayer == 1) {
                    alert.setHeaderText("Koniec możliwych ruchów, wygrał gracz drugi.");
                } else {
                    alert.setHeaderText("Koniec możliwych ruchów, wygrał gracz pierwszy.");
                }
            }
            if (gamemode == 0) {
                if (actualPlayer == 1) {
                    alert.setHeaderText("Koniec możliwych ruchów, wygrał komputer.");
                } else {
                    alert.setHeaderText("Koniec możliwych ruchów, wygrałeś.");
                }
            }
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonPlayAgain) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainStage.backToMenu();
            }
            return true;
        }
        return false;
    }

    private void walkover(int computerPlayer, String reason) {
        Optional<ButtonType> result;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
        alert.setTitle("Koniec Gry");
        alert.setContentText("Co chcesz zrobić?");
        ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
        ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");

        alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);

        if (reason.equals("Timeout")) {
            System.out.println("Timeout");
            if (computerPlayer == 1) {
                Bricks.firstRobotPlayer.sendEndingMessages(false);
                Bricks.secondRobotPlayer.sendEndingMessages(true);
                alert.setHeaderText("Komputer numer 1 przekroczył czas na wykonanie ruchu, wygrał program drugi");
            } else {
                Bricks.firstRobotPlayer.sendEndingMessages(true);
                Bricks.secondRobotPlayer.sendEndingMessages(false);
                alert.setHeaderText("Komputer numer 2 przekroczył czas na wykonanie ruchu, wygrał program pierwszy");
            }
        } else {
            if (computerPlayer == 1) {
                Bricks.firstRobotPlayer.sendEndingMessages(false);
                Bricks.secondRobotPlayer.sendEndingMessages(true);
                alert.setHeaderText("Komputer numer 1 wykonał błędny ruch, wygrał program drugi");
            } else {
                Bricks.firstRobotPlayer.sendEndingMessages(true);
                Bricks.secondRobotPlayer.sendEndingMessages(false);
                alert.setHeaderText("Komputer numer 2 wykonał błędny ruch, wygrał program pierwszy");
            }
        }
        this.computerPlayer = 1;
        result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonPlayAgain) {
            resetBoard();
        } else {
            resetBoard();
            Bricks.mainStage.backToMenu();
        }
    }

    private void singlePlayerComputerMakeMove() {
        int[] move = new int[4];
        try {
            move = Bricks.singlePlayerRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
        } catch (InvalidMoveException exception) {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");
            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            alert.setHeaderText("Komputer wykonał niepoprawny ruch, wygrałałeś, chcesz zagrać jeszcze raz?");
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonPlayAgain) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainStage.backToMenu();
            }
        } catch (TimeoutException exception) {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");
            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            alert.setHeaderText("Komputer przekroczył czas na wykonanie ruchu, wygrałałeś, chcesz zagrać jeszcze raz?");
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonPlayAgain) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainStage.backToMenu();
            }
        }


        int x1 = move[0];
        int y1 = move[1];
        int x2 = move[2];
        int y2 = move[3];

        if (possibleMove(x1, y1, x2, y2)) {
            board.board[x1][y1] = 2;
            board.board[x2][y2] = 2;
            movesStorage.addMove(x1, y1, x2, y2);
        }
        else {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");
            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            alert.setHeaderText("Komputer wykonał niepoprawny ruch, wygrałeś!");
            result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonPlayAgain) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainStage.backToMenu();
            }
        }
    }

    void resetBoard() {
        board.reset();
        if (gamemode == 1)
            undoMoveButton.setDisable(true);
        isSelected = false;
        movesStorage.reset();
        computerPlayer = 1;
        if (gamemode == 2) {
            try {
                Bricks.autoPlayRunning = false;
                Bricks.firstRobotPlayer.reset();
                Bricks.secondRobotPlayer.reset();
            } catch (Exception ignored) {
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        if (gamemode == 0 && Bricks.mainStage.computerPlayerType != 0 && Bricks.singlePlayerRobotPlayer != null) {
            try {
                Bricks.singlePlayerRobotPlayer.reset();
            } catch (Exception ignored) {
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        actualPlayer = 1;
        drawFrame();
    }

    private boolean possibleMove(int x1, int y1, int x2, int y2) {
        if(x1<0 || x1>board.board.length)
            return false;
        if(y1<0 || y1>board.board.length)
            return false;
        if(x2<0 || x2>board.board.length)
            return false;
        if(y2<0 || y2>board.board.length)
        if (board.board[x1][y1] != 0 || board.board[x2][y2] != 0)
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

    private void controlAutoPlayButtons(boolean turnOn) {
        if (turnOn) {
            if (speedUpButton != null) {
                speedUpButton.setDisable(false);
                speedDownButton.setDisable(false);
                autoPlayButton.setText("Automatyczna Gra");
                nextMoveButton.setDisable(false);
                autoPlayButton.setDisable(false);
            }
        } else {
            if (speedUpButton != null) {
                speedUpButton.setDisable(true);
                speedDownButton.setDisable(true);
                autoPlayButton.setText("Przerwij");
                nextMoveButton.setDisable(true);
            }
        }
    }

    private Task<Void> autoGame;

    private BoardLogic board;

    private int actualPlayer = 1;
    private double marginX = 20;
    private double marginY = 20;
    private int selectedX;
    private int selectedY;
    private int computerPlayer;
    private int gamemode;
    private int cancelReason = 0;
    private int randomBackground[];
    private int boardStyle;

    private double oneFieldWidth;
    private double oneFieldHeight;

    private boolean[] directions;
    private boolean isSelected = false;
    private boolean isGameFinished = false;


    private MovesStorage movesStorage;

    private ComputerPlayer comp;

    private Button speedUpButton;
    private Button speedDownButton;
    private Button nextMoveButton;
    private Button autoPlayButton;
    private Button undoMoveButton;
    private Button backToMenuButton;

    private TextField speedTextField;

    private Image firstPlayerImage;
    private Image secondPlayerImage;
    private Image normalBackground;
    private Image grassBackground;
    private Image sandBackground;
    private Image snowBackground;
    private Image skyboxtop;
    private Image skyboxsideclouds;
    private Image skyboxsidehills;
}
