package Vue.panes;

import model.*;
import javax.swing.*;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class ParametrePane extends JPanel {
    private JLabel title = Abstract.titreJlabel("PARAMETRE", Abstract.titleSize);
    private JLabel ligne = Abstract.getDecoDragon();
    private JButton retourner, son, touche, infoButton, themeBtn, sonControl;
    private JPanel boutons;

    // attribut for the resizing java components
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;

    private Action echape;

    private String sonS = "ON";

    public ParametrePane(ViewController view, Database data) {
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

        this.retourner = new JButton();
        retourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Retour.retourArriere(view, ParametrePane.this);
            }
        });

        this.themeBtn = new JButton();
        setIconTheme();
        themeBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Abstract.lightTheme = !Abstract.lightTheme;
                Abstract.changeTheme();
                setIconTheme();
                view.gotoParametrePane();
                // repaint();
                // view.revalidate();
            }
        });

        this.son = Abstract.createbutton("Son " + sonS, Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        testeSound();
        this.son.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound.soundOff = !Sound.soundOff;
                son.setFocusable(false);
                testeSound();
                son.setText("Son " + sonS);
                if (Sound.soundOff) {
                    Sound.stopMusic();
                } else {
                    Sound.playMusic();
                }
            }
        });
        sonControl = Abstract.createbutton("Sons Control", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        sonControl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.goToSoundPane();
            }
        });
        touche = Abstract.createbutton("Touches", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        touche.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.goToTouche();
            }
        });

        infoButton = Abstract.createbutton("Information", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
        infoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.gotoInfoPane();
            }
        });

        boutons = new JPanel();

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

    private void testeSound() {
        if (Sound.soundOff) {
            sonS = "OFF";
        } else {
            sonS = "ON";
        }

    }

    private void setIconTheme() {
        if (Abstract.lightTheme) {
            themeBtn.setIcon(Abstract.iconLight);
        } else {
            themeBtn.setIcon(Abstract.iconDark);
        }
    }

    private void setStyle() {
        retourner.setIcon(Abstract.iconRetour);
        retourner.setBackground(Abstract.appColor);
        retourner.setBorderPainted(false);
        retourner.setBounds(10, 10, Abstract.iconRetour.getIconWidth() + 10,
                Abstract.iconRetour.getIconHeight() + 10);
        this.retourner.setFocusable(false);
        // setIconSound();
        this.themeBtn.setBackground(Abstract.appColor);
        this.themeBtn.setBorderPainted(false);
        this.themeBtn.setBounds(Abstract.WIDTH - themeBtn.getPreferredSize().width, 10,
                Abstract.iconDark.getIconWidth() + 10,
                Abstract.iconDark.getIconHeight() + 10);
        this.themeBtn.setFocusable(false);

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
        c.insets = new Insets(20, 0, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        boutons.add(son, c);

        c.gridy++;
        boutons.add(sonControl, c);
        c.gridy++;
        boutons.add(touche, c);
        c.gridy++;
        boutons.add(infoButton, c);
    }

    private void addElements() {
        addButtons();
        this.add(themeBtn);
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
