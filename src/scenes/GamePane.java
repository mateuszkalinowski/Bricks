package scenes;

import core.Bricks;
import exceptions.InvalidMoveException;
import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.MovesStorage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
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

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (gametype == 0 || gametype == 1) {
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
                                   /* if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                        Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() == 0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() != 0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer = 1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }*/
                                    if(gamemode==1) {
                                        if(actualPlayer==1)
                                            actualPlayer=2;
                                        else if(actualPlayer==2)
                                            actualPlayer=1;
                                    }
                                    isSelected=false;
                                    checkNoMoves();
                                }
                            } else if ((tempSelectedX == selectedX - 1) && tempSelectedY == selectedY) {
                                if (directions[3]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                   /* if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                        Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() == 0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() != 0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer = 1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }*/
                                    if(gamemode==1) {
                                        if(actualPlayer==1)
                                            actualPlayer=2;
                                        else if(actualPlayer==2)
                                            actualPlayer=1;
                                    }
                                    isSelected=false;
                                    checkNoMoves();

                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY + 1) {
                                if (directions[2]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                   /* if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                        Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() == 0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() != 0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer = 1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }*/
                                    if(gamemode==1) {
                                        if(actualPlayer==1)
                                            actualPlayer=2;
                                        else if(actualPlayer==2)
                                            actualPlayer=1;
                                    }
                                    isSelected=false;
                                    checkNoMoves();

                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY - 1) {
                                if (directions[0]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                   /* if (Bricks.mainFrame.getComputerPlayerType() == 0)
                                        Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() == 0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType() != 0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer = 1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }*/
                                    if(gamemode==1) {
                                        if(actualPlayer==1)
                                            actualPlayer=2;
                                        if(actualPlayer==2)
                                            actualPlayer=1;
                                    }
                                    isSelected=false;
                                    checkNoMoves();

                                }
                            } else {
                                isSelected = false;
                            }

                        } else
                            isSelected = false;
                    }
                }
            }
        });


        
        
    }
    public void drawFrame(int w,int h){
        int width = w;
        int height = (int)mainGridPane.getRowConstraints().get(0).getPercentHeight()*h/100;
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
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, (int) (oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), (int) (oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) (oneFieldHeight));
                else
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), height - margin * 2 - i * (int) (oneFieldHeight));
            }
        }
        if (actualPlayer == 2) {
            if (isSelected) {
                gc.setFill(secondPlayerColor);
                int j = selectedX;
                int i = selectedY;
                if (j != board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, (int) (oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), (int) (oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) (oneFieldHeight));
                else
                    gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), height - margin * 2 - i * (int) (oneFieldHeight));
            }
        }

        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                if (board.board[j][i] == 1) {
                    gc.setFill(firstPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, (int) (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), (int) (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) (oneFieldHeight));
                    else
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), height - margin * 2 - i * (int) (oneFieldHeight));
                }
                if (board.board[j][i] == 2) {
                    gc.setFill(secondPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, (int) (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), (int) (oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, (int) (oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) (oneFieldHeight));
                    else
                        gc.fillRect(j * (int) (oneFieldWidth) + margin + inFieldMargin, i * (int) (oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) (oneFieldWidth), height - margin * 2 - i * (int) (oneFieldHeight));
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
            volumeControl.setValue(Bricks.mainFrame.volume);
            if (Bricks.mainFrame.isSound) {
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
                /*if (gamemode == 2) {
                    if (Bricks.mainFrame.computerPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał program drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał program pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                    Bricks.mainFrame.computerPlayer = 1;

                }*/
                if (gamemode == 1) {
                    if (actualPlayer == 1) {
                        alert.setHeaderText("Koniec możliwych ruchów, wygrał gracz drugi.");
                    } else {
                        alert.setHeaderText("Koniec możliwych ruchów, wygrał gracz pierwszy.");
                    }
                }
                /*if (gamemode == 0) {
                    if (actualPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał komputer, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                }*/
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
    private void singlePlayerComputerMakeMove() {
        int[] move = new int[4];
        try {
            move = Bricks.singlePlayerRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
        } catch (InvalidMoveException exception) {
            int selection = JOptionPane.showConfirmDialog(null, "Komputer wykonał niepoprawny ruch, wygrałałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                    " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            Bricks.mainFrame.computerPlayer = 1;
            if (selection == JOptionPane.OK_OPTION) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainFrame.stopGame();
            }
        } catch (TimeoutException exception) {
            int selection = JOptionPane.showConfirmDialog(null, "Komputer przekroczył czas na wykonanie ruchu, wygrałałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                    " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            Bricks.mainFrame.computerPlayer = 1;
            if (selection == JOptionPane.OK_OPTION) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainFrame.stopGame();
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
                Bricks.firstRobotPlayer.reset();
                Bricks.secondRobotPlayer.reset();
            } catch (Exception ignored) {
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        if (gamemode == 0 && Bricks.mainFrame.getComputerPlayerType() != 0 && Bricks.singlePlayerRobotPlayer != null) {
            try {
                Bricks.singlePlayerRobotPlayer.reset();
            } catch (Exception ignored) {
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        actualPlayer = 1;
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
    private BoardLogic board;

    private int actualPlayer = 1;

    private int margin = 20;

    private double oneFieldWidth;
    private double oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;


    public MovesStorage movesStorage;
    private int gamemode;


}
