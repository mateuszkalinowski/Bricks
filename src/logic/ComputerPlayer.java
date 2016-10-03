package logic;

import gfx.BoardPanel;

import java.util.Random;

/**
 * Created by Mateusz on 03.10.2016.
 * Project InferenceEngine
 */
public class ComputerPlayer {
    public ComputerPlayer(){

    }
    public void performMove(Board board){
        while(true) {
            int x = (int) (Math.random() * (board.staticSize));
            int y = (int) (Math.random() * (board.staticSize));
            int direction = (int) (Math.random() * 4);
            boolean[] directions = board.possibleDirections(x, y);

            if(board.board[x][y]==0) {
                if(direction==0 && directions[0]) {
                    board.board[x][y]=2;
                    board.board[x][y-1]=2;
                    return;
                }
                if(direction==1 && directions[1]) {
                    board.board[x][y]=2;
                    board.board[x+1][y]=2;
                    return;
                }
                if(direction==2 && directions[2]) {
                    board.board[x][y]=2;
                    board.board[x][y+1]=2;
                    return;
                }
                if(direction==3 && directions[3]) {
                    board.board[x][y]=2;
                    board.board[x-1][y]=2;
                    return;
                }
            }

        }
    }
}
