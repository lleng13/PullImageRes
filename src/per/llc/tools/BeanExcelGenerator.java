package per.llc.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class BeanExcelGenerator {
	private HSSFWorkbook workBook;
	private Path path;
	private File file;
	private Map<String, Method[]> map;
	private FileOutputStream out;
	private int rowIndex = 0;
	private CellStyle styleFontBold;
	private CellStyle styleFontColName;



	public BeanExcelGenerator(String path, Map<String, Method[]> map) {
		this.path = Paths.get(path);
		this.map = map;
		this.workBook = new HSSFWorkbook();
		initStyles();
	}
	public void initStyles() {
		setStyleFontBold();
		setStyleFontColName();
	}

	public HSSFWorkbook getWorkBook() {
		return this.workBook;
	}

	public File getFile() {
		return this.file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	private OutputStream getOut() {
		return this.out;
	}
	public void setOut(FileOutputStream out) {
		this.out = out;
	}
	
	public CellStyle getStyleFontColName() {
		return styleFontColName;
	}

	public void setStyleFontColName() {
		CellStyle style = getWorkBook().createCellStyle();
		Font font = getWorkBook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)15);
		font.setColor(HSSFColor.BLUE.index);
		style.setFont(font);
		this.styleFontColName = style;
	}


	private CellStyle getStyleFontBold() {
		return styleFontBold;
	}

	private void setStyleFontBold() {
		CellStyle style = getWorkBook().createCellStyle();
		Font font = getWorkBook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)12);
		style.setFont(font);
		this.styleFontBold = style;
	}
	
	
	private Path getPath() {
		return this.path;
	}
	public void save() throws Exception {
			if(Files.exists(getPath(), LinkOption.NOFOLLOW_LINKS)) {
				writePresentExcel();
			} else {
				setFile(getPath().toFile());
				setOut(new FileOutputStream(getFile()));
				writeNewExcel();
			}
	}

	private void writeNewExcel() {
		initExcel();
		
		Iterator<String> keyIt = map.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Method[] m = map.get(key);
			HSSFSheet sheet = getWorkBook().getSheet(file.getName());
			HSSFCell beanCell = sheet.createRow(rowIndex).createCell(0);
			beanCell.setCellStyle(getStyleFontBold());
			beanCell.setCellValue(key);
			rowIndexIncrease();
			for(int i = 0;i<m.length;i++) {
				sheet.createRow(rowIndex).createCell(0).setCellValue(m[i].getName());
				rowIndexIncrease();
			}
		}
		try {
			workBook.write(getOut());
			getOut().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//TO-DO
	private void writePresentExcel() {

	}
	
	private void initExcel() {
		int top = 0;
		rowIndexIncrease();
		String[] colNames = { "1" , "2 ", "2" , "3" }; 
		HSSFSheet sheet = getWorkBook().createSheet(file.getName());
		HSSFRow topRow = sheet.createRow(top);
		for(int i= 0 ; i < colNames.length ; i++) {
			HSSFCell cell = topRow.createCell(i);
			cell.setCellStyle(getStyleFontColName());
			cell.setCellValue(colNames[i]);
		}
	}
	private void rowIndexIncrease() {
		rowIndex++;
	}
	
}
