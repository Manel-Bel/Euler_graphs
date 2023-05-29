package Vue.panes;

//pas de mouse clicker interface

import model.*;

import javax.swing.*;
import javax.swing.border.*;

import Abstrait.*;
import Controller.*;

import java.awt.*;
import java.awt.event.*;

public class Connexion extends JPanel {

    private JLabel userLabel = Abstract.textJlabel("ID :  ", Abstract.textSize);
    private JLabel passwordLabel = new JLabel("Mot de passe :");
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = Abstract.createbutton("Entrer", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    private JButton resetButton = Abstract.createbutton("RESET", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    private JButton inscriButton = Abstract.createbutton("Nouveau?", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    private JCheckBox showPassword = new JCheckBox("Afficher");

    private JLabel ligne = Abstract.getDecoDragon();
    private Database database;
    private ViewController view;

    private JPanel panel = new JPanel();
    

    private int currentFrameWidth, currentFrameHeight;
    private Resize resize;

    private Action entrer, nvUser;

    public Connexion(ViewController view, Database data) {

        this.view = view;
        this.database = data;
        this.currentFrameHeight = Abstract.HEIGHT;
        this.currentFrameWidth = Abstract.WIDTH;

        // -------------touches -----------
        this.entrer = new Enter();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[0]), "entrerAction");
        this.getActionMap().put("entrerAction", entrer);

        this.nvUser = new ToucheNouveauUser();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[7]), "nvUserAction");
        this.getActionMap().put("nvUserAction", nvUser);

        passwordField.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[0]), "entrerAction");
        passwordField.getActionMap().put("entrerAction", entrer);

        userTextField.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[0]), "entrerAction");
        userTextField.getActionMap().put("entrerAction", entrer);

        showPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound.btn();
                if (showPassword.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        inscriButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.goToInscription();
            }
        });
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));
        panel.setBounds(100, 60, 800, 700);
        this.add(panel);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Abstract.appColor);
        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.RAISED, Color.black, Color.white), "Connexion",
                TitledBorder.LEADING, TitledBorder.TOP, Abstract.titleFont1, Abstract.titleColor));

        addComponentsToContainer();
        this.setStyle();

        // resize = new ResizeListener(this);
        //
        // // resize automatic java component after frame is resized
        //
        // addComponentListener(new ComponentAdapter() {
        // public void componentResized(ComponentEvent e) {
        // currentFrameWidth = e.getComponent().getWidth();
        // currentFrameHeight = e.getComponent().getHeight();
        // // System.out.println(e.getComponent());
        // resize.setCurrentH(currentFrameHeight);
        // resize.setCurrentW(currentFrameWidth);
        // resize.ResizeAllComponent();
        // resetAllComponentFont();
        //
        // }
        // });

    }

    private void addComponentsToContainer() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(ligne, c);
        c.insets = new Insets(10, 10, 15, 10);
        c.gridwidth = 1;
        c.gridy++;
        panel.add(userLabel, c);

        c.gridx = 1;
        panel.add(userTextField, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(passwordLabel, c);
        c.gridx = 1;
        panel.add(passwordField, c);

        c.gridx = 1;
        c.gridy++;
        panel.add(showPassword, c);

        c.gridx = 0;
        c.gridy++;
        panel.add(inscriButton, c);

        c.gridx++;
        panel.add(resetButton, c);

        c.gridx = 0;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        panel.add(loginButton, c);

    }

    private void setStyle() {
        this.setBackground(Abstract.appColor);
        for (int i = 1; i < panel.getComponentCount() - 3; i++) {
            Component tmp = panel.getComponent(i);
            tmp.setFont(Abstract.textFont);
            tmp.setForeground(Abstract.textColor);
            tmp.setBackground(Abstract.appColor);
            tmp.setPreferredSize(new Dimension(350, 60));
        }
        userTextField.setCaretColor(Abstract.titleColor);
        passwordField.setEchoChar('*');
        passwordField.setCaretColor(Abstract.titleColor);
        repaint();
    }

    private void setMessageErreur(String msg) {
        JOptionPane.showMessageDialog(view, msg, "ERREUR", JOptionPane.ERROR_MESSAGE);
    }

    private void login() {
        String password = "";
        for (char i : passwordField.getPassword()) {
            password += i;
        }
        System.out.println("depuis connexion :" + password);
        try {
            loginPartiel(userTextField.getText(), password);
        } catch (Exception e1) {
            // e1.printStackTrace();
            System.out.println("userFild ou mot de passe vide ");
        }
    }

    private void loginPartiel(String userID, String password) throws Exception {
        String msg = database.Login(userID, password);
        if (msg.length() == 0) {
            System.out.println("Login successful");
            view.goToMenu();
        } else {
            System.out.println("Login failed");
            Sound.warning();
            reset();
            setMessageErreur(msg);
        }
    }

    private void reset() {
        System.out.println("Reset user + pass");
        userTextField.setText("");
        passwordField.setText("");
    }

    public void resetAllComponentFont() {
        panel.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.RAISED, Color.black, Color.white), "Connexion",
                TitledBorder.LEADING, TitledBorder.TOP, Abstract.changeSizeTitleFont(currentFrameWidth),
                Abstract.titleColor));
        for (int i = 0; i < panel.getComponentCount(); i++) {
            Component tmp = panel.getComponent(i);
            tmp.setFont(Abstract.changeSizeFontG(currentFrameWidth));
        }
        ligne.setFont(Abstract.changeSizeFontFantasy(currentFrameWidth));

    }

    private class Enter extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("touche clavier entrer");
            login();
        }

    }

    private class ToucheNouveauUser extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("touche clavier N");
            view.goToInscription();
        }

    }

}
