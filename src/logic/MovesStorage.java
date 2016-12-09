package logic;

import java.util.ArrayList;

/**
 * Created by Mateusz on 04.10.2016.
 * Project Bricks
 */
public class MovesStorage {
    class Move {
        public Move(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int x1;
        public int y1;
        public int x2;
        public int y2;
    }

    public MovesStorage() {
        moves = new ArrayList<>();
    }

    public void addMove(int x1, int y1, int x2, int y2) {
        moves.add(new Move(x1, y1, x2, y2));
    }

    public void reset() {
        moves.clear();
    }

    public int[] returnMoveLikeArray() {
        int[] moveValues = new int[4];
        int movesSize = moves.size();

        if (movesSize > 0) {

            moveValues[0] = moves.get(moves.size() - 1).x1;
            moveValues[1] = moves.get(moves.size() - 1).y1;
            moveValues[2] = moves.get(moves.size() - 1).x2;
            moveValues[3] = moves.get(moves.size() - 1).y2;
            moves.remove(movesSize - 1);
        } else {
            moveValues[0] = -1;
            moveValues[1] = -1;
            moveValues[2] = -1;
            moveValues[3] = -1;
        }
        return moveValues;
    }

    public int[] getLastMove() {
        int[] moveValues = new int[4];
        moveValues[0] = moves.get(moves.size() - 1).x1;
        moveValues[1] = moves.get(moves.size() - 1).y1;
        moveValues[2] = moves.get(moves.size() - 1).x2;
        moveValues[3] = moves.get(moves.size() - 1).y2;
        return moveValues;
    }

    public String getLastMoveAsString() {
        return moves.get(moves.size() - 1).x1 + " " +
                moves.get(moves.size() - 1).y1 + " " +
                moves.get(moves.size() - 1).x2 + " " +
                moves.get(moves.size() - 1).y2;
    }

    public boolean isEmpty() {
        return moves.size() == 0;
    }

    private ArrayList<Move> moves;
}
