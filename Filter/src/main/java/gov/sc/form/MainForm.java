package gov.sc.form;

import gov.sc.form.listener.BegButHandler;
import gov.sc.form.listener.ConfMiHandler;
import gov.sc.form.listener.DropDragSupportTextField;
import gov.sc.form.listener.ExitMiHandler;
import gov.sc.form.listener.HelpMiHandler;
import gov.sc.form.listener.JFrameExitHandler;
import gov.sc.form.listener.OpenMiHandler;
import gov.sc.form.listener.ScanButHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainForm {
	public final JFrame jFrame = new JFrame("舆情拣摙器");
	public final JTextField srcPthTxtFiled = new DropDragSupportTextField(this);
	public final JComboBox<String> selectTarCol = new JComboBox<String>();
	public final JComboBox<String> selectTarTim = new JComboBox<String>();
	public final JButton scanBut = new JButton("浏览");
	public final JButton begBut = new JButton("开始");
	public final JProgressBar progressbar = new JProgressBar(0, 1000);
	public final JMenu fileMenu = new JMenu("文件");
	public final JMenu helpMenu = new JMenu("帮助");
	public final JMenuItem helpMI = new JMenuItem("使用帮助");
	public final JMenuItem openMI = new JMenuItem("打开");
	public final JMenuItem exitMI = new JMenuItem("退出");
	public final JMenuItem confMI = new JMenuItem("配置");
	public final JLabel selectFile = new JLabel("选择文本:");
	public final JLabel selectCol = new JLabel("目标列:");
	public final JLabel selectTim = new JLabel("时间列:");

	public void createForm() {

		JPanel jpanela = new JPanel();
		JPanel jpanelb = new JPanel();
		JPanel jpanelc = new JPanel();
		JPanel jpaneld = new JPanel();
		JPanel jpanele = new JPanel();
		JPanel jpanelf = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		progressbar.setStringPainted(true);
		progressbar.setForeground(Color.GREEN);
		progressbar.setString("等待选择Excel文件");
		jpanela.setPreferredSize(new Dimension(150, 55));
		jFrame.add(jpanela, BorderLayout.NORTH);
		jFrame.add(jpanelb, BorderLayout.CENTER);// 三个嵌套
		jFrame.add(jpanelc, BorderLayout.SOUTH);
		jpanelb.add(jpaneld, BorderLayout.WEST);
		jpanelb.add(jpanele, BorderLayout.EAST);
		jpanelb.add(jpanelf, BorderLayout.SOUTH);
		jpanela.add(selectFile);
		jpanela.add(srcPthTxtFiled);
		jpanela.add(scanBut);

		jpaneld.add(selectCol);
		jpaneld.add(selectTarCol);
		jpanele.add(selectTim);
		jpanele.add(selectTarTim);
		jpanelf.add(begBut);

		jpanelc.add(progressbar);
		jFrame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(openMI);
		fileMenu.add(confMI);
		fileMenu.add(exitMI);
		helpMenu.add(helpMI);
		jFrame.setFont(new Font("宋体", Font.BOLD, 20));
		selectTarCol.setFont(new Font("宋体", Font.BOLD, 15));
		selectTarTim.setFont(new Font("宋体", Font.BOLD, 15));
		selectTarCol.addItem("请选择文件");
		selectTarTim.addItem("请选择文件");
		srcPthTxtFiled.setColumns(45);
		selectTarCol.setPreferredSize(new Dimension(138, 30));
		selectTarTim.setPreferredSize(new Dimension(138, 30));
		selectFile.setFont(new Font("宋体", Font.BOLD, 22));
		srcPthTxtFiled.setPreferredSize(new Dimension(446, 30));
		scanBut.setFont(new Font("宋体", Font.BOLD, 22));
		fileMenu.setFont(new Font("宋体", Font.BOLD, 15));
		helpMenu.setFont(new Font("宋体", Font.BOLD, 15));
		progressbar.setPreferredSize(new Dimension(600, 45));
		progressbar.setFont(new Font("宋体", Font.BOLD, 22));// 进度条大小
		selectTarCol.setFont(new Font("宋体", Font.BOLD, 20));
		selectTarTim.setFont(new Font("宋体", Font.BOLD, 20));
		selectCol.setFont(new Font("宋体", Font.BOLD, 22));
		selectCol.setLocation(300, 10);
		selectTim.setFont(new Font("宋体", Font.BOLD, 22));
		begBut.setFont(new Font("宋体", Font.BOLD, 22));
		openMI.setFont(new Font("宋体", Font.BOLD, 13));
		helpMI.setFont(new Font("宋体", Font.BOLD, 13));
		exitMI.setFont(new Font("宋体", Font.BOLD, 13));
		confMI.setFont(new Font("宋体", Font.BOLD, 13));
		openMI.setIcon(new ImageIcon("./src/main/resources/image/open.jpg"));// xiugai
		exitMI.setIcon(new ImageIcon("./src/main/resources/image/exit.jpg"));
		helpMI.setIcon(new ImageIcon("./src/main/resources/image/help.jpg"));
		confMI.setIcon(new ImageIcon("./src/main/resources/image/config.png"));
		ImageIcon icon = new ImageIcon("./src/main/resources/image/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		srcPthTxtFiled.setEditable(false);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setBounds(new Rectangle(320, 290, 562, 299));
		jFrame.setResizable(false);// 大小不变
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void setListener() {
		jFrame.addWindowListener(new JFrameExitHandler());
		helpMI.addActionListener(new HelpMiHandler());// 类名
		confMI.addActionListener(new ConfMiHandler());
		exitMI.addActionListener(new ExitMiHandler());
		openMI.addActionListener(new OpenMiHandler(this));
		scanBut.addActionListener(new ScanButHandler(this));
		begBut.addActionListener(new BegButHandler(this));
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		MainForm ui = new MainForm();
		ui.createForm();
		ui.setListener();
	}
}
