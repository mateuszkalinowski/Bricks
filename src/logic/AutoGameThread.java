package logic;

import core.Bricks;

import java.util.ArrayList;

/**
 * Created by Mateusz on 14.12.2016.
 * Project Bricks
 */
public class AutoGameThread extends Thread {
    @Override
    public void run(){
        int firstPlayerWins = 0;
        int secondPlayerWins = 0;
        int counter = 0;
        for (Integer boardsSize : boardsSizes) {
            counter++;
            BoardLogic board = new BoardLogic(boardsSize);
            MovesStorage movesStorage = new MovesStorage();
            try {
                Bricks.firstRobotPlayer.reset();
                Bricks.secondRobotPlayer.reset();
            }
            catch (Exception ignored) {
                break;
            }
            int player = 1;
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ignored){}
            while (true) {
                int[] move = new int[4];
                if (movesStorage.isEmpty()) {
                    try {
                        move = Bricks.firstRobotPlayer.makeMove("Zaczynaj");
                    } catch (Exception badMove) {
                        secondPlayerWins++;
                        break;
                    }
                }
                else {
                    if(player==1) {
                        try {
                            move = Bricks.firstRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                        } catch (Exception badMove) {
                            secondPlayerWins++;
                            break;
                        }
                    }
                    if(player==2) {
                        try {
                            move = Bricks.secondRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                        } catch (Exception badMove) {
                            firstPlayerWins++;
                            break;
                        }
                    }
                }

                int x1 = move[0];
                int y1 = move[1];
                int x2 = move[2];
                int y2 = move[3];

                if(Bricks.mainFrame.possibleMove(x1,y1,x2,y2,board.board)) {
                    board.board[x1][y1]=player;
                    board.board[x2][y2]=player;
                }
                if(!board.anyMoves()) {
                    if(player==1) {
                        firstPlayerWins++;
                        break;
                    }
                    else {
                        secondPlayerWins++;
                        break;
                    }
                }
                else {
                    if (player == 1) {
                        player = 2;
                    } else {
                        player = 1;
                    }
                }
            }
            double progress = ((counter*1.0)/(boardsSizes.size()*1.0))*100;
            Bricks.mainFrame.autoPlayFrame.setProgress((int)Math.round(progress));
        }
        Bricks.mainFrame.autoPlayFrame.setWinCounts(firstPlayerWins + "",secondPlayerWins+"");
    }
    public AutoGameThread(ArrayList<Integer> boardsSizes){
        this.boardsSizes = boardsSizes;
    }
    private ArrayList<Integer> boardsSizes;
}
