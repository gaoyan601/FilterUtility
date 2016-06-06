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
		proBar.setMaximum(cells.size() * 3);
		proBar.setValue(cells.size());
		String[] items = cells.get(0);
		for (int i = 0; i < items.length; i++) {
			selectTarCol.addItem(items[i]);
			selectTarTim.addItem(items[i]);
			if (items[i].matches("标题")) {
				selectTarCol.setSelectedIndex(i);
				continue;
			}
			if (items[i].matches(".*时间|.*日期")) {
				selectTarTim.setSelectedIndex(i);
			}
		}
		proBar.setString("initialization succeed!");
	}

	public void dragEnter(DropTargetDragEvent dtde) {

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
		JProgressBar proBar = form.progressbar;
		proBar.setString("initializing.....");
		try {
			Transferable tr = dtde.getTransferable(); // 得到传递来的数据对象
			// 处理数据对象，得到其中的文本信息
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(dtde.getDropAction());
				@SuppressWarnings("unchecked")
				List<File> s = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);			
				String addr = s.get(s.size()-1).getAbsolutePath();			
				if(addr.endsWith(".xls") || addr.endsWith(".xlsx"))
				{
					 // 在放置目标上显示从拖拽源传递来的文本信息
					form.srcPthTxtFiled.setText(addr);
					dtde.dropComplete(true);
					addItem();
					
				} else {
					form.progressbar.setString("请选择excel文件格式...");
				}
				
				
				
			} else {
				dtde.rejectDrop();
			}
		} catch (Exception err) {
			form.progressbar.setString("initialization failed.....");
			logger.info("initialization failed!" + err.toString());
		}

	}
}
