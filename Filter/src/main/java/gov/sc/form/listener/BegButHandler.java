package gov.sc.form.listener;

import org.apache.log4j.Logger;

import gov.sc.file.ReadExcelFile;
import gov.sc.file.WriteExcelFile;
import gov.sc.filter.Cluster;
import gov.sc.form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class BegButHandler implements ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BegButHandler.class);
	private MainForm form;

	public BegButHandler(MainForm form) {
		this.form = form;
	}

	public void actionPerformed(ActionEvent e) {
		String reFile = form.srcPthTxtFiled.getText().trim();
		if (reFile == null || reFile.equals("")) {
			JOptionPane.showMessageDialog(null, "请选择Excel文件");
			return;
		}
		HandleThread ht = new HandleThread(form);
		ht.start();
	}

	
	

}
