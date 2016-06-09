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

public class MainForm {
	public final JFrame jFrame = new JFrame("过滤器");
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
	public final JLabel selectFile = new JLabel("请选择文本:");
	public final JLabel selectCol = new JLabel("目标列:");
	public final JLabel selectTim = new JLabel("时间列:");

	public void createForm() {

		JPanel jpanelb = new JPanel();
		JPanel jpanelc = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		progressbar.setStringPainted(true);
		progressbar.setForeground(Color.GREEN);
		progressbar.setString("等待选择Excel文件。。。");
		jpanelb.add(selectFile);
		jpanelb.add(srcPthTxtFiled);
		jpanelb.add(scanBut);
		jpanelb.add(selectCol);
		jpanelb.add(selectTarCol);
		jpanelb.add(selectTim);
		jpanelb.add(selectTarTim);
		jpanelb.add(begBut);
		jpanelc.add(progressbar);
		jFrame.add(jpanelb, BorderLayout.CENTER);
		jFrame.add(jpanelc, BorderLayout.SOUTH);
		jFrame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(openMI);
		fileMenu.add(confMI);
		fileMenu.add(exitMI);
		helpMenu.add(helpMI);
		selectTarCol.setFont(new Font("宋体", Font.BOLD, 15));
		selectTarTim.setFont(new Font("宋体", Font.BOLD, 15));
		selectTarCol.addItem("请选择文件");
		selectTarTim.addItem("请选择文件");
		progressbar.setPreferredSize(new Dimension(540, 30));// 进度条大小
		srcPthTxtFiled.setColumns(25);
		selectFile.setFont(new Font("宋体", Font.BOLD, 18));
		scanBut.setFont(new Font("宋体", Font.BOLD, 15));
		selectCol.setFont(new Font("宋体", Font.BOLD, 18));
		selectTim.setFont(new Font("宋体", Font.BOLD, 18));
		begBut.setFont(new Font("宋体", Font.BOLD, 15));
		openMI.setIcon(new ImageIcon("images/open.jpg"));// xiugai
		exitMI.setIcon(new ImageIcon("images/exit.jpg"));
		helpMI.setIcon(new ImageIcon("images/help.jpg"));
		confMI.setIcon(new ImageIcon("images/config.png"));
		ImageIcon icon = new ImageIcon("images/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		srcPthTxtFiled.setEditable(false);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setBounds(new Rectangle(320, 290, 540, 200));
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
		MainForm ui = new MainForm();
		ui.createForm();
		ui.setListener();
	}
}
