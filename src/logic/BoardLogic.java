package logic;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class BoardLogic {
    public BoardLogic(int size) {
        this.width = size;
        this.height = size;
        staticSize = size;
        board = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                board[i][j] = 0;
    }

    public void reset() {
        board = new int[staticSize][staticSize];
        for (int i = 0; i < staticSize; i++)
            for (int j = 0; j < staticSize; j++)
                board[i][j] = 0;
    }

    public boolean[] possibleDirections(int x, int y) {
        boolean result[] = new boolean[4];
        result[0] = false;
        result[1] = false;
        result[2] = false;
        result[3] = false;
        try {
            if (board[x][y - 1] == 0) result[0] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[0] = false;
        }

        try {
            if (board[x + 1][y] == 0) result[1] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[1] = false;
        }

        try {
            if (board[x][y + 1] == 0) result[2] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[2] = false;
        }

        try {
            if (board[x - 1][y] == 0) result[3] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[3] = false;
        }

        return result;
    }

    public boolean anyMoves() {
        for (int i = 0; i < staticSize - 1; i++) {
            for (int j = 0; j < staticSize; j++) {
                if ((board[i][j] == 0) && (board[i + 1][j] == 0))
                    return true;
            }
        }
        for (int i = 0; i < staticSize; i++) {
            for (int j = 0; j < staticSize - 1; j++) {
                if ((board[i][j] == 0) && (board[i][j + 1] == 0))
                    return true;
            }
        }

        return false;
    }

    public int width;
    public int height;
    int staticSize;
    public int[][] board;

}
