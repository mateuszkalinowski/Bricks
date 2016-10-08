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
                board.board[x][y] = 2;
                board.board[x][y - 1] = 2;
                movesStorage.addMove(x,y,x,y-1);
           //     System.out.println("-----------------");
                return true;
            }
            if (direction == 1 && directions[1]) {
                board.board[x][y] = 2;
                board.board[x + 1][y] = 2;
                movesStorage.addMove(x,y,x+1,y);
          //      System.out.println("-----------------");
                return true;
            }
            if (direction == 2 && directions[2]) {
                board.board[x][y] = 2;
                board.board[x][y + 1] = 2;
                movesStorage.addMove(x,y,x,y+1);
          //      System.out.println("-----------------");
                return true;
            }
            if (direction == 3 && directions[3]) {
                board.board[x][y] = 2;
                board.board[x - 1][y] = 2;
                movesStorage.addMove(x,y,x-1,y);
            //    System.out.println("-----------------");
                return true;
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
