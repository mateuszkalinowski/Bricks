package logic;

import core.Bricks;
import exceptions.InvalidMoveException;

import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 10.12.2016.
 * Project Bricks
 */
public class Runner extends Thread {
    @Override
    public void run() {
        while (Bricks.autoPlayRunning) {
            int computerPlayer = Bricks.mainFrame.computerPlayer;
            Bricks.mainFrame.computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
            int move[] = new int[4];
            if (computerPlayer == 1) {
                if (Bricks.mainFrame.boardPanel.movesStorage.isEmpty()) {
                    try {
                        move = Bricks.firstRobotPlayer.makeMove("Zaczynaj");
                    } catch (InvalidMoveException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "Timeout");
                        break;
                    }
                } else {
                    try {
                        move = Bricks.firstRobotPlayer.makeMove(Bricks.mainFrame.boardPanel.movesStorage.getLastMoveAsString());
                    } catch (InvalidMoveException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "Timeout");
                        break;
                    }
                }
            }
            if (computerPlayer == 2) {
                if (Bricks.mainFrame.boardPanel.movesStorage.isEmpty()) {
                    try {
                        move = Bricks.secondRobotPlayer.makeMove("Zaczynaj");
                    } catch (InvalidMoveException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "Timeout");
                        break;
                    }
                } else {
                    try {
                        move = Bricks.secondRobotPlayer.makeMove(Bricks.mainFrame.boardPanel.movesStorage.getLastMoveAsString());
                    } catch (InvalidMoveException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainFrame.boardPanel.walkover(computerPlayer, "Timeout");
                        break;
                    }
                }
            }

            int x1 = move[0];
            int y1 = move[1];
            int x2 = move[2];
            int y2 = move[3];


            if (Bricks.mainFrame.boardPanel.possibleMove(x1, y1, x2, y2)) {
                Bricks.mainFrame.board.board[x1][y1] = computerPlayer;
                Bricks.mainFrame.board.board[x2][y2] = computerPlayer;
                Bricks.mainFrame.boardPanel.playSound();
                Bricks.mainFrame.boardPanel.movesStorage.addMove(x1, y1, x2, y2);


                if (computerPlayer == 1) {
                    computerPlayer = 2;
                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);

                } else if (computerPlayer == 2) {
                    computerPlayer = 1;
                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                }
                Bricks.mainFrame.computerPlayer = computerPlayer;
                Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
            } else {
                Bricks.mainFrame.boardPanel.walkover(computerPlayer, "InvalidMove");
            }
            Bricks.mainFrame.repaintThis();
            Bricks.mainFrame.repaint();
            Bricks.mainFrame.boardPanel.checkNoMoves();
            try {
                Thread.sleep(1000 / speed);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Runner(int speed) {
        this.speed = speed;
    }

    private int speed;
}
