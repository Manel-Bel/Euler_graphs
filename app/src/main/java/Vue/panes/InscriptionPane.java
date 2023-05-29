package Vue.panes;


import model.*;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.ViewController;

import java.nio.file.Files;
import java.nio.file.Path;

public class InscriptionPane extends JPanel {

    static final String IconPath = "src\\main\\resources\\";
    private File imageFile = null;

    private JPasswordField password = new JPasswordField();
    private JPasswordField confirm_password = new JPasswordField();

    private JTextField pseudo = new JTextField();
    private int newUserId;
    private JButton retourner = new JButton("retourner");
    private JLabel IconLabel = Abstract.textJlabel("Icon :", Abstract.textSize);
    private JLabel IDLabel = Abstract.textJlabel("ID :", Abstract.textSize);
    private JLabel userid = Abstract.textJlabel("", Abstract.textSize);
    private JButton icon = new JButton("Photo");
    private JLabel pseudoLabel = Abstract.textJlabel("Pseudo : ", Abstract.textSize);
    private JLabel passwordlabel = Abstract.textJlabel("mot de passe : ", Abstract.textSize);
    private JLabel confirm_pass = Abstract.textJlabel("confirmer : ", Abstract.textSize);

    private JButton reset = Abstract.createbutton("RESET", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    private JButton submit = Abstract.createbutton("enter", Abstract.bntWidth, Abstract.btnheight, Abstract.textSize);
    private int TextHeight = 60;
    private int TextWight = 250;
    private int vgap = 100;
    private int hgap = 150;

    private ViewController view;
    private Database database;

    private Action entrer, echape, retourArr;

    // attribut for the resizing java components
    private Resize resize;
    private int currentFrameWidth, currentFrameHeight;

    public InscriptionPane(ViewController view, Database data) {

        // ---------------touches-----------------------

        echape = new Echape(view);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
        this.getActionMap().put("echapeAction", echape);

        retourArr = new Retour(view, this);
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
        this.getActionMap().put("retourArrAction", retourArr);

        this.entrer = new Entrer();
        this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[0]), "enterAction");
        this.getActionMap().put("enterAction", entrer);

        this.database = data;
        this.view = view;

        newUserId = database.getnombreUser() + 1;
        System.out.println("Nombre user " + newUserId);

