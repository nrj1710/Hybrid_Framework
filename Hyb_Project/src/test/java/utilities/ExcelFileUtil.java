package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
                                                               //constructor for reading excel path
public ExcelFileUtil(String ExcelPath)throws Throwable
{
FileInputStream fi = new FileInputStream(ExcelPath);
wb = WorkbookFactory.create(fi);
}
 
public int rowCount(String sheetName)
{
	return wb.getSheet(sheetName).getLastRowNum();
}
                                                                             //get cell data
public String getCellData(String sheetName,int row,int column)
{
	String data ="";
	if(wb.getSheet(sheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
	{
		int celldata = (int) wb.getSheet(sheetName).getRow(row).getCell(column).getNumericCellValue();
		data =String.valueOf(celldata);
	}
	else
	{
		data = wb.getSheet(sheetName).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
                                                                                                       //method for writing data
public void setCellData(String sheetname,int row,int column,String status,String WriteExcel) throws Throwable
{
	                                                                                                         //get sheet from wb
	Sheet ws =wb.getSheet(sheetname);
	                                                                                                    //get row from sheet
	Row rowNum = ws.getRow(row);
	                                                                                                   //create cell from row
	Cell cell =rowNum.createCell(column);
	                                                                                                //write status
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		                                                                                          //colour text with green
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
			style.setFont(font);
		ws.getRow(row).getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//colour text with green
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		
		style.setFont(font);
		ws.getRow(row).getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Blocked"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//colour text with green
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		
		style.setFont(font);
		ws.getRow(row).getCell(column).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(WriteExcel);
	wb.write(fo);

}
}














