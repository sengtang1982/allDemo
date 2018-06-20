package com.ebitg.commons.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsxTest {
	private static final int BUF_ROWS = 100;
	private static final Logger LOG = LoggerFactory.getLogger(XlsxTest.class);

	@Test
	public void testRead() throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\2017年主持稿—修改版.xls")));
		HSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
				HSSFRow row = sheet.getRow(j);
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
						if (row.getCell(k) != null) { // getCell 获取单元格数据
							System.out.print(row.getCell(k) + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				System.out.println(""); // 读完一行后换行
			}
			System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
		}
		workbook.close();
	}

	@Test
	@Ignore
	public void testWrite() {
		List<String[]> list = new ArrayList<String[]>();
		String str1 = "edit,17382,260828,立健药店集团,离职部门,左希萍,成西药营业员,门店专业,营运前台类,离职,1996-08-26,00:00:00.0,18200403609,37";
		String[] line1 = str1.split(",");
		String str2 = "edit,1943,064923,立健药店集团,人力资源中心>商学院,李炳臻,商学院院长,本部经理,中级管理,正式,1984-06-06 00:00:00.0,13606459117,19";
		String[] line2 = str2.split(",");
		String str3 = "edit,6153,151229,立健药店集团,人力资源中心>人力资源本部,刘燕,人力资源本部经理,本部经理,中级管理,正式,1982-01-15 00:00:00.0,13697609561,19";
		String[] line3 = str3.split(",");
		list.add(line1);
		list.add(line2);
		list.add(line3);
		write(list);
	}

	public void write(List<String[]> lines) {
		try {
			// 创建基于stream的工作薄对象的
			Workbook wb = new SXSSFWorkbook(BUF_ROWS);
			Sheet sh = wb.createSheet();
			for (int rownum = 0; rownum < lines.size(); rownum++) {
				Row row = sh.createRow(rownum);
				String[] contents = lines.get(rownum);
				for (int cellnum = 0; cellnum < contents.length; cellnum++) {
					System.out.print(contents[cellnum]);
					Cell cell = row.createCell(cellnum);
					cell.setCellValue(contents[cellnum]);
				}
				System.out.println();
			}
			FileOutputStream out = new FileOutputStream("D://sxssf.xlsx");
			wb.write(out);
			out.close();
			LOG.info("基于流写入执行完毕!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void write2CSV(List<String[]> lines) {
		String fileName = "E://temp//a.csv";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
			for (int i = 0; i < lines.size(); i++) {
				csvPrinter.printRecord(lines.get(i));
			}
			csvPrinter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testWrite2CSV() {
		List<String[]> lines = new ArrayList<String[]>();
		String str1 = "edit,17382,260828,立健药店集团,离职部门,左希萍,成西药营业员,门店专业,营运前台类,离职,1996-08-26,00:00:00.0,18200403609,37";
		String[] line1 = str1.split(",");
		String str2 = "edit,1943,064923,立健药店集团,人力资源中心>商学院,李炳臻,商学院院长,本部经理,中级管理,正式,1984-06-06 00:00:00.0,13606459117,19";
		String[] line2 = str2.split(",");
		String str3 = "edit,6153,151229,立健药店集团,人力资源中心>人力资源本部,刘燕,人力资源本部经理,本部经理,中级管理,正式,1982-01-15 00:00:00.0,13697609561,19";
		String[] line3 = str3.split(",");
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		write2CSV(lines);
	}

	@Test
	@Ignore
	public void testCreateTable() throws IOException, ClassNotFoundException, SQLException {
		String excelPath = "E:\\temp\\位置180102.xlsx";
		excelPath.substring(0, excelPath.lastIndexOf("."));
		XSSFWorkbook wb = new XSSFWorkbook(excelPath);
		int numberOfSheets = wb.getNumberOfSheets();
		LOG.debug("共有Sheet{}个", numberOfSheets);
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			LOG.debug("第{}个sheet的名字为{}", i, sheetName);
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			for (int j = firstRowNum; j < lastRowNum; j++) {
				XSSFRow row = sheet.getRow(j);
				if (row == null) {
					if (j == firstRowNum) {
						throw new RuntimeException("首行不能为空");
					}
					continue;
				}
				short firstCellNum = row.getFirstCellNum();
				short lastCellNum = row.getLastCellNum();
				if (j == firstRowNum) {
					StringBuffer tableStruct = new StringBuffer(sheetName);
					tableStruct.append("(");
					for (int k = firstCellNum; k < lastCellNum; k++) {
						XSSFCell cell = row.getCell(k);
						String head = cell.getStringCellValue();
						if (!StringUtils.isBlank(head)) {
							tableStruct.append(head + ",");
						}
					}
					tableStruct.deleteCharAt(tableStruct.length() - 1);
					tableStruct.append(")");
					System.out.println(tableStruct);
					// createTable(excelPath, tableStruct.toString());
				}
				StringBuffer sb = new StringBuffer();
				for (int k = firstCellNum; k < lastCellNum; k++) {
					XSSFCell cell = row.getCell(k);
					sb.append("(").append(j).append(",").append(k).append(")").append("=").append(cell);
				}
				LOG.debug(sb.toString());
			}
		}
		wb.close();
	}

}
