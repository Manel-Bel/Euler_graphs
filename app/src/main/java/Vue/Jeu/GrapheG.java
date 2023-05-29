package Vue.Jeu;

import java.util.LinkedList;
import javax.swing.*;
import javax.swing.event.*;

import Controller.*;
import Vue.Jeu.JeuPane;
import Abstrait.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import model.*;

public class GrapheG extends JPanel implements ActionListener {
    private LinkedList<SommetG> sommets;
    // Liste qui suavegarde les positions des sommets en fonction de leur indice;
    private LinkedList<SommetG> cheminEulerien = new LinkedList<>();
    private Timer timer;
    private Image finger;
    // private ImageIcon carrot;
    private double x = 0, y = 0, x2 = 0, y2 = 0;
    private double xVelocity = 1, yVelocity = 1;
    private boolean indice = false;
    private int compt = -1;
    private Court court;
    private JeuPane jeu;

    public GrapheG(int width, int height, JeuPane jeu) {
        // ------------ graphe modele ----------------
        this.jeu = jeu;
        this.court = jeu.getCourt();
        // this.requestFocusInWindow();

        // --------grapheG -----------------------
        this.setBounds(0, 80, width, height);
        this.setLayout(null);
        this.setBackground(Color.white);

        sommets = new LinkedList<>();
        drawSommet();
        /// charger l'image du pointeur
        File f = new File("src/main/resources/finger.png");
        finger = new ImageIcon(f.getAbsolutePath()).getImage();
        // carrot = new ImageIcon(c.getAbsolutePath());
        timer = new Timer(5, this); // Timer(int delay,ActionListener actionlistener);
        drawRabbitCarrot();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setStroke(new BasicStroke(4));
        Arrete arrete;
        for (int i = 0; i < sommets.size(); i++) {
            int debutJ = court.isgGrapheOriented() ? 0 : i + 1;
            for (int j = debutJ; j < sommets.size(); j++) {
                if (i != j && court.getGraphe().checkArrete(i, j)) {
                    arrete = new Arrete(sommets.get(i).getX(), sommets.get(i).getY(), sommets.get(j).getX(),
                            sommets.get(j).getY(), sommets.get(j).returnSize());
                    if (court.isRoadToken(i, j))
                        arrete.drawArrete(gr, 2);
                    else
                        arrete.drawArrete(gr, 1);
                    if (court.isgGrapheOriented()) {
                        arrete.drawFleche(gr);
                    }
                }
            }
        }
        if (indice){
            drawInteger(gr);
            g.drawImage(finger, (int) Math.round(x), (int) Math.round(y), null);
        }
    }

    public boolean jeuUpdate(int id) {
        if (court.updateSommet(id)) {
            repaint();
            jeu.repaint();
            if (court.fini()) {
                court.getPlayerI(0).incScore(50);
                court.updateBonus(court.getPlayerScore());
                repaint();
                Sound.victory();
                JOptionPane.showMessageDialog(null, "Bravo vous avez reussi");
                try {
                    Thread.sleep(5000);
                    jeu.getView().goToNiveau(court.getMode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x == x2)
            updateposition(++compt);
        if (x != x2) {
            x = x + xVelocity;
            // System.out.println("xvelocity:" + xVelocity);
            // System.out.println("x:" + x + "x2:" + x2);
        }
        if (y != y2) {
            y = y + yVelocity;
        }
        repaint();
    }

    public void updateCoeff(double x1, double y1, double x2, double y2) {
        xVelocity = (x2 < x1) ? -1 : 1;
        double tmp = 0;
        if (x2 != x1) {
            // System.out.println("x:" + x1 + "x2:" + x2);
            // System.out.println("y:" + y1 + "y2:" + y2);
            tmp = (double) (y2 - y1) / (double) (x2 - x1);
        }
        yVelocity = tmp * xVelocity;

        System.out.println("yvelocity:" + yVelocity);

    }

    public void afficheindice() {
        indice = true;
        updateCheminIndice();
        timer.start();
        System.out.println("timer declencher");
    }

    public void updateCheminIndice() {
        cheminEulerien.clear();
        LinkedList<Integer> path;
        if (court.getMode() == 1) {
            path = court.getCheminEuclerien();
        } else {
            path = court.dijkstra();
        }

        for (int i : path) {
            for (SommetG s : sommets) {
                if (s.getId() == i) {
                    cheminEulerien.add(s);
                }
            }
        }

        System.out.println("chemin retourne par Court :" + path);
        System.out.println("chemin mis à jour avec les positions sur le graph:" + cheminEulerien);
        System.out.println(sommets);
    }

    public void updateposition(int compt) {
        System.out.println("comt:" + compt);
        if (compt + 1 < cheminEulerien.size()) {
            // System.out.println("comt:" + compt);
            x = cheminEulerien.get(compt).getX();
            y = cheminEulerien.get(compt).getY();
            x2 = cheminEulerien.get(compt + 1).getX();
            y2 = cheminEulerien.get(compt + 1).getY();
            updateCoeff(x, y, x2, y2);
        } else {
            indice = false;
            this.compt = -1;
            stopTimer();
        }

    }

    public void stopTimer() {
        timer.stop();
    }

    private void drawRabbitCarrot() {
        if (court.getMode() == 2) {
            int d = court.getStart();
            SommetG r = sommets.get(d);
            r.setIcon(Abstract.rabbit);
            r.setImage(true);
            int e = court.getEnd();
            SommetG c = sommets.get(e);
            c.setIcon(Abstract.carrot);
            c.setImage(true);
        }
    }

    private void drawInteger(Graphics g) {
        // Le panel coloré

        // super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        for (int i = 0; i < sommets.size(); i++) {
            for (int j = i + 1; j < sommets.size(); j++) {
                if (court.getGraphe().checkArrete(i, j)) {
                    if (court.getMode() == 2) {
                        int milieuX = (sommets.get(i).getX() + sommets.get(j).getX()) / 2;
                        int milieuY = (sommets.get(i).getY() + sommets.get(j).getY()) / 2;
                        gr.setColor(Color.BLUE);
                        gr.setStroke(new BasicStroke(4));
                        gr.drawString(Integer.toString(court.getWeight(i, j)), milieuX, milieuY);

                    }
                }
            }
        }
    }

    public Court getCourt() {
        return this.court;
    }

    private boolean drawSommet() {
        String position = court.getPositionString();
        if (position == null)
            return false;
        int i = 0;
        while (i < position.length()) {
            String id = "", x = "", y = "";
            if (position.charAt(i) == '(') {
                i++;
                while (position.charAt(i) != ';') {
                    id += position.charAt(i);
                    i++;
                }
                i++;
                while (position.charAt(i) != ';') {
                    x += position.charAt(i);
                    i++;
                }
                i++;
                while (position.charAt(i) != ')') {
                    y += position.charAt(i);
                    i++;
                }

                if (court.getMode() == 2 && Integer.parseInt(String.valueOf(id)) == court.getEnd()) {
                    SommetG ss = new SommetG(Integer.parseInt(String.valueOf(id)),
                            Integer.parseInt(String.valueOf(x)),
                            Integer.parseInt(String.valueOf(y)), false, this);
                    sommets.add(ss);
                    this.add(ss);
                } else {
                    SommetG s = new SommetG(Integer.parseInt(String.valueOf(id)),
                            Integer.parseInt(String.valueOf(x)),
                            Integer.parseInt(String.valueOf(y)), false, this);
                    sommets.add(s);
                    this.add(s);
                }

            }
            i++;
        }
        return true;
    }

}
