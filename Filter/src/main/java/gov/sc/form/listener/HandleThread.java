package gov.sc.form.listener;

import gov.sc.file.ReadExcelFile;
import gov.sc.file.WriteExcelFile;
import gov.sc.filter.Cluster;
import gov.sc.form.MainForm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class HandleThread extends Thread {
	MainForm form;
	private JProgressBar proBar;
	private JButton begBut;
	private JButton scanBut;
	private JComboBox<String> selectTarCol;
	private JComboBox<String> selectTarTim;
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

	public List<String[]> getCells() {
		return cells;
	}

	public void showMessage() {
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
		/*File fileSta = new File(reFile.replace(".xls",
				"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
						+ selectTarTim.getSelectedItem() + ").xls"));*/
		if (fileAll.exists()) {
			int Yes = JOptionPane.showConfirmDialog(null,
					"文件已经解析过，是否删除已存在的文件，并生成新文件");
			if (Yes == JOptionPane.YES_OPTION) {
				fileAll.delete();
				//fileSta.delete();
			} else {
				return;
			}
		}
		begBut.setEnabled(false);
		scanBut.setEnabled(false);
		ReadExcelFile read = null;
		try {
			read = ReadExcelFile.getInstance(reFile);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			cells = read.getCells();
		} catch (Exception e) {
			e.printStackTrace();
			showMessage();
			return;
		}
		int value = cells.size();
		proBar.setString("文件解析中...");
		Cluster cluster;
		tarline = selectTarCol.getSelectedIndex();
		timeline = selectTarTim.getSelectedIndex();
		cluster = new Cluster(cells, tarline, timeline);
		List<String[]> reList = cluster.getResult_all();
		proBar.setValue(value * 2);
		WriteExcelFile write = new WriteExcelFile(reFile.replace(".xls",
				"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
						+ selectTarTim.getSelectedItem() + ").xls"));
		try {
			write.write(reList,0);
			proBar.setValue(value * 3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			/*write.setFile(reFile.replace(".xls",
					"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));*/
			reList = cluster.getResult_original();// 统计结果
			write.write(reList,1);
			proBar.setValue(value * 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "解析成功");
		proBar.setString("解析成功！！！");
		begBut.setEnabled(true);
		scanBut.setEnabled(true);
		proBar.setValue(0);
	}
}
