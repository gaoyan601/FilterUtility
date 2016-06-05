package gov.sc.form.listener;

import gov.sc.file.WriteFile;
import gov.sc.filter.Cluster;
import gov.sc.form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class BegButHandler implements ActionListener {

	private MainForm form;
	public List<String[]> cells;

	public BegButHandler(MainForm form) {
		this.form = form;

	}

	public void actionPerformed(ActionEvent e) {
		String reFile = form.srcPthTxtFiled.getText().trim();
		if (reFile == null || reFile.equals("")) {
			JOptionPane.showMessageDialog(null, "请选择Excel文件");
			return;
		}
		HandleThread ht = new HandleThread(form);
		ht.start();
	}

	static class HandleThread extends Thread {
		private MainForm form;
		private JProgressBar proBar;
		private JButton begBut;
		private JButton scanBut;
		private JComboBox selectTarCol;
		private JComboBox selectTarTim;
		private String reFile;
		private List<String[]> cells;
		private int tarline;
		private int timeline;
		public HandleThread(MainForm form) {
			this.form = form;
			this.proBar = form.progressbar;
			this.begBut = form.begBut;
			this.scanBut = form.scanBut;
			this.selectTarCol = form.selectTarCol;
			this.selectTarTim = form.selectTarTim;
			this.reFile = form.srcPthTxtFiled.getText();

		}

		public void showMessage() {
			JOptionPane.showMessageDialog(null, "文件解析失败");
			proBar.setValue(0);
			proBar.setString("请选择正确目标列和时间列");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
		}

		public void get(List<String[]> cells) {
			this.cells = cells;
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
			DropDragSupportTextField read = new DropDragSupportTextField(form);
			begBut.setEnabled(false);
			scanBut.setEnabled(false);
			cells = read.getCell();
			int value = cells.size();
			proBar.setString("文件解析中...");
			Cluster cluster;
			tarline = selectTarCol.getSelectedIndex();
			timeline = selectTarTim.getSelectedIndex();
			cluster = new Cluster(cells, tarline,timeline);
			List<String[]> reList = new ArrayList<String[]>();
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
				reList = cluster.getResult_original();// 统计结果
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
