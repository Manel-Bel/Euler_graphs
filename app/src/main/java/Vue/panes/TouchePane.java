package Vue.panes;

import javax.swing.*;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.ViewController;
import model.Database;
import model.Sound;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TouchePane extends JPanel {

        private JLabel title, enterLabel, retourLabel, echapLabel, annulerLabel, indiceLabel, precLabel, sauvLabel,
                        nvUserLabel, enterTouche, retourTouche, echapTouche, annulerTouche, indiceTouche, precTouche,
                        sauvTouche,
                        nvUserTouche;
        private JButton retourner;
        private JLabel ligne = Abstract.getDecoDragon();
        private Database database;
        private JButton sonBtn;

        private int currentFrameWidth, currentFrameHeight;
        private Resize resize;
        private ViewController view;

        private Action echape, retourArr;

        public TouchePane(ViewController view) {

                this.view = view;

                title = Abstract.titreJlabel("Les touches", Abstract.titleSize);
                // title = new JLabel("Les touches ");
                // title.setHorizontalAlignment(JLabel.CENTER);
                // title.setFont(Abstract.titleFont1);
                // title.setForeground(Abstract.titleColor);

                // ---------------touches-----------------------

                this.echape = new Echape(view);
                this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
                this.getActionMap().put("echapeAction", echape);

                this.retourArr = new Retour(view, this);
                this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
                this.getActionMap().put("retourArrAction", retourArr);

                enterLabel = Abstract.gameJlabel("Touche Entrer : ", 30);
                retourLabel = Abstract.gameJlabel("Touche retour : ", 30);
                echapLabel = Abstract.gameJlabel("Touche echap : ", 30);
                indiceLabel = Abstract.gameJlabel("Touche Jeu indice : ", 30);
                annulerLabel = Abstract.gameJlabel("Touche Jeu annuler : ", 30);
                precLabel = Abstract.gameJlabel("Touche Jeu precedant : ", 30);
                sauvLabel = Abstract.gameJlabel("Touche Graphe sauv : ", 30);
                nvUserLabel = Abstract.gameJlabel("Touche User : ", 30);

                enterTouche = Abstract.gameJlabel(view.touche.toucheTakan[0], 30);
                retourTouche = Abstract.gameJlabel(view.touche.toucheTakan[1], 30);
                echapTouche = Abstract.gameJlabel(view.touche.toucheTakan[2], 30);
                indiceTouche = Abstract.gameJlabel(view.touche.toucheTakan[3], 30);
                annulerTouche = Abstract.gameJlabel(view.touche.toucheTakan[4], 30);
                precTouche = Abstract.gameJlabel(view.touche.toucheTakan[5], 30);
                sauvTouche = Abstract.gameJlabel(view.touche.toucheTakan[6], 30);
                nvUserTouche = Abstract.gameJlabel(view.touche.toucheTakan[7], 30);

                this.retourner = new JButton("");
                retourner.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                Retour.retourArriere(view, TouchePane.this);
                        }
                });

                setStyle();
                addElements();
                currentFrameHeight = 800;
                currentFrameWidth = 1000;

                resize = new Resize(this);

                // change the size of currentScreenSize Value to resize all java components

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

        private void setStyle() {
                this.setLayout(null);
                this.setBackground(Abstract.appColor);
                this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

                retourner.setIcon(Abstract.iconRetour);
                retourner.setBackground(Abstract.appColor);
                retourner.setBorderPainted(false);

        }

        private void addElements() {

                retourner.setBounds(10, 10, Abstract.iconRetour.getIconWidth() + 10,
                                Abstract.iconRetour.getIconHeight() + 10);
                this.retourner.setFocusable(false);
                this.add(retourner);

                title.setBounds((Abstract.WIDTH - title.getPreferredSize().width) / 2, retourner.getY() + 40,
                                title.getPreferredSize().width + 10,
                                title.getPreferredSize().height + 10);

                this.ligne.setBounds(200, this.title.getY() + this.title.getPreferredSize().height + 30, Abstract.WIDTH,
                                ligne.getPreferredSize().height);
                this.add(title);

                this.add(ligne);

                this.enterLabel.setBounds(150, ligne.getY() + ligne.getHeight() + 20,
                                enterLabel.getPreferredSize().width + 20,
                                enterLabel.getPreferredSize().height);
                this.add(enterLabel);

                this.retourLabel.setBounds(150, enterLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                retourLabel.getPreferredSize().width + 20,
                                retourLabel.getPreferredSize().height);
                this.add(retourLabel);

                this.echapLabel.setBounds(150, retourLabel.getY() + retourLabel.getPreferredSize().height + 20,
                                echapLabel.getPreferredSize().width + 20,
                                echapLabel.getPreferredSize().height);
                this.add(echapLabel);

                this.indiceLabel.setBounds(150, echapLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                indiceLabel.getPreferredSize().width + 20,
                                indiceLabel.getPreferredSize().height);
                this.add(indiceLabel);

                this.annulerLabel.setBounds(150, indiceLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                annulerLabel.getPreferredSize().width + 20,
                                annulerLabel.getPreferredSize().height);
                this.add(annulerLabel);

                this.precLabel.setBounds(150, annulerLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                precLabel.getPreferredSize().width + 20,
                                precLabel.getPreferredSize().height);
                this.add(precLabel);

                this.sauvLabel.setBounds(150, precLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                sauvLabel.getPreferredSize().width + 20,
                                sauvLabel.getPreferredSize().height);
                this.add(sauvLabel);

                this.nvUserLabel.setBounds(150, sauvLabel.getY() + enterLabel.getPreferredSize().height + 20,
                                nvUserLabel.getPreferredSize().width + 20,
                                nvUserLabel.getPreferredSize().height);
                this.add(nvUserLabel);
                // ------------Touche------------------------
                this.enterTouche.setBounds(enterLabel.getX() + enterLabel.getPreferredSize().width + 5,
                                ligne.getY() + ligne.getHeight() + 20,
                                enterTouche.getPreferredSize().width + 20,
                                enterTouche.getPreferredSize().height);
                this.add(enterTouche);

                this.retourTouche.setBounds(retourLabel.getX() + retourLabel.getPreferredSize().width + 5,
                                enterTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                retourTouche.getPreferredSize().width + 20,
                                retourTouche.getPreferredSize().height);
                this.add(retourTouche);

                this.echapTouche.setBounds(echapLabel.getX() + echapLabel.getPreferredSize().width + 5,
                                retourTouche.getY() + retourTouche.getPreferredSize().height + 20,
                                echapTouche.getPreferredSize().width + 20,
                                echapTouche.getPreferredSize().height);
                this.add(echapTouche);

                this.indiceTouche.setBounds(indiceLabel.getX() + indiceLabel.getPreferredSize().width + 5,
                                echapTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                indiceTouche.getPreferredSize().width + 20,
                                indiceTouche.getPreferredSize().height);
                this.add(indiceTouche);

                this.annulerTouche.setBounds(annulerLabel.getX() + annulerLabel.getPreferredSize().width + 5,
                                indiceTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                annulerTouche.getPreferredSize().width + 20,
                                annulerTouche.getPreferredSize().height);
                this.add(annulerTouche);

                this.precTouche.setBounds(precLabel.getX() + precLabel.getPreferredSize().width + 5,
                                annulerTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                precTouche.getPreferredSize().width + 20,
                                precTouche.getPreferredSize().height);
                this.add(precTouche);

                this.sauvTouche.setBounds(sauvLabel.getX() + sauvLabel.getPreferredSize().width + 5,
                                precTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                sauvTouche.getPreferredSize().width + 20,
                                sauvTouche.getPreferredSize().height);
                this.add(sauvTouche);

                this.nvUserTouche.setBounds(nvUserLabel.getX() + nvUserLabel.getPreferredSize().width + 5,
                                sauvTouche.getY() + enterTouche.getPreferredSize().height + 20,
                                nvUserTouche.getPreferredSize().width + 20,
                                nvUserTouche.getPreferredSize().height);
                this.add(nvUserTouche);

                for (int i = 0; i < 8; i++) {
                        JLabel label = (JLabel) this.getComponent(i + 11);
                        JButton b = Abstract.createbutton("Changer", 200, label.getPreferredSize().height,
                                        Abstract.textSize);
                        int j = i;
                        b.setBounds(label.getX() + label.getPreferredSize().width + 50, label.getY(),
                                        b.getPreferredSize().width,
                                        b.getPreferredSize().height);
                        b.addActionListener(e -> {
                                System.out.println("bnt " + label.getText());
                                // view.touche.changerTouche(j);
                                label.setText(view.touche.changerTouche(j));

                        });
                        this.add(b);

                }

        }

        public void resetAllComponentFont() {
                title.setFont(Abstract.changeSizeTitleFont(currentFrameWidth));
                sonBtn.setFont(Abstract.changeSizeFontG(currentFrameWidth));
                ligne.setFont(Abstract.changeSizeFontFantasy(currentFrameWidth));
                repaint();
        }

}
