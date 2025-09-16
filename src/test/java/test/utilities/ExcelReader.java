package test.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import constants.FrameworkConstants;

/**
 * Utility class for reading and writing Excel (.xlsx) files.
 * Supports operations like reading cell data, setting cell data,
 * counting rows/columns, and marking cells with colors.
 * Uses Apache POI library.
 */
public class ExcelReader {

    private static String path; // Path to Excel file

	/**
	 * Constructor
	 * 
	 * @param path Path to the Excel file
	 */
	public ExcelReader(String path) {
		ExcelReader.path = path;
	}
	
	/**
	 * Common method: ensures file + sheet exist, returns workbook & sheet.
	 */
	private XSSFWorkbook getOrCreateWorkbookAndSheet(String sheetName) throws IOException {
	    File file = new File(path);
	    XSSFWorkbook workbook;
	    XSSFSheet sheet;

	    if (file.exists()) {
	        try (FileInputStream fis = new FileInputStream(file)) {
	            workbook = new XSSFWorkbook(fis);
	        }
	    } else {
	        workbook = new XSSFWorkbook();
	    }

	    sheet = workbook.getSheet(sheetName);
	    if (sheet == null) {
	        sheet = workbook.createSheet(sheetName);
	    }

	    return workbook; // sheet is already guaranteed inside workbook
	}

	/**
	 * Common method to open workbook
	 * 
	 * @throws IOException
	 */
	private XSSFWorkbook openWorkbook() throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			return new XSSFWorkbook();
		}
		try (FileInputStream fi = new FileInputStream(file)) {
			return new XSSFWorkbook(fi);
		}
	}

	/**
	 * Common method to save & close workbook
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	private void saveAndCloseWorkbook(XSSFWorkbook workbook) throws FileNotFoundException, IOException {
		try (FileOutputStream fo = new FileOutputStream(path)) {
			workbook.write(fo);
		}
		workbook.close();
	}

    /** 
     * Get total number of rows in a sheet 
     */
    public int getRowCount(String sheetName) throws IOException {
        try (XSSFWorkbook workbook = openWorkbook()) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            return (sheet != null) ? sheet.getLastRowNum() : 0;
        }
    }

    /** 
     * Get total number of cells in a row 
     */
    public int getCellCount(String sheetName, int rownum) throws IOException {
        try (XSSFWorkbook workbook = openWorkbook()) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = (sheet != null) ? sheet.getRow(rownum) : null;
            return (row != null) ? row.getLastCellNum() : 0;
        }
    }

    /** 
     * Get cell data as String 
     */
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        try (XSSFWorkbook workbook = openWorkbook()) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return "";
            
            XSSFRow row = sheet.getRow(rownum);
            if (row == null) return "";

            XSSFCell cell = row.getCell(colnum);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        }
    }

    /** 
     * Write data to a cell (creates sheet/row/cell if missing) 
     */
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
       
        XSSFWorkbook workbook = getOrCreateWorkbookAndSheet(sheetName);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        XSSFRow row = (sheet.getRow(rownum) != null) ? sheet.getRow(rownum) : sheet.createRow(rownum);
        XSSFCell cell = row.createCell(colnum);
        cell.setCellValue(data);

      saveAndCloseWorkbook(workbook);
    }

    /** 
     * Fill cell with green background 
     */
    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    /** 
     * Fill cell with red background 
     */
    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    /** 
     * Helper: Fill a cell with a specific color 
     */
    private void fillCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        try (XSSFWorkbook workbook = openWorkbook()) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return;

            XSSFRow row = sheet.getRow(rownum);
            if (row == null) return;

            XSSFCell cell = row.getCell(colnum);
            if (cell == null) return;

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

	/**
	 * Append test result to the Excel file in "Results" sheet. If file/sheet does
	 * not exist, creates them.
	 */
	public static void writeResult(String testCaseId, String result) {
		try {
			
			ExcelReader reader = new ExcelReader(path);
			XSSFWorkbook workbook = reader.getOrCreateWorkbookAndSheet("Results");
			XSSFSheet sheet = workbook.getSheet("Results");

			// If first time, create header
			if (sheet.getLastRowNum() == 0 && sheet.getRow(0) == null) {
				createResultHeader(sheet);
			}

			int rowCount = sheet.getLastRowNum();
			Row row = sheet.createRow(rowCount + 1);
			row.createCell(0).setCellValue(testCaseId);
			row.createCell(1).setCellValue(result);

			try (FileOutputStream fos = new FileOutputStream(path)) {
				workbook.write(fos);
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /** 
     * Create header row for Results sheet 
     */
    private static void createResultHeader(Sheet sheet) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Test Case ID");
        header.createCell(1).setCellValue("Result");
    }
}
