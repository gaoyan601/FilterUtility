package gov.sc.form;

import gov.sc.form.listener.ExitHandler;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConfForm {
	public JFrame jFrame;
	public JTextArea jTextArea;
	JScrollPane jScrollPane;
	public void setForm(){
		if (jFrame == null) {
			jFrame = new JFrame("停用词配置");
			jTextArea = new JTextArea();
			jFrame.addWindowListener(new ExitHandler(this));
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
		jScrollPane = new JScrollPane(jTextArea);
		Container contentPane = jFrame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(jScrollPane, BorderLayout.CENTER);
		jFrame.setEnabled(true);
		jFrame.setState(Frame.NORMAL);
		jFrame.setSize(520, 430);
		jFrame.setLocation(400, 200);
		jFrame.setVisible(true);
		jFrame.requestFocus();
		ImageIcon icon = new ImageIcon("./src/main/resources/image/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		jTextArea.setTabSize(4);
		jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setEditable(true);
		String content = null;
		try {
			content = readTxt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		jTextArea.setText(content);
	}
	public String readTxt() throws Exception {
		StringBuilder sb = new StringBuilder();
		String line = "";
		String encoding = "utf-8";
		File file = new File("././library/stopwords.dic");
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			read.close();
		} else {
			System.out.println("找不到指定的文件");
		}
		return sb.toString();
	}
}
