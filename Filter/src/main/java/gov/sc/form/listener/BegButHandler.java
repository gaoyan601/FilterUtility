package gov.sc.form.listener;

import gov.sc.file.WriteFile;
import gov.sc.filter.Cluster;
import gov.sc.form.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class BegButHandler implements ActionListener {

	private Gui gui;
	public List<String[]> map;

	public BegButHandler(Gui gui) {
		this.gui = gui;

	}

	public void actionPerformed(ActionEvent e) {
		String reFile = gui.srcPthTxtFiled.getText().trim();
		if (reFile == null || reFile.equals("")) {
			JOptionPane.showMessageDialog(null, "请选择Excel文件");
			return;
		}
		// 获取拖拽时间中的 cell。
		HandleThread ht = new HandleThread(gui);
		ht.start();
	}

	static class HandleThread extends Thread {
		private Gui gui;
		private JProgressBar proBar;
		private JButton begBut;
		private JButton scanBut;
		private JComboBox<String> selectTarCol;
		private JComboBox<String> selectTarTim;
		private String reFile;
		private List<String[]> map;

		public HandleThread(Gui gui) {
			this.gui = gui;
			this.proBar = gui.progressbar;
			this.begBut = gui.begBut;
			this.scanBut = gui.scanBut;
			this.selectTarCol = gui.selectTarCol;
			this.selectTarTim = gui.selectTarTim;
			this.reFile = gui.srcPthTxtFiled.getText();

		}

		public void showMessage() {
			JOptionPane.showMessageDialog(null, "文件解析失败");
			proBar.setValue(0);
			proBar.setString("请选择正确目标列和时间列");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
		}

		public void get(List<String[]> map) {
			this.map = map;
		}

		@Override
		public void run() {
			File fileAll = new File(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			File fileSta = new File(reFile.replace(".xls",
					"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			if (fileAll.exists()) {
				int Yes = JOptionPane.showConfirmDialog(null,
						"文件已经解析过，是否删除已存在的文件，并生成新文件");
				if (Yes == JOptionPane.YES_OPTION) {
					fileAll.delete();
					fileSta.delete();
				} else {
					return;
				}
			}
			DropDragSupportTextField read = new DropDragSupportTextField(gui);
			begBut.setEnabled(false);
			scanBut.setEnabled(false);
			map = read.getCell();
			int value = map.size();
			proBar.setString("文件解析中...");
			Cluster cluster;
			cluster = new Cluster(map, selectTarCol.getSelectedIndex() + 1,
					read.addItem());

			List<List<String[]>> reList = new ArrayList<List<String[]>>();
			try {
				reList = cluster.getResult_all();
				proBar.setValue(value * 2);

			} catch (Exception e) {
				e.printStackTrace();
				showMessage();
				return;
			}

			WriteFile write = new WriteFile(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			write.setFile(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			try {
				write.write(reList);
				proBar.setValue(value * 3);
			} catch (Exception e1) {
				e1.printStackTrace();
				showMessage();
				return;
			}
			try {
				write.setFile(reFile.replace(".xls",
						"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
								+ selectTarTim.getSelectedItem() + ").xls"));
				reList = cluster.getResultOfCountAndTime();// 统计结果
				write.write(reList);
				proBar.setValue(value * 4);
			} catch (Exception e) {
				e.printStackTrace();
				showMessage();
				return;
			}
			JOptionPane.showMessageDialog(null, "解析成功");
			proBar.setString("解析成功！！！");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
			proBar.setValue(0);
		}
	}

}
