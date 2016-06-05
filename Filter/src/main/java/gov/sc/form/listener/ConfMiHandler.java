package gov.sc.form.listener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConfMiHandler implements ActionListener {
	JFrame jFrame ;
	JTextArea jTextArea;
	JScrollPane jScrollPane;

	public void setForm() {
		if (jFrame == null) {

			jFrame = new JFrame("停用词配置");
		}
		Container contentPane = jFrame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("./src/main/resources/image/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		jTextArea = new JTextArea();
		jTextArea.setTabSize(4);
		jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setEditable(true);
		String elements = null;
		try {
			elements = readTxt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		jTextArea.setText(elements);
		jScrollPane = new JScrollPane(jTextArea);
		contentPane.add(jScrollPane, BorderLayout.CENTER);
		jFrame.setEnabled(true);
		jFrame.setState(Frame.NORMAL);
		jFrame.setSize(520, 430);
		jFrame.setLocation(400, 200);
		jFrame.setVisible(true);
		jFrame.requestFocus();
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new ExitHandler(this));
		
	}

	public String getString(List<String> list) {
		String elements = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			elements = elements + "\n" + list.get(i);
		}
		return elements;
	}

	// 读取txt文件
	public String readTxt() throws Exception {
		List<String> list = new ArrayList<String>();
		String lineTxt = null;
		String elements = null;
		String encoding = "utf-8";
		File file = new File("././library/stopwords.dic");
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((lineTxt = bufferedReader.readLine()) != null) {
				list.add(lineTxt);
			}
			elements = getString(list);
			read.close();

		} else {
			System.out.println("找不到指定的文件");
		}
		return elements;

	}

	public void actionPerformed(ActionEvent e) {
		setForm();
		
	}
}
