package per.llc.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BeanExcelGenerator {
	private HSSFWorkbook workBook;
	private Path path;
	private File file;
	private Map<String, Method[]> map;
	private FileOutputStream out;
	private int rowIndex = 0;
	
	public BeanExcelGenerator(String path, Map<String, Method[]> map) {
		this.file = new File(path);
		this.map = map;
		try {
			this.out = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.workBook = new HSSFWorkbook();
	}

	public HSSFWorkbook getWorkBook() {
		return this.workBook;
	}

	public File getFile() {
		return this.file;
	}
	
	public OutputStream getOut() {
		return this.out;
	}
	public void save() {
		
		// file 如果存在就用修改
		if (getFile().exists()) {
			writePresentExcel();
		} else {
			writeNewExcel();
		}
	}

	private void writeNewExcel() {
		//初始化Excel表格栏的标题
		initExcel();
		Iterator<String> keyIt = map.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Method[] m = map.get(key);
			HSSFSheet sheet = getWorkBook().getSheet(file.getName());
			//写入Bean名称
			sheet.createRow(rowIndex).createCell(0).setCellValue(key);
			rowIndexIncrease();
			for(int i = 0;i<m.length;i++) {
				sheet.createRow(rowIndex).createCell(0).setCellValue(m[i].getName());
			}
		}
		try {
			workBook.write(getOut());
			getOut().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writePresentExcel() {

	}
	
	private void initExcel() {
		int top = 0;
		rowIndexIncrease();
		String[] colNames = { "DWR接口和方法名" , "完成情况（灰色为忽略）" , "负责人" , "备注" }; 
		HSSFSheet sheet = getWorkBook().createSheet(file.getName());
		HSSFRow topRow = sheet.createRow(top);
		for(int i= 0 ; i < colNames.length ; i++) {
			topRow.createCell(i).setCellValue(colNames[i]);
		}
	}
	private void rowIndexIncrease() {
		rowIndex++;
	}
}
