package Vue.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JButton;
import Abstrait.*;



public class SommetG extends JButton {
    private int id;
    private int x;
    private int y;
    private int size = 40;
    private boolean creationEnCours;
    private Color bntC;

    public SommetG(int id, int x, int y, boolean creationEnCours, GrapheG grapheG) {

        super(Integer.toString(id));
        this.setFont(new Font("Montserrat", Font.BOLD, 10));
        this.setForeground(Abstract.textColor);
        this.creationEnCours = creationEnCours;

        this.bntC = Abstract.appColor;

        this.id = id;
        this.x = x;
        this.y = y;

        this.setBounds(x, y, size, size);
        setFocusable(false);

        // ---------------pour mettre le bouton comme un cercle ----------------------

        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (creationEnCours) {
                    int newX = SommetG.this.getX() + e.getX() - SommetG.this.x;
                    int newY = SommetG.this.getY() + e.getY() - SommetG.this.y;
                    SommetG.this.setLocation(newX, newY);
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (creationEnCours) {
                    SommetG.this.x = e.getX();
                    SommetG.this.y = e.getY();
                    System.out.println("sommet " + id + " pressed");
                    CreationGraphe.setSommets(SommetG.this);
                } else { // dans le mode jeu
                    System.out.println("sommet clicked jeu");
                    grapheG.jeuUpdate(id);
                }
            }

            public void mouseEntered(MouseEvent evt) {
                // if (CreationGraphe.second != SommetG.this && CreationGraphe.premier !=
                // SommetG.this)
                // bntC = Abstract.appColor;
            }

            public void mouseExited(MouseEvent e) {
                setFocusable(false);
            }
        });

        repaint();
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(bntC);
        } else {
            g.setColor(getBackground());
        }
        g.setColor(bntC);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    public boolean getCreationEnCours() {
        return this.creationEnCours;
    }

    public void setCreationEnCours(boolean setC) {
        this.creationEnCours = setC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + id + ",x : " + x + ", y: " + y;
    }

    public int returnSize() {
        return size;
    }

    public void setBtnColor(Color c) {
        this.bntC = c;
    }

    public void setImage(boolean b) {
        if (b) {
            this.bntC = Color.WHITE;
            super.setText("");
        } else {
            super.setText(Integer.toString(id));
            this.bntC = Abstract.appColor;
        }
    }
}
