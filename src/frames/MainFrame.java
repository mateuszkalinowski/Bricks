package frames;

import core.Bricks;
import core.Settings;
import gfx.BoardPanel;
import gfx.ColorPreview;
import logic.BoardLogic;
import logic.ComputerPlayer;

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

    public MainFrame(int initialBoardSize,Color firstPlayerColor,Color secondPlayerColor,boolean isSound,int volume,boolean debugModeInitialize) {
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

        URL imgURL = this.getClass().getResource("brick-wall.png");
        ImageIcon icon = new ImageIcon(imgURL);
        setIconImage(icon.getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        actualPlayerColorPreview = new ColorPreview(playerFirstColor);

     /*   try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}*/

      //  com.apple.eawt.Application.getApplication().setDockIconImage(icon.getImage());

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
                    try {
                        computerPlayerLabel.setText("Gracz Numer " + computerPlayer);
                        board.saveToFile();
                        java.util.List<String> command = new ArrayList<>();
                        command.add("java");
                        command.add("-cp");
                        if(computerPlayer==1) {
                            command.add(Bricks.mainFrame.pathToPlayerOne);
                            command.add(Bricks.mainFrame.playerFirstProgramName);
                        }
                        if(computerPlayer==2) {
                            command.add(Bricks.mainFrame.pathToPlayerTwo);
                            command.add(Bricks.mainFrame.playerSecondProgramName);
                        }
                        command.add(Bricks.mainFrame.pathToPlayerOne + "/board.txt");

                        ProcessBuilder builder = new ProcessBuilder(command);
                        final Process process = builder.start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String output = br.readLine();
                        String[] outputDivided = output.split(" ");
                        int x1 = Integer.parseInt(outputDivided[0]);
                        int y1 = Integer.parseInt(outputDivided[1]);
                        int x2 = Integer.parseInt(outputDivided[2]);
                        int y2 = Integer.parseInt(outputDivided[3]);

                        if(board.board[x1][y1]==0 && board.board[x2][y2]==0) {
                            board.board[x1][y1]=computerPlayer;
                            board.board[x2][y2]=computerPlayer;
                            boardPanel.playSound();
                            boardPanel.movesStorage.addMove(x1,y1,x2,y2);


                            if(computerPlayer==1) {
                                computerPlayer = 2;
                                actualPlayerColorPreview.setColor(playerSecondColor);

                            }
                            else if(computerPlayer==2) {
                                computerPlayer = 1;
                                actualPlayerColorPreview.setColor(playerFirstColor);
                            }
                            undoLastMoveButton.setEnabled(true);
                        }
                        repaintThis();
                        repaint();
                        boardPanel.checkNoMoves();

                    }
                    catch(Exception ignored) {

                    }

                }
            });
            southBorderLayout.add(undoLastMoveButton, BorderLayout.EAST);
            southBorderLayout.add(nextMoveButton,BorderLayout.CENTER);

            Bricks.mainFrame.restTiles.setText("Gracz: ");
            JPanel southLeftGridLayout = new JPanel(new GridLayout(1,2));
            southLeftGridLayout.add(restTiles);
            southLeftGridLayout.add(actualPlayerColorPreview);
            southBorderLayout.add(southLeftGridLayout,BorderLayout.WEST);
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
        JButton optionsButton = new JButton("Opcje");
        optionsButton.addActionListener(e -> {
            JPanel optionsPanel = new JPanel(new BorderLayout());
            OptionsPanel opcje = new OptionsPanel(new Settings(BoardSize, playerFirstColor, playerSecondColor,this.isSound,this.volume,this.debugMode,playerFirstFullPath,playerSecondFullPath));
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

        credits = new JLabel("<html><center>Autor: Mateusz Kalinowski @2016 Wersja: 0.9.6, Ikona: Madebyoliver, www.flaticon.com, <br> Kod źródłowy na www.github.com/mateuszkalinowski/Bricks</center></html>");
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

                int index = playerFirstFullPath.length()-1;
                int i = index;
                for(; i>0;i--) {
                    if(playerFirstFullPath.charAt(i) == '/')
                        break;
                }
                pathToPlayerOne = playerFirstFullPath.substring(0,i);
                playerFirstProgramName = playerFirstFullPath.substring(i+1,playerFirstFullPath.length());
                playerFirstProgramName = playerFirstProgramName.substring(0,playerFirstProgramName.length()-6);
                System.out.println(playerFirstProgramName);

                index = playerSecondFullPath.length() - 1;
                i = index;
                for(;i>0;i--) {
                    if(playerSecondFullPath.charAt(i) == '/')
                        break;
                }
                pathToPlayerTwo = playerSecondFullPath.substring(0,i);
                playerSecondProgramName = playerSecondFullPath.substring(i+1,playerSecondFullPath.length());
                playerSecondProgramName = playerSecondProgramName.substring(0,playerSecondProgramName.length()-6);
                exportSettings();
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
