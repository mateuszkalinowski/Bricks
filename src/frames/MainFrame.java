package frames;

import core.Bricks;
import core.Settings;
import exceptions.InvalidMoveException;
import gfx.BoardPanel;
import gfx.ColorPreview;
import logic.BoardLogic;
import logic.ComputerPlayer;
import logic.RobotPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class MainFrame extends JFrame implements Runnable {

    public MainFrame(int initialBoardSize,Color firstPlayerColor,Color secondPlayerColor,boolean isSound,int volume,boolean debugModeInitialize,String firstPlayerPath, String secondPlayerPath,
    int firstPlayerProgramTypeArgument,int secondPlayerProgramTypeArgument,
                     String firstPlayerRunCommandArgument,String secondPlayerRunCommandArgument) {
        setTitle("Bricks");
        setSize(600, 650);
        setMinimumSize(new Dimension(550, 650));
        setResizable(true);

        BoardSize = initialBoardSize;
        playerFirstColor = firstPlayerColor;
        playerSecondColor = secondPlayerColor;
        this.isSound = isSound;
        this.volume = volume;
        debugMode = debugModeInitialize;

        playerFirstFullPath = firstPlayerPath;
        playerSecondFullPath = secondPlayerPath;

        this.firstPlayerProgramType = firstPlayerProgramTypeArgument;
        this.secondPlayerProgramType = secondPlayerProgramTypeArgument;

        this.firstPlayerRunCommand = firstPlayerRunCommandArgument;
        this.secondPlayerRunCommand = secondPlayerRunCommandArgument;

            if (playerFirstFullPath.length() > 7) {
                int index = playerFirstFullPath.length() - 1;
                int i = index;
                for (; i > 0; i--) {
                    if (playerFirstFullPath.charAt(i) == '/')
                        break;
                }
                pathToPlayerOne = playerFirstFullPath.substring(0, i);
                playerFirstProgramName = playerFirstFullPath.substring(i + 1, playerFirstFullPath.length());
                playerFirstProgramName = playerFirstProgramName.substring(0, playerFirstProgramName.length() - 6);

            }
            if (playerSecondFullPath.length() > 7) {
                int index = playerSecondFullPath.length() - 1;
                int i = index;
                for (; i > 0; i--) {
                    if (playerSecondFullPath.charAt(i) == '/')
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

       /* try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}*/
        //OS X AND macOS ONLY, ON WINDOWS/LINUX/UNIX must be set as a comment
        com.apple.eawt.Application.getApplication().setDockIconImage(icon.getImage());

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
                    int selection = JOptionPane.showConfirmDialog(null, "Chcesz opuścić grę i wrócić do menu?", "Koniec" +
                            " gry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (selection == JOptionPane.OK_OPTION) {
                        Bricks.mainFrame.stopGame();
                    }
                }
            };

            gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
            gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");
            board = new BoardLogic(BoardSize);
            undoLastMoveButton.setEnabled(false);
            comp = new ComputerPlayer();
            boardPanel = new BoardPanel(board, 0);
            gametype = 0;
            JPanel southBorderLayout = new JPanel(new BorderLayout());

            movesLeftLabel = new JLabel("Pozostały " + board.getPossibleMovesLeft() + " ruchy");
            movesLeftLabel.setHorizontalTextPosition(JLabel.CENTER);
            movesLeftLabel.setHorizontalAlignment(JLabel.CENTER);

            southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
            southBorderLayout.add(movesLeftLabel,BorderLayout.CENTER);
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
        });
        undoLastMoveButton.addActionListener(e -> {
            boardPanel.undoLastMove();
        });

        runMultiPlayer.addActionListener(e -> {
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

            gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
            gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");

            board = new BoardLogic(BoardSize);
            undoLastMoveButton.setEnabled(false);
            Bricks.mainFrame.restTiles.setText("Gracz: ");
            JPanel southBorderLayout = new JPanel(new BorderLayout());
            gametype = 1;
            JPanel southLeftGridLayout = new JPanel(new GridLayout(1,2));


            movesLeftLabel = new JLabel("Pozostały " + board.getPossibleMovesLeft() + " ruchy");
            movesLeftLabel.setHorizontalTextPosition(JLabel.CENTER);
            movesLeftLabel.setHorizontalAlignment(JLabel.CENTER);

            southLeftGridLayout.add(restTiles);
            southLeftGridLayout.add(actualPlayerColorPreview);
            southBorderLayout.add(southLeftGridLayout, BorderLayout.WEST);
            southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
            if(debugMode) {
                southBorderLayout.add(movesLeftLabel);
            }
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
            //TODO CHANGE TO FALSE!
            boolean checkFirstComputerPlayer = false;
            boolean checkSecondComputerPlayer = false;
            if(firstPlayerProgramType==0) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(playerFirstFullPath,BoardSize);
                    checkFirstComputerPlayer = true;
                }
                catch (Exception playerFirstError){
                }
            }
            if(firstPlayerProgramType==1) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerOne +" "+Bricks.mainFrame.playerFirstProgramName,BoardSize);
                    checkFirstComputerPlayer = true;
                }
                catch (Exception playerFirstError){
                }

            }
            if(firstPlayerProgramType==2) {
                try {
                    Bricks.firstRobotPlayer = new RobotPlayer(firstPlayerRunCommand,BoardSize);
                    checkFirstComputerPlayer = true;
                }
                catch (Exception playerFirstError){
                }

            }

            if(secondPlayerProgramType==0) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(playerSecondFullPath,BoardSize);
                    checkSecondComputerPlayer = true;
                }
                catch (Exception playerSecondError){
                }
            }
            if(secondPlayerProgramType==1) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer("java -cp " + Bricks.mainFrame.pathToPlayerTwo +" "+Bricks.mainFrame.playerSecondProgramName,BoardSize);
                    checkSecondComputerPlayer = true;
                }
                catch (Exception playerSecondError){
                }

            }
            if(secondPlayerProgramType==2) {
                try {
                    Bricks.secondRobotPlayer = new RobotPlayer(secondPlayerRunCommand,BoardSize);
                    checkSecondComputerPlayer = true;
                }
                catch (Exception playerSecondError){
                }
            }
            if(checkFirstComputerPlayer && checkSecondComputerPlayer) {
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
                movesLeftLabel = new JLabel();
                gameBorderLayout.getActionMap().put("backToMenuAction", backToMenuAction);
                gameBorderLayout.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ESCAPE"), "backToMenuAction");
                board = new BoardLogic(BoardSize);
                undoLastMoveButton.setEnabled(false);
                comp = new ComputerPlayer();
                boardPanel = new BoardPanel(board, 2);
                gametype = 2;
                computerPlayer = 1;
                JPanel southBorderLayout = new JPanel(new BorderLayout());
                computerPlayerLabel = new JLabel("Gracz Numer " + computerPlayer);
                JButton nextMoveButton = new JButton("Następny Ruch");
                nextMoveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //TODO
                        boolean gameFinished = false;
                        try {
                            computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
                            board.saveToFile();
                            int move[] = new int[4];
                            if(computerPlayer==1) {
                                if(boardPanel.movesStorage.isEmpty()) {
                                    try {
                                        move = Bricks.firstRobotPlayer.makeMove("Zaczynaj");
                                    }
                                    catch (InvalidMoveException exception) {
                                        System.out.println("czas");
                                        boardPanel.walkover(computerPlayer,"Timeout");
                                        gameFinished=true;
                                    }
                                }
                                else {
                                    try {
                                        move = Bricks.firstRobotPlayer.makeMove(boardPanel.movesStorage.getLastMoveAsString());
                                    }
                                    catch (InvalidMoveException exception) {
                                        System.out.println("czas");
                                        boardPanel.walkover(computerPlayer,"Timeout");
                                        gameFinished=true;
                                    }
                                }
                            }
                            if(computerPlayer==2) {
                                if(boardPanel.movesStorage.isEmpty()) {
                                    try {
                                        move = Bricks.secondRobotPlayer.makeMove("Zaczynaj");
                                    } catch (InvalidMoveException exception) {
                                        System.out.println("czas");
                                        boardPanel.walkover(computerPlayer,"Timeout");
                                        gameFinished=true;
                                    }
                                }
                                else {
                                    try {
                                        move = Bricks.secondRobotPlayer.makeMove(boardPanel.movesStorage.getLastMoveAsString());
                                    } catch (InvalidMoveException exception) {
                                        System.out.println("czas");
                                        boardPanel.walkover(computerPlayer,"Timeout");
                                        gameFinished=true;
                                    }
                                }
                            }

                            int x1 = move[0];
                            int y1 = move[1];
                            int x2 = move[2];
                            int y2 = move[3];


                            if(possibleMove(x1,y1,x2,y2,board.board) && !gameFinished)
                             {
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
                            }
                            else if (!gameFinished) {
                                boardPanel.walkover(computerPlayer,"InvalidMove");
                            }
                            repaintThis();
                            repaint();
                            boardPanel.checkNoMoves();

                        } catch (Exception ignored) {

                        }

                    }
                });
                southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
                southBorderLayout.add(nextMoveButton, BorderLayout.CENTER);

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
            }
            else if (checkFirstComputerPlayer && !checkSecondComputerPlayer){
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność drugiego programu grającego", "Błąd" +
                        " gry", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
            else if (!checkFirstComputerPlayer && checkSecondComputerPlayer) {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność pierwszego programu grającego", "Błąd" +
                        " gry", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showConfirmDialog(null, "Sprawdź poprawność obu programów grających", "Błąd" +
                        " gry", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE);
            }

        });
        JButton optionsButton = new JButton("Opcje");
        optionsButton.addActionListener(e -> {
            JPanel optionsPanel = new JPanel(new BorderLayout());
            OptionsPanel opcje = new OptionsPanel(new Settings(BoardSize, playerFirstColor, playerSecondColor,
                    this.isSound,this.volume,this.debugMode,playerFirstFullPath,playerSecondFullPath,
                    firstPlayerProgramType,secondPlayerProgramType,firstPlayerRunCommand,secondPlayerRunCommand));
            optionsPanel.add(opcje,BorderLayout.CENTER);
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
        JButton exitButton = new JButton("Wyjdź");
        exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        credits = new JLabel("<html><center>Autorzy: Mateusz Kalinowski, Michał Romaszko <br> Wersja: 0.9.6, Ikona: Madebyoliver, www.flaticon.com, <br> Kod źródłowy na www.github.com/mateuszkalinowski/Bricks</center></html>");
        credits.setHorizontalAlignment(0);


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
        this.getContentPane().removeAll();
        prepareMainBorderLayout();
        this.getContentPane().add(mainBorderLayout);
        this.getContentPane().repaint();
        validate();
        repaint();
    }
    public void backToMenu() {
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

        // int frames = 0;
        // int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        boolean shouldRender = false;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                shouldRender = true;
                //  ticks++;
                // tick(ticks);
                delta -= 1;
            }
            if (shouldRender) {
                //frames++;
                boardPanel.render();
                shouldRender = false;
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                //frames = 0;
                //  ticks = 0;
            }
        }
    }

    public void exportSettings() {
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
            createCfg.close();
        } catch (Exception ignored) {

        }
    }
    public void repaintThis() {
        repaint();
    }

    public void setSettings(Settings newSettings) {
                BoardSize = newSettings.getBoardSize();
                playerFirstColor = newSettings.getPlayerFirstColor();
                playerSecondColor = newSettings.getPlayerSecondColor();
                this.isSound = newSettings.getIsSound();
                this.volume = newSettings.getVolume();
                this.debugMode = newSettings.getDebugMode();
                playerFirstFullPath = newSettings.getFirstComputerPlayerPath();
                playerSecondFullPath = newSettings.getSecondComputerPlayerPath();
                if(playerFirstFullPath.length()>7) {
                    int index = playerFirstFullPath.length() - 1;
                    int i = index;
                    for (; i > 0; i--) {
                        if (playerFirstFullPath.charAt(i) == '/')
                            break;
                    }
                    pathToPlayerOne = playerFirstFullPath.substring(0, i);
                    playerFirstProgramName = playerFirstFullPath.substring(i + 1, playerFirstFullPath.length());
                    playerFirstProgramName = playerFirstProgramName.substring(0, playerFirstProgramName.length() - 6);

                }
                if(playerSecondFullPath.length()>7) {
                    int index = playerSecondFullPath.length() - 1;
                    int i = index;
                    for (; i > 0; i--) {
                        if (playerSecondFullPath.charAt(i) == '/')
                            break;
                    }
                    pathToPlayerTwo = playerSecondFullPath.substring(0, i);
                    playerSecondProgramName = playerSecondFullPath.substring(i + 1, playerSecondFullPath.length());
                    playerSecondProgramName = playerSecondProgramName.substring(0, playerSecondProgramName.length() - 6);
                }
                firstPlayerProgramType=newSettings.getFirstPlayerProgramType();
                secondPlayerProgramType=newSettings.getSecondPlayerProgramType();

                firstPlayerRunCommand=newSettings.getFirstPlayerRunCommand();
                secondPlayerRunCommand=newSettings.getSecondPlayerRunCommand();
                exportSettings();
    }

    private boolean possibleMove(int x1,int y1,int x2,int y2,int[][] board) {
        if(board[x1][y1]!=0 || board[x2][y2]!=0)
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

        if(flag==false)
            return false;
        return true;
    }

    public boolean getDebugMode(){
        return debugMode;
    }

    //private void tick(int ticks) {}
    private Thread game;
    private BoardLogic board;
    private BoardPanel boardPanel;
    private JPanel gameBorderLayout;
    private boolean debugMode;
    public boolean running = false;
    public ComputerPlayer comp;

    private int firstPlayerProgramType;
    private int secondPlayerProgramType;

    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;


    public  JLabel movesLeftLabel;
    public JLabel computerPlayerLabel;

    public ColorPreview actualPlayerColorPreview;

    public Color playerFirstColor;

    public Color playerSecondColor;

    public boolean isSound;

    public int volume;

    public int computerPlayer;

    public int BoardSize = 5;

    private JPanel mainBorderLayout;
    private JPanel buttonsGridLayout;

    private JLabel gameName;
    private JLabel credits;

    private int gametype = -1;

    public JLabel restTiles;
    public JButton undoLastMoveButton;

    //GRACZE KOMPUTEROWI
    public String playerFirstFullPath = "";
    public String playerSecondFullPath = "";
    public String pathToPlayerOne = "";
    public String pathToPlayerTwo = "";
    public String playerFirstProgramName = "";
    public String playerSecondProgramName = "";
}