        confirm_password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    submitFonc();
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound.btn();
                reset();
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitFonc();
            }
        });
        icon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sound.btn();
                openJFileChoose();
                icon.setIcon(new ImageIcon(imageFile.getAbsolutePath()));

            }
        });
        retourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Retour.retourArriere(view, InscriptionPane.this);
            }
        });

        userid.setText("" + newUserId);
        this.setStyle();
        setSizeAndLocation();
        addComponentsToContainer();

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

    public void reset() {
        pseudo.setText("");
        password.setText("");
        confirm_password.setText("");
        icon.setIcon(null);
    }

    public String getPassword(JPasswordField passwordField) {
        String password = "";
        for (char i : passwordField.getPassword()) {
            password += i;
        }
        return password;

    }

    private void submitFonc() {
        System.out.println("trying to submit");
        String nameimg = (imageFile == null) ? "." : imageFile.getName();
        if (!nameimg.equals(".")) {
            copieFile(imageFile);
            imageFile = new File(IconPath + "userIcon" + newUserId + "." + getFileExtension(imageFile));
            nameimg = imageFile.getName();
        }
        String msg = database.signUp(pseudo.getText(), getPassword(password), getPassword(confirm_password),
                nameimg);

        if (msg.length() == 0) {
            System.out.println("submition successful");
            view.goToConnection();
        } else {
            System.out.println("submition failed");
            Sound.warning();
            JOptionPane.showMessageDialog(this, msg, "ERREUR",
                    JOptionPane.ERROR_MESSAGE);
            reset();
        }
    }

    private void setSizeAndLocation() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Abstract.WIDTH, Abstract.HEIGHT));

        retourner.setIcon(Abstract.iconRetour);
        retourner.setBackground(Abstract.appColor);
        retourner.setBorderPainted(false);
        retourner.setBounds(20, 10, Abstract.iconRetour.getIconWidth() + 15, Abstract.iconRetour.getIconHeight() + 10);
        this.retourner.setFocusable(false);

        icon.setBounds(370 + hgap, 11 + vgap, 200, 200);
        // IconLabel.setLocation(120, 71 + vgap);
        IconLabel.setBounds(150, 71 + vgap, TextWight, TextHeight);

        vgap += 50;
        // IDLabel.setLocation(150, 147 + vgap);

        IDLabel.setBounds(150, icon.getY() + icon.getHeight() + 10, IDLabel.getPreferredSize().width + 10,
                IDLabel.getPreferredSize().height + 10);

        userid.setBounds(icon.getX(), IDLabel.getY(), TextWight, TextHeight);

        pseudoLabel.setBounds(150, IDLabel.getY() + IDLabel.getPreferredSize().height + 30,
                pseudoLabel.getPreferredSize().width + 10, TextHeight);

        pseudo.setBounds(icon.getX(), pseudoLabel.getY(), TextWight, TextHeight);

        passwordlabel.setBounds(150, pseudoLabel.getY() + pseudoLabel.getHeight() + 30,
                passwordlabel.getPreferredSize().width + 10,
                TextHeight);

        password.setBounds(icon.getX(), passwordlabel.getY(), TextWight, TextHeight);

        confirm_pass.setBounds(150, passwordlabel.getY() + passwordlabel.getHeight() + 30, TextWight,
                TextHeight);

        confirm_password.setBounds(icon.getX(), confirm_pass.getY(), TextWight, TextHeight);

        reset.setBounds(150, confirm_pass.getY() + confirm_pass.getHeight() + 20, TextWight, TextHeight);

        submit.setBounds(icon.getX(), reset.getY(), TextWight, TextHeight);

    }

    private void addComponentsToContainer() {
        add(retourner);
        add(IconLabel);
        add(icon);
        add(IDLabel);
        add(userid);
        add(pseudoLabel);
        add(pseudo);
        add(passwordlabel);
        add(password);
        add(confirm_pass);
        add(confirm_password);
        add(reset);
        add(submit);
    }

    public void openJFileChoose() {
        File file = new File(".");
        JFileChooser choose = new JFileChooser(
                FileSystemView
                        .getFileSystemView()
                        .getHomeDirectory());
        // ouvrir le fichier
        int res = choose.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            file = choose.getSelectedFile();
        }
        this.imageFile = file;
    }

    public void copieFile(File f) {
        Path source = Paths.get(f.getAbsolutePath());
        Path dest = Paths.get(IconPath + "userIcon" + newUserId + "." + getFileExtension(f));
        System.out.println("source:" + source);
        System.out.println(dest);
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getFileExtension(File file) {
        String filename = file.getAbsolutePath();
        String ex = "";
        for (int i = filename.length() - 1; i >= 0; i--) {
            if (filename.charAt(i) == '.') {
                break;
            }
            ex = filename.charAt(i) + ex;
        }
        return ex;

    }

    public void setStyle() {
        this.setBackground(Abstract.appColor);

        icon.setFont(Abstract.textFont);
        icon.setForeground(Abstract.textColor);
        icon.setBackground(Abstract.appColor);
        pseudo.setFont(Abstract.textFont);
        password.setFont(Abstract.textFont);
        confirm_password.setEchoChar('*');
        confirm_password.setCaretColor(Abstract.titleColor);
        password.setEchoChar('*');
        password.setCaretColor(Abstract.titleColor);
        pseudo.setCaretColor(Abstract.titleColor);

    }

    private void resetAllComponentFont() {

        for (int i = 0; i < this.getComponentCount(); i++) {
            this.getComponent(i).setFont(Abstract.changeSizeFontG(currentFrameWidth));
            // panel.getComponent(i).setPreferredSize(new Dimension(300,60));
        }
    }

    private class Entrer extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            submitFonc();
        }

    }

}
