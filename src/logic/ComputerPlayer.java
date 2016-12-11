package logic;

/**
 * Created by Mateusz on 03.10.2016.
 * Project Bricks
 */
public class ComputerPlayer {
    public ComputerPlayer() {

    }

    public void performMove(BoardLogic board, MovesStorage movesStorage) {
        this.board = board;
        this.movesStorage = movesStorage;
        while (true) {
            if (FullRandomMove())
                return;
        }
    }

    private boolean FullRandomMove() {
        int x = (int) (Math.random() * (board.staticSize));
        int y = (int) (Math.random() * (board.staticSize));
        int direction = (int) (Math.random() * 4);
        boolean[] directions = board.possibleDirections(x, y);

        if (board.board[x][y] == 0) {
            if (direction == 0 && directions[0]) {
                board.board[x][y] = 2;
                board.board[x][y - 1] = 2;
                movesStorage.addMove(x, y, x, y - 1);
                return true;
            }
            if (direction == 1 && directions[1]) {
                board.board[x][y] = 2;
                board.board[x + 1][y] = 2;
                movesStorage.addMove(x, y, x + 1, y);
                return true;
            }
            if (direction == 2 && directions[2]) {
                board.board[x][y] = 2;
                board.board[x][y + 1] = 2;
                movesStorage.addMove(x, y, x, y + 1);
                return true;
            }
            if (direction == 3 && directions[3]) {
                board.board[x][y] = 2;
                board.board[x - 1][y] = 2;
                movesStorage.addMove(x, y, x - 1, y);
                return true;
            }
        }
        return false;
    }

    private BoardLogic board;
    private MovesStorage movesStorage;
}
