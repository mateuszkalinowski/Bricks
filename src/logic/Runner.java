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
            int computerPlayer = Bricks.mainStage.gamePane.computerPlayer;
            //Bricks.mainFrame.computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
            int move[] = new int[4];
            System.out.println(computerPlayer);
            if (computerPlayer == 1) {
                if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                    try {
                        move = Bricks.firstRobotPlayer.makeMove("Zaczynaj");
                    } catch (InvalidMoveException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "Timeout");
                        break;
                    }
                } else {
                    try {
                        move = Bricks.firstRobotPlayer.makeMove(Bricks.mainFrame.boardPanel.movesStorage.getLastMoveAsString());
                    } catch (InvalidMoveException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "Timeout");
                        break;
                    }
                }
            }
            if (computerPlayer == 2) {
                if (Bricks.mainStage.gamePane.movesStorage.isEmpty()) {
                    try {
                        move = Bricks.secondRobotPlayer.makeMove("Zaczynaj");
                    } catch (InvalidMoveException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "Timeout");
                        break;
                    }
                } else {
                    try {
                        move = Bricks.secondRobotPlayer.makeMove(Bricks.mainFrame.boardPanel.movesStorage.getLastMoveAsString());
                    } catch (InvalidMoveException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "BadMove");
                        break;
                    } catch (TimeoutException exception) {
                        Bricks.mainStage.gamePane.walkover(computerPlayer, "Timeout");
                        break;
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
                    //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);

                } else if (computerPlayer == 2) {
                    computerPlayer = 1;
                    //Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                }
                Bricks.mainStage.computerPlayer = computerPlayer;
                //Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
            } else {
               // Bricks.mainStage.gamePane.walkover(computerPlayer, "InvalidMove");
            }
            //Bricks.mainFrame.repaintThis();
            //Bricks.mainFrame.repaint();
            Bricks.mainStage.gamePane.checkNoMoves();
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
