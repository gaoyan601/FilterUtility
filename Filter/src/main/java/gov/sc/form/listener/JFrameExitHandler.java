package gov.sc.form.listener;

import gov.sc.form.Gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class JFrameExitHandler implements WindowListener{
	private JFrame jFrame;
	public JFrameExitHandler(Gui gui){
		this.jFrame = gui.jFrame;
	}
	private void sureExit() {
		int result = JOptionPane.showConfirmDialog(null, "是否退出程序");
		if (result == JOptionPane.YES_OPTION) {
			jFrame.dispose();
		}else{
			return;
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
