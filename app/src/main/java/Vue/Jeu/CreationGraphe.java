package Vue.Jeu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.*;

import Abstrait.Actions.*;
import Abstrait.*;
import Controller.ViewController;

import model.Database;
import model.Graphe;

public class CreationGraphe extends JPanel implements KeyListener {
    private int idS;
    private JButton retour, sauvegarder, recommencer, addArreteB, valider;
    private JPanel navBar, bottomBar, grapheTmp;
    private LinkedList<SommetG> sommets;
    private JCheckBox addSommet;
    private int currentFrameWidth, currentFrameHeight;
    private Resize resize;
    private LinkedList<LinkedList<Integer>> matrice;
    public static SommetG premier, second;
    private Sauv sauv;
    private boolean oriente;
    private ViewController view;

    private Graphe graphe;

    public CreationGraphe(ViewController view, boolean oriente) {

        this.view = view;
        this.oriente = oriente;

        // ---------------touches----------------------

        Action echape, retourArr, toucheSauv;
        this.addKeyListener(this);

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[3]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new Retour(view, this);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        toucheSauv = new ToucheSauv();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[6]), "toucheSauvAction");
        this.getActionMap().put("toucheSauvAction", toucheSauv);

        sommets = new LinkedList<>();
        matrice = new LinkedList<>();

        // ------------add Sommet check box----------------------
        addSommet = new JCheckBox("Sommet");
        addSommet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (addSommet.isSelected()) {
                    addSommetFonc();
                }
            }
        });

        this.addArreteB = Abstract.createbuttonJeu2("src/main/resources/fleche.png", "");
        this.addArreteB.addActionListener(e -> {
            addArreteFonc();
        });
        this.grapheTmp = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Arrete arrete;
                for (int i = 0; i < matrice.size(); i++) {
                    for (int j : matrice.get(i)) {
                        arrete = new Arrete(sommets.get(i).getX(), sommets.get(i).getY(), sommets.get(j).getX(),
                                sommets.get(j).getY(), sommets.get(j).returnSize());
                        arrete.drawArrete(g2d, 3);
                        if (oriente) {
                            arrete.drawFleche(g2d);
                        }

                    }
                }
            };
        };

        // ------------------------bottomPart -------------------------
        navBar = new JPanel();
        this.navBar.setBounds(0, 0, Abstract.WIDTH, 80);

        retour = Abstract.createbuttonJeu2("src/main/resources/exit.png", "");
        retour.addActionListener(e -> {
            Retour.retourArriere(view, CreationGraphe.this);
        });

        sauvegarder = Abstract.createbuttonJeu2("src/main/resources/sauvegarder.png", "");
        sauvegarder.addActionListener(e -> {
            sauvFenetre();
        });

        recommencer = Abstract.createbuttonJeu2("src/main/resources/recommencer.png", "");
        recommencer.addActionListener(e -> {
            recommencerFonc();
        });

        valider = Abstract.createbuttonJeu2("", "Valide?");
        valider.addActionListener(e -> {
            validerFonc();
        });

        bottomBar = new JPanel();

        // ------------ un panel ------------
        addElements();
        setstyle();

        // To resize all component after JFrame resized
        this.currentFrameHeight = Abstract.HEIGHT;
        this.currentFrameWidth = Abstract.WIDTH;
        resize = new Resize(this);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                currentFrameWidth = e.getComponent().getWidth();
                currentFrameHeight = e.getComponent().getHeight();
                resize.setCurrentH(currentFrameHeight);
                resize.setCurrentW(currentFrameWidth);
                resize.ResizeAllComponent();
            }
        });

    }

    private void addSommetFonc() {
        System.out.println("addSommetFonc");
        SommetG sommet = new SommetG(idS, (Abstract.WIDTH - 25) / 2, (Abstract.HEIGHT - 25) / 2, true,
                null);
        idS++;
        sommets.add(sommet); // ajout dans la liste des sommets
        grapheTmp.add(sommet);
        addSommet.setFocusable(false);
        repaint();
    }

    private void addArreteFonc() {
        if (premier != null && second != null) {
            if (addArreteMatrice(premier.getId(), second.getId())) { // on ajoute un 1 dans la matrice
                System.out.println("Matrice actuelle " + sauvMatrice());
                reInitSommet(); // on remet premier et secon a null
                // grapheTmp.repaint();
                CreationGraphe.this.repaint();
            }

        }
    }

    private boolean addArreteMatrice(int s, int d) {
        int m = (s > d) ? s : d;
        int i = matrice.size() - 1;
        while (i < m) { // si le sommet selec > size , on cree des listes vides
            matrice.add(new LinkedList<Integer>());
            i++;
        }
        if (rechMatrice(s, d) == -1) { // si l'element n'y figure pas
            matrice.get(s).add(d);
            if (!oriente) {
                matrice.get(d).add(s);
            }
            return true;
        }
        return false;
    }

    private int rechMatrice(int s, int d) {
        for (int i = 0; i < matrice.get(s).size(); i++) {
            if (matrice.get(s).get(i) == d) {
                return i;
            }
        }
        return -1;
    }

    public int[][] ListeToMatrice() {
        int n = matrice.size();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new int[matrice.get(i).size()];
            for (int k = 0; k < matrix[i].length; k++) {
                matrix[i][k] = matrice.get(i).get(k).intValue();
            }
        }
        System.out.println(matrix.length);
        System.out.println(n);
        return matrix;
    }

    private String sauvPosition() {
        String position = "";
        for (SommetG sommet : sommets) {
            position += "(" + sommet.getId() + ";" + sommet.getX() + ";" + sommet.getY() + ")";
        }
        System.out.println("Position " + position);
        return position;
    }

    private String sauvMatrice() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.size() - 1; j++) {
                if (rechMatrice(i, j) != -1)
                    builder.append(1 + ",");
                else
                    builder.append(0 + ",");
            }
            if (rechMatrice(i, matrice.size() - 1) != -1)
                builder.append(1 + ";");
            else
                builder.append(0 + ";");
        }
        builder.append(matrice.size()); // enregister le nombre de sommets a la fin
        return (builder.toString());
    }

    private void sauvegardeFile(int i) {
        String f, p;
        if (i == 0) {
            f = "grapheF.txt";
            p = "PositionsF.txt";
        } else if (i == 1) {
            f = "grapheM.txt";
            p = "PositionsM.txt";
        } else {
            f = "grapheD.txt";
            p = "PositionsD.txt";
        }
        Database.appendLineInFile(f, sauvMatrice());
        Database.appendLineInFile(p, sauvPosition());
        System.out.println("sauv in a fine succ");
    }

    private void validerFonc() {
        this.graphe = new Graphe(oriente, sauvMatrice());
        String msg;
        if (oriente) {
            if (graphe.isEulerianDG()) {
                msg = "Votre graphe est bien eulerian";
            } else {
                msg = "votre Graphe n'est pas eulerian ";
            }
        } else {
            if (graphe.isEulirian() == -1)
                msg = "votre Graphe n'est pas eulerian ";
            else
                msg = "Votre graphe est bien eulerian";
        }

        JOptionPane.showMessageDialog(this, msg, "INFORMATION",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void recommencerFonc() {
        System.out.println("recommencer");
        grapheTmp.removeAll();
        sommets.clear();
        grapheTmp.repaint();
        idS = 0;
        reInitSommet();
        System.out.println("removes " + matrice.removeAll(matrice));
    }

    private void sauvFenetre() {
        if (sauv == null) {
            sauv = new Sauv();
        } else
            sauv.setVisible(true);
    }

    public static void setSommets(SommetG s) {
        if (premier == null) {
            premier = s;
            premier.setBtnColor(Color.red);
            System.out.println("premier init");
        } else {
            if (premier != s && second != s) {
                if (second != null)
                    second.setBtnColor(Abstract.appColor);
                second = s;
                second.setBtnColor(Color.red);
            } else {
                reInitSommet();
                System.out.println("reiiniit");
            }
        }
    }

    private static void reInitSommet() {
        if (premier != null) {
            premier.setBtnColor(Abstract.appColor); // remettre les boutons a leur couleur normal
        }
        if (second != null) {
            second.setBtnColor(Abstract.appColor);
        }
        premier = null;
        second = null;
    }

    private void setstyle() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

        addSommet.setFocusable(false);

        navBar.setBackground(Abstract.appColor);
        JLabel titre = Abstract.titreJlabel("Creation", 50);

        this.navBar.setLayout(null);
        titre.setBounds(Abstract.WIDTH / 2 - 150, 10, 300, 50);
        this.navBar.add(titre);
        // Abstract.WIDTH - valider.getPreferredSize().width - 20,
        valider.setBounds(10, (navBar.getHeight() - recommencer.getPreferredSize().height) / 2,
                valider.getPreferredSize().width, recommencer.getPreferredSize().height);
        System.out.println(valider.getPreferredSize().width + " i  " + valider.getPreferredSize().height);
        this.navBar.add(valider);

        bottomBar.setBackground(Abstract.appColor);
        this.bottomBar.setBounds(0, Abstract.HEIGHT - 80, Abstract.WIDTH, 80);
        this.bottomBar.setLayout(new GridBagLayout());
        addElementsbottom();

        grapheTmp.setBounds(0, navBar.getHeight() + navBar.getY(), Abstract.WIDTH,
                bottomBar.getY() - navBar.getHeight());

        System.out.println(Abstract.HEIGHT - navBar.getHeight() - bottomBar.getHeight());
        System.out.println(
                this.getHeight() + " " + this.getPreferredSize().getHeight() + " " + bottomBar.getHeight() + " "
                        + bottomBar.getY());

        grapheTmp.setBackground(Color.WHITE);
        this.grapheTmp.setLayout(null);

        addSommet.setFont(Abstract.textFont);
        addSommet.setBackground(Abstract.appColor);
        addSommet.setForeground(Abstract.textColor);
    }

    private void addElementsbottom() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 25, 0, 25);
        c.gridx = 0;
        c.gridy = 0;
        this.bottomBar.add(retour, c);
        c.gridx++;
        this.bottomBar.add(sauvegarder, c);
        c.gridx++;
        this.bottomBar.add(recommencer, c);
        c.gridx++;
        this.bottomBar.add(addArreteB, c);
        c.gridx++;
        this.bottomBar.add(addSommet, c);
        c.gridx++;
        // this.bottomBar.add(valider, c);
    }

    private void addElements() {
        this.add(navBar);
        this.add(grapheTmp);
        this.add(bottomBar);
    }

    private class Sauv extends JFrame {
        JLabel msg;
        JButton enter;
        JComboBox<String> choix;

        public Sauv() {

            msg = Abstract.gameJlabel("veuillez chosir le niveau", 20);
            msg.setHorizontalAlignment(JLabel.CENTER);
            // LinkedList<String> choixb = new LinkedList<>();
            String[] choixb;
            if (!oriente) {
                choixb = new String[] { "Facile", "Moyen" };
            } else {
                choixb = new String[] { "Difficile" };
            }
            choix = new JComboBox<>(choixb);

            enter = Abstract.createbutton(view.touche.toucheTakan[0], 50, 30, 20);
            enter.addActionListener(e -> {
                if (choixb.length == 1) {
                    sauvegardeFile(2);
                } else
                    sauvegardeFile(choix.getSelectedIndex());

                dispose();
                JOptionPane.showMessageDialog(Sauv.this, "Sauvegarde terminee ", "INFORMATION",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            setStyle();
            addComonents();

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setVisible(true);
            this.pack();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width / 2 - this.getPreferredSize().width / 2,
                    dim.height / 2 - this.getPreferredSize().height / 2 - 50);
        }

        private void addComonents() {
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridwidth = 2;
            c.gridy = 0;
            this.add(msg, c);

            c.gridy++;
            this.add(choix, c);
            c.gridy++;
            this.add(enter, c);

        }

        private void setStyle() {
            this.setTitle("Sauvegarde");
            this.getContentPane().setBackground(Abstract.appColor);
            this.setMinimumSize(new Dimension(300, 200));

            // this.msg.setFont(Abstract.textFont);
            // this.msg.setForeground(Abstract.textColor);

            this.choix.setFont(Abstract.loadFont(20));
            this.choix.setForeground(Abstract.textColor);
            this.choix.setBackground(Abstract.appColor);

        }

    }

    private class ToucheSauv extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            sauvFenetre();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("event keycode " + e.getKeyChar());
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            SommetG s = sommets.get((Integer.parseInt(e.getKeyChar() + "")));
            setSommets(s);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
