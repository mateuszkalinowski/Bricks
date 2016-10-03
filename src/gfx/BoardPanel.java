package gfx;

import core.Bricks;
import logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class BoardPanel extends Canvas {
    public BoardPanel(Board board) {
        this.board = board;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isSelected) {
                    if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                        selectedX = (e.getX() - margin) / oneFieldWidth;
                        selectedY = (e.getY() - margin) / oneFieldHeight;
                        if (firstMove && (board.boardType.equals("Kwadrat") || board.boardType.equals("Trojkat"))) {
                            if (board.board[selectedX][selectedY] == 1) {
                                firstMove = false;
                                board.board[selectedX][selectedY] = 0;
                                board.size--;
                                Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            }
                        } else if (board.board[selectedX][selectedY] == 1) {
                            isSelected = true;
                            directions = board.possibleDirections(selectedX, selectedY);
                        }

                    }
                } else {
                    if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                        int tempSelectedX = (e.getX() - margin) / oneFieldWidth;
                        int tempSelectedY = (e.getY() - margin) / oneFieldHeight;

                        if ((tempSelectedX == selectedX) && (tempSelectedY == selectedY + 2) && directions[2]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX][tempSelectedY - 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size--;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if ((tempSelectedX == selectedX) && (tempSelectedY == selectedY - 2) && directions[0]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX][tempSelectedY + 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if ((tempSelectedX == selectedX + 2) && (tempSelectedY == selectedY) && directions[1]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX - 1][tempSelectedY] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if ((tempSelectedX == selectedX - 2) && (tempSelectedY == selectedY) && directions[3]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX + 1][tempSelectedY] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        }
                        //TYLKO JESLI SA RUCHY PO SKOSIE
                        else if (board.diagonallyMoves && (tempSelectedX == selectedX + 2) && (tempSelectedY == selectedY - 2) && directions[4]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX - 1][tempSelectedY + 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if (board.diagonallyMoves && (tempSelectedX == selectedX + 2) && (tempSelectedY == selectedY + 2) && directions[5]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX - 1][tempSelectedY - 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if (board.diagonallyMoves && (tempSelectedX == selectedX - 2) && (tempSelectedY == selectedY + 2) && directions[6]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX + 1][tempSelectedY - 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else if (board.diagonallyMoves && (tempSelectedX == selectedX - 2) && (tempSelectedY == selectedY - 2) && directions[7]) {
                            board.board[tempSelectedX][tempSelectedY] = 1;
                            board.board[tempSelectedX + 1][tempSelectedY + 1] = 0;
                            board.board[selectedX][selectedY] = 0;
                            isSelected = false;
                            board.size -= 1;
                            Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
                            checkNoMoves();
                        } else
                            isSelected = false;
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
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(margin, margin, getWidth() - 2 * margin, getHeight() - 2 * margin);

        oneFieldWidth = (width - margin * 2) / board.width;
        oneFieldHeight = (height - margin * 2) / board.height;
        int inFieldMargin = 12;
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                if (board.board[j][i] == 0 || board.board[j][i] == 1) {
                    g2.setColor(new Color(142, 142, 56));
                    int fieldBackgroundMargin = 8;
                    g2.fillOval(j * oneFieldWidth + margin + fieldBackgroundMargin, i * oneFieldHeight + margin + fieldBackgroundMargin, oneFieldWidth - 2 * fieldBackgroundMargin, oneFieldHeight - 2 * fieldBackgroundMargin);
                }
                if (board.board[j][i] == 1) {
                    g2.setColor(Color.BLACK);
                    g2.fillOval(j * oneFieldWidth + margin + inFieldMargin, i * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
                }
            }
        }
        if (isSelected) {
            g2.setColor(new Color(238, 44, 44));
            g2.fillOval(selectedX * oneFieldWidth + margin + inFieldMargin, selectedY * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
        }

    }

    private void checkNoMoves() {
        if (!board.anyMoves()) {
            int selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, chcesz zagrać jeszcze raz?", "Koniec" +
                    " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                resetBoard();
                firstMove = true;
            } else {
                System.exit(0);
            }
        }
    }

    private void resetBoard() {
        board.reset();
        Bricks.mainFrame.restTiles.setText("Pozostało: " + board.size);
        isSelected = false;
    }

    private Board board;

    private int margin = 20;

    private int oneFieldWidth;
    private int oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;

    private boolean firstMove = true;

    private Graphics g;
}
