package Vue.panes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Abstrait.*;
import Abstrait.Actions.*;
import Controller.*;

import model.*;

public class SoundsControlPane extends JPanel {
	private JLabel title = Abstract.titreJlabel("Sounds Controls", Abstract.titleSize);
	private JLabel ligne = Abstract.getDecoDragon();
	private JSlider BGMControl = new JSlider(-40, 6);
	private JSlider effetControl = new JSlider(-40, 6);
	private Action echape, retourArr;
	private ViewController view;
	private JButton retourner;

	public SoundsControlPane(ViewController view) {
		this.view = view;
		this.setLayout(null);


		this.retourner = new JButton();
		retourner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("retourner");
				Retour.retourArriere(view, SoundsControlPane.this);
			}
		});
		retourner.setIcon(Abstract.iconRetour);
		retourner.setBackground(Abstract.appColor);
		retourner.setBorderPainted(false);
		retourner.setBounds(10, 10, Abstract.iconRetour.getIconWidth() + 10,
				Abstract.iconRetour.getIconHeight() + 10);
		this.retourner.setFocusable(false);
		this.add(retourner);

		title.setBounds((Abstract.WIDTH - title.getPreferredSize().width) / 2, retourner.getY() + 40,
				title.getPreferredSize().width + 10,
				title.getPreferredSize().height + 10);

		this.ligne.setBounds(200, this.title.getY() + this.title.getPreferredSize().height + 30, Abstract.WIDTH,
				ligne.getPreferredSize().height);
		this.add(title);
		this.add(ligne);
		// BGM//
		BGMControl.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (BGMControl.getValue() == -40.0) {
					Sound.setBGMVolume(-80.0f);
				}
				Sound.setBGMVolume((float) BGMControl.getValue());

			}

		});
		JLabel BGM = Abstract.gameJlabel("BGM Sound ", Abstract.textSize);
		BGMControl.setBounds(200 + Abstract.bntWidth, ligne.getY() + ligne.getHeight() + 25, 200, 40);
		BGMControl.setValue(6);
		BGMControl.setBackground(Abstract.appColor);
		BGM.setBounds(10, ligne.getY() + ligne.getHeight() + 25, Abstract.bntWidth, Abstract.btnheight);
		this.add(BGM);
		this.add(BGMControl);

		this.setBackground(Abstract.appColor);

		// EFFET//

		effetControl.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (effetControl.getValue() == -40.0) {
					Sound.seteffetVolume(-80.0f);
				}
				Sound.seteffetVolume((float) effetControl.getValue());

			}

		});
		JLabel effet = Abstract.gameJlabel("Effet Sound ", Abstract.textSize);
		effetControl.setBounds(200 + Abstract.bntWidth, BGM.getY() + BGM.getHeight() + 25, 200, 40);
		effetControl.setBackground(Abstract.appColor);
		effetControl.setValue(6);
		effet.setBounds(10, BGM.getY() + BGM.getHeight() + 25, Abstract.bntWidth, Abstract.btnheight);
		this.add(effet);
		this.add(effetControl);

		

		// ---------------touches-----------------------

		this.echape = new Echape(view);
		this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[2]), "echapeAction");
		this.getActionMap().put("echapeAction", echape);

		this.retourArr = new Retour(view, this);
		this.getInputMap().put(KeyStroke.getKeyStroke(view.touche.toucheTakan[1]), "retourArrAction");
		this.getActionMap().put("retourArrAction", retourArr);

	}

}
