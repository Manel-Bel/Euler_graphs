package Abstrait;

import java.awt.*;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;
import javax.swing.border.Border;

public class Abstract {

    public static boolean lightTheme = false;

    public static int WIDTH = 1000;
    public static int HEIGHT = 800;
    public static int titleSize = 90;
    public static int textSize = 30;
    public static int gameTextSize = 40;
    public static int btnSize = 12;
    public static int bntWidth = 350;
    public static int btnheight = 60;

    public static Font titleFont1 = loadFont(titleSize);
    public static Font textFont = loadFont(textSize);
    public static Font textFontGame = loadFontGame(gameTextSize);
    public static Font textFontFantasy = loadFontFantasy();

    public static Color appColorLight = new Color(120, 161, 187);// new Color(184, 192, 255)120, 161, 187
    public static Color textColorLight = new Color(0, 18, 25); // new Color(231, 198, 255)191, 168, 158
    public static Color titleColorLight = new Color(40, 48, 68); // new Color(255, 214, 255)139, 120, 109
    public static Color btnColorLight = new Color(235, 245, 238);
    public static Color lineColorLight = new Color(0, 18, 25); // (38, 49, 89)

    public static Color appColorDark = new Color(0, 8, 20);
    public static Color textColorDark = new Color(255, 195, 0); // (255, 251, 235) //(238, 238, 238)
    public static Color titleColorDark = new Color(255, 214, 10); // (255, 251, 235)
    public static Color btnColorDark = new Color(0, 29, 61);
    public static Color lineColorDark = Color.WHITE;

    public static Color appColor = appColorDark;
    public static Color textColor = textColorDark;
    public static Color titleColor = titleColorDark;
    public static Color btnColor = btnColorDark;
    public static Color lineColor = lineColorDark;

    public static File fileSoundOn = new File("src/main/resources/sonOn.png");
    public static Icon iconSoundOn = new ImageIcon(fileSoundOn.getAbsolutePath());
    public static File fileSoundOff = new File("src/main/resources/sonOff.png");
    public static Icon iconSoundOff = new ImageIcon(fileSoundOff.getAbsolutePath());
    public static Icon iconRetour = new ImageIcon(new File("src/main/resources/retour.png").getAbsolutePath());
    public static Icon iconLight = new ImageIcon(new File("src/main/resources/lightMode.png").getAbsolutePath());
    public static Icon iconDark = new ImageIcon(new File("src/main/resources/darkMode.png").getAbsolutePath());
    public static Icon rabbit = new ImageIcon(new File("src/main/resources/rabbit.png").getAbsolutePath());
    public static Icon carrot = new ImageIcon(new File("src/main/resources/carotte.png").getAbsolutePath());

    // ------------------titres des fenetres -----------------------------

    public static JLabel titreJlabel(String s, int size) {
        JLabel texte = new JLabel(s);
        texte.setHorizontalAlignment(JLabel.CENTER);
        texte.setForeground(titleColor);
        texte.setFont(loadFont(size));
        texte.setHorizontalAlignment(JLabel.CENTER);
        return texte;
    }

    public static JLabel textJlabel(String s, int size) {
        JLabel texte = new JLabel(s);
        texte.setForeground(textColor);
        texte.setFont(loadFont(size));
        return texte;
    }

    public static JLabel gameJlabel(String s, int size) {
        JLabel texte = new JLabel(s);
        texte.setForeground(textColor);
        texte.setFont(loadFontGame(size));
        texte.setHorizontalAlignment(JLabel.CENTER);
        return texte;
    }

