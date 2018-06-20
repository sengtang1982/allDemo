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
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\2017�����ָ塪�޸İ�.xls")));
		HSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// ��ȡÿ��Sheet��
			sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum����ȡ���һ�е��б�
				HSSFRow row = sheet.getRow(j);
				if (row != null) {
					for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum���ǻ�ȡ���һ����Ϊ�յ����ǵڼ���
						if (row.getCell(k) != null) { // getCell ��ȡ��Ԫ������
							System.out.print(row.getCell(k) + "\t");
						} else {
							System.out.print("\t");
						}
					}
				}
				System.out.println(""); // ����һ�к���
			}
			System.out.println("��ȡsheet��" + workbook.getSheetName(i) + " ���");
		}
		workbook.close();
	}

	@Test
	@Ignore
	public void testWrite() {
		List<String[]> list = new ArrayList<String[]>();
		String str1 = "edit,17382,260828,����ҩ�꼯��,��ְ����,��ϣƼ,����ҩӪҵԱ,�ŵ�רҵ,Ӫ��ǰ̨��,��ְ,1996-08-26,00:00:00.0,18200403609,37";
		String[] line1 = str1.split(",");
		String str2 = "edit,1943,064923,����ҩ�꼯��,������Դ����>��ѧԺ,�����,��ѧԺԺ��,��������,�м�����,��ʽ,1984-06-06 00:00:00.0,13606459117,19";
		String[] line2 = str2.split(",");
		String str3 = "edit,6153,151229,����ҩ�꼯��,������Դ����>������Դ����,����,������Դ��������,��������,�м�����,��ʽ,1982-01-15 00:00:00.0,13697609561,19";
		String[] line3 = str3.split(",");
		list.add(line1);
		list.add(line2);
		list.add(line3);
		write(list);
	}

	public void write(List<String[]> lines) {
		try {
			// ��������stream�Ĺ����������
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
			LOG.info("������д��ִ�����!");
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
		String str1 = "edit,17382,260828,����ҩ�꼯��,��ְ����,��ϣƼ,����ҩӪҵԱ,�ŵ�רҵ,Ӫ��ǰ̨��,��ְ,1996-08-26,00:00:00.0,18200403609,37";
		String[] line1 = str1.split(",");
		String str2 = "edit,1943,064923,����ҩ�꼯��,������Դ����>��ѧԺ,�����,��ѧԺԺ��,��������,�м�����,��ʽ,1984-06-06 00:00:00.0,13606459117,19";
		String[] line2 = str2.split(",");
		String str3 = "edit,6153,151229,����ҩ�꼯��,������Դ����>������Դ����,����,������Դ��������,��������,�м�����,��ʽ,1982-01-15 00:00:00.0,13697609561,19";
		String[] line3 = str3.split(",");
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		write2CSV(lines);
	}

	@Test
	@Ignore
	public void testCreateTable() throws IOException, ClassNotFoundException, SQLException {
		String excelPath = "E:\\temp\\λ��180102.xlsx";
		excelPath.substring(0, excelPath.lastIndexOf("."));
		XSSFWorkbook wb = new XSSFWorkbook(excelPath);
		int numberOfSheets = wb.getNumberOfSheets();
		LOG.debug("����Sheet{}��", numberOfSheets);
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			LOG.debug("��{}��sheet������Ϊ{}", i, sheetName);
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			for (int j = firstRowNum; j < lastRowNum; j++) {
				XSSFRow row = sheet.getRow(j);
				if (row == null) {
					if (j == firstRowNum) {
						throw new RuntimeException("���в���Ϊ��");
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
