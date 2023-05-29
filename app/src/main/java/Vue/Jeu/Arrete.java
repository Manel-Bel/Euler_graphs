package Vue.Jeu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Arrete {
    int x1, y1, x2, y2;
    // int arrowX,arrowY;

    public Arrete(int x1, int y1, int x2, int y2, int size) {
        this.x1 = x1 + size / 2;
        this.x2 = x2 + size / 2;
        this.y1 = y1 + size / 2;
        this.y2 = y2 + size / 2;
        // this.drawArrete(null);
    }

    public void drawArrete(Graphics g, int indice) {
        Graphics2D g2d = (Graphics2D) g;
        switch (indice) {
            case 1:
                g2d.setColor(Color.LIGHT_GRAY);
                break;
            case 2:
                g2d.setColor(Color.RED);
                break;
            case 3:
                g2d.setColor(Color.BLACK);
                break;
        }
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x1, y1, x2, y2);

    }

    public void drawFleche(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // le milieu

        int miX = (x1 + x2) / 2;
        int miY = (y1 + y2) / 2;

        // l'angle de la fleche

        double angle = Math.atan2(y2 - y1, x2 - x1);

        // dessiner la fleche au milieu

        int arrowSize = 10;
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 0);
        arrowHead.addPoint(-arrowSize, arrowSize);
        arrowHead.addPoint(-arrowSize, -arrowSize);
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(miX, miY);
        g2d.rotate(angle);
        g2d.setColor(Color.BLACK);
        g2d.fill(arrowHead);
        g2d.draw(arrowHead);
        g2d.setTransform(oldTransform);
    }

}