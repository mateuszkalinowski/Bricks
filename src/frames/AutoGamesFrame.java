package frames;

import core.Bricks;
import logic.AutoGameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mateusz on 14.12.2016
 * Project Bricks
 */
public class AutoGamesFrame extends JDialog {
    AutoGamesFrame(JFrame owner){
        super(owner,true);
        setSize(270,350);
        exitButton = new JButton("Powrót");
        JMenuBar mainMenu = new JMenuBar();

        JMenu fileMenu = new JMenu("Plik");
        JMenu editMenu = new JMenu("Edycja");
        mainMenu.add(fileMenu);
        mainMenu.add(editMenu);

        setJMenuBar(mainMenu);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Bricks.firstRobotPlayer.reset();
                    Bricks.secondRobotPlayer.reset();
                    exportPoints();
                }catch (Exception ignored) {}
            }
        });
        exitButton.addActionListener(e -> {
            setVisible(false);
            try {
                Bricks.firstRobotPlayer.reset();
                Bricks.secondRobotPlayer.reset();
                exportPoints();
            }catch (Exception ignored) {}
        });
        setLocationRelativeTo(null);

        mainBorderLayout = new JPanel(new BorderLayout());
        bottomGridLayout = new JPanel(new GridLayout(1,3));
        progressBarGridLayout = new JPanel(new GridLayout(2,1));
        contentGridLayout = new JPanel(new GridLayout(2,1));
        listGridLayout = new JPanel(new GridLayout(1,3));
        //MAIN CONTENT LAYOUT

        boardSizesList = new JList<>();
        boardSizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boardSizesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        boardSizesList.setVisibleRowCount(-1);
        boardSizesList = new JList<>(boardSizesListModel);
        boardSizesList.setPrototypeCellValue("X:   Y:   ");
        boardSizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        KeyListener[] lsnrs = boardSizesList.getKeyListeners();
        for (KeyListener lsnr : lsnrs) {
            boardSizesList.removeKeyListener(lsnr);
        }
        JScrollPane boardSizesListScrollPane = new JScrollPane(boardSizesList);
        importPoints();
        boardSizesList.setSelectedIndex(0);
        boardSizesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                int selectedIndex = boardSizesList.getSelectedIndex();
                if (boardSizesListModel.size() > 0 && selectedIndex>=0) {
                    String currentText = boardSizesListModel.get(selectedIndex);
                    if (selectedIndex >= 0) {
                        if (e.getKeyCode() == KeyEvent.VK_1) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "1");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_2) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "2");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_3) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "3");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_4) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "4");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_5) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "5");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_6) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "6");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_7) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "7");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_8) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "8");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_9) {
                            if (currentText.length() <= 2)
                                boardSizesListModel.set(selectedIndex, currentText + "9");
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_0) {
                            if (currentText.length() <= 2 && currentText.length() >= 1)
                                boardSizesListModel.set(selectedIndex, currentText + "0");

                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            if (currentText.length() >= 2) {
                                boardSizesListModel.set(selectedIndex, currentText.substring(0, currentText.length() - 1));
                            } else if (currentText.length() == 1 && boardSizesListModel.size() > 1) {
                                boardSizesListModel.remove(selectedIndex);
                                boardSizesList.setSelectedIndex(selectedIndex - 1);
                            } else if (currentText.length() == 1 && boardSizesListModel.size() == 1) {
                                boardSizesListModel.set(selectedIndex, "");
                            } else if (currentText.length() == 0 && selectedIndex > 0) {
                                boardSizesListModel.remove(selectedIndex);
                                boardSizesList.setSelectedIndex(selectedIndex - 1);
                            }
                            repaint();
                        }
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (selectedIndex < boardSizesListModel.getSize() - 1 && boardSizesListModel.get(selectedIndex).length() > 0) {
                                boardSizesList.setSelectedIndex(selectedIndex + 1);
                            } else if (boardSizesListModel.get(selectedIndex).length() > 0) {
                                boardSizesListModel.addElement("");
                                boardSizesList.setSelectedIndex(selectedIndex + 1);
                                boardSizesListScrollPane.getVerticalScrollBar().setValue(boardSizesListScrollPane.getVerticalScrollBar().getMaximum());
                            }

                            repaint();
                        }
                    }
                }
            }
        });


        resultsBorderLayout = new JPanel(new BorderLayout());
        resultsGridLayout = new JPanel(new GridLayout(5,2));
        resultsBorderLayout.add(resultsGridLayout,BorderLayout.CENTER);

        firstPlayerWinsCount = new JLabel("0");
        firstPlayerWinsCount.setHorizontalAlignment(SwingConstants.CENTER);
        secondPlayerWinsCount = new JLabel("0");
        secondPlayerWinsCount.setHorizontalAlignment(SwingConstants.CENTER);

        resultsGridLayout.add(new JLabel("Wygranych:"));
        resultsGridLayout.add(new JLabel());
        resultsGridLayout.add(new JLabel("Komputer Pierwszy:"));
        resultsGridLayout.add(firstPlayerWinsCount);
        resultsGridLayout.add(new JLabel());
        resultsGridLayout.add(new JLabel());
        resultsGridLayout.add(new JLabel("Komputer Drugi:"));
        resultsGridLayout.add(secondPlayerWinsCount);

        runButton = new JButton("Uruchom");
        runButton.addActionListener(e -> {
            if(runButton.getText().equals("Uruchom")) {
                setProgress(0);
                ArrayList<Integer> boardsSizes = new ArrayList<Integer>();
                for (int i = boardSizesListModel.getSize() - 1; i >= 0; i--) {
                    try {
                        int toAdd = Integer.parseInt(boardSizesListModel.get(i));
                        if (toAdd >= 5 && toAdd <= 255 && toAdd % 2 == 1) {
                            boardsSizes.add(toAdd);
                        } else {
                            boardSizesListModel.remove(i);
                        }
                    } catch (Exception ignored) {
                    }
                }
                if(boardSizesListModel.size()==0)
                    boardSizesListModel.addElement("");
                if (boardsSizes.size() > 0) {
                    autoGameThread = new AutoGameThread(boardsSizes);
                    autoGameThread.running = true;
                    autoGameThread.start();
                    runButton.setText("Przerwij");
                    setWinCounts("0","0");
                } else {
                    setProgress(100);
                }
            }
            else {
                autoGameThread.running = false;
                runButton.setText("Uruchom");
            }
        });

        JMenuItem exportPointsAction = new JMenuItem("Eksportuj Punkty");

        exportPointsAction.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showSaveDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                String filename = chooseFile.getSelectedFile().getPath();
                PrintWriter writer;
                try {
                    writer = new PrintWriter(filename, "UTF-8");
                    for (int i = 0; i < boardSizesListModel.size(); i++)
                        writer.println(boardSizesListModel.getElementAt(i));

                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException exception) {
                    JOptionPane.showMessageDialog(null, "Nie można wyeksportować danych do tego pliku.", "Błąd" +
                            " danych",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JMenuItem importPointsAction = new JMenuItem("Importuj Punkty");

        importPointsAction.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showOpenDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                String filename = chooseFile.getSelectedFile().getPath();
                String line;
                try {
                    Scanner in = new Scanner(new File(filename));
                    boardSizesListModel.removeAllElements();

                    while (in.hasNextLine()) {
                        line = in.nextLine();
                        try {
                            int i = Integer.parseInt(line);
                            if(i>=5 && i<=255 && i%2==1) {
                                boardSizesListModel.addElement(i+"");
                            }
                        } catch (Exception ignored){}
                    }
                    if(boardSizesListModel.isEmpty())
                        boardSizesListModel.addElement("");
                    in.close();
                    repaint();

                } catch (FileNotFoundException exception) {
                    JOptionPane.showMessageDialog(null, "Nie można czytać danych z tego pliku.", "Błąd" +
                            " danych",JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JMenuItem clearListAction = new JMenuItem("Wyczyść Listę");
        clearListAction.addActionListener(e -> {
            boardSizesListModel.removeAllElements();
            boardSizesListModel.addElement("");
            boardSizesList.setSelectedIndex(0);
            repaint();
        });




        fileMenu.add(exportPointsAction);
        fileMenu.add(importPointsAction);

        editMenu.add(clearListAction);

        runButton.setHorizontalAlignment(SwingConstants.CENTER);
        resultsBorderLayout.add(runButton,BorderLayout.SOUTH);

        listGridLayout.add(new JLabel());
        listGridLayout.add(boardSizesListScrollPane);
        listGridLayout.add(new JLabel());

        contentGridLayout.add(listGridLayout);
        contentGridLayout.add(resultsBorderLayout);

        //PONIZEJ CONTENT LAYOUT
        bottomGridLayout.add(new JLabel());
        bottomGridLayout.add(exitButton);
        bottomGridLayout.add(new JLabel());

        progressBar = new JProgressBar();

        progressBarGridLayout.add(progressBar);
        progressBarGridLayout.add(bottomGridLayout);
        mainBorderLayout.add(progressBarGridLayout,BorderLayout.SOUTH);
        mainBorderLayout.add(contentGridLayout,BorderLayout.CENTER);
        add(mainBorderLayout);
    }
    private void importPoints(){
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks/boardsSizes";
            Scanner in = new Scanner(new File(path));
            boardSizesListModel.removeAllElements();
            String line;
            while (in.hasNextLine()) {
                line = in.nextLine();
                    int i = Integer.parseInt(line);
                    if(i>=5 && i<=255 && i%2==1) {
                        boardSizesListModel.addElement(i+"");
                    }
            }
            if(boardSizesListModel.isEmpty())
                boardSizesListModel.addElement("");
            in.close();
            repaint();
        }
        catch (Exception ignored){
            if(boardSizesListModel.isEmpty())
                boardSizesListModel.addElement("");
        }
    }
    private void exportPoints(){
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/boardsSizes").exists()) {
                new File(path + "/boardsSizes").createNewFile();
            }
            String filename = path + "/boardsSizes";
            PrintWriter writer;
                writer = new PrintWriter(filename, "UTF-8");
                for (int i = 0; i < boardSizesListModel.size(); i++)
                    writer.println(boardSizesListModel.getElementAt(i));
                writer.close();

        }
        catch (Exception ignored) {}

    }
    public void setWinCounts(String s1,String s2) {
        firstPlayerWinsCount.setText(s1);
        secondPlayerWinsCount.setText(s2);
        repaint();
    }
    public void setProgress(int value) {
        if(value>=0 && value<=100)
            progressBar.setValue(value);
        repaint();
    }
    public void setRunButton(boolean toSet){
        if(toSet) {
            runButton.setText("Uruchom");
        }
        else
            runButton.setText("Przerwij");
    }
    private JList boardSizesList;

    private JButton runButton;

    private JLabel firstPlayerWinsCount;
    private JLabel secondPlayerWinsCount;

    private JProgressBar progressBar;

    private JButton exitButton;

    private JPanel mainBorderLayout;
    private JPanel resultsBorderLayout;

    private JPanel bottomGridLayout;
    private JPanel progressBarGridLayout;
    private JPanel contentGridLayout;
    private JPanel listGridLayout;
    private JPanel resultsGridLayout;

    private AutoGameThread autoGameThread;
    final DefaultListModel<String> boardSizesListModel = new DefaultListModel<>();

}
