package Abstrait.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import Controller.*;
import Vue.panes.*;
import Vue.Jeu.*;

public class Retour extends AbstractAction {
    ViewController view;
    JPanel currentPane;

    public Retour(ViewController v, JPanel currentPane) {
        this.view = v;
        this.currentPane = currentPane;
    }

    public static void retourArriere(ViewController view, JPanel currentPane) {
        if (currentPane.getClass() == MenuPane.class || currentPane.getClass() == InscriptionPane.class) {
            view.goToConnection();
            return;
        }
        if (currentPane.getClass() == ParametrePane.class || currentPane.getClass() == ModeJeu.class
                || currentPane.getClass() == ModeOriente.class) {
            view.goToMenu();
            return;
        }
        if (currentPane.getClass() == Niveau.class || currentPane.getClass() == NiveauGraphe.class) {
            view.goToModeJeu();
            return;
        }
        if (currentPane.getClass() == InfoPane.class || currentPane.getClass() == TouchePane.class) {
            view.gotoParametrePane();
            return;
        }
        if (currentPane.getClass() == CreationGraphe.class) {
            view.goToModeOriente();
            return;
        }
        if (currentPane.getClass() == SoundsControlPane.class) {
            view.gotoParametrePane();
            return;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        retourArriere(view, currentPane);
    }

}
