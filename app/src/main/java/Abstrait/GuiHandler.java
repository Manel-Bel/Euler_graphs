package Abstrait;


import java.awt.*;

import Controller.ViewController;

public class GuiHandler {
  private ViewController window;

  public GuiHandler() {
  }

  public void run() {
    GuiHandler gh = this;
    EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            window = new ViewController(gh);
            window.setVisible(true);
            window.pack();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pour mettre au centre de l'ecrant
            window.setLocation(dim.width / 2 - window.getSize().width / 2,
                dim.height / 2 - window.getSize().height / 2 - 50);
          }
        });
  }

  public void close() {
    System.exit(0);
  }
}
