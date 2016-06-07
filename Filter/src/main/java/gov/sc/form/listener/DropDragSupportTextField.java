package gov.sc.form.listener;

import org.apache.log4j.Logger;

import gov.sc.file.ReadExcelFile;
import gov.sc.form.MainForm;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DropDragSupportTextField extends JTextField implements
		DropTargetListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DropDragSupportTextField.class);

	private MainForm form;

	public DropDragSupportTextField(MainForm form) {
		this.form = form;
		new DropTarget(form.jFrame, DnDConstants.ACTION_COPY_OR_MOVE, this,
				true);
	}

	private void addItem() throws FileNotFoundException, IOException {
		JComboBox<String> selectTarCol = form.selectTarCol;
		JComboBox<String> selectTarTim = form.selectTarTim;
		JProgressBar proBar = form.progressbar;
		selectTarCol.removeAllItems();
		selectTarTim.removeAllItems();
		List<String[]> cells = ReadExcelFile.getInstance(
				form.srcPthTxtFiled.getText()).getCells();
		proBar.setMaximum(cells.size() * 4);
		proBar.setString("文件读取成功");
		String[] list = cells.get(0);
		// 判断标题
		for (int i = 0; i < list.length; i++) {
			if (list[i].matches("标题")) {
			 // 就把标题换到第一行。
			
			}
		}
		if (list[0].matches("标题")) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].length() >= 5) {
					selectTarCol.addItem(list[i].substring(0, 4) + "...");
				} else {
					selectTarCol.addItem(list[i]);
				}
			}
		} else {
			for (int i = 0; i < list.length; i++) {
				if (list[i].matches("标题")) {
					String element = list[i];
					list[i] = list[0];
					list[0] = element;
				}
			}
			for (int i = 0; i < list.length; i++) {
				if (list[i].length() >= 5) {
					selectTarCol.addItem(list[i].substring(0, 4) + "...");
				} else {
					selectTarCol.addItem(list[i]);
				}
			}
		}
		// 时间
		if (list[0].matches(".*时间.*") || list[0].matches(".*日期.*")) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].length() >= 5) {
					selectTarTim.addItem(list[i].substring(0, 4) + "...");
				} else {
					selectTarTim.addItem(list[i]);
				}
			}
		} else {
			for (int i = 0; i < list.length; i++) {
				if (list[i].matches(".*时间.*") || list[i].matches(".*日期.*")) {
					String element = list[i];
					list[i] = list[0];
					list[0] = element;
				}
			}
			for (int i = 0; i < list.length; i++) {
				if (list[i].length() >= 5) {
					selectTarTim.addItem(list[i].substring(0, 4) + "...");
				} else {
					selectTarTim.addItem(list[i]);
				}

			}
			// 问题是 获取时间了 时间这一列别放入到了第一条， 选择时间列的索引的时候时间列为 0；
		}
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
		if (dataFlavors[0].match(DataFlavor.javaFileListFlavor)) {
			try {
				Transferable tr = dtde.getTransferable();
				Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);
				@SuppressWarnings("unchecked")
				List<File> files = (List<File>) obj;
				String fileName = files.get(files.size() - 1).getAbsolutePath();
				form.srcPthTxtFiled.setText(fileName);
				if (fileName.matches(".*xls") || fileName.matches(".*xlsx")) {
					addItem();
				}
			} catch (Exception e) {
				logger.info("init file error--->" + e.toString());
			}
		}
	}

	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub

	}

}
