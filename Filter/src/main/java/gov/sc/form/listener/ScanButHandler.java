package gov.sc.form.listener;

import gov.sc.form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;


public class ScanButHandler implements ActionListener {

	private MainForm gui;

	public ScanButHandler(MainForm gui) {
		this.gui = gui;
	}

	public void actionPerformed(ActionEvent e) {
		JTextField srcPthTxtFiled = new JTextField();
		srcPthTxtFiled = gui.srcPthTxtFiled;
		JProgressBar proBar = new JProgressBar();
		proBar = gui.progressbar;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) { // 设定可用的文件的后缀名 || f.isDirectory()
				if (file.getName().endsWith(".xls")
						|| file.getName().endsWith(".xlsx")
						|| file.isDirectory()) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return "Excel文件(*.xls/xlsx)";
			}
		});
		if (chooser.showDialog(null, "选择") == JFileChooser.CANCEL_OPTION) {
			return;
		}
		srcPthTxtFiled.setText(chooser.getSelectedFile().toString());
		proBar.setString("请选择正确的目标列和时间列");

		

	}
}
