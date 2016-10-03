package logic;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class Board {
    public Board(int size) {
        this.width = size;
        this.height = size;
        this.size = size * size;
        staticSize = this.size;
        board = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                board[i][j] = 1;
        diagonallyMoves = false;
        boardType = "Kwadrat";
    }

    public Board() {
        width = 7;
        height = 7;
        size = 32;
        staticSize = this.size;
        board = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if ((i < 2 && j < 2) || (i > 4 && j > 4) || (i > 4 && j < 2) || (j > 4 && i < 2))
                    board[i][j] = 2;
                else
                    board[i][j] = 1;
            }
        board[3][3] = 0;
        boardType = "Klasyczna";

    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        double sizeD = (width * height) / 2.0 + ((width) / 2.0);
        this.size = (int) sizeD;
        staticSize = this.size;
        diagonallyMoves = true;
        board = new int[width][height];
        int line = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < line; j++)
                board[j][i] = 1;
            for (int j = line; j < width; j++)
                board[j][i] = 2;
            line++;
        }
        boardType = "Trojkat";
    }

    public void reset() {
        switch (boardType) {
            case "Klasyczna":
                width = 7;
                height = 7;
                board = new int[width][height];
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++) {
                        if ((i < 2 && j < 2) || (i > 4 && j > 4) || (i > 4 && j < 2) || (j > 4 && i < 2))
                            board[i][j] = 2;
                        else
                            board[i][j] = 1;
                    }

                board[3][3] = 0;
                break;
            case "Kwadrat":
                board = new int[width][height];
                for (int i = 0; i < width; i++)
                    for (int j = 0; j < height; j++)
                        board[i][j] = 1;
                diagonallyMoves = false;
                break;
            case "Trojkat":
                int line = 1;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < line; j++)
                        board[j][i] = 1;
                    for (int j = line; j < width; j++)
                        board[j][i] = 2;
                    line++;
                }
                break;
        }
        size = staticSize;
    }

    public boolean[] possibleDirections(int x, int y) {
        boolean result[] = new boolean[8];
        result[0] = false;
        result[1] = false;
        result[2] = false;
        result[3] = false;
        result[4] = false;
        result[5] = false;
        result[6] = false;
        result[7] = false;
        try {
            if (board[x][y - 2] == 0 && board[x][y - 1] == 1 && board[x][y] == 1)
                result[0] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[0] = false;
        }
        try {
            if (board[x + 2][y] == 0 && board[x + 1][y] == 1 && board[x][y] == 1)
                result[1] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[1] = false;
        }
        try {
            if (board[x][y + 2] == 0 && board[x][y + 1] == 1 && board[x][y] == 1)
                result[2] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[2] = false;
        }
        try {
            if (board[x - 2][y] == 0 && board[x - 1][y] == 1 && board[x][y] == 1)
                result[3] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[3] = false;
        }
        //GORA PRAWO
        try {
            if (board[x + 2][y - 2] == 0 && board[x + 1][y - 1] == 1 && board[x][y] == 1)
                result[4] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[4] = false;
        }
        //DOL PRAWO
        try {
            if (board[x + 2][y + 2] == 0 && board[x + 1][y + 1] == 1 && board[x][y] == 1)
                result[5] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[5] = false;
        }

        //DOL LEWO
        try {
            if (board[x - 2][y + 2] == 0 && board[x - 1][y + 1] == 1 && board[x][y] == 1)
                result[6] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[6] = false;
        }

        //GORA LEWO
        try {
            if (board[x - 2][y - 2] == 0 && board[x - 1][y - 1] == 1 && board[x][y] == 1)
                result[7] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            result[7] = false;
        }


        return result;
    }

    public boolean anyMoves() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean moves[] = possibleDirections(x, y);
                if (diagonallyMoves) {
                    if (moves[0] || moves[1] || moves[2] || moves[3] || moves[4] || moves[5] || moves[6] || moves[7])
                        return true;
                } else {
                    if (moves[0] || moves[1] || moves[2] || moves[3])
                        return true;
                }
            }
        }
        return false;
    }

    public int width;
    public int height;
    public int size;
    private static int staticSize;
    public boolean diagonallyMoves;
    public String boardType;
    public int[][] board;

}
