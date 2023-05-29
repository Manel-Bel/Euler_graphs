package Abstrait.Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Controller.ViewController;

public class Echape extends AbstractAction {
    ViewController v;

    public Echape(ViewController v) {
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        v.goToConnection();
        System.out.println("quit");
    }

}
