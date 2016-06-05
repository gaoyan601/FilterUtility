package gov.sc.form.listener;

import gov.sc.file.ReadFile;
import gov.sc.form.MainForm;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DropDragSupportTextField extends JTextField implements
		DropTargetListener {
	private MainForm gui;
	public int tarTime;
	public List<String[]> cell;

	public DropDragSupportTextField(MainForm gui) {
		this.gui = gui;
		new DropTarget(gui.jFrame, DnDConstants.ACTION_COPY_OR_MOVE, this, true);
	}

	public List<String[]> getCell() {
		JProgressBar proBar = new JProgressBar();
		proBar = gui.progressbar;
		ReadFile read = new ReadFile(gui.srcPthTxtFiled.getText());
		try {
			cell = read.getCells();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "文件读取失败");
			proBar.setString("文件读取失败");
		}
		return cell;
	}

	public void addItem() {
		JComboBox selectTarCol = new JComboBox();
		selectTarCol = gui.selectTarCol;
		JComboBox selectTarTim = new JComboBox();
		selectTarTim = gui.selectTarTim;
		JProgressBar proBar = new JProgressBar();
		proBar = gui.progressbar;
		selectTarCol.removeAllItems();
		selectTarTim.removeAllItems();
		getCell();
		proBar.setMaximum(cell.size() * 4);
		proBar.setValue(cell.size());
		proBar.setString("文件读取成功");
		String[] list;
		list = getCell().get(0);
		for (String elements : list) {
			if (elements.length() >= 5) {
				selectTarCol.addItem((String) elements.substring(0, 4) + "...");
				selectTarTim.addItem((String) elements.substring(0, 4) + "...");
			} else {
				selectTarCol.addItem(elements);
				selectTarTim.addItem(elements);
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
				for (int i = 0; i < files.size(); i++) {
					gui.srcPthTxtFiled.setText(files.get(i).getAbsolutePath());
				}
				if (gui.srcPthTxtFiled.getText().toString().matches(".*xls")
						|| gui.srcPthTxtFiled.getText().toString()
								.matches(".*xlsx")) {
					addItem();
				} else {
					JOptionPane.showMessageDialog(null, "请选择Excel文件");
				}

			} catch (UnsupportedFlavorException ex) {

			} catch (IOException ex) {
			} catch (Exception e) {

				e.printStackTrace();
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
