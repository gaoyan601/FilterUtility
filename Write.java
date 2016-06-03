package com.zzz.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class Write {
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Write(String file) {
		super();
		this.file = file;
	}

	public void write(List<List<String>> list) throws IOException, ClassNotFoundException {
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
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void dictSort(List<List<String>> list) throws IOException
	{
		Collections.sort(list, new Comparator<List<String>>()
		{
			@Override
			public int compare(List<String> o1, List<String> o2)
			{
				// TODO 自动生成的方法存根
				return o1.get(0).compareTo(o2.get(0));
			}
		});
	}
	
}
