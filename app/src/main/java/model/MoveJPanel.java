package model;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MoveJPanel extends JFrame {
    private JPanel panel;
    private int x, y;

    public MoveJPanel() {
        this.setLayout(null);
        panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setBounds(10, 10, 25, 25);
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int newX = panel.getX() + e.getX() - x;
                int newY = panel.getY() + e.getY() - y;
                panel.setLocation(newX, newY);
            }
        });
        add(panel);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // public static void main(String[] args) {
    // new MoveJPanel();
    // }
}