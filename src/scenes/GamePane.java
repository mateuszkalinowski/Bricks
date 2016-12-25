package scenes;

import core.Bricks;
import exceptions.InvalidMoveException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class GamePane extends Pane {
    public GamePane(BoardLogic board, int gametype){
        this.board = board;
        this.gamemode = gametype;
        stop = new Button("Stop");
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
        mainGridPane.add(canvas,0,0);

        getChildren().add(mainGridPane);

        movesStorage = new MovesStorage();
        movesStorage.reset();

        if(gamemode==0 && Bricks.mainStage.computerPlayerType==0)
            comp = new ComputerPlayer();

        if(gamemode==2) {
            HBox gamemodeRobotsWarsHBox = new HBox();
            javafx.scene.control.Label speedLabel = new javafx.scene.control.Label("Prędkość:");
            speedLabel.setFont(javafx.scene.text.Font.font("Comic Sans MS",12));
            //gamemodeRobotsWarsHBox.getChildren().add(speedLabel);

            speedDownButton = new Button("-");
            gamemodeRobotsWarsHBox.getChildren().add(speedDownButton);
            speedDownButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int i = Integer.parseInt(speedTextField.getText());
                    if(i>1) {
                        i--;
                        speedTextField.setText(i+"");
                    }
                }
            });

            autoGameSpeed = 5;
            speedTextField = new TextField(autoGameSpeed + "");
            speedTextField.setMaxWidth(32);
            speedTextField.setMinWidth(32);
            speedTextField.setEditable(false);
            gamemodeRobotsWarsHBox.getChildren().add(speedTextField);

            speedUpButton = new Button("+");
            gamemodeRobotsWarsHBox.getChildren().add(speedUpButton);
            speedUpButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int i = Integer.parseInt(speedTextField.getText());
                    if(i<20) {
                        i++;
                        speedTextField.setText(i+"");
                    }
                }
            });
            computerPlayer=1;
            nextMoveButton = new Button("Następny Ruch");
            gamemodeRobotsWarsHBox.getChildren().add(nextMoveButton);
            nextMoveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
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
                }
            });

            autoPlayButton = new Button("Automatyczna Gra");
            autoPlayButton.setMinWidth(150);
            gamemodeRobotsWarsHBox.getChildren().add(autoPlayButton);
            autoPlayButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(autoPlayButton.getText().equals("Automatyczna Gra")){
                        controlAutoPlayButtons(false);
                        Bricks.autoPlayRunning=true;
                        cancelReason=0;
                        isGameFinished=false;
                        autoGame = new Task<Void>() {
                            @Override
                            protected Void call() {
                                while (Bricks.autoPlayRunning) {
                                    int computerPlayer = Bricks.mainStage.gamePane.computerPlayer;
                                    //Bricks.mainFrame.computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
                                    int move[] = new int[4];
                                    if (computerPlayer == 1) {
                                        if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                                            try {
                                                move = Bricks.firstRobotPlayer.makeMove("ZACZYNAJ");
                                            } catch (InvalidMoveException exception) {
                                                cancelReason=1;
                                                cancel();
                                            } catch (TimeoutException exception) {
                                                cancelReason=2;
                                                cancel();
                                            }
                                        } else {
                                            try {
                                                move = Bricks.firstRobotPlayer.makeMove(Bricks.mainStage.gamePane.movesStorage.getLastMoveAsString());
                                            } catch (InvalidMoveException exception) {
                                                cancelReason=1;
                                                cancel();
                                            } catch (TimeoutException exception) {
                                                cancelReason=2;
                                                cancel();
                                            }
                                        }
                                    }
                                    if (computerPlayer == 2) {
                                        if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                                            try {
                                                move = Bricks.secondRobotPlayer.makeMove("ZACZYNAJ");
                                            } catch (InvalidMoveException exception) {
                                                cancelReason=1;
                                                cancel();
                                            } catch (TimeoutException exception) {
                                                cancelReason=2;
                                                cancel();
                                            }
                                        } else {
                                            try {
                                                move = Bricks.secondRobotPlayer.makeMove(Bricks.mainStage.gamePane.movesStorage.getLastMoveAsString());
                                            } catch (InvalidMoveException exception) {
                                                cancelReason=1;
                                                cancel();
                                            } catch (TimeoutException exception) {
                                                cancelReason=2;
                                                cancel();
                                            }
                                        }
                                    }

                                    int x1 = move[0];
                                    int y1 = move[1];
                                    int x2 = move[2];
                                    int y2 = move[3];
                                    if (Bricks.mainStage.gamePane.possibleMove(x1, y1, x2, y2)) {
                                        Bricks.mainStage.gamePane.board.board[x1][y1] = computerPlayer;
                                        Bricks.mainStage.gamePane.board.board[x2][y2] = computerPlayer;
                                        Bricks.mainStage.gamePane.playSound();
                                        Bricks.mainStage.gamePane.drawFrame();
                                        Bricks.mainStage.gamePane.movesStorage.addMove(x1, y1, x2, y2);


                                        if (computerPlayer == 1) {
                                            computerPlayer = 2;

                                        } else if (computerPlayer == 2) {
                                            computerPlayer = 1;
                                        }
                                        Bricks.mainStage.gamePane.computerPlayer = computerPlayer;
                                    } else {
                                        cancelReason=1;
                                        cancel();
                                    }
                                    if(!Bricks.mainStage.gamePane.board.anyMoves()) {
                                        isGameFinished=true;
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
                        autoGame.setOnCancelled(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent event) {
                                Optional<ButtonType> result;
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Koniec Gry");
                                alert.setContentText("Co chcesz zrobić?");
                                ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
                                ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");
                                alert.getButtonTypes().setAll(buttonPlayAgain,buttonExitToMenu);
                                if(cancelReason==1) {
                                    if(computerPlayer==1)
                                        alert.setHeaderText("Komputer pierwszy wykonał błędny ruch, wygrał komputer drugi.");
                                    if(computerPlayer==2)
                                        alert.setHeaderText("Komputer drugi wykonał błędny ruch, wygrał komputer pierwszy.");
                                }
                                if(cancelReason==2) {
                                    if(computerPlayer==1)
                                        alert.setHeaderText("Komputer pierwszy przekroczył czas ruchu, wygrał komputer drugi.");
                                    if(computerPlayer==2)
                                        alert.setHeaderText("Komputer drugi przekroczył czas ruchu, wygrał komputer pierwszy.");
                                }
                                if(cancelReason==1 || cancelReason==2) {
                                    result = alert.showAndWait();
                                    resetBoard();
                                    controlAutoPlayButtons(true);
                                    if (result.get() == buttonExitToMenu)
                                        Bricks.mainStage.backToMenu();
                                }
                            }
                        });
                        autoGame.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent event) {
                                if(isGameFinished) {
                                    Optional<ButtonType> result;
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Koniec Gry");
                                    alert.setContentText("Co chcesz zrobić?");
                                    ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
                                    ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");

                                    alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
                                    if (computerPlayer == 1)
                                        alert.setHeaderText("Wygrał komputer drugi");
                                    else
                                        alert.setHeaderText("Wygrał komputer drugi");
                                    result = alert.showAndWait();
                                    resetBoard();
                                    controlAutoPlayButtons(true);
                                    if (result.get() == buttonExitToMenu)
                                        Bricks.mainStage.backToMenu();
                                }
                            }
                        });
                    }
                    else {
                        Bricks.autoPlayRunning=false;
                        controlAutoPlayButtons(true);
                    }
                }
            });

            gamesButton = new Button("Rozgrywki");
            gamemodeRobotsWarsHBox.getChildren().add(gamesButton);

            gamemodeRobotsWarsHBox.setSpacing(10);
            gamemodeRobotsWarsHBox.setAlignment(Pos.CENTER);
            mainGridPane.add(gamemodeRobotsWarsHBox,0,1);
        }

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (gamemode == 0 || gamemode == 1) {
                    if (!isSelected) {
                        if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {

                            double intOneFieldWidth = (oneFieldWidth);
                            double intOneFieldHeight = (oneFieldHeight);

                            for (int i = 0; i < board.width - 1; i++) {
                                if (e.getX() >= (margin + i * intOneFieldWidth) && e.getX() < margin + (i + 1) * intOneFieldWidth) {
                                    selectedX = i;
                                    break;
                                }
                                if (e.getX() >= margin + (board.width - 1) + intOneFieldWidth) {
                                    selectedX = board.width - 1;
                                }
                            }

                            for (int i = 0; i < board.height - 1; i++) {
                                if (e.getY() >= (margin + i * intOneFieldHeight) && e.getY() < margin + (i + 1) * intOneFieldHeight) {
                                    selectedY = i;
                                    break;
                                }
                                if (e.getY() >= margin + (board.height - 1) + intOneFieldHeight) {
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
                        if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                            int tempSelectedX;
                            int tempSelectedY;
                            double intOneFieldWidth = (oneFieldWidth);
                            double intOneFieldHeight = (oneFieldHeight);
                            tempSelectedX = 0;
                            tempSelectedY = 0;

                            for (int i = 0; i < board.width - 1; i++) {
                                if (e.getX() >= (margin + i * intOneFieldWidth) && e.getX() < margin + (i + 1) * intOneFieldWidth) {
                                    tempSelectedX = i;
                                    break;
                                }
                                if (e.getX() >= margin + (board.width - 1) + intOneFieldWidth) {
                                    tempSelectedX = board.width - 1;
                                }
                            }

                            for (int i = 0; i < board.height - 1; i++) {
                                if (e.getY() >= (margin + i * intOneFieldHeight) && e.getY() < margin + (i + 1) * intOneFieldHeight) {
                                    tempSelectedY = i;
                                    break;
                                }
                                if (e.getY() >= margin + (board.height - 1) + intOneFieldHeight) {
                                    tempSelectedY = board.height - 1;
                                }
                            }
                            if ((tempSelectedX == selectedX + 1) && tempSelectedY == selectedY) {
                                if (directions[1]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    drawFrame();
                                    //if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                    //    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        //Bricks.mainFrame.repaint();
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
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        //Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();
                                }
                            } else if ((tempSelectedX == selectedX - 1) && tempSelectedY == selectedY) {
                                if (directions[3]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    drawFrame();
                                    //if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                    //    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        //Bricks.mainFrame.repaint();
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
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        //Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();
                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY + 1) {
                                if (directions[2]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    drawFrame();
                                    //if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                    //    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        //Bricks.mainFrame.repaint();
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
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        //Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();
                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY - 1) {
                                if (directions[0]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    drawFrame();
                                    //if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                    //    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        //Bricks.mainFrame.repaint();
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
                                        //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        //Bricks.mainFrame.repaint();
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
            }
        });
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawFrame();
            }
        });
        heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawFrame();
            }
        });
    }
    public void drawFrame(){
        try {
            int[] rozmiar = Bricks.mainStage.getSizeAsArray();
        int width = rozmiar[0];
        int height = (int)mainGridPane.getRowConstraints().get(0).getPercentHeight()*rozmiar[1]/100;
        //canvas = new Canvas();
        canvas.setHeight(height);
        canvas.setWidth(width);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,width,height);
        oneFieldWidth = (width - margin * 2.0) / (board.width * 1.0);
        oneFieldHeight = (height - margin * 2.0) / (board.height * 1.0);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(margin, margin, width - margin * 2, height - margin * 2);
        int inFieldMargin = 0;
        
        javafx.scene.paint.Color firstPlayerColor = Bricks.mainStage.firstPlayerColor;
        
        javafx.scene.paint.Color secondPlayerColor = Bricks.mainStage.secondPlayerColor;

        if (actualPlayer == 1) {
            if (isSelected) {
                gc.setFill(firstPlayerColor);
                int j = selectedX;
                int i = selectedY;
                if (j != board.width - 1 && i != board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin,  (oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth),  (oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i *  (oneFieldHeight));
                else
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth), height - margin * 2 - i *  (oneFieldHeight));
            }
        }
        if (actualPlayer == 2) {
            if (isSelected) {
                gc.setFill(secondPlayerColor);
                int j = selectedX;
                int i = selectedY;
                if (j != board.width - 1 && i != board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin,  (oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth),  (oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i *  (oneFieldHeight));
                else
                    gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth), height - margin * 2 - i *  (oneFieldHeight));
            }
        }

        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                if (board.board[j][i] == 1) {
                    gc.setFill(firstPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin,  (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth),  (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i *  (oneFieldHeight));
                    else
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth), height - margin * 2 - i *  (oneFieldHeight));
                }
                if (board.board[j][i] == 2) {
                    gc.setFill(secondPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin,  (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth),  (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin,  (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i *  (oneFieldHeight));
                    else
                        gc.fillRect(j *  (oneFieldWidth) + margin + inFieldMargin, i *  (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j *  (oneFieldWidth), height - margin * 2 - i *  (oneFieldHeight));
                }
            }
        }
        for (int i = 0; i < board.height; i++) {
            if (i > 0) {
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeLine(margin, margin + i * oneFieldHeight, width - margin, margin + i * oneFieldHeight);
            }

            for (int j = 0; j < board.width; j++) {
                if (j > 0) {
                    gc.setStroke(javafx.scene.paint.Color.BLACK);
                    gc.strokeLine(margin + j * oneFieldWidth, margin, margin + j * oneFieldWidth, height - margin);
                }
            }
        }
        }
        catch (NullPointerException ignored) {}//JESZCZE NIE ZOSTALA STWORZONA MAINGAMESCENE w MAINSTAGE, NIE PROBLEM


    }
    Button stop;
    Canvas canvas = new Canvas();
    GridPane mainGridPane;

    public void playSound() {
        try {
            URL putBrickURL = this.getClass().getResource("putbrick.wav");
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

    public boolean checkNoMoves() {
        if (!board.anyMoves()) {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");

            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            if (gamemode == 2) {
                    if (computerPlayer == 1) {
                        alert.setHeaderText("Koniec możliwych ruchów, wygrał program drugi.");
                    } else {
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
                if (result.get()==buttonPlayAgain) {
                    resetBoard();
                } else {
                    resetBoard();
                    Bricks.mainStage.backToMenu();
                }
                return true;
            }
        return false;
    }

    public void walkover(int computerPlayer, String reason) {
        //int selection;
        Optional<ButtonType> result;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Koniec Gry");
        alert.setContentText("Co chcesz zrobić?");
        ButtonType buttonPlayAgain = new ButtonType("Kolejna Gra");
        ButtonType buttonExitToMenu = new ButtonType("Powrót do Menu");

        alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);

        if (reason.equals("Timeout")) {
            System.out.println("Timeout");
            if (computerPlayer == 1) {
                alert.setHeaderText("Komputer numer 1 przekroczył czas na wykonanie ruchu, wygrał program drugi");
            } else {
                alert.setHeaderText("Komputer numer 2 przekroczył czas na wykonanie ruchu, wygrał program pierwszy");
            }
        } else {
            if (computerPlayer == 1) {
                alert.setHeaderText("Komputer numer 1 wykonał błędny ruch, wygrał program drugi");
            } else {
                alert.setHeaderText("Komputer numer 2 wykonał błędny ruch, wygrał program pierwszy");
            }
        }
        this.computerPlayer = 1;
        result = alert.showAndWait();
        if (result.get()==buttonPlayAgain) {
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
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");
            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            alert.setHeaderText("Komputer wykonał niepoprawny ruch, wygrałałeś, chcesz zagrać jeszcze raz?");
            result = alert.showAndWait();
            if (result.get()==buttonPlayAgain) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainStage.backToMenu();
            }
        } catch (TimeoutException exception) {
            Optional<ButtonType> result;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Koniec Gry");
            alert.setContentText("Co chcesz zrobić?");

            ButtonType buttonPlayAgain = new ButtonType("Zagraj jeszcze raz");
            ButtonType buttonExitToMenu = new ButtonType("Wróć do menu");
            alert.getButtonTypes().setAll(buttonPlayAgain, buttonExitToMenu);
            alert.setHeaderText("Komputer przekroczył czas na wykonanie ruchu, wygrałałeś, chcesz zagrać jeszcze raz?");
            result = alert.showAndWait();
            if (result.get()==buttonPlayAgain) {
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
    }

    public void resetBoard() {
        board.reset();
        isSelected = false;
        movesStorage.reset();
        if (gamemode == 2) {
            try {
                Bricks.autoPlayRunning=false;
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
    public boolean possibleMove(int x1, int y1, int x2, int y2) {
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
                gamesButton.setDisable(false);
            }
        } else {
            if (speedUpButton != null) {
                speedUpButton.setDisable(true);
                speedDownButton.setDisable(true);
                autoPlayButton.setText("Przerwij");
                nextMoveButton.setDisable(true);
                gamesButton.setDisable(true);
            }
        }
    }

    public BoardLogic board;

    private int actualPlayer = 1;

    private int margin = 20;

    private double oneFieldWidth;
    private double oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;

    public int computerPlayer;


    public MovesStorage movesStorage;
    private int gamemode;

    private ComputerPlayer comp;

    Button speedUpButton;
    Button speedDownButton;
    Button nextMoveButton;
    Button autoPlayButton;
    Button gamesButton;
    TextField speedTextField;

    private int autoGameSpeed;

    public Runner runner;

    Task<Void> autoGame;

    boolean isGameFinished = false;
    int cancelReason = 0;

}
