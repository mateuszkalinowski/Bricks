package frames;

import core.Bricks;
import core.Settings;
import exceptions.InvalidMoveException;
import gfx.BoardPanel;
import gfx.ColorPreview;
import logic.BoardLogic;
import logic.ComputerPlayer;
import logic.RobotPlayer;
import logic.Runner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class MainFrame extends JFrame implements Runnable {

    public MainFrame(int initialBoardSize, Color firstPlayerColor, Color secondPlayerColor, boolean isSound, int volume, boolean debugModeInitialize, String firstPlayerPath, String secondPlayerPath,
                     int firstPlayerProgramTypeArgument, int secondPlayerProgramTypeArgument,
                     String firstPlayerRunCommandArgument, String secondPlayerRunCommandArgument, int computerPlayerType) {
        setTitle("Bricks");
        setSize(600, 650);
        setMinimumSize(new Dimension(550, 650));
        setResizable(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored) {

        }
        BoardSize = initialBoardSize;
        playerFirstColor = firstPlayerColor;
        playerSecondColor = secondPlayerColor;
        this.isSound = isSound;
        this.volume = volume;
        debugMode = debugModeInitialize;

        this.computerPlayerType = computerPlayerType;
        playerFirstFullPath = firstPlayerPath;
        playerSecondFullPath = secondPlayerPath;

        this.firstPlayerProgramType = firstPlayerProgramTypeArgument;
        this.secondPlayerProgramType = secondPlayerProgramTypeArgument;

        this.firstPlayerRunCommand = firstPlayerRunCommandArgument;
        this.secondPlayerRunCommand = secondPlayerRunCommandArgument;

        if (playerFirstFullPath.length() > 7) {
            int i = playerFirstFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerFirstFullPath.charAt(i) == '/' || playerFirstFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerOne = playerFirstFullPath.substring(0, i);
            playerFirstProgramName = playerFirstFullPath.substring(i + 1, playerFirstFullPath.length());
            playerFirstProgramName = playerFirstProgramName.substring(0, playerFirstProgramName.length() - 6);

        }
        if (playerSecondFullPath.length() > 7) {
            int i = playerSecondFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerSecondFullPath.charAt(i) == '/' || playerFirstFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerTwo = playerSecondFullPath.substring(0, i);
            playerSecondProgramName = playerSecondFullPath.substring(i + 1, playerSecondFullPath.length());
            playerSecondProgramName = playerSecondProgramName.substring(0, playerSecondProgramName.length() - 6);
        }

        URL imgURL = this.getClass().getResource("brick-wall.png");
        ImageIcon icon = new ImageIcon(imgURL);
        setIconImage(icon.getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        actualPlayerColorPreview = new ColorPreview(playerFirstColor);

        //OS X AND macOS ONLY, ON WINDOWS/LINUX/UNIX must be set as a comment
       // com.apple.eawt.Application.getApplication().setDockIconImage(icon.getImage());

        gameName = new JLabel("Bricks", SwingConstants.CENTER);
        buttonsGridLayout = new JPanel(new GridLayout(11, 1));
        undoLastMoveButton = new JButton("Cofnij");

        restTiles = new JLabel("Gracz Pierwszy");
        buttonsGridLayout.setBorder(new EmptyBorder(0, getWidth() / 4, 0, getWidth() / 4));
        JButton runSinglePlayer = new JButton("Gra jednoosobowa");
        JButton runMultiPlayer = new JButton("Gra dwuosobowa");
        JButton runComputerPlayers = new JButton("Wojna Robotów");

        runSinglePlayer.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        runSinglePlayer.setFocusPainted(false);
        runMultiPlayer.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        runMultiPlayer.setFocusPainted(false);
        runComputerPlayers.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        runComputerPlayers.setFocusPainted(false);

        runSinglePlayer.addActionListener(e -> {
            gameBorderLayout = new JPanel(new BorderLayout());
            Action backToMenuAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stopRunner();
                    int selection = JOptionPane.showConfirmDialog(null, "Chcesz opuścić grę i wrócić do menu?", "Koniec" +
                            " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (selection == JOptionPane.OK_OPTION) {
                        Bricks.mainFrame.stopGame();
                    }
                }
            };
            boolean computerPlayerFound = false;
            if (this.computerPlayerType == 1) {
                if (firstPlayerProgramType == 0) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(playerFirstFullPath, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
                if (firstPlayerProgramType == 1) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerOne + " " + Bricks.mainFrame.playerFirstProgramName, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
                if (firstPlayerProgramType == 2) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(firstPlayerRunCommand, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
            }
            if (this.computerPlayerType == 2) {
                if (secondPlayerProgramType == 0) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(playerSecondFullPath, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
                if (secondPlayerProgramType == 1) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerTwo + " " + Bricks.mainFrame.playerSecondProgramName, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }

                }
                if (secondPlayerProgramType == 2) {
                    try {
                        Bricks.singlePlayerRobotPlayer = new RobotPlayer(secondPlayerRunCommand, BoardSize);
                        computerPlayerFound = true;
                    } catch (Exception ignored) {
                    }
                }
            }
            if (this.computerPlayerType == 0 || (computerPlayerFound)) {
                gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
                gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");
                board = new BoardLogic(BoardSize);
                undoLastMoveButton.setEnabled(false);
                comp = new ComputerPlayer();
                boardPanel = new BoardPanel(board, 0);
                JPanel southBorderLayout = new JPanel(new BorderLayout());
                southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
                gameBorderLayout.add(southBorderLayout, BorderLayout.SOUTH);
                gameBorderLayout.add(boardPanel, BorderLayout.CENTER);
                this.getContentPane().removeAll();
                this.getContentPane().add(gameBorderLayout);
                this.revalidate();
                this.repaint();
                running = true;
                game = new Thread(this);
                game.start();
                gameBorderLayout.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność programu grającego", "Błąd" +
                        " gry", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
        undoLastMoveButton.addActionListener(e -> boardPanel.undoLastMove());

        runMultiPlayer.addActionListener(e -> {
            gameBorderLayout = new JPanel(new BorderLayout());

            Action backToMenuAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stopRunner();
                    int selection = JOptionPane.showConfirmDialog(null, "Chcesz opuścić grę i wrócić do menu?", "Koniec" +
                            " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (selection == JOptionPane.OK_OPTION) {
                        Bricks.mainFrame.stopGame();
                    }
                }
            };
            actualPlayerColorPreview = new ColorPreview(playerFirstColor);
            gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
            gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");

            board = new BoardLogic(BoardSize);
            undoLastMoveButton.setEnabled(false);
            Bricks.mainFrame.restTiles.setText("Gracz: ");
            JPanel southBorderLayout = new JPanel(new BorderLayout());
            JPanel southLeftGridLayout = new JPanel(new GridLayout(1, 2));

            southLeftGridLayout.add(restTiles);
            southLeftGridLayout.add(actualPlayerColorPreview);
            southBorderLayout.add(southLeftGridLayout, BorderLayout.WEST);
            southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
            gameBorderLayout.add(southBorderLayout, BorderLayout.SOUTH);

            boardPanel = new BoardPanel(board, 1);
            gameBorderLayout.add(boardPanel, BorderLayout.CENTER);
            this.getContentPane().removeAll();
            this.getContentPane().add(gameBorderLayout);
            this.revalidate();
            this.repaint();
            running = true;
            game = new Thread(this);
            game.start();
            gameBorderLayout.requestFocus();
        });
        runComputerPlayers.addActionListener(e -> {
            boolean checkFirstComputerPlayer = false;
            boolean checkSecondComputerPlayer = false;
            if (firstPlayerProgramType == 0) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(playerFirstFullPath, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if (firstPlayerProgramType == 1) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerOne + " " + Bricks.mainFrame.playerFirstProgramName, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }
            if (firstPlayerProgramType == 2) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(firstPlayerRunCommand, BoardSize);
                    checkFirstComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }

            if (secondPlayerProgramType == 0) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(playerSecondFullPath, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if (secondPlayerProgramType == 1) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerTwo + " " + Bricks.mainFrame.playerSecondProgramName, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }

            }
            if (secondPlayerProgramType == 2) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(secondPlayerRunCommand, BoardSize);
                    checkSecondComputerPlayer = true;
                } catch (Exception ignored) {
                }
            }
            if (checkFirstComputerPlayer && checkSecondComputerPlayer) {
                gameBorderLayout = new JPanel(new BorderLayout());
                Action backToMenuAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selection = JOptionPane.showConfirmDialog(null, "Chcesz opuścić grę i wrócić do menu?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (selection == JOptionPane.OK_OPTION) {
                            Bricks.mainFrame.stopGame();
                        }
                    }
                };
                actualPlayerColorPreview = new ColorPreview(playerFirstColor);
                gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
                gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");
                board = new BoardLogic(BoardSize);
                undoLastMoveButton.setEnabled(false);
                comp = new ComputerPlayer();
                boardPanel = new BoardPanel(board, 2);
                computerPlayer = 1;
                JPanel southBorderLayout = new JPanel(new BorderLayout());
                computerPlayerLabel = new JLabel("Gracz Numer " + computerPlayer);
                nextMoveButton = new JButton("Następny Ruch");
                nextMoveButton.addActionListener(e1 -> {
                    boolean gameFinished = false;
                    try {
                        computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
                        board.saveToFile();
                        int move[] = new int[4];
                        if (computerPlayer == 1) {
                            if (boardPanel.movesStorage.isEmpty()) {
                                try {
                                    move = Bricks.firstRobotPlayer.makeMove("Zaczynaj");
                                } catch (InvalidMoveException exception) {
                                    boardPanel.walkover(computerPlayer, "BadMove");
                                    gameFinished = true;
                                } catch (TimeoutException exception) {
                                    boardPanel.walkover(computerPlayer, "Timeout");
                                    gameFinished = true;
                                }
                            } else {
                                try {
                                    move = Bricks.firstRobotPlayer.makeMove(boardPanel.movesStorage.getLastMoveAsString());
                                } catch (InvalidMoveException exception) {
                                    boardPanel.walkover(computerPlayer, "BadMove");
                                    gameFinished = true;
                                } catch (TimeoutException exception) {
                                    boardPanel.walkover(computerPlayer, "Timeout");
                                    gameFinished = true;
                                }
                            }
                        }
                        if (computerPlayer == 2) {
                            if (boardPanel.movesStorage.isEmpty()) {
                                try {
                                    move = Bricks.secondRobotPlayer.makeMove("Zaczynaj");
                                } catch (InvalidMoveException exception) {
                                    boardPanel.walkover(computerPlayer, "BadMove");
                                    gameFinished = true;
                                } catch (TimeoutException exception) {
                                    boardPanel.walkover(computerPlayer, "Timeout");
                                    gameFinished = true;
                                }
                            } else {
                                try {
                                    move = Bricks.secondRobotPlayer.makeMove(boardPanel.movesStorage.getLastMoveAsString());
                                } catch (InvalidMoveException exception) {
                                    boardPanel.walkover(computerPlayer, "BadMove");
                                    gameFinished = true;
                                } catch (TimeoutException exception) {
                                    boardPanel.walkover(computerPlayer, "Timeout");
                                    gameFinished = true;
                                }
                            }
                        }

                        int x1 = move[0];
                        int y1 = move[1];
                        int x2 = move[2];
                        int y2 = move[3];


                        if (possibleMove(x1, y1, x2, y2, board.board) && !gameFinished) {
                            board.board[x1][y1] = computerPlayer;
                            board.board[x2][y2] = computerPlayer;
                            boardPanel.playSound();
                            boardPanel.movesStorage.addMove(x1, y1, x2, y2);


                            if (computerPlayer == 1) {
                                computerPlayer = 2;
                                actualPlayerColorPreview.setColor(playerSecondColor);

                            } else if (computerPlayer == 2) {
                                computerPlayer = 1;
                                actualPlayerColorPreview.setColor(playerFirstColor);
                            }
                            undoLastMoveButton.setEnabled(true);
                        } else if (!gameFinished) {
                            boardPanel.walkover(computerPlayer, "InvalidMove");
                        }
                        repaintThis();
                        repaint();
                        boardPanel.checkNoMoves();

                    } catch (Exception ignored) {

                    }

                });

                JPanel robotWarsControlGridPanel = new JPanel(new GridLayout(1, 4));

                JPanel speedControlGridLayout = new JPanel(new GridLayout(1, 3));
                JTextField speedTextField = new JTextField("1");
                speedTextField.setEditable(false);
                speedTextField.setHorizontalAlignment(SwingConstants.CENTER);

                speedUpButton = new JButton("+");
                speedUpButton.addActionListener(e13 -> {
                    int i = Integer.parseInt(speedTextField.getText());
                    if (i <= 9)
                        i++;
                    speedTextField.setText(i + "");
                });
                speedDownButton = new JButton("-");
                speedDownButton.addActionListener(e12 -> {
                    int i = Integer.parseInt(speedTextField.getText());
                    if (i > 1)
                        i--;
                    speedTextField.setText(i + "");
                });

                speedControlGridLayout.add(speedDownButton);
                speedControlGridLayout.add(speedTextField);
                speedControlGridLayout.add(speedUpButton);

                robotWarsControlGridPanel.add(speedControlGridLayout);

                robotWarsControlGridPanel.add(nextMoveButton);
                autoPlayButton = new JButton("Rozgrywki");
                autoPlayFrame = new AutoGamesFrame(this);
                autoPlayButton.addActionListener(action -> {
                    int selection = 0;
                    if(!boardPanel.movesStorage.isEmpty()) {
                        selection = JOptionPane.showConfirmDialog(null, "Spowoduje to zakończenie obecnej gry, kontynuować?", "Koniec" +
                                " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }

                    if (selection == JOptionPane.OK_OPTION || boardPanel.movesStorage.isEmpty()) {
                        stopRunner();
                        try {
                            Bricks.firstRobotPlayer.reset();
                            Bricks.secondRobotPlayer.reset();
                        }
                        catch (Exception ignored){}
                        boardPanel.resetBoard();
                        autoPlayFrame.setVisible(true);
                    }
                });


                runAutoMovesButton = new JButton("Automatyczna Gra");
                runAutoMovesButton.addActionListener(e14 -> {
                    if (runAutoMovesButton.getText().equals("Automatyczna Gra")) {
                        try {
                            runner = new Runner(Integer.parseInt(speedTextField.getText()));
                        } catch (Exception ignored) {
                        }
                        Bricks.autoPlayRunning = true;
                        runner.start();
                        controlAutoPlayButtons(false);
                    } else {
                        Bricks.autoPlayRunning = false;
                        controlAutoPlayButtons(true);
                    }
                });
                robotWarsControlGridPanel.add(runAutoMovesButton);
                robotWarsControlGridPanel.add(autoPlayButton);

                southBorderLayout.add(robotWarsControlGridPanel, BorderLayout.CENTER);


                Bricks.mainFrame.restTiles.setText("Gracz: ");
                JPanel southLeftGridLayout = new JPanel(new GridLayout(1, 2));
                southLeftGridLayout.add(restTiles);
                southLeftGridLayout.add(actualPlayerColorPreview);
                southBorderLayout.add(southLeftGridLayout, BorderLayout.WEST);
                gameBorderLayout.add(southBorderLayout, BorderLayout.SOUTH);
                gameBorderLayout.add(boardPanel, BorderLayout.CENTER);
                this.getContentPane().removeAll();
                this.getContentPane().add(gameBorderLayout);
                this.revalidate();
                this.repaint();
                running = true;
                game = new Thread(this);
                game.start();
                gameBorderLayout.requestFocus();
            } else if (checkFirstComputerPlayer) {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność drugiego programu grającego", "Błąd" +
                        " gry", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            } else if (checkSecondComputerPlayer) {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność pierwszego programu grającego", "Błąd" +
                        " gry", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność obu programów grających", "Błąd" +
                        " gry", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }

        });
        JButton optionsButton = new JButton("Opcje");
        optionsButton.addActionListener(e -> {
            JPanel optionsPanel = new JPanel(new BorderLayout());
            OptionsPanel opcje = new OptionsPanel(new Settings(BoardSize, playerFirstColor, playerSecondColor,
                    this.isSound, this.volume, this.debugMode, playerFirstFullPath, playerSecondFullPath,
                    firstPlayerProgramType, secondPlayerProgramType, firstPlayerRunCommand, secondPlayerRunCommand, this.computerPlayerType));
            optionsPanel.add(opcje, BorderLayout.CENTER);
            this.getContentPane().removeAll();
            this.getContentPane().add(optionsPanel);
            revalidate();
            repaint();
        });
        optionsButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        optionsButton.setFocusPainted(false);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getWidth() < 800)
                    buttonsGridLayout.setBorder(new EmptyBorder(0, getWidth() / 4, 0, getWidth() / 4));
                else
                    buttonsGridLayout.setBorder(new EmptyBorder(0, getWidth() / 3, 0, getWidth() / 3));
                repaint();
            }
        });
        JButton exitButton = new JButton("Wyjście");
        exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        credits = new JLabel("<html><center>Autorzy: Mateusz Kalinowski, Michał Romaszko <br> Wersja: 1.0.0, Ikona: Madebyoliver, www.flaticon.com</center></html>");
        credits.setHorizontalAlignment(SwingConstants.CENTER);


        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runSinglePlayer);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runMultiPlayer);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runComputerPlayers);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(optionsButton);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(exitButton);
        buttonsGridLayout.add(new JLabel());


        gameName.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        gameName.setForeground(Color.BLACK);

        prepareMainBorderLayout();
        add(mainBorderLayout);
    }


    private void prepareMainBorderLayout() {
        mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(credits, BorderLayout.SOUTH);
        mainBorderLayout.add(buttonsGridLayout, BorderLayout.CENTER);
        mainBorderLayout.add(gameName, BorderLayout.NORTH);
    }

    public void stopGame() {
        game.interrupt();
        running = false;
        stopRunner();
        if (Bricks.firstRobotPlayer != null)
            Bricks.firstRobotPlayer.killRobot();
        if (Bricks.secondRobotPlayer != null)
            Bricks.secondRobotPlayer.killRobot();
        if (Bricks.singlePlayerRobotPlayer != null)
            Bricks.singlePlayerRobotPlayer.killRobot();
        this.getContentPane().removeAll();
        prepareMainBorderLayout();
        this.getContentPane().add(mainBorderLayout);
        this.getContentPane().repaint();
        validate();
        repaint();
    }

    void backToMenu() {
        this.getContentPane().removeAll();
        prepareMainBorderLayout();
        this.getContentPane().add(mainBorderLayout);
        this.getContentPane().repaint();
        validate();
        repaint();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int FRAMERATE = 60;
        double nsPerTick = 1000000000D / FRAMERATE;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        boolean shouldRender = false;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                shouldRender = true;
                delta -= 1;
            }
            if (shouldRender) {
                boardPanel.render();
                shouldRender = false;
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
            }
        }
    }

    private void exportSettings() {
        try {
            PrintWriter createCfg = new PrintWriter(new File(Bricks.path + "/options"));
            createCfg.println("BoardSize=" + BoardSize);
            createCfg.println("FirstColor=" + playerFirstColor.toString());
            createCfg.println("SecondColor=" + playerSecondColor.toString());
            createCfg.println("sound=" + isSound);
            createCfg.println("volume=" + volume);
            createCfg.println("debugMode=" + debugMode);
            createCfg.println("firstPlayer=" + playerFirstFullPath);
            createCfg.println("secondPlayer=" + playerSecondFullPath);
            createCfg.println("firstPlayerProgramType=" + firstPlayerProgramType);
            createCfg.println("secondPlayerProgramType=" + secondPlayerProgramType);
            createCfg.println("firstPlayerRunCommand=" + firstPlayerRunCommand);
            createCfg.println("secondPlayerRunCommand=" + secondPlayerRunCommand);
            createCfg.println("computerPlayerType=" + computerPlayerType);
            createCfg.close();
        } catch (Exception ignored) {

        }
    }

    public void repaintThis() {
        repaint();
    }

    void setSettings(Settings newSettings) {
        BoardSize = newSettings.getBoardSize();
        playerFirstColor = newSettings.getPlayerFirstColor();
        playerSecondColor = newSettings.getPlayerSecondColor();
        this.isSound = newSettings.getIsSound();
        this.volume = newSettings.getVolume();
        this.debugMode = newSettings.getDebugMode();
        playerFirstFullPath = newSettings.getFirstComputerPlayerPath();
        playerSecondFullPath = newSettings.getSecondComputerPlayerPath();
        computerPlayerType = newSettings.getComputerPlayerType();
        if (playerFirstFullPath.length() > 7) {
            int i = playerFirstFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerFirstFullPath.charAt(i) == '/' || playerFirstFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerOne = playerFirstFullPath.substring(0, i);
            playerFirstProgramName = playerFirstFullPath.substring(i + 1, playerFirstFullPath.length());
            playerFirstProgramName = playerFirstProgramName.substring(0, playerFirstProgramName.length() - 6);

        }
        if (playerSecondFullPath.length() > 7) {
            int i = playerSecondFullPath.length() - 1;
            for (; i > 0; i--) {
                if (playerSecondFullPath.charAt(i) == '/' || playerSecondFullPath.charAt(i) == '\\')
                    break;
            }
            pathToPlayerTwo = playerSecondFullPath.substring(0, i);
            playerSecondProgramName = playerSecondFullPath.substring(i + 1, playerSecondFullPath.length());
            playerSecondProgramName = playerSecondProgramName.substring(0, playerSecondProgramName.length() - 6);
        }
        firstPlayerProgramType = newSettings.getFirstPlayerProgramType();
        secondPlayerProgramType = newSettings.getSecondPlayerProgramType();

        firstPlayerRunCommand = newSettings.getFirstPlayerRunCommand();
        secondPlayerRunCommand = newSettings.getSecondPlayerRunCommand();
        exportSettings();
    }

    public boolean possibleMove(int x1, int y1, int x2, int y2, int[][] board) {
        if (board[x1][y1] != 0 || board[x2][y2] != 0)
            return false;
        boolean flag = false;

        if (y1 == y2) {
            if (x1 + 1 == x2)
                flag = true;
            if (x1 - 1 == x2)
                flag = true;
        }
        if (x1 == x2) {
            if (y1 + 1 == y2)
                flag = true;
            if (y1 - 1 == y2)
                flag = true;
        }

        return flag;
    }

    public void stopRunner() {
        Bricks.autoPlayRunning = false;
        controlAutoPlayButtons(true);
    }

    public int getComputerPlayerType() {
        return computerPlayerType;
    }

    private void controlAutoPlayButtons(boolean turnOn) {
        if (turnOn) {
            if (speedUpButton != null) {
                speedUpButton.setEnabled(true);
                speedDownButton.setEnabled(true);
                runAutoMovesButton.setText("Automatyczna Gra");
                nextMoveButton.setEnabled(true);
                autoPlayButton.setEnabled(true);
            }
        } else {
            if (speedUpButton != null) {
                speedUpButton.setEnabled(false);
                speedDownButton.setEnabled(false);
                runAutoMovesButton.setText("Przerwij");
                nextMoveButton.setEnabled(false);
                autoPlayButton.setEnabled(false);
            }
        }
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    private Thread game;

    public BoardLogic board;

    public BoardPanel boardPanel;

    private boolean debugMode;
    private boolean running = false;
    public boolean isSound;

    public ComputerPlayer comp;

    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    public int volume;
    public int computerPlayer;
    private int BoardSize;
    private int computerPlayerType;

    public ColorPreview actualPlayerColorPreview;

    public Color playerFirstColor;
    public Color playerSecondColor;

    private String playerFirstFullPath = "";
    private String playerSecondFullPath = "";
    private String pathToPlayerOne = "";
    private String pathToPlayerTwo = "";
    private String playerFirstProgramName = "";
    private String playerSecondProgramName = "";
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    public Runner runner;

    private JButton runAutoMovesButton;
    private JButton speedUpButton;
    private JButton speedDownButton;
    private JButton nextMoveButton;
    public JButton undoLastMoveButton;
    private JButton autoPlayButton;

    private JPanel gameBorderLayout;
    private JPanel mainBorderLayout;
    private JPanel buttonsGridLayout;

    public JLabel computerPlayerLabel;
    private JLabel gameName;
    private JLabel credits;
    public JLabel restTiles;

    public AutoGamesFrame autoPlayFrame;

}
