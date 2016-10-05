package frames;

import core.Bricks;
import core.Settings;
import gfx.BoardPanel;
import logic.BoardLogic;
import logic.ComputerPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Created by Mateusz on 20.05.2016.
 * Project Bricks
 */
public class MainFrame extends JFrame implements Runnable {

    public MainFrame() {
        setTitle("Bricks");
        setSize(600, 600);
        setMinimumSize(new Dimension(550, 500));
        setResizable(true);

        URL imgURL = this.getClass().getResource("brick-wall.png");
        ImageIcon icon = new ImageIcon(imgURL);
        setIconImage(icon.getImage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {

        }

        optionsDialog = new OptionsFrame();

        gameName = new JLabel("Bricks", SwingConstants.CENTER);
        buttonsGridLayout = new JPanel(new GridLayout(9, 1));
        undoLastMoveButton = new JButton("Cofnij");

        restTiles = new JLabel("Gracz Pierwszy");
        buttonsGridLayout.setBorder(new EmptyBorder(0, getWidth() / 4, 0, getWidth() / 4));
        JButton runSinglePlayer = new JButton("Gra jednoosobowa");
        JButton runMultiPlayer = new JButton("Gra dwuosobowa");

        runSinglePlayer.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        runSinglePlayer.setFocusPainted(false);
        runMultiPlayer.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        runMultiPlayer.setFocusPainted(false);
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
            Bricks.mainFrame.restTiles.setText("Gracz Pierwszy");
            JPanel southBorderLayout = new JPanel(new BorderLayout());
            gametype = 1;
            southBorderLayout.add(restTiles, BorderLayout.WEST);
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

        JButton optionsButton = new JButton("Opcje");
        optionsButton.addActionListener(e -> {
            Settings newSettings = optionsDialog.showDialog(new Settings(BoardSize, playerFirstColor, playerSecondColor));
            if (optionsDialog.wasSaveClicked) {
                BoardSize = newSettings.getBoardSize();
                playerFirstColor = newSettings.getPlayerFirstColor();
                playerSecondColor = newSettings.getPlayerSecondColor();
            }
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

        credits = new JLabel("<html><center>Autor: Mateusz Kalinowski @2016 Wersja: 0.9.6, Icon made by Madebyoliver from www.flaticon.com, <br> Kod źródłowy na www.github.com/mateuszkalinowski/Bricks</center></html>");
        credits.setHorizontalAlignment(0);


        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runSinglePlayer);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runMultiPlayer);
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

    //private void tick(int ticks) {}
    private OptionsFrame optionsDialog;
    private Thread game;
    private BoardLogic board;
    private BoardPanel boardPanel;
    private JPanel gameBorderLayout;
    public boolean running = false;
    public ComputerPlayer comp;

    public Color playerFirstColor = new Color(69, 136, 58);

    public Color playerSecondColor = new Color(238, 44, 44);

    public int BoardSize = 5;

    private JPanel mainBorderLayout;
    private JPanel buttonsGridLayout;

    private JLabel gameName;
    private JLabel credits;

    private int gametype = -1;

    public JLabel restTiles;
    public JButton undoLastMoveButton;
}
