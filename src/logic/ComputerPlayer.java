package logic;

/**
 * Created by Mateusz on 03.10.2016.
 * Project Bricks
 */
public class ComputerPlayer {
    public ComputerPlayer(){

    }
    public void performMove(BoardLogic board,MovesStorage movesStorage){
        this.board = board;
        this.movesStorage = movesStorage;
        possibleMovesLeft = board.getPossibleMovesLeft();
        if(possibleMovesLeft%2==0) {
            int randomZeroOrOne = (int) (Math.random() * 2);
            if(randomZeroOrOne==0) {
                System.out.println("Próbuję znaleźć linię na cztery");
                if(findFourRowHorizontallyStrict()) return;
                if(findFourRowVerticallyStrict()) return;
            }
            if(randomZeroOrOne==1) {
                System.out.println("Próbuję znaleźć linię na cztery");
                if(findFourRowVerticallyStrict()) return;
                if(findFourRowHorizontallyStrict()) return;
            }
        }
        while(true) {
            if(FullRandomMove())
                return;
        }
    }

    private boolean isFourRowHorizontallyStrict(int x1,int y1,int x2,int y2) {
        if(y1!=y2) return false;
        int tmp = 0;
        if(x1>x2) {
            tmp = x1;
            x2 = x1;
            x1 = tmp;
        }
        int y = y1;
        try{
            if(board.board[x1-1][y]!=0 || board.board[x2+1][y]!=0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
            return false;
        }

        try{
            if(board.board[x1-2][y]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x2+2][y]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }

        try{
            if(board.board[x1-1][y-1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x1][y-1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x2][y-1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x2+1][y-1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }


        try{
            if(board.board[x1-1][y+1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x1][y+1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x2][y+1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try{
            if(board.board[x2+1][y+1]==0)
                return false;
        }catch (ArrayIndexOutOfBoundsException ignored) {
        }



        return true;
    }

   private boolean isFourRowVerticallyStrict(int x1,int y1,int x2,int y2) {
       /* if (x1 != x2) return false;
        int tmp = 0;
        if (y1 > y2) {
            tmp = y1;
            y2 = y1;
            y1 = tmp;
        }
        int x = x1;
*/



        return false;
    }

    private boolean findFourRowHorizontallyStrict() {
        for (int i = 0; i < board.staticSize - 3; i++) {
            for (int j = 0; j < board.staticSize; j++) {
                try {
                    if (board.board[i - 1][j] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board.board[i][j-1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+1][j-1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+2][j-1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+3][j-1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board.board[i][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+1][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+2][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+3][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board.board[i + 4][j] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {

                }
                if (board.board[i][j] == 0 && board.board[i + 1][j] == 0 && board.board[i + 2][j] == 0 && board.board[i + 3][j] == 0) {
                    board.board[i+1][j] = 2;
                    board.board[i+2][j] = 2;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean findFourRowVerticallyStrict() {
        for (int i = 0; i < board.staticSize; i++) {
            for (int j = 0; j < board.staticSize - 3; j++) {
                try {
                    if (board.board[i][j - 1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i][j + 4] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board.board[i-1][j] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i-1][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i-1][j+2] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i-1][j+3] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board.board[i+1][j] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+1][j+1] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+1][j+2] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                try {
                    if (board.board[i+1][j+3] == 0) {
                        continue;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                if (board.board[i][j] == 0 && board.board[i][j + 1] == 0 && board.board[i][j + 2] == 0 && board.board[i][j + 3] == 0) {
                    board.board[i][j+1] = 2;
                    board.board[i][j+2] = 2;
                    return true;
                }
            }
        }
        return false;
    }



    private boolean FullRandomMove() {
        int x = (int) (Math.random() * (board.staticSize));
        int y = (int) (Math.random() * (board.staticSize));
        int direction = (int) (Math.random() * 4);
        boolean[] directions = board.possibleDirections(x, y);

        if (board.board[x][y] == 0) {
            if (direction == 0 && directions[0]) {
                if(!isFourRowHorizontallyStrict(x,y,x,y-1) && !isFourRowVerticallyStrict(x,y,x,y-1)) {
                    board.board[x][y] = 2;
                    board.board[x][y - 1] = 2;
                    movesStorage.addMove(x, y, x, y - 1);
                    //     System.out.println("-----------------");
                    return true;
                }
            }
            if (direction == 1 && directions[1]) {
                if(!isFourRowHorizontallyStrict(x,y,x+1,y) && !isFourRowVerticallyStrict(x,y,x+1,y)) {
                    board.board[x][y] = 2;
                    board.board[x + 1][y] = 2;
                    movesStorage.addMove(x, y, x + 1, y);
                    //      System.out.println("-----------------");
                    return true;
                }
            }
            if (direction == 2 && directions[2]) {
                if(!isFourRowHorizontallyStrict(x,y,x,y+1) && !isFourRowVerticallyStrict(x,y,x,y+1)) {
                    board.board[x][y] = 2;
                    board.board[x][y + 1] = 2;
                    movesStorage.addMove(x, y, x, y + 1);
                    //      System.out.println("-----------------");
                    return true;
                }
            }
            if (direction == 3 && directions[3]) {
                if(!isFourRowHorizontallyStrict(x,y,x-1,y) && !isFourRowVerticallyStrict(x,y,x-1,y)) {
                    board.board[x][y] = 2;
                    board.board[x - 1][y] = 2;
                    movesStorage.addMove(x, y, x - 1, y);
                    //    System.out.println("-----------------");
                    return true;
                }
            }
        }
        return false;
    }
/*
    private int countPossibleMovesLeft() {
        int moves = 0;
        for(int i = 0; i < board.staticSize-1;i++) {
            for(int j = 0; j <  board.staticSize;j++) {
                if((board.board[i][j]==0) && (board.board[i+1][j]==0))
                    moves++;
            }
        }
        for(int i = 0; i <  board.staticSize;i++) {
            for(int j = 0; j <  board.staticSize-1;j++) {
                if((board.board[i][j]==0) && (board.board[i][j+1]==0))
                    moves++;
            }
        }
        return moves;
    }
*/
    private int possibleMovesLeft = 0;
    private BoardLogic board;
    private MovesStorage movesStorage;
}
