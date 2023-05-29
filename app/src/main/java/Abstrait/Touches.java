package Abstrait;

import java.util.LinkedList;

public class Touches {
    public LinkedList<String> toucheDispo;
    public String[] toucheTakan;

    public Touches() {
        toucheTakan = new String[8];
        toucheTakan[0] = "ENTER";
        toucheTakan[1] = "R";
        toucheTakan[2] = "Q";
        toucheTakan[3] = "I";
        toucheTakan[4] = "A";
        toucheTakan[5] = "P";
        toucheTakan[6] = "S";
        toucheTakan[7] = "N";

        toucheDispo = new LinkedList<>();
        toucheDispo.addFirst("UP");
        toucheDispo.addFirst("DOWN");
        toucheDispo.addFirst("SPACE");
        char car = 'B';
        while (car <= 'Z') {
            if (car != 'I' || car != 'P' || car != 'S' || car != 'R' || car != 'Q' || car != 'N')
                toucheDispo.addFirst(car + "");
            car++;
        }
    }

    public String changerTouche(int indiceTouche) {

        String toucheNv = toucheDispo.pop();
        System.out.println("touche recuperee " + toucheNv);

        toucheDispo.addLast(toucheTakan[indiceTouche]);
        toucheTakan[indiceTouche] = toucheNv;

        return toucheNv;
    }

}
