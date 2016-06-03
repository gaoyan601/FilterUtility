package gov.sc.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 该类继承自File类
 * 
 * @author Kevin
 *
 */
public class WriteFile extends File {

	private String file;

	/**
	 * @param file
	 */
	public WriteFile(String file) {
		this.file = file;
	}

	/**
	 * 
	 */
	public WriteFile() {
		// TODO Auto-generated constructor stub
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * 该方法用于将目标list写入targetFile中
	 * 
	 * @param list
	 *            list中的每一个元素都是String数组，表示excel中的一行数据
	 * @return 如果写入成功返回true，如果写入失败，返回false
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void write(List<List<String>> list) throws IOException {
		Workbook workbook;
		if (file.endsWith(".xls")) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("sheet1");
		
		//为了不让原list改变。
		List<List<String>> copyList = new ArrayList<List<String>>(list);	
		dictSort(copyList);
		
		for (int i = 0; i < copyList.size(); i++) {
			List<String> rowList = copyList.get(i);
			Row row = sheet.createRow(i);
			for (int j = 0; j < rowList.size(); j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(rowList.get(j));
			}
		}
		FileOutputStream fout = new FileOutputStream(file);
		workbook.write(fout);
		fout.flush();
		fout.close();
	}
	
	/**
	 * 对list排序的字典排序算法
	 * 
	 * @param list
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void dictSort(List<List<String>> list) throws IOException
	{
		Collections.sort(list, new Comparator<List<String>>()
		{
			public int compare(List<String> o1, List<String> o2)
			{
				// TODO 自动生成的方法存根
				return o1.get(0).compareTo(o2.get(0));
			}
		});
	}
}
