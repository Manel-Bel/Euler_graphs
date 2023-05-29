package Vue.panes;

import model.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.border.TitledBorder;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.*;

import javax.swing.border.EtchedBorder;

public class InfoPane extends JPanel {
    private JButton retourner;
    private JLabel title = Abstract.titreJlabel("Info du Joueur", Abstract.titleSize);
    private JPanel panel = new JPanel();
    private JLabel img = new JLabel("Photo");
    private JLabel idlabel = new JLabel("ID :");
    private JLabel pseudolabel = new JLabel("Pseudo : ");
    private JLabel niveaulabel = new JLabel("Niveau :");
    private JLabel bonuslabel = new JLabel("Bonus :");
    private JLabel niveau_actuelle = new JLabel("New label");
    private JLabel nbr_indice = new JLabel("New label");
    private JLabel ID = new JLabel("New label");
    private JLabel pseudo = new JLabel("New label");
    // private JLabel start_end = new JLabel("Find the shortest path between ");
    private int TextHeight = 60;
    private int TextWight = 300;
    private int vgap = 50;
    private int hgap = 50;
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;

    public InfoPane(ViewController view, Database data) {

        // ---------------touches-----------------------
        Action echape, retourArr;

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new Retour(view, this);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        HashMap<String, String> userinfo = data.getUserInfo(); // user actuel

        ID.setText(userinfo.get("id"));
        pseudo.setText(userinfo.get("pseudo"));
        nbr_indice.setText(userinfo.get("nbr_indice"));
        niveau_actuelle.setText(userinfo.get("niveau_actuelle"));
        System.out.println(userinfo.get("img"));
        img.setText("");
        File f = new File("src/main/resources/" + userinfo.get("img"));
        img.setIcon(new ImageIcon(f.getAbsolutePath()));
        img.setPreferredSize(new Dimension(200, 200));

        retourner = new JButton();
        retourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Retour.retourArriere(view, InfoPane.this);
            }
        });

        this.setLayout(null);
        this.setLocationAndSize();
        this.addComponentsToContainer();

        this.setStyle();

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

    public void setLocationAndSize() {
        retourner.setIcon(Abstract.iconRetour);
        retourner.setBackground(Abstract.appColor);
        retourner.setBorderPainted(false);
        retourner.setBounds(10, 10, Abstract.iconRetour.getIconWidth() + 10, Abstract.iconRetour.getIconHeight() + 10);
        this.retourner.setFocusable(false);
        title.setBounds(160, 50 + vgap, TextWight + 400, TextHeight + 100);
        vgap += 50;

        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, Abstract.appColor), "Icon",
                TitledBorder.LEADING, TitledBorder.TOP, Abstract.titleFont1, Abstract.titleColor));
        panel.setBounds(46, 150 + vgap, 300, 300);
        panel.add(img);

        idlabel.setBounds(320 + hgap, 143 + vgap, TextWight, TextHeight);
        pseudolabel.setBounds(320 + hgap, 227 + vgap, TextWight, TextHeight);
        niveaulabel.setBounds(320 + hgap, 310 + vgap, TextWight, TextHeight);
        bonuslabel.setBounds(320 + hgap, 394 + vgap, TextWight, TextHeight);
        niveau_actuelle.setBounds(467 + hgap, 310 + vgap, TextWight, TextHeight);
        nbr_indice.setBounds(467 + hgap, 394 + vgap, TextWight, TextHeight);
        ID.setBounds(467 + hgap, 143 + vgap, TextWight, TextHeight);
        pseudo.setBounds(467 + hgap, 227 + vgap, TextWight, TextHeight);

    }

    public void addComponentsToContainer() {
        add(retourner);
        add(title);
        add(panel);
        add(idlabel);
        add(pseudolabel);
        add(niveaulabel);
        add(bonuslabel);
        add(niveau_actuelle);
        add(nbr_indice);
        add(ID);
        add(pseudo);
    }

    public void setStyle() {
        this.setBackground(Abstract.appColor);
        for (int i = 1; i < this.getComponentCount(); i++) {
            this.getComponent(i).setFont(Abstract.textFont);
            this.getComponent(i).setForeground(Abstract.textColor);
            this.getComponent(i).setBackground(Abstract.appColor);
            // panel.getComponent(i).setPreferredSize(new Dimension(300,60));
        }
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(Abstract.titleFont1);

    }

    private void resetAllComponentFont() {
        for (int i = 0; i < this.getComponentCount(); i++) {
            this.getComponent(i).setFont(Abstract.changeSizeFontG(currentFrameWidth));
            // panel.getComponent(i).setPreferredSize(new Dimension(300,60));
        }
        title.setFont(Abstract.changeSizeTitleFont(currentFrameWidth));
        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.RAISED, Color.WHITE, Abstract.appColor), "Icon",
                TitledBorder.LEADING, TitledBorder.TOP, Abstract.changeSizeFontG(currentFrameWidth),
                Abstract.titleColor));

    }

}
