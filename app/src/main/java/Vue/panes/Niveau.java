package Vue.panes;

import javax.swing.*;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.ViewController;
import model.*;

import java.awt.*;
import java.awt.event.*;

public class Niveau extends JPanel {

    private JLabel title;
    private JButton facile, moyen, retourner, difficile;
    private JPanel boutons;
    private JLabel ligne = Abstract.getDecoDragon();

    // attribut for the resizing java components
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;

    public Niveau(ViewController view, int modeDeJeu) {

        this.setLayout(null);
        this.setBackground(Abstract.appColor);
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

        // ---------------touches-----------------------
        Action echape, retourArr;

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new Retour(view, this);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        title = Abstract.titreJlabel("Niveau", Abstract.titleSize);

        this.retourner = new JButton("");
        retourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Retour.retourArriere(view, Niveau.this);
            }
        });

        boutons = new JPanel();
        boutons.setBackground(Abstract.appColor);
        boutons.setLayout(new GridBagLayout());

        facile = Abstract.createbutton("Facile", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        facile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                view.goToNiveauGraphe(modeDeJeu, 'f');
            }

        });

        moyen = Abstract.createbutton("Moyen", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        moyen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                view.goToNiveauGraphe(modeDeJeu, 'm');
            }

        });

        difficile = Abstract.createbutton("Difficile", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        difficile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                view.goToNiveauGraphe(modeDeJeu, 'd');
            }

        });

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

    private void setStyle() {
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
        boutons.setBounds(((Abstract.WIDTH - 800) / 2), ligne.getY() + ligne.getHeight() + 40, 800, 400);

    }

    private void addButtons() {
        boutons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 00, 0, 00);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        boutons.add(facile, c);
        c.gridy++;
        boutons.add(moyen, c);
        c.gridy++;
        boutons.add(difficile, c);
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

}
