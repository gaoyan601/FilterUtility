package gov.sc.form.listener;

import gov.sc.form.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenMiHandler implements ActionListener{

	private Gui gui;
	
	public OpenMiHandler(Gui gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new ScanButHandler(gui).actionPerformed(e);
	}

	

}
