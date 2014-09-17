package per.llc.tools.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import per.llc.bean.Method;
import per.llc.bean.MethodStatus;

public class BeanExcelGenerator {
	private HSSFWorkbook workBook;
	private Path path;
	private File file;
	private Map<String, ArrayList<Method>> map;
	private FileOutputStream out;
	private int rowIndex = 0;
	private String DEFAULT_SHEET = "defautl sheet";
	

	private final long TIMESTAMP = System.currentTimeMillis();
	private final String NEW_SHEET = String.valueOf(TIMESTAMP);
	private int readRowNum;
	private int writeRowNum;
	private ExcelStyle es;
	
	public BeanExcelGenerator(Map<String, ArrayList<Method>> map) {
		
		this.map = map;
		this.workBook = new HSSFWorkbook();   
	}
	public void setDefaultSheet(String defautSheet) {
		this.DEFAULT_SHEET = defautSheet;
	}
	public HSSFWorkbook getWorkBook() {
		return this.workBook;
	}
	
	public void setReaderWorkBook() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(getPath().toFile()));
			this.workBook = workbook;
			setDefaultSheet(this.workBook.getSheetAt(0).getSheetName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ExcelStyle getES() {
		this.es = new ExcelStyle(this.workBook);
		return this.es;
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
	private Path getPath() {
		return this.path;
	}
	
	/**
	 * save the target excel file in to given path
	 * @throws Exception
	 */
	public void save(String path) throws Exception {
			this.path = Paths.get(path);
			if(Files.exists(getPath(), LinkOption.NOFOLLOW_LINKS)) {
				setReaderWorkBook();//before write
				writePresentExcel();
				clearEmptyCell();//after write
			} else {
				writeNewExcel();
			}
			try {
				initOutputStream();
				getWorkBook().write(getOut());
				System.out.println("xls has been wroten successfully!");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void clearEmptyCell() {
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		HSSFRow row = null;
		while(readRowNum>writeRowNum) {
			row = sheet.getRow(readRowNum);
			sheet.removeRow(row);
			readRowNum--;
		}
		
	}

	/**
	 * initialize the output stream 
	 */
	private void initOutputStream() {
		try {
			setFile(getPath().toFile());
			setOut(new FileOutputStream(getFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeNewExcel() {
		initExcel(Method.DES);
		Iterator<String> keyIt = this.map.keySet().iterator();
		String key = null;
		while(keyIt.hasNext()) {
			key = keyIt.next();
			writeSingleBean(key, this.map.get(key));
		}
	}
	
	private void writeSingleBean(String beanName, ArrayList<Method> mlist) {
		Iterator<Method> mIt = mlist.iterator();
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		HSSFRow row = null;
		Method m = null;
		while(mIt.hasNext()) {
			row = sheet.createRow(sheet.getLastRowNum()+1);
			m = mIt.next();
			writeCell(row, m);
		}
	}
	/**
	 * write row cells
	 * @param row
	 * @param method
	 */
	private void writeCell(HSSFRow row, Method method) {
		row.createCell(0).setCellValue(method.getBean());
		row.createCell(1).setCellValue(method.getMethod());
		row.createCell(2).setCellValue(method.getSchedule());
		row.createCell(3).setCellValue(method.isValid());
		row.createCell(4).setCellValue(method.getMaintainer());
		row.createCell(5).setCellValue(method.getMemo());
		row.createCell(6).setCellValue(method.getStatus());
	}
	
	private void initExcel(String[] colNames) {
		int top = 0;
		HSSFSheet sheet = getWorkBook().createSheet(NEW_SHEET);
		setDefaultSheet(NEW_SHEET);
		HSSFRow topRow = sheet.createRow(top);
		for(int i= 0 ; i < colNames.length ; i++) {
			HSSFCell cell = topRow.createCell(i);
			cell.setCellStyle(getES().STYLE_FONT_COLNAME);
			cell.setCellValue(colNames[i]);
		}
		
	}
	private void writePresentExcel() {
		rowIndex = 1;
		ArrayList<Method> mlist = readExcel();//read
		initExcel(Method.DES);//initialize sheet
		ArrayList<Method> resultList = getResultList(mlist, this.map);
		Collections.sort(resultList);
		Iterator<Method> it = resultList.iterator();
		Method m = null;
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		for(;it.hasNext();rowIndex++) {
			HSSFRow row = sheet.createRow(rowIndex);
			m = it.next();
			writeCell(row, m);
			Iterator<Cell> cellIt = row.cellIterator();
			if(!m.isValid()) {
				while(cellIt.hasNext()) {
					cellIt.next().setCellStyle(getES().STYLE_FONT_INVALID);
				}
			}
		}
		writeRowNum = sheet.getLastRowNum();
	}
	
	private ArrayList<Method> getResultList(ArrayList<Method> mlist,
			Map<String, ArrayList<Method>> map) {
		Iterator<String> it = map.keySet().iterator();
		String key = null;
		Method m = null;
		while(it.hasNext()) {
			key = it.next();
			mlist = markDifferences(key, mlist, map.get(key));
		}
		Iterator<Method> mIt = mlist.iterator();
		while(mIt.hasNext()) {
			m = mIt.next();
			if(!checkBean(m.getBean(), map)) {
				m.setValidity(false);
			} /*else {
				m.setValidity(true);
			}*/
			
		}
		return mlist;
	}
	private boolean checkBean(String beanName, Map<String, ArrayList<Method>> map) {
		if(map.keySet().contains(beanName))
			return true;
		else
			return false;
	}

	private ArrayList<Method> markDifferences(String beanName, ArrayList<Method> rlist,
			ArrayList<Method> slist) {
		Iterator<Method> rIt = rlist.iterator();
		Iterator<Method> sIt = slist.iterator();
		ArrayList<Method> templist = null;
		Method m = null;
		if(containBean(beanName, rlist)) {
			while(sIt.hasNext()) {
				m = sIt.next();
				templist = new ArrayList<>();
				if(!containMethod(m, rlist)) {
					m.setStatus(MethodStatus.NEW);
					templist.add(m);
					//rlist.add(m);
				} 
			}
			while(rIt.hasNext()) {
				
				try{
					m = rIt.next();
				} catch(Exception e) {
					System.out.println(m);
					e.printStackTrace();
				}
				if(m.getBean().equals(beanName)) {
					if(!containMethod(m, slist)) {
						m.setValidity(false);
						m.setStatus(MethodStatus.CHANGED);
					}
				} /*else {
					m.setValidity(true);
				}*/
			}
			rlist.addAll(templist);
		} else {
			while(sIt.hasNext()) {
				m = sIt.next();
				m.setStatus(MethodStatus.NEW);
			}
			rlist.addAll(slist); //add scanned list into read list
		}
		return rlist;
	}
	
	private boolean containMethod(Method method, ArrayList<Method> list) {
		Iterator<Method> aIt = list.iterator();
		while(aIt.hasNext()) {
			if(aIt.next().getMethod().equals(method.getMethod())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean containBean(String beanName, ArrayList<Method> list) {
		Iterator<Method> aIt = list.iterator();
		while(aIt.hasNext()) {
			if(aIt.next().getBean().equals(beanName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * read excel file input stream into map
	 * @return
	 */
	private ArrayList<Method> readExcel() {
		HSSFSheet sheet = getWorkBook().getSheet(DEFAULT_SHEET);
		HSSFRow row = null;
		Method m = null;
		ArrayList<Method> mlist = new ArrayList<>();
		readRowNum = sheet.getLastRowNum();
		for(int i = 1;i<sheet.getLastRowNum()+1;i++) {
			row = sheet.getRow(i);
			if(row.getCell(0).getStringCellValue()==null) {
				readRowNum = i;
				return mlist;
			}
			m = new Method(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue());
			m.setSchedule(row.getCell(2).getStringCellValue());
			m.setValidity(row.getCell(3).getBooleanCellValue());
			m.setMaintainer(row.getCell(4).getStringCellValue());
			m.setMemo(row.getCell(5).getStringCellValue());
			m.setStatus(MethodStatus.OLD);
			mlist.add(m);
		}
		return mlist;
	}
}