    public static Font loadFont(int size) {
        if (size == 0)
            size = 30;
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/AnglocelestialBold.ttf"))
                    .deriveFont((float) size);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, size);
        }
    }

    public static Font loadFontBtn(int size) {
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Pixelbroidery-0n0G.ttf"))
                    .deriveFont((float) size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            // return new Font("Montserrat", Font.BOLD, 10);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, size);
        }
    }

    public static Font loadFontGame(int size) {
        if (size == 0)
            size = gameTextSize;
        try {
            // create the font to use. Specify the size!
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Pixelbroidery-0n0G.ttf"))
                    .deriveFont((float) size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, size);
        }
    }
    //
    // private static Font loadFontNum() {
    // try {
    // Font customFont = Font
    // .createFont(Font.TRUETYPE_FONT, new
    // File("src/main/resources/Gamingdicestandard-8532.ttf"))
    // .deriveFont(20f);
    // return customFont;
    // } catch (Exception e) {
    // return new Font("Montserrat", Font.BOLD, 30);
    // }
    // }

    private static Font loadFontFantasy() {
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/FantasyClipart-ZEGZ.ttf"))
                    .deriveFont(45f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, 45);
        }
    }

    public static JLabel getDecoDragon() {
        JLabel text = new JLabel("II  II  II  II ");
        text.setFont(loadFontFantasy());
        text.setForeground(lineColor);
        return text;
    }

    public static JButton createbutton(String text, int width, int height, int size) {
        JButton btn = new JButton(text);
        btn.setForeground(Abstract.textColor);
        btn.setFont(loadFont(size));
        btn.setBackground(Abstract.btnColor);
        btn.setHorizontalAlignment(JButton.CENTER);
        btn.setBorder(new RoundedBorder(5));
        btn.setPreferredSize(new Dimension(width, height));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(appColor);
                btn.getGraphics().setColor(appColor);
            }

            public void mouseExited(MouseEvent evt) {
                btn.setFocusable(false);
                btn.setBackground(Abstract.btnColor);
                btn.getGraphics().setColor(btnColor);
            }
        });
        return btn;
    }

    public static JButton createbuttonJeu2(String url, String txt) {
        JButton btn = new CircleButton(url, txt);
        return btn;
    }

    static class RoundedBorder implements Border {
        private int r;

        RoundedBorder(int r) {
            this.r = r;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.r + 1, this.r + 1, this.r + 2, this.r);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y,
                int width, int height) {
            g.setColor(textColor);
            g.drawRoundRect(x, y, width - 1, height - 1, r, r);
            ((Graphics2D) g).setStroke(new BasicStroke(6));

        }
    }

    public static class CircleButton extends JButton {
        Color btnC;

        public CircleButton(String url, String txt) {
            super(txt);
            this.setBackground(appColor);
            if (url != "") {
                File file = new File(url);
                setIcon(new ImageIcon(file.getAbsolutePath()));
            }
            if (txt != "") {
                setForeground(titleColor);
                setFont(loadFont(16));
            }

            btnC = appColor;
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setContentAreaFilled(false);
            this.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btnC = btnColor;
                }

                public void mouseExited(MouseEvent evt) {
                    btnC = appColor;
                    setFocusable(false);
                }
            });
        }

        protected void paintComponent(Graphics g) {
            g.setColor(btnC);
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(textColor);
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }

    }

    public static void changeTheme() {
        if (lightTheme) {
            System.out.println("light theme");
            appColor = appColorLight;
            textColor = textColorLight;
            titleColor = titleColorLight;
            btnColor = btnColorLight;
            lineColor = lineColorLight;
        } else {
            System.out.println("dark theme ");
            appColor = appColorDark;
            textColor = textColorDark;
            titleColor = titleColorDark;
            btnColor = btnColorDark;
            lineColor = lineColorDark;
        }
    }

    public static Font changeSizeFontG(int width) {
        int textSize = (int) Math.round(width * 0.03);
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/AnglocelestialBold.ttf"))
                    .deriveFont((float) textSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;

        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, textSize);
        }
    }

    public static Font changeSizeTitleFont(int width) {
        int textSize = (int) Math.round(width * 0.08);
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/AnglocelestialBoldItalic.ttf"))
                    .deriveFont((float) textSize);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, textSize);
        }

    }

    public static Font changeSizeFontFantasy(int width) {
        int textSize = (int) Math.round(width * 0.045);
        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/FantasyClipart-ZEGZ.ttf"))
                    .deriveFont((float) textSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, textSize);
        }
    }

    public static Font changeSizeFontPix(int width) {
        int textSize = (int) Math.round(width * 0.04);

        try {
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Pixelbroidery-0n0G.ttf"))
                    .deriveFont((float) textSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, textSize);
        }

    }

    public static Font changeSizeTextFont2(int width) {
        int textSize = (int) Math.round(width * 0.03);
        try {
            // create the font to use. Specify the size!
            Font customFont = Font
                    .createFont(Font.TRUETYPE_FONT, new File("src/main/resources/AncientModernTales-a7Po.ttf"))
                    .deriveFont((float) textSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(customFont);
            return customFont;

        } catch (Exception e) {
            return new Font("Montserrat", Font.BOLD, textSize);
        }

    }

}
