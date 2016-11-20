package logic;

import core.Bricks;
import frames.MainFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class BoardLogic {
    public BoardLogic(int size) {
        this.width = size;
        this.height = size;
        this.size = size * size;
        staticSize = size;
        board = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                board[i][j] = 0;
        diagonallyMoves = false;
        boardType = "Kwadrat";
    }

    public void reset() {
                board = new int[staticSize][staticSize];
                for (int i = 0; i < staticSize; i++)
                    for (int j = 0; j < staticSize; j++)
                        board[i][j] = 0;
                diagonallyMoves = false;
    }

    public void saveToFile(){
        try {
            PrintWriter createBoardFile = new PrintWriter(new File( System.getProperty("user.home") + "/Documents/Bricks/board.txt"));
            createBoardFile.println(this.width);
            for(int i = 0; i < height;i++) {
                String line = "";
                for(int j = 0; j < width;j++) {
                    line+=board[j][i]+" ";
                }
                createBoardFile.println(line);
            }
            createBoardFile.close();
        }
        catch (Exception e) {
            System.out.println("Nie działa");
        }
    }

    public boolean[] possibleDirections(int x, int y) {
        boolean result[] = new boolean[4];
        result[0] = false;
        result[1] = false;
        result[2] = false;
        result[3] = false;
        try {
            if (board[x][y-1] == 0) result[0] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[0] = false;
        }

        try {
            if (board[x+1][y] == 0) result[1] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[1] = false;
        }

        try {
            if (board[x][y+1] == 0) result[2] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[2] = false;
        }

        try {
            if (board[x-1][y] == 0) result[3] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[3] = false;
        }

        return result;
    }

    public boolean anyMoves() {
        for(int i = 0; i < staticSize-1;i++) {
            for(int j = 0; j < staticSize;j++) {
                if((board[i][j]==0) && (board[i+1][j]==0))
                    return true;
            }
        }
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize-1;j++) {
                if((board[i][j]==0) && (board[i][j+1]==0))
                    return true;
            }
        }

        return false;
    }
    public  int getPossibleMovesLeft2() {
        int moves = 0;
        for(int i = 0; i < staticSize-1;i++) {
            for(int j = 0; j <  staticSize;j++) {
                if((board[i][j]==0) && (board[i+1][j]==0))
                    moves++;
            }
        }
        for(int i = 0; i <  staticSize;i++) {
            for(int j = 0; j <  staticSize-1;j++) {
                if((board[i][j]==0) && (board[i][j+1]==0))
                    moves++;
            }
        }
        return moves;
    }
    public  int getPossibleMovesLeft() {
        int maxmoves = 0;
        int temp = 0;
        int[][] tempBoard = new int[staticSize][staticSize];
        //LEWY GORNY
        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int j = 0; j < staticSize;j++) {
            for(int i = 0; i < staticSize-1;i++) {
                if((tempBoard[i][j]==0) && (tempBoard[i+1][j]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i+1][j]=1;
                    temp++;
                    i++;
                }
            }
        }
        for(int i = 0; i <  staticSize;i++) {
            for(int j = 0; j <  staticSize-1;j++) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j+1]==0)) {
                    temp++;
                    j++;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;

        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int i = 0; i <  staticSize;i++) {
            for(int j = 0; j <  staticSize-1;j++) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j+1]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i][j+1]=1;
                    temp++;
                    j++;
                }
            }
        }
        for(int j = 0; j < staticSize;j++) {
            for(int i = 0; i < staticSize-1;i++) {
                if((tempBoard[i][j]==0) && (tempBoard[i+1][j]==0)) {
                    temp++;
                    i++;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;
        //PRAWY GORNY
        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int j = 0; j < staticSize;j++) {
            for(int i = staticSize-1; i >0;i--) {
                if((tempBoard[i][j]==0) && (tempBoard[i-1][j]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i-1][j]=1;
                    temp++;
                    i--;
                }
            }
        }
        for(int i = staticSize-1; i >=0;i--) {
            for(int j = 0; j <  staticSize-1;j++) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j+1]==0)) {
                    temp++;
                    j++;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;

        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int i = staticSize-1; i >= 0;i--) {
            for(int j = 0; j <  staticSize-1;j++) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j+1]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i][j+1]=1;
                    temp++;
                    j++;
                }
            }
        }
        for(int j = 0; j < staticSize;j++) {
            for(int i = staticSize-1; i >0;i--) {
                if((tempBoard[i][j]==0) && (tempBoard[i-1][j]==0)) {
                    temp++;
                    i--;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;
        //LEWY DOLNY
        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int j = staticSize-1; j >=0;j--) {
            for(int i = 0; i < staticSize-1;i++) {
                if((tempBoard[i][j]==0) && (tempBoard[i+1][j]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i+1][j]=1;
                    temp++;
                    i++;
                }
            }
        }
        for(int i = 0; i <  staticSize;i++) {
            for(int j = staticSize-1; j >0;j--) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j-1]==0)) {
                    temp++;
                    j--;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;

        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int i = 0; i <  staticSize;i++) {
            for(int j = staticSize-1; j > 0;j--) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j-1]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i][j-1]=1;
                    temp++;
                    j--;
                }
            }
        }
        for(int j = staticSize-1; j >0;j--) {
            for(int i = 0; i < staticSize-1;i++) {
                if((tempBoard[i][j]==0) && (tempBoard[i+1][j]==0)) {
                    temp++;
                    i++;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;
        //PRAWY DOLNY
        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int j = staticSize-1; j >=0 ;j--) {
            for(int i = staticSize - 1; i > 0;i--) {
                if((tempBoard[i][j]==0) && (tempBoard[i-1][j]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i-1][j]=1;
                    temp++;
                    i--;
                }
            }
        }
        for(int i = staticSize-1; i >=0;i--) {
            for(int j = staticSize-1; j > 0;j--) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j-1]==0)) {
                    temp++;
                    j--;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;

        tempBoard = new int[staticSize][staticSize];
        for(int i = 0; i < staticSize;i++) {
            for(int j = 0; j < staticSize; j++)
                tempBoard[i][j] = board[i][j];
        }
        for(int i = staticSize - 1; i >=0;i--) {
            for(int j = staticSize-1; j >0;j--) {
                if((tempBoard[i][j]==0) && (tempBoard[i][j-1]==0)) {
                    tempBoard[i][j]=1;
                    tempBoard[i][j-1]=1;
                    temp++;
                    j--;
                }
            }
        }
        for(int j = staticSize-1; j >=0;j--) {
            for(int i = staticSize-1; i >0;i--) {
                if((tempBoard[i][j]==0) && (tempBoard[i-1][j]==0)) {
                    temp++;
                    i--;
                }
            }
        }
        if(temp>maxmoves)
            maxmoves = temp;
        temp=0;




        return maxmoves;
    }

    public int width;
    public int height;
    public int size;
    public int staticSize;
    public boolean diagonallyMoves;
    public String boardType;
    public int[][] board;

}
