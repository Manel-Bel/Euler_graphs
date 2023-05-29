package Vue.Jeu;

import javax.swing.*;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.*;
import model.Database;
import model.*;
import Vue.Jeu.GrapheG;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JeuPane extends JPanel implements KeyListener {
    private GrapheG grapheTmp;
    private JPanel navBar;
    private JPanel bottomBar;
    private Court court;
    private JLabel nomP, pointP, timerLable, deco;
    private JButton abandon, prec, indice, annuler;
    private int mode;
    private Timer timer;
    private int count = 20;
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;
    private ViewController view;


    public JeuPane(ViewController view, Database data, int mode, char niveau, int gra) {

        this.court = new Court(data, mode, niveau, gra); // init court
        this.mode = mode;
        this.view = view;

        // ----------------------timer-----------------
        switch (niveau) {
            case 'f':
                this.count = 60;
                break;
            case 'm':
                this.count = 50;
                break;
            case 'd':
                this.count = 35;
                break;
        }

        // ----------------------pane------------------

        this.addKeyListener(this);

        // ---------------touches-----------------------
        Action echape, retourArr, toucheIndice, touchePre, toucheAnnuler;

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new ToucheRetour();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        toucheIndice = new ToucheIndice();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[3]), "toucheIndiceAction");
        this.getActionMap().put("toucheIndiceAction", toucheIndice);

        touchePre = new TouchePre();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[5]), "touchePreAction");
        this.getActionMap().put("touchePreAction", touchePre);

        toucheAnnuler = new ToucheAnnuler();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[4]), "toucheAnnulerAction");
        this.getActionMap().put("toucheAnnulerAction", toucheAnnuler);

        // -----------------navbar elements---------------------
        nomP = Abstract.gameJlabel(court.getPlayerI(0).getNom(), 40);

        pointP = Abstract.gameJlabel(data.getUserInfo().get("nbr_indice"), 40);
        // Integer.toString(court.getPlayerI(0).getScore())

        deco = new JLabel("999");
        timerLable = Abstract.gameJlabel("00 : " + String.format("%02d", count), 35);
        if (mode != 1) {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(count == 0 ){
                        view.goToNiveau(mode);
                        ((Timer) (e.getSource())).stop();
                    }
                    if(count>0)count--;
                    // System.out.println(count);
                    if (count >= 0) {
                        timerLable.setText("00 : " + String.format("%02d", count));
                    } else {
                        ((Timer) (e.getSource())).stop();
                        
                        Retour.retourArriere(view, JeuPane.this);
                    }
                }
            });
            timer.setInitialDelay(5);
            // timer.start();
        }

        // ------------------------bottomPart -------------------------

        prec = Abstract.createbuttonJeu2("src/main/resources/annuler.png", "");
        prec.addActionListener(e -> {
            Sound.btn();
            precFonc();
        });

        annuler = Abstract.createbuttonJeu2("src/main/resources/recommencer.png", "");
        annuler.addActionListener(e -> {
            Sound.btn();
            annulerFonc();
        });

        indice = Abstract.createbuttonJeu2("src/main/resources/indice.png", "");

        // ------------ panel GRAPHE------------

        indice.addActionListener(e -> {
            Sound.btn();
            indiceFonc();
        });
        abandon = Abstract.createbuttonJeu2("src/main/resources/exit.png", "");
        abandon.addActionListener(e -> {
            Sound.btn();
            abandonFonc();
        });

        navBar = new JPanel();
        bottomBar = new JPanel();
        setstyle();

        addElementsNav();
        addElementsbottom();

        grapheTmp = new GrapheG(Abstract.WIDTH, bottomBar.getY() - navBar.getHeight(), this);

        addElements();
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

    @Override
    public void paint(Graphics arg0) {
        this.pointP.setText(Integer.toString(court.getPlayerI(0).getScore()));
        // if (mode != 1)
        this.timerLable.setText("00 : " + String.format("%02d", count));
        navBar.repaint();
        super.paint(arg0);
    }

    private void annulerFonc() {
        System.out.println("annuler");
        court.annuler();
        repaint();
    }

    private void indiceFonc() {
        System.out.println("indice");
        if (court.indice()) {
            grapheTmp.afficheindice();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Vos points sont insuffisants", "ERREUR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void precFonc() {
        System.out.println("precedant");
        court.prec();
        repaint();
    }

    private void abandonFonc() {
        System.out.println("abandon");
        if (timer != null)
            timer.stop();
        
        grapheTmp.stopTimer();
        view.goToModeJeu();
    }

    private void addElements() {
        this.add(navBar);
        this.add(grapheTmp);
        this.add(bottomBar);
    }

    public ViewController getView() {
        return view ;
    }

    private void addElementsNav() {
        navBar.setLayout(null);
        this.navBar.setBounds(0, 0, Abstract.WIDTH, 80);
        if (mode != 1) {
            timerLable.setBounds(10, (navBar.getPreferredSize().height - timerLable.getPreferredSize().height) / 2,
                    timerLable.getPreferredSize().width + 20,
                    timerLable.getPreferredSize().height);
            this.navBar.add(timerLable);
            timer.start();
            // c.gridx++;
        }
        nomP.setBounds((Abstract.WIDTH - nomP.getPreferredSize().width) / 2 - 100,
                (navBar.getPreferredSize().height - nomP.getPreferredSize().height) / 2, nomP.getPreferredSize().width,
                nomP.getPreferredSize().height);
        this.navBar.add(nomP);
        // c.gridx++;
        pointP.setBounds(nomP.getX() + nomP.getWidth() + 20,
                (navBar.getPreferredSize().height - pointP.getPreferredSize().height) / 2,
                pointP.getPreferredSize().width + 20,
                pointP.getPreferredSize().height);
        this.navBar.add(pointP);
        deco.setBounds(pointP.getX() + pointP.getWidth() + 10,
                (navBar.getPreferredSize().height - deco.getPreferredSize().height) / 2,
                deco.getPreferredSize().width + 20,
                deco.getPreferredSize().height);
        this.navBar.add(deco);
        // c.gridx++;
        // c.insets = new Insets(0, 160, 0, 0);
        // this.navBar.add(deco, c);
    }

    private void addElementsbottom() {
        bottomBar.setBackground(Abstract.appColor);
        this.bottomBar.setBounds(0, Abstract.HEIGHT - 80, Abstract.WIDTH, 80);
        this.bottomBar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 40, 0, 40);
        c.gridx = 0;
        c.gridy = 0;
        this.bottomBar.add(abandon, c);
        c.gridx++;
        this.bottomBar.add(prec, c);
        c.gridx++;
        this.bottomBar.add(annuler, c);
        c.gridx++;
        this.bottomBar.add(indice, c);
    }

    private void setstyle() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

        navBar.setBackground(Abstract.appColor);

        deco.setForeground(Abstract.textColor);
        deco.setFont(Abstract.textFontFantasy);
    }

    public void stopTimer() {
        if (mode != 1) {
            this.timer.stop();
        }
    }

    public void setTimer(int c) {
        this.count = c;
    }

    public Court getCourt() {
        return this.court;
    }

    private void resetAllComponentFont() {
        nomP.setFont(Abstract.changeSizeFontPix(currentFrameWidth));
        pointP.setFont(Abstract.changeSizeFontPix(currentFrameWidth));
    }

    private class ToucheIndice extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            indiceFonc();
        }
    }

    private class TouchePre extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            precFonc();
        }
    }

    private class ToucheAnnuler extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            annulerFonc();
        }
    }

    private class ToucheRetour extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("abondonner");
            abandonFonc();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("event keycode " + e.getKeyChar());
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
            grapheTmp.jeuUpdate(Integer.parseInt(e.getKeyChar() + ""));
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
