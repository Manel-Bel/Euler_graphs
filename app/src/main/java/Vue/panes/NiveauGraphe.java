package Vue.panes;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.*;

import model.Database;
import java.awt.*;
import java.awt.event.*;

public class NiveauGraphe extends JPanel {

    private JLabel title;
    private JButton retourner;
    private JPanel boutons;
    private JLabel ligne = Abstract.getDecoDragon();
    private int modeDeJeu, nbgraphe;
    private ViewController view;
    private char niveau;
    private String file;

    // attribut for the resizing java components
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;

    private Action echape;

    public NiveauGraphe(ViewController view, int modeDeJeu, char niveau) {

        // ---------------touches-----------------------
        Action echape, retourArr;

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new Retour(view, this);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        this.modeDeJeu = modeDeJeu;
        setFile(niveau);
        this.niveau = niveau;
        this.nbgraphe = Database.getNbrGraph(file);

        this.view = view;

        title = Abstract.titreJlabel("Niveau", Abstract.titleSize);

        this.retourner = new JButton("");
        retourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Retour.retourArriere(view, NiveauGraphe.this);
            }
        });

        boutons = new JPanel();
        boutons.setBackground(Abstract.appColor);
        boutons.setLayout(new GridBagLayout());

        setStyle();
        addElements();

        // To resize all component after JFrame resized
        this.currentFrameHeight = Abstract.HEIGHT;
        this.currentFrameWidth = Abstract.WIDTH;
         resize = new Resize(this);
 
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                currentFrameWidth = e.getComponent().getWidth();
                currentFrameHeight = e.getComponent().getHeight();
                // System.out.println(e.getComponent());
                resize.setCurrentH(currentFrameHeight);
                resize.setCurrentW(currentFrameWidth);
                resize.ResizeAllComponent();
                resetAllComponentFont();

            }
        });

    }

    private void setFile(char niv) {
        if (niv == 'd')
            file = "grapheD.txt";
        else {
            if (niv == 'm')
                file = "grapheM.txt";
            else
                file = "grapheF.txt";
        }
    }

    private void setStyle() {
        this.setLayout(null);
        this.setBackground(Abstract.appColor);
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

        retourner.setIcon(Abstract.iconRetour);
        retourner.setBackground(Abstract.appColor);
        retourner.setBorderPainted(false);
        retourner.setBounds(10, 10, Abstract.iconRetour.getIconWidth() + 10, Abstract.iconRetour.getIconHeight() + 10);
        this.retourner.setFocusable(false);

        title.setBounds((Abstract.WIDTH - title.getPreferredSize().width) / 2, retourner.getY() + 40,
                title.getPreferredSize().width + 10,
                title.getPreferredSize().height + 10);

        this.ligne.setBounds(200, this.title.getY() + this.title.getPreferredSize().height + 30, Abstract.WIDTH,
                ligne.getPreferredSize().height);

        boutons.setBackground(Abstract.appColor);
        boutons.setBounds(((Abstract.WIDTH - 800) / 2), ligne.getY() + ligne.getHeight() + 40, 800,
                (nbgraphe / 2 + 1) * 90);

    }

    private void addButtons() {
        boutons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        System.out.println("nbb graphes " + nbgraphe);
        for (int i = 0; i < nbgraphe - 1; i++) {
            JButton b = Abstract.createbutton("Graphe " + (i + 1), Abstract.bntWidth, Abstract.btnheight,
                    Abstract.textSize);
            b.getText();
            boutons.add(b, c);
            if ((i + 1) % 2 == 1)
                c.gridx++;
            else {
                c.gridy++;
                c.gridx--;
            }

        }
        if (nbgraphe % 2 == 1) {
            c.gridwidth = 2;
        }
        JButton b = Abstract.createbutton("Graphe " + nbgraphe, Abstract.bntWidth, Abstract.btnheight,
                Abstract.textSize);
        boutons.add(b, c);
        addListBtn();
    }

    private void addElements() {
        addButtons();
        this.add(retourner);
        this.add(title);
        this.add(ligne);
        this.add(boutons);
    }

    private void resetAllComponentFont() {

        for (int i = 0; i < this.getComponentCount(); i++) {
            this.getComponent(i).setFont(Abstract.changeSizeFontG(currentFrameWidth));
            if (this.getComponent(i).getClass() == JPanel.class) {
                resetComponentFont((JPanel) this.getComponent(i));
            }
            // panel.getComponent(i).setPreferredSize(new Dimension(300,60));
        }

        ligne.setFont(Abstract.changeSizeFontFantasy(currentFrameWidth));
        title.setFont(Abstract.changeSizeTitleFont(currentFrameWidth));
    }

    private void resetComponentFont(JPanel j) {
        for (int i = 0; i < j.getComponentCount(); i++) {
            j.getComponent(i).setFont(Abstract.changeSizeFontG(currentFrameWidth));
        }

    }

    private void addListBtn() {
        for (int i = 0; i < nbgraphe; i++) {
            // boutons.getComponent(i).addMouseListener(new MouseAdapter() {
            // public void mouseEntered(MouseEvent evt) {
            // Sound.btn();
            // view.goto
            // }
            //
            // public void mouseExited(MouseEvent evt) {
            // }
            // });
            JButton s = (JButton) boutons.getComponent(i);
            // boutons.getComponent(i).addMouseListener(new MouseAdapter() {
            // public void mouseEntered(MouseEvent evt) {
            // //
            // view.goToNiveauGraphe(Integer.parseInt(s.getText().charAt(s.getText().length()
            // // - 1) + ""));
            // System.out.println(Integer.parseInt(s.getText().charAt(s.getText().length() -
            // 1) + ""));
            // }
            //
            // });
            s.addActionListener(e -> {
                view.goToGame(modeDeJeu, niveau,
                        Integer.parseInt(s.getText().charAt(s.getText().length() - 1) + "") - 1);
            });
        }

    }

}
