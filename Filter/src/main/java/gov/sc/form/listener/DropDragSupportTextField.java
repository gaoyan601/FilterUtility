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
		proBar.setValue(cells.size());
		proBar.setString("文件读取成功");
		String[] list = cells.get(0);
		for (String element : list) {
			if (element.length() >= 5) {
				selectTarCol.addItem((String) element.substring(0, 4) + "...");
				selectTarTim.addItem((String) element.substring(0, 4) + "...");
			} else {
				selectTarCol.addItem(element);
				selectTarTim.addItem(element);
			}
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
				
				if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
					form.srcPthTxtFiled.setText(fileName);
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
