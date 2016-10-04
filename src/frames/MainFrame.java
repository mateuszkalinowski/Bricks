package frames;

import core.Settings;
import gfx.BoardPanel;
import logic.Board;
import logic.ComputerPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Mateusz on 20.05.2016.
 * Project InferenceEngine
 */
public class MainFrame extends JFrame implements Runnable {
    public MainFrame() {
        setTitle("Bricks");
        setSize(600, 600);
        setMinimumSize(new Dimension(550,500));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception unsupportedLookAndFeel) {

        }

        optionsDialog = new OptionsFrame();
        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        gameBorderLayout = new JPanel(new BorderLayout());
        JLabel gameName = new JLabel("Bricks", SwingConstants.CENTER);
        JPanel buttonsGridLayout = new JPanel(new GridLayout(9, 1));
        //boardPanel = new BoardPanel();
        restTiles = new JLabel("Gracz Pierwszy");
        buttonsGridLayout.setBorder(new EmptyBorder(getHeight()/5, getWidth()/5, getHeight()/5, getWidth()/5));
        JButton runSinglePlayer = new JButton("Gra Jednoosobowa");
        JButton runMultiPlayer = new JButton("Gra Dwuosobowa");

        runSinglePlayer.setFont(new Font("Comic Sans MS", Font.BOLD,25));
        runSinglePlayer.setFocusPainted(false);
        runMultiPlayer.setFont(new Font("Comic Sans MS", Font.BOLD,25));
        runMultiPlayer.setFocusPainted(false);
        runSinglePlayer.addActionListener(e -> {
                board = new Board(BoardSize);
                comp = new ComputerPlayer();
                boardPanel = new BoardPanel(board,0);
                gameBorderLayout.add(boardPanel, BorderLayout.CENTER);
                this.getContentPane().removeAll();
                this.getContentPane().add(gameBorderLayout);
                this.revalidate();
                this.repaint();
                running = true;
                game = new Thread(this);
                game.start();
        });
        runMultiPlayer.addActionListener(e -> {
                board = new Board(BoardSize);
            gameBorderLayout.add(restTiles,BorderLayout.SOUTH);
                boardPanel = new BoardPanel(board,1);
                gameBorderLayout.add(boardPanel, BorderLayout.CENTER);
                this.getContentPane().removeAll();
                this.getContentPane().add(gameBorderLayout);
                this.revalidate();
                this.repaint();
                running = true;
                game = new Thread(this);
                game.start();
        });

        JButton optionsButton = new JButton("Opcje");
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings newSettings = optionsDialog.showDialog();
                BoardSize = newSettings.getBoardSize();
                playerFirstColor = newSettings.getPlayerFirstColor();
                playerSecondColor = newSettings.getPlayerSecondColor();
            }
        });
        optionsButton.setFont(new Font("Comic Sans MS", Font.BOLD,25));
        optionsButton.setFocusPainted(false);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buttonsGridLayout.setBorder(new EmptyBorder(0, getWidth()/4, 0, getWidth()/4));
                repaint();
            }
        });
        JButton exitButton = new JButton("Wyjdź");
        exitButton.setFont(new Font("Comic Sans MS", Font.BOLD,25));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        JLabel credits = new JLabel("Autor: Mateusz Kalinowski @2016 Wersja: 0.9.2");
        credits.setHorizontalAlignment(0);
        mainBorderLayout.add(credits,BorderLayout.SOUTH);

        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runSinglePlayer);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(runMultiPlayer);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(optionsButton);
        buttonsGridLayout.add(new JLabel());
        buttonsGridLayout.add(exitButton);
        buttonsGridLayout.add(new JLabel());

        mainBorderLayout.add(buttonsGridLayout, BorderLayout.CENTER);
        gameName.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        gameName.setForeground(Color.BLACK);
        mainBorderLayout.add(gameName, BorderLayout.NORTH);
        add(mainBorderLayout);
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
    private Board board;
    private BoardPanel boardPanel;
    private JPanel gameBorderLayout;
    private boolean running = false;
    public ComputerPlayer comp;

    public Color playerFirstColor = new Color(69, 136, 58);

    public Color playerSecondColor = new Color(238, 44, 44);

    public int BoardSize = 5;

    public JLabel restTiles;
}
