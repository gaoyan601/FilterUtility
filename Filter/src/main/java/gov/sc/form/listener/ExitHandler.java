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
	ConfMiHandler con;
	JFrame jFrame;
    JTextArea jTextArea;
	public ExitHandler(ConfMiHandler con) {
		this.con = con;
		this.jFrame = con.jFrame;
		this.jTextArea = con.jTextArea;
	}
	public void write() throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"././library/stopwords.dic")));
		try {
			writer.write(jTextArea.getText());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void sureExit() {
		int result = JOptionPane.showConfirmDialog(null, "文件是否保存");
		if (result == JOptionPane.YES_OPTION) {
			// 保存写入txt文件
			try {
				write();
			} catch (Exception e) {
				e.printStackTrace();
			}
			jFrame.dispose();
			
		}else if(result == JOptionPane.NO_OPTION){
			jFrame.dispose();
		}else{
			jFrame.dispose();
		}
	}
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		sureExit();
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
