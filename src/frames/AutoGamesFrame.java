package frames;

import core.Bricks;
import javafx.scene.input.KeyCode;
import logic.AutoGameThread;
import logic.BoardLogic;
import logic.MovesStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Mateusz on 14.12.2016.
 * Project Bricks
 */
public class AutoGamesFrame extends JDialog {
    public AutoGamesFrame(JFrame owner){
        super(owner,true);
        setSize(250,300);
        exitButton = new JButton("Powrót");
        exitButton.addActionListener(e -> setVisible(false));

        mainBorderLayout = new JPanel(new BorderLayout());
        bottomGridLayout = new JPanel(new GridLayout(1,3));
        progressBarGridLayout = new JPanel(new GridLayout(2,1));
        contentGridLayout = new JPanel(new GridLayout(2,1));
        listGridLayout = new JPanel(new GridLayout(1,3));
        //MAIN CONTENT LAYOUT

        final DefaultListModel<String> boardSizesListModel = new DefaultListModel<>();
        boardSizesList = new JList<>();
        boardSizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boardSizesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        boardSizesList.setVisibleRowCount(-1);
        boardSizesList = new JList<>(boardSizesListModel);
        boardSizesList.setPrototypeCellValue("X:   Y:   ");
        boardSizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        KeyListener[] lsnrs = boardSizesList.getKeyListeners();
        for (int i = 0; i < lsnrs.length; i++) {
            boardSizesList.removeKeyListener(lsnrs[i]);
        }
        JScrollPane boardSizesListScrollPane = new JScrollPane(boardSizesList);
        boardSizesListModel.addElement("");
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
                String currentText = boardSizesListModel.get(selectedIndex);
                if(selectedIndex>=0) {
                    if (e.getKeyCode() == KeyEvent.VK_1) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"1");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_2) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"2");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_3) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"3");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_4) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"4");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_5) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"5");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_6) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"6");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_7) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"7");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_8) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"8");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_9) {
                        if(currentText.length()<=2)
                            boardSizesListModel.set(selectedIndex,currentText+"9");
                        repaint();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_0) {
                        if(currentText.length()<=2 && currentText.length()>=1)
                            boardSizesListModel.set(selectedIndex,currentText+"0");

                        repaint();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        if(currentText.length()>=1) {
                            boardSizesListModel.set(selectedIndex,currentText.substring(0,currentText.length()-1));
                        }
                        if(currentText.length()==0 && selectedIndex!=0) {
                            boardSizesListModel.remove(selectedIndex);
                            boardSizesList.setSelectedIndex(selectedIndex-1);
                        }
                        repaint();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if(selectedIndex<boardSizesListModel.getSize()-1 && boardSizesListModel.get(selectedIndex).length()>0) {
                            boardSizesList.setSelectedIndex(selectedIndex+1);
                        }
                        else if(boardSizesListModel.get(selectedIndex).length()>0){
                            boardSizesListModel.addElement("");
                            boardSizesList.setSelectedIndex(selectedIndex+1);
                        }
                        repaint();
                        boardSizesListScrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
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
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> boardsSizes = new ArrayList<Integer>();
                for(int i = 0; i < boardSizesListModel.getSize();i++) {
                    try {
                        boardsSizes.add(Integer.parseInt(boardSizesListModel.get(i)));
                    } catch (Exception ignored) {}
                }
                AutoGameThread autoGameThread = new AutoGameThread(boardsSizes);
                autoGameThread.start();
            }
        });
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
    public void setWinCounts(String s1,String s2) {
        firstPlayerWinsCount.setText(s1);
        secondPlayerWinsCount.setText(s2);
    }
    JList boardSizesList;

    JButton runButton;

    JLabel firstPlayerWinsCount;
    JLabel secondPlayerWinsCount;

    JProgressBar progressBar;

    JButton exitButton;

    JPanel mainBorderLayout;
    JPanel resultsBorderLayout;

    JPanel bottomGridLayout;
    JPanel progressBarGridLayout;
    JPanel contentGridLayout;
    JPanel listGridLayout;
    JPanel resultsGridLayout;

}
