package per.llc.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BeanExcelGenerator {
	private HSSFWorkbook workBook;
	private Path path;
	private File file;
	private Map<String, Method[]> map;
	private FileOutputStream out;

	public BeanExcelGenerator(String path, Map<String, Method[]> map) {
		this.file = new File(path);
		this.map = map;
	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public File getFile() {
		return file;
	}

	public void save() {
		// file 如果存在就用修改
		if (getFile().exists()) {

		} else {

		}
	}

	public void writeNewExcel() {
		HSSFSheet sheet = getWorkBook().createSheet(file.getName());
		Iterator<String> keyIt = map.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Method[] m = map.get(key);
		
		}

	}

	public void writePresentExcel() {

	}
}
