package gov.sc.form.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ExitHandler implements WindowListener {
	JFrame jFrame;
	JTextArea jTextArea;
	public ExitHandler(ConfMiHandler con) {
		this.jTextArea = con.jTextArea;
		this.jFrame = con.jFrame;
		
	}
	private void write() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(
					"././library/stopwords.dic")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer.write(jTextArea.getText());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int result = JOptionPane.showConfirmDialog(null, "是否保存文件");
		if (result == JOptionPane.YES_OPTION) {
			write();
			jFrame.dispose();
		}
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
