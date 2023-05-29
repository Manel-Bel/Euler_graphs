package Controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;

import Vue.panes.*;
import Vue.Jeu.*;
import model.*;
import Abstrait.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ViewController extends JFrame {

  private JPanel currentPane;
  private Database database;
  private Action echape;
  public static boolean themeLight = false;
  public Touches touche;

  public ViewController(GuiHandler guiHandler) {

    setTitle("2022-AL1-GB");
    setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));
    setResizable(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.setIconImage(getIconImage());
    Sound.playMusic();
    touche = new Touches();

    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        if (e.getComponent().getHeight() < Abstract.HEIGHT ||
            e.getComponent().getWidth() < Abstract.WIDTH) {
          e.getComponent().setSize(Abstract.WIDTH, Abstract.HEIGHT);
        }
      }
    });

    database = new Database();

    currentPane = new Connexion(this, database);

    currentPane.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));
    // add(currentPane, BorderLayout.CENTER);
    currentPane.requestFocusInWindow();
    add(currentPane);

  }

  // -------------Fenetre de connexion--------------------

  public void goToConnection() {
    Sound.btn();
    this.setFocusable(false);
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new Connexion(this, database);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour inscription du joueur--------------------

  public void goToInscription() {
    Sound.btn();
    this.setFocusable(false);
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new InscriptionPane(this, database);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour Le Menu--------------------z
  public void goToMenu() {
    Sound.btn();
    setVisible(true);
    this.setFocusable(false);
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new MenuPane(this, database);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre Parametre --------------------
  public void gotoParametrePane() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new ParametrePane(this, database);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre Pour Voir/modifications des touches -------------------

  public void goToTouche() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new TouchePane(this);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour Voir les infrormations du joueur---

  public void gotoInfoPane() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new InfoPane(this, database);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour choisir le Mode de Jeu ----

  public void goToModeJeu() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new ModeJeu(this);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour choisir le Niveau de difficulté----

  public void goToNiveau(int modeDeJeu) {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new Niveau(this, modeDeJeu);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  public void goToSoundPane() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new SoundsControlPane(this);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour le choix du graphes -------------------

  public void goToNiveauGraphe(int modeDeJeu, char niveau) {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new NiveauGraphe(this, modeDeJeu, niveau);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour Le Jeu classique --------------------

  public void goToGame(int mode, char niveau, int nGraphe) {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new JeuPane(this, database, mode, niveau, nGraphe);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre Pour la creation d'un graphe -------------------

  public void goToModeOriente() {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new ModeOriente(this);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  public void goToCreationGraphe(boolean oriente) {
    Sound.btn();
    currentPane.setFocusable(false);
    remove(currentPane);
    currentPane = new CreationGraphe(this, oriente);
    add(currentPane);
    revalidate();
    currentPane.setFocusable(true);
    currentPane.requestFocusInWindow();
  }

  // -------------Fenetre pour Editer les graphes --------------------

  // public void goToEditGraphe() {
  //
  // Sound.warning();
  // JOptionPane.showMessageDialog(this, "Ce mode n'est pas encore disponible.",
  // "ERREUR",
  // JOptionPane.ERROR_MESSAGE);
  // }

  // -------------------------------

}
