package gfx;

import core.Bricks;
import exceptions.InvalidMoveException;
import exceptions.RobotPlayerNotWorkingException;
import logic.BoardLogic;
import logic.MovesStorage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.net.URL;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class BoardPanel extends Canvas {
    public BoardPanel(BoardLogic board, int gametype) {
        this.board = board;
        gamemode = gametype;
        UIManager.put("OptionPane.yesButtonText", "Tak");
        UIManager.put("OptionPane.noButtonText", "Nie");
        UIManager.put("OptionPane.cancelButtonText", "Cofnij ostatni ruch");

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(gametype==0 || gametype==1) {
                    if (isSelected) {
                        if (e.getKeyCode() == KeyEvent.VK_UP) {
                            if (directions[0]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[selectedX][selectedY - 1] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, selectedX, selectedY - 1);
                                Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                            Bricks.mainFrame.comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                        }
                                    }
                                } else {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            if (directions[1]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[selectedX + 1][selectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, selectedX + 1, selectedY);
                                Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                            Bricks.mainFrame.comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                        }
                                    }
                                } else {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 1;
                                }
                                checkNoMoves();
                            }
                        }
                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                            if (directions[2]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[selectedX][selectedY + 1] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, selectedX, selectedY + 1);
                                Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    // Bricks.mainFrame.restTiles.setText("Gracz Drugi");
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                            Bricks.mainFrame.comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                        }
                                    }
                                } else {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            if (directions[3]) {
                                board.board[selectedX][selectedY] = actualPlayer;
                                board.board[selectedX - 1][selectedY] = actualPlayer;
                                movesStorage.addMove(selectedX, selectedY, selectedX - 1, selectedY);
                                Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                isSelected = false;
                                playSound();
                                if (actualPlayer == 1) {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 2;
                                    if (!checkNoMoves()) {
                                        if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                            Bricks.mainFrame.comp.performMove(board, movesStorage);
                                            actualPlayer = 1;
                                        }
                                    }
                                } else {
                                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                    Bricks.mainFrame.repaint();
                                    actualPlayer = 1;
                                }
                                checkNoMoves();

                            }
                        }
                    }
                }
            }
        });
        movesStorage = new MovesStorage();
        movesStorage.reset();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(gametype==0 || gametype==1) {
                    if (!isSelected) {
                        if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                            selectedX = (e.getX() - margin) / oneFieldWidth;
                            selectedY = (e.getY() - margin) / oneFieldHeight;
                            try {
                                if (board.board[selectedX][selectedY] == 0) {
                                    isSelected = true;
                                    directions = board.possibleDirections(selectedX, selectedY);
                                }
                            }
                            catch (Exception ignored) {

                            }

                        }
                    } else {
                        if ((e.getX() > margin && e.getX() < getWidth() - margin) && (e.getY() > margin && e.getY() < getHeight() - margin)) {
                            int tempSelectedX = (e.getX() - margin) / oneFieldWidth;
                            int tempSelectedY = (e.getY() - margin) / oneFieldHeight;

                            if ((tempSelectedX == selectedX + 1) && tempSelectedY == selectedY) {
                                if (directions[1]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if(gametype == 0 && Bricks.mainFrame.getComputerPlayerType()!=0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer=1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();
                                }
                            } else if ((tempSelectedX == selectedX - 1) && tempSelectedY == selectedY) {
                                if (directions[3]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if(gametype == 0 && Bricks.mainFrame.getComputerPlayerType()!=0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer=1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();

                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY + 1) {
                                if (directions[2]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if(gametype == 0 && Bricks.mainFrame.getComputerPlayerType()!=0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer=1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();

                                }
                            } else if ((tempSelectedX == selectedX) && tempSelectedY == selectedY - 1) {
                                if (directions[0]) {
                                    board.board[selectedX][selectedY] = actualPlayer;
                                    board.board[tempSelectedX][tempSelectedY] = actualPlayer;
                                    movesStorage.addMove(selectedX, selectedY, tempSelectedX, tempSelectedY);
                                    Bricks.mainFrame.undoLastMoveButton.setEnabled(true);
                                    isSelected = false;
                                    playSound();
                                    if (actualPlayer == 1) {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 2;
                                        if (!checkNoMoves()) {
                                            if (gametype == 0 && Bricks.mainFrame.getComputerPlayerType()==0) {
                                                Bricks.mainFrame.comp.performMove(board, movesStorage);
                                                actualPlayer = 1;
                                            }
                                            if(gametype == 0 && Bricks.mainFrame.getComputerPlayerType()!=0) {
                                                singlePlayerComputerMakeMove();
                                                actualPlayer=1;
                                            }
                                        }
                                    } else {
                                        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                                        Bricks.mainFrame.repaint();
                                        actualPlayer = 1;
                                    }
                                    checkNoMoves();

                                }
                            } else {
                                isSelected = false;
                            }

                        } else
                            isSelected = false;
                    }
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

    public void playSound() {
        try {
            URL putBrickURL = this.getClass().getResource("putbrick.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(putBrickURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(Bricks.mainFrame.volume);
            if (Bricks.mainFrame.isSound) {
                clip.start();
            }
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void drawBoardFrame() {
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        oneFieldWidth = (width - margin * 2) / board.width;
        oneFieldHeight = (height - margin * 2) / board.height;
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(margin, margin, oneFieldWidth * board.width, oneFieldHeight * board.height);
        g2.setColor(Color.WHITE);
        g2.fillRect(margin, margin, oneFieldWidth * board.width, oneFieldHeight * board.height);
        int inFieldMargin = 0;
        if (actualPlayer == 1) {
            if (isSelected) {
                g2.setColor(Bricks.mainFrame.playerFirstColor);
                g2.fillRect(selectedX * oneFieldWidth + margin + inFieldMargin, selectedY * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
            }
        }
        if (actualPlayer == 2) {
            if (isSelected) {
                g2.setColor(Bricks.mainFrame.playerSecondColor);
                g2.fillRect(selectedX * oneFieldWidth + margin + inFieldMargin, selectedY * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
            }
        }

        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                if (board.board[j][i] == 1) {
                    g2.setColor(Bricks.mainFrame.playerFirstColor);
                    g2.fillRect(j * oneFieldWidth + margin + inFieldMargin, i * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
                }
                if (board.board[j][i] == 2) {
                    g2.setColor(Bricks.mainFrame.playerSecondColor);
                    g2.fillRect(j * oneFieldWidth + margin + inFieldMargin, i * oneFieldHeight + margin + inFieldMargin, oneFieldWidth - 2 * inFieldMargin, oneFieldHeight - 2 * inFieldMargin);
                }
            }
        }
        for (int i = 0; i < board.height; i++) {
            if (i > 0) {
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.BLACK);
                g2.drawLine(margin, margin + i * oneFieldHeight, margin + oneFieldWidth * board.width, margin + i * oneFieldHeight);
            }
            for (int j = 0; j < board.width; j++) {
                if (j > 0) {
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(Color.BLACK);
                    g2.drawLine(margin + j * oneFieldWidth, margin, margin + j * oneFieldWidth, oneFieldHeight * board.height + margin);
                }
            }
        }

    }

    public boolean checkNoMoves() {

        Bricks.mainFrame.movesLeftLabel.setText("Pozostały " + board.getPossibleMovesLeft() + " ruchy");
        if (!board.anyMoves()) {
            int selection = 0;
            if (Bricks.mainFrame.getDebugMode() && gamemode !=2) {
                if (gamemode == 1) {
                    if (actualPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                }
                if (gamemode == 0) {
                    if (actualPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał komputer, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                }
                if (selection == JOptionPane.OK_OPTION) {
                    resetBoard();
                } else if (selection == JOptionPane.CANCEL_OPTION) {
                    undoLastMove();
                } else {
                    resetBoard();
                    Bricks.mainFrame.stopGame();
                }
                return true;
            }
            else  {
                if (gamemode ==  2) {
                    if( Bricks.mainFrame.computerPlayer==1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał program drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                    else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał program pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                    Bricks.mainFrame.computerPlayer=1;

                }
                if (gamemode == 1) {
                    if (actualPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał gracz pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                }
                if (gamemode == 0) {
                    if (actualPlayer == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrał komputer, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        selection = JOptionPane.showConfirmDialog(null, "Koniec możliwych ruchów, wygrałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                }
                if (selection == JOptionPane.OK_OPTION) {
                    resetBoard();
                }  else {
                    resetBoard();
                    Bricks.mainFrame.stopGame();
                }
                return true;
            }
        }
        return false;
    }

    public void walkover(int computerPlayer, String reason) {
        int selection;
        if(reason.equals("Timeout")) {
            if (computerPlayer == 1) {
                selection = JOptionPane.showConfirmDialog(null, "Komputer numer 1 przekroczył czas na wykonanie ruchu, wygrał program drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            } else {
                selection = JOptionPane.showConfirmDialog(null, "Komputer numer 2 przekroczył czas na  wykonanie ruchu, wygrał program pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
        }
        else {
            if (computerPlayer == 1) {
                selection = JOptionPane.showConfirmDialog(null, "Komputer numer 1 wykonał błędny ruch!, wygrał program drugi, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            } else {
                selection = JOptionPane.showConfirmDialog(null, "Komputer numer 2 wykonał błędny ruch!, wygrał program pierwszy, chcesz zagrać jeszcze raz?", "Koniec" +
                        " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }
        }
        Bricks.mainFrame.computerPlayer=1;
        if (selection == JOptionPane.OK_OPTION) {
            resetBoard();
        }  else {
            resetBoard();
            Bricks.mainFrame.stopGame();
        }
    }

    public void resetBoard() {
        board.reset();
        isSelected = false;
        movesStorage.reset();
        Bricks.mainFrame.restTiles.setText("Gracz: ");
        Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
        Bricks.mainFrame.undoLastMoveButton.setEnabled(false);
        Bricks.mainFrame.movesLeftLabel.setText("Pozostały " + board.getPossibleMovesLeft() + " ruchy");
        Bricks.mainFrame.repaintThis();
        Bricks.mainFrame.repaint();
        if(gamemode==2) {
            try {
                Bricks.firstRobotPlayer.reset();
                Bricks.secondRobotPlayer.reset();
            } catch (Exception ignored) {
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        if(gamemode==0 && Bricks.mainFrame.getComputerPlayerType()!=0 && Bricks.singlePlayerRobotPlayer!=null) {
            try {
                Bricks.singlePlayerRobotPlayer.reset();
            }
            catch (Exception ignored){
                //TODO EWENTUALNIE, OBSŁUGA BŁĘDU GDYBY PROGRAM GRAJACY PRZESTAL NAGLE DZIALAC
            }
        }
        actualPlayer = 1;
    }

    public void undoLastMove() {
        if (gamemode == 2) {
            int[] lastMove = movesStorage.returnMoveLikeArray();
            if(lastMove[0] != -1) {
                board.board[lastMove[0]][lastMove[1]] = 0;
                board.board[lastMove[2]][lastMove[3]] = 0;
                drawBoardFrame();
                if (Bricks.mainFrame.computerPlayer == 1) {
                    Bricks.mainFrame.computerPlayer =2;
                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                    Bricks.mainFrame.repaint();
                }
                else
                    Bricks.mainFrame.computerPlayer = 1;
                   Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                   Bricks.mainFrame.repaint();
            }
        }
        if (gamemode == 1) {
            int[] lastMove = movesStorage.returnMoveLikeArray();
            if (lastMove[0] != -1) {
                board.board[lastMove[0]][lastMove[1]] = 0;
                board.board[lastMove[2]][lastMove[3]] = 0;
                drawBoardFrame();
                if (actualPlayer == 1) {
                    actualPlayer = 2;
                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerSecondColor);
                    Bricks.mainFrame.repaint();
                } else {
                    actualPlayer = 1;
                    Bricks.mainFrame.actualPlayerColorPreview.setColor(Bricks.mainFrame.playerFirstColor);
                    Bricks.mainFrame.repaint();
                }
            }
        }
        if (gamemode == 0) {
            int[] lastMove = movesStorage.returnMoveLikeArray();
            if (lastMove[0] != -1) {
                board.board[lastMove[0]][lastMove[1]] = 0;
                board.board[lastMove[2]][lastMove[3]] = 0;
            }
            lastMove = movesStorage.returnMoveLikeArray();
            if (lastMove[0] != -1) {
                board.board[lastMove[0]][lastMove[1]] = 0;
                board.board[lastMove[2]][lastMove[3]] = 0;
            }
            drawBoardFrame();
            actualPlayer = 1;
        }
        if (movesStorage.isEmpty())
            Bricks.mainFrame.undoLastMoveButton.setEnabled(false);
        Bricks.mainFrame.movesLeftLabel.setText("Pozostały " + board.getPossibleMovesLeft() + " ruchy");
    }

    public void singlePlayerComputerMakeMove(){
        int[] move = new int[4];
        try {
            move = Bricks.singlePlayerRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
        } catch (InvalidMoveException exception) {
            //TODO
            int selection = JOptionPane.showConfirmDialog(null, "Komputer przekroczył czas na wykonanie ruchu, wygrałałeś, chcesz zagrać jeszcze raz?", "Koniec" +
                    " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            Bricks.mainFrame.computerPlayer=1;
            if (selection == JOptionPane.OK_OPTION) {
                resetBoard();
            }  else {
                resetBoard();
                Bricks.mainFrame.stopGame();
            }
        }

        int x1 = move[0];
        int y1 = move[1];
        int x2 = move[2];
        int y2 = move[3];

        if(possibleMove(x1,y1,x2,y2)) {
            board.board[x1][y1] = 2;
            board.board[x2][y2] = 2;
            movesStorage.addMove(x1, y1, x2, y2);
        }
    }
    private boolean possibleMove(int x1,int y1,int x2,int y2) {
        if(board.board[x1][y1]!=0 || board.board[x2][y2]!=0)
            return false;
        boolean flag=false;

        if(y1==y2) {
            if(x1+1==x2)
                flag=true;
            if(x1-1==x2)
                flag=true;
        }
        if(x1==x2) {
            if(y1+1==y2)
                flag=true;
            if(y1-1==y2)
                flag=true;
        }

        if(!flag)
            return false;
        return true;
    }
    public int getActualPlayer() {
        return actualPlayer;
    }

    private BoardLogic board;

    private int actualPlayer = 1;

    private int margin = 20;

    private int oneFieldWidth;
    private int oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;


    public MovesStorage movesStorage;
    private int gamemode;

    private Graphics g;
}
