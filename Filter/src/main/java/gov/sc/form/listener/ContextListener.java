package gov.sc.form.listener;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ContextListener implements DocumentListener {
	private JTextArea jTextArea;

	public ContextListener(ConfMiHandler con) {
		this.jTextArea = con.jTextArea;
	}

	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Text Inserted:" + jTextArea.getText());
		
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Text Removed:" + jTextArea.getText());
		
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Attribute Changed" + e);
		
	}




}
