package gfx;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Mateusz on 05.10.2016.
 * Project Bricks
 */
public class ColorPreview extends JPanel {
    public ColorPreview(Color color){
        this.color = color;
    }
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRoundRect(getWidth()/10,getHeight()/10,getWidth() - 2*(getWidth()/10),getHeight() - 2*(getHeight()/10),15,15);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    Color color;

}