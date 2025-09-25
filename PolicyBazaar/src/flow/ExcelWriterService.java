package flow;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriterService {
	
	private final XSSFWorkbook workbook;
	private final String filePath;
	
	public ExcelWriterService(String filepath) {
		this.filePath=filepath;
		this.workbook = new XSSFWorkbook();
	}
	
	public  void createSheetsWithHeader(String sheetName, String... headers) {
		XSSFSheet sheet = workbook.createSheet(sheetName);
		Row headerRow = sheet.createRow(0);
		for(int i=0;i<headers.length;i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}
	}
	
	public void writeDataToSheet(String sheetName, List<String[]> data) {
		 XSSFSheet sheet = workbook.getSheet(sheetName);
	        int lastRowNum = sheet.getLastRowNum();

	        for (String[] rowData : data) {
	            Row row = sheet.createRow(++lastRowNum);
	            for (int i = 0; i < rowData.length; i++) {
	                row.createCell(i).setCellValue(rowData[i]);
	            }
	        }
	}
	public void saveWorkbook() throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

}
