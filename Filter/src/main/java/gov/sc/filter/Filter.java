package gov.sc.filter;

import org.apache.log4j.Logger;

import gov.sc.file.ReadExcelFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Filter.class);

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		String file = "D:\\数据\\政法-法院-政法-检察-政法-公安-政法-司法.xls";
		ReadExcelFile rf = new ReadExcelFile(file);
		List<String[]> cells = rf.getCells();
		Cluster cluster = new Cluster(cells, 2, 3);
		List<String[]> result = cluster.getResult_all();
		logger.info("总共有" + result.size() + "个类");

		for (String[] row : result) {
			logger.info(row[2]);
		}
		logger.info("");
	}
}
