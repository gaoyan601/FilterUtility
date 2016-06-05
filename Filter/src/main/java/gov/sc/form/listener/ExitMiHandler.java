package gov.sc.form.listener;

import gov.sc.form.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class ExitMiHandler implements ActionListener {
	private Gui gui;

	public ExitMiHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFrame jFrame = new JFrame();
		jFrame = gui.jFrame;
		jFrame.dispose();
	}

	

}
