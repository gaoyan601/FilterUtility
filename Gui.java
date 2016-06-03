package com.zzz.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.zzz.handler.BegButHandler;
import com.zzz.handler.OpenMiHandler;
import com.zzz.handler.ExitMiHandler;
import com.zzz.handler.HelpMiHandler;
import com.zzz.handler.ScanButHandler;


public class Gui implements DropTargetListener{
	public final JFrame jFrame = new JFrame("过滤器");
	public final JTextField srcPthTxtFiled = new JTextField(30);
	public final JTextField tarColTxtFiled = new JTextField(8);
	public final JButton scanBut = new JButton("浏览");
	public final JButton begBut = new JButton("开始");
	public final JProgressBar progressbar = new JProgressBar(0,1000);
	public final JMenu fileMenu = new JMenu("文件");
	public final JMenu helpMenu = new JMenu("帮助");
	StringBuilder helpContent = new StringBuilder();
	StringBuilder openContent = new StringBuilder();
	StringBuilder exitContent = new StringBuilder();
	public final JMenuItem helpMI = new JMenuItem(helpContent.append("  使用帮助").toString());
	public final JMenuItem openMI = new JMenuItem(openContent.append("  打开").toString());//String
	public final JMenuItem exitMI = new JMenuItem(exitContent.append("  退出").toString());
	public final JLabel selectFile = new JLabel("请选择文本:");
	public final JLabel selectCol = new JLabel("请输入目标列:");
	public final JLabel selectTim = new JLabel("请输入时间列:");
	public final JTextField tarTimTxtFiled = new JTextField(8);
	private DropTarget dropTarget;  	
	
	public Gui() 
	{  
	    //注册DropTarget，并将它与组件相连，处理哪个组件的相连  
	    //即连通组件（第一个this）和Listener(第二个this)  
	    dropTarget = new DropTarget(jFrame,DnDConstants.ACTION_COPY_OR_MOVE, this, true);
	    dropTarget = new DropTarget(srcPthTxtFiled,DnDConstants.ACTION_COPY_OR_MOVE, this, true);
	}  

	@Override
	public void dragEnter(DropTargetDragEvent dtde)
	{
		// TODO 自动生成的方法存根
		 DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();  
	     if(dataFlavors[0].match(DataFlavor.javaFileListFlavor))
	     {  
	         try 
	         {   
	        	 Transferable tr = dtde.getTransferable();     
	        	 Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);  	                
	        	 List<File> files = (List<File>)obj;  	                
	        	
	        	 for(int i = 0; i < files.size(); i++)
	        	 {
		        	 String addr = files.get(i).getAbsolutePath();	                	
		        	 int length = addr.length();	                	
		      		 if(addr.endsWith(".xls") || addr.endsWith(".xlsx"))
		      		 {
		      			 srcPthTxtFiled.setText(addr);
		      			 tarColTxtFiled.setText("A");
		      			 tarTimTxtFiled.setText("");
		      			 progressbar.setString("请选择正确的目标列和时间列");
		       		 }
		        	
	        	 }

	          } catch(Exception e)
	         	{	        		 
	        	}
	     } 

	        
	}
	@Override
	public void dragOver(DropTargetDragEvent dtde)
	{
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde)
	{
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void dragExit(DropTargetEvent dte)
	{
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void drop(DropTargetDropEvent dtde)
	{
		// TODO 自动生成的方法存根
		
	}

	public void createForm() throws IOException {

		JPanel jpanelb = new JPanel();
		JPanel jpanelc = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		progressbar.setStringPainted(true);
		progressbar.setForeground(Color.GREEN);
		progressbar.setString("等待选择Excel文件...");
		jpanelb.add(selectFile);
		jpanelb.add(srcPthTxtFiled);
		jpanelb.add(scanBut);
		jpanelb.add(selectCol);
		jpanelb.add(tarColTxtFiled);
		jpanelb.add(selectTim);
		jpanelb.add(tarTimTxtFiled);
		jpanelb.add(begBut);
		jpanelc.add(progressbar);
		jFrame.add(jpanelb, BorderLayout.CENTER);
		jFrame.add(jpanelc, BorderLayout.SOUTH);
		jFrame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(openMI);
		fileMenu.add(exitMI);
		helpMenu.add(helpMI);
		progressbar.setPreferredSize(new Dimension(540,30));//进度条大小
		srcPthTxtFiled.setSize(17, 5);
		selectFile.setFont(new Font("宋体", Font.BOLD, 18));
		scanBut.setFont(new Font("宋体", Font.BOLD, 15));
		selectCol.setFont(new Font("宋体", Font.BOLD, 18));
		selectTim.setFont(new Font("宋体", Font.BOLD, 18));
		begBut.setFont(new Font("宋体", Font.BOLD, 15));
		openMI.setIcon(new ImageIcon("./image/open.png"));//xiugai
		exitMI.setIcon(new ImageIcon("./image/exit.png"));
		helpMI.setIcon(new ImageIcon("./image/help.png"));
		ImageIcon icon=new ImageIcon("./image/filter.png");
		jFrame.setIconImage(icon.getImage());
		srcPthTxtFiled.setEditable(false);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setBounds(new Rectangle(320, 290, 540, 200));
		jFrame.setResizable(false);// 大小不变
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 整个程序关闭
	}
	public void setListener() {
		helpMI.addActionListener(new HelpMiHandler());//类名
		exitMI.addActionListener(new ExitMiHandler(this));
		openMI.addActionListener(new OpenMiHandler(this));
		scanBut.addActionListener(new ScanButHandler(this));
		begBut.addActionListener(new BegButHandler(this));
	}
	
}
