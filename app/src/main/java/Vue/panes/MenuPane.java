package Vue.panes;

import javax.swing.*;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.ViewController;
import model.Database;
import model.Sound;
import java.awt.*;
import java.awt.event.*;

public class MenuPane extends JPanel {

  private JLabel title;
  private JButton play, settings, exit, grapheBtn;
  private JPanel buttons;
  private JLabel ligne = Abstract.getDecoDragon();
  private Database database;
  private JButton sonBtn;
  private JPanel panel = new JPanel();

  private int currentFrameWidth, currentFrameHeight;
  private Resize resize;
  private ViewController view;

  private Action echape, retourArr;

  public MenuPane(ViewController view, Database data) {

    this.view = view;
    this.database = data;

    title = Abstract.titreJlabel("Menu", Abstract.titleSize);

    // ---------------touches-----------------------

    this.echape = new Echape(view);
    this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
    this.getActionMap().put("echapeAction", echape);

    this.retourArr = new Retour(view, this);
    this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
    this.getActionMap().put("retourArrAction", retourArr);

    buttons = new JPanel();

    play = Abstract.createbutton("JOUER", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    play.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        view.goToModeJeu();
      }

    });

    settings = Abstract.createbutton("Parametres", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    settings.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        view.gotoParametrePane();
      }

    });

    grapheBtn = Abstract.createbutton("GRAPHE", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    grapheBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        if (database.getUserInfo().get("id").equals("1")) {
          view.goToModeOriente();
        } else {
          Sound.warning();
          setMessageErreur("Access denied. Admin only");
        }
      }

    });

    exit = Abstract.createbutton("Quitter", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        Retour.retourArriere(view, MenuPane.this);
      }

    });
    this.sonBtn = new JButton();

    setStyle();
    addButtons();
    addElements();
    currentFrameHeight = 800;
    currentFrameWidth = 1000;

    resize = new Resize(this);
    // change the size of currentScreenSize Value to resize all java components
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {

        currentFrameWidth = e.getComponent().getWidth();
        currentFrameHeight = e.getComponent().getHeight();

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

    this.panel.setLayout(null);
    this.panel.setBackground(Abstract.appColor);
    setIconSound();
    this.sonBtn.setBackground(Abstract.appColor);
    this.sonBtn.setBorderPainted(false);
    sonBtn.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        Sound.soundOff = !Sound.soundOff;
        if (Sound.soundOff) {
          Sound.stopMusic();
        } else {
          Sound.playMusic();

        }
        setIconSound();
        resize.ResizeComponent(sonBtn, true);
      }
    });

    buttons.setBackground(Abstract.appColor);
    buttons.setLayout(new GridBagLayout());
  }

  private void setIconSound() {
    if (Sound.soundOff) {
      sonBtn.setIcon(Abstract.iconSoundOff);

    } else {
      sonBtn.setIcon(Abstract.iconSoundOn);

    }
  }

  private void addElements() {
    this.panel.setBounds(0, 0, Abstract.WIDTH, Abstract.HEIGHT);

    this.sonBtn.setBounds(Abstract.WIDTH - 60, 10, Abstract.iconSoundOn.getIconWidth() + 10,
        Abstract.iconSoundOn.getIconHeight() + 10);
    this.sonBtn.setFocusable(false);

    this.panel.add(sonBtn);

    title.setBounds((Abstract.WIDTH - title.getPreferredSize().width) / 2, sonBtn.getY() + 40,
        title.getPreferredSize().width + 10,
        title.getPreferredSize().height + 10);
    this.panel.add(title);

    this.ligne.setBounds(200, this.title.getY() + this.title.getPreferredSize().height + 30, Abstract.WIDTH,
        ligne.getPreferredSize().height);
    this.panel.add(ligne);

    buttons.setBounds(((Abstract.WIDTH - 800) / 2), ligne.getY() + ligne.getHeight() + 40, 800, 400);
    // buttons.setBackground(Color.red)
    this.panel.add(buttons);

    this.add(panel);
  }

  private void addButtons() {
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(20, 0, 0, 0);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    buttons.add(play, c);
    c.gridy++;
    buttons.add(settings, c);
    c.gridy++;
    buttons.add(grapheBtn, c);
    c.gridy++;
    buttons.add(exit, c);
  }

  public void resetAllComponentFont() {
    title.setFont(Abstract.changeSizeTitleFont(currentFrameWidth));
    buttons.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    sonBtn.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    ligne.setFont(Abstract.changeSizeFontFantasy(currentFrameWidth));
    play.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    exit.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    settings.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    grapheBtn.setFont(Abstract.changeSizeFontG(currentFrameWidth));
    repaint();
  }

  private void setMessageErreur(String msg) {
    JOptionPane.showMessageDialog(view, msg, "ERREUR", JOptionPane.ERROR_MESSAGE);
  }

}
