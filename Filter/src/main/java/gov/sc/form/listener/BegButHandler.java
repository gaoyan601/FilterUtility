package gov.sc.form.listener;

import org.apache.log4j.Logger;

import gov.sc.file.ReadExcelFile;
import gov.sc.file.WriteExcelFile;
import gov.sc.filter.Cluster;
import gov.sc.form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class BegButHandler implements ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BegButHandler.class);
	private MainForm form;

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
		private JProgressBar proBar;
		private JButton begBut;
		private JButton scanBut;
		private JComboBox<String> selectTarCol;
		private JComboBox<String> selectTarTim;
		private String reFile;
		private List<String[]> cells;
		private int tarline;
		private int timeline;
		private String[] searchTime;
		private int timeIndex = 0;
		private int tarIndex = 0;

		public HandleThread(MainForm form) {
			this.proBar = form.progressbar;
			this.begBut = form.begBut;
			this.scanBut = form.scanBut;
			this.selectTarCol = form.selectTarCol;
			this.selectTarTim = form.selectTarTim;
			this.reFile = form.srcPthTxtFiled.getText();
		}

		private void showErrorMessage() {
			JOptionPane.showMessageDialog(null, "文件解析失败");
			proBar.setValue(0);
			proBar.setString("请选择正确目标列和时间列");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
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
			begBut.setEnabled(false);
			scanBut.setEnabled(false);
			try {
				ReadExcelFile ref = ReadExcelFile.getInstance(reFile);
				cells = ref.getCells();
			} catch (Exception e) {
				logger.info("read Excel file error---->" + e.toString());
				return;
			}
			searchTime = cells.get(0);
			for(int i =0;i<searchTime.length;i++){
				if(searchTime[i].matches(".*时间.*")||searchTime[i].matches(".*日期.*")){
					timeIndex = i;
				}
				if(searchTime[i].matches("标题")){
					tarIndex = i;
				}
			}
			
			int value = cells.size();
			proBar.setString("文件解析中...");
			tarline = selectTarCol.getSelectedIndex()+tarIndex;
			timeline = selectTarTim.getSelectedIndex()+timeIndex;
			Cluster cluster = new Cluster(cells, tarline, timeline);
			List<String[]> reList = cluster.getResult_all();
			proBar.setValue(value * 2);
			WriteExcelFile write = new WriteExcelFile(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			try {
				write.write(reList);
				proBar.setValue(value * 3);
			} catch (Exception e) {
				logger.info("write all result file error--->" + e.toString());
				showErrorMessage();
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
				logger.info("write statistics result file error--->"
						+ e.toString());
				showErrorMessage();
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
