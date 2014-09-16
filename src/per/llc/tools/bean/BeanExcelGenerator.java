package per.llc.tools.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import per.llc.bean.Method;

public class BeanExcelGenerator {
	private HSSFWorkbook workBook;
	private Path path;
	private File file;
	private Map<String, ArrayList<String>> map;
	private FileOutputStream out;
	private int rowIndex = 0;
	private CellStyle styleFontBold;
	private CellStyle styleFontColName;
	private CellStyle styleFontGrey;
	private final String DEFAULT_SHEET = "defautl sheet";
	
	public BeanExcelGenerator(String path, Map<String, ArrayList<String>> map) {
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
	//保存excel
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
		Iterator<String> keyIt = this.map.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Iterator<String> listIt = this.map.get(key).iterator();
			writeNewSingleBean(key, listIt);
		}
		try {
			workBook.write(getOut());
			getOut().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void writeNewSingleBean(String beanName, Iterator<String> methodIt) {
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);//default sheet name
		HSSFCell beanCell = sheet.createRow(sheet.getLastRowNum()+1).createCell(0);
		beanCell.setCellStyle(getStyleFontBold());
		beanCell.setCellValue(beanName);
		while(methodIt.hasNext()) {
			sheet.createRow(sheet.getLastRowNum()+1).createCell(0).setCellValue(methodIt.next());
			rowIndexIncrease();
		}
	}
	//读入excel
	private Map<String, ArrayList<String>> readExcel() {
		Map<String, ArrayList<String>> xlsMap = new HashMap<String, ArrayList<String>>();
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		int rowIndex = 1;
		int beantimes = 0;
		
		String beanName = null;
		ArrayList<String> methodList = new ArrayList<String>();
		do {
			HSSFRow row = sheet.getRow(rowIndex);
			String cellValue = row.getCell(0).getStringCellValue();
			rowIndex++;
			if(isBean(cellValue)) {
				beantimes++;
				if(beantimes == 2) {
					xlsMap.put(beanName, methodList);
					methodList = new ArrayList<String>();
					beantimes = 1;
				}
				beanName = cellValue;
			} else {
				methodList.add(cellValue);
			}
		} while (sheet.getLastRowNum()==rowIndex-1);
		return xlsMap;
	}
	
	//TO-DO
	private void writePresentExcel() {
		//first to read the present excel file
		JavaScriptUsedBeanMethodsCollector jsc = new JavaScriptUsedBeanMethodsCollector();
		String method = null;
		rowIndexIncrease();
		Map<String, ArrayList<String>> xlsMap = readExcel();
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		HSSFRow row = sheet.getRow(rowIndex);
		String beanName = row.getCell(0).getStringCellValue();
		List<String> methods = nextSingleBeanMethodNames();
		if(jsc.isBeanExists(beanName, xlsMap)) {
			Iterator<String> it = methods.iterator();
			while(it.hasNext()) {
				if(jsc.isMethodExists(method = it.next(), this.map.get(beanName))) {
				
				} else {
					
				}
			}
		}
		
	}
	private boolean isBean(String beanName) {
		Pattern pt = Pattern.compile(".*Bean");
		Matcher mt = pt.matcher(beanName);
		if(mt.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	private ArrayList<String> nextSingleBeanMethodNames() {
		return null;
	}
	
	private void initExcel() {
		int top = 0;
		rowIndexIncrease();
		String[] colNames = { "DWR接口和方法" , "完成情况（灰色为忽略）", "负责人" , "备注" }; 
		HSSFSheet sheet = getWorkBook().createSheet(DEFAULT_SHEET);
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
