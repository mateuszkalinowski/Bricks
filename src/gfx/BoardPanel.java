package gfx;

import core.Bricks;
import frames.MainFrame;
import logic.Board;
import logic.MovesStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class BoardPanel extends Canvas {
    public BoardPanel(Board board,int gametype) {
        this.board = board;

        UIManager.put("OptionPane.yesButtonText","Tak");
        UIManager.put("OptionPane.noButtonText","Nie");

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE && Bricks.mainFrame.running) {
                        int selection = JOptionPane.showConfirmDialog(null, "Chcesz opuścić grę i wrócić do menu?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Bricks.mainFrame.stopGame();
                }
                }
            }
        });
        movesStorage = new MovesStorage();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isSelected) {
                    if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                        selectedX = (e.getX() - margin) / oneFieldWidth;
                        selectedY = (e.getY() - margin) / oneFieldHeight;

                        if (board.board[selectedX][selectedY] == 0) {
                            isSelected = true;
                            directions = board.possibleDirections(selectedX, selectedY);
                        }

                    }
                } else {
                    if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                        int tempSelectedX = (e.getX() - margin) / oneFieldWidth;
                        int tempSelectedY = (e.getY() - margin) / oneFieldHeight;

                        if((tempSelectedX==selectedX+1) && tempSelectedY == selectedY) {
                            if(directions[1]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX,selectedY,tempSelectedX,tempSelectedY);
                                isSelected = false;
                                if(actualPlayer==1) {
                                    Bricks.mainFrame.restTiles.setText("Gracz Drugi");
                                    actualPlayer = 2;
                                    if(!checkNoMoves()) {
                                        if (gametype == 0) {
                                            Bricks.mainFrame.comp.performMove(board);
                                            actualPlayer = 1;
                                        }
                                    }
                                }
                                else {
                                    Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        else if((tempSelectedX==selectedX-1) && tempSelectedY == selectedY) {
                            if(directions[3]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX,selectedY,tempSelectedX,tempSelectedY);
                                isSelected = false;
                                if(actualPlayer==1) {
                                    Bricks.mainFrame.restTiles.setText("Gracz Drugi");
                                    actualPlayer = 2;
                                    if(!checkNoMoves()) {
                                        if (gametype == 0) {
                                            Bricks.mainFrame.comp.performMove(board);
                                            actualPlayer = 1;
                                        }
                                    }
                                }
                                else {
                                    Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        else if((tempSelectedX==selectedX) && tempSelectedY == selectedY+1) {
                            if(directions[2]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX,selectedY,tempSelectedX,tempSelectedY);
                                isSelected = false;
                                if(actualPlayer==1) {
                                    Bricks.mainFrame.restTiles.setText("Gracz Drugi");
                                    actualPlayer = 2;
                                    if(!checkNoMoves()) {
                                        if (gametype == 0) {
                                            Bricks.mainFrame.comp.performMove(board);
                                            actualPlayer = 1;
                                        }
                                    }
                                }
                                else {
                                    Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        else if((tempSelectedX==selectedX) && tempSelectedY == selectedY-1) {
                            if(directions[0]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                movesStorage.addMove(selectedX,selectedY,tempSelectedX,tempSelectedY);
                                isSelected = false;
                                if(actualPlayer==1) {
                                    Bricks.mainFrame.restTiles.setText("Gracz Drugi");
                                    actualPlayer = 2;
                                    if(!checkNoMoves()) {
                                        if (gametype == 0) {
                                            Bricks.mainFrame.comp.performMove(board);
                                            actualPlayer = 1;
                                        }
                                    }
                                }
                                else {
                                    Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        else {
                            isSelected = false;
                        }

                    } else
                        isSelected = false;
                }
            }
        });
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        drawBoardFrame();
        g.dispose();
        bs.show();
    }

    private void drawBoardFrame() {
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        oneFieldWidth = (width - margin * 2) / board.width;
        oneFieldHeight = (height - margin * 2) / board.height;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(margin, margin, oneFieldWidth*board.width, oneFieldHeight*board.height);
        g2.setColor(Color.WHITE);
        g2.fillRect(margin, margin, oneFieldWidth*board.width, oneFieldHeight*board.height);
        int inFieldMargin = 0;
        for (int i = 0; i < board.height; i++) {
            if(i>0) {
                g2.setColor(Color.BLACK);
                g2.drawLine(margin,margin+i*oneFieldHeight,margin+oneFieldWidth*board.width,margin+i*oneFieldHeight);
            }
            for (int j = 0; j < board.width; j++) {
                if(j>0) {
                    g2.setColor(Color.BLACK);
                    g2.drawLine(margin+j*oneFieldWidth,margin,margin+j*oneFieldWidth,oneFieldHeight*board.height+margin);
                }
                if (board.board[j][i] == 1) {
                   // g2.setColor(new Color(69, 136, 58));
                    g2.setColor(Bricks.mainFrame.playerFirstColor);
                    g2.fillRect(j * oneFieldWidth + margin + inFieldMargin, i * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
                }
                if (board.board[j][i] == 2) {
                    //g2.setColor(new Color(238, 44, 44));
                    g2.setColor(Bricks.mainFrame.playerSecondColor);
                    g2.fillRect(j * oneFieldWidth + margin + inFieldMargin, i * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
                }
            }
        }
        if(actualPlayer==1) {
            if (isSelected) {
                //g2.setColor(new Color(69, 136, 58));
                g2.setColor(Bricks.mainFrame.playerFirstColor);
                g2.fillRect(selectedX * oneFieldWidth + margin + inFieldMargin, selectedY * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
            }
        }
        if(actualPlayer==2) {
            if (isSelected) {
                //g2.setColor(new Color(238, 44, 44));
                g2.setColor(Bricks.mainFrame.playerSecondColor);
                g2.fillRect(selectedX * oneFieldWidth + margin + inFieldMargin, selectedY * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
            }
        }

    }

    private boolean checkNoMoves() {
        if (!board.anyMoves()) {
            int selection;
            if(actualPlayer == 1) {
                selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
            else {
                selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
            if (selection == JOptionPane.OK_OPTION) {
                resetBoard();
            } else {
                resetBoard();
                Bricks.mainFrame.stopGame();
            }
            return true;
        }
        return false;
    }

    private void resetBoard() {
        board.reset();
        isSelected = false;
        movesStorage.reset();
        Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
        actualPlayer = 1;
    }

    public void undoLastMove() {
        int[] lastMove = movesStorage.returnMoveLikeArray();
        if(lastMove[0]!=-1) {
            board.board[lastMove[0]][lastMove[1]] = 0;
            board.board[lastMove[2]][lastMove[3]] = 0;
            drawBoardFrame();
            if (actualPlayer == 1) {
                actualPlayer = 2;
                Bricks.mainFrame.restTiles.setText("Gracz Drugi");
            }
            else {
                actualPlayer = 1;
                Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
            }
        }

    }

    public int getActualPlayer(){
        return actualPlayer;
    }

    private Board board;

    private int actualPlayer = 1;

    private int margin = 20;

    private int oneFieldWidth;
    private int oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;


    private MovesStorage movesStorage;

    private Graphics g;
}
