package per.llc.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class TestPoi {
	public static void main(String[] args) throws Throwable{
		/*BeanMethodsCollector bmc = new BeanMethodsCollector(".*Bean\\.java$", "E:/edu-mooc/src/main/java/");
	    Iterator<String> it = bmc.getBeanMethodsMap().keySet().iterator();
	    while(it.hasNext()) {
	    	String str = it.next();
	    	Method[] m = bmc.getBeanMethodsMap().get(str);
	    	System.out.println(str);
	    	for(int i = 0;i<m.length;i++) {
	    		System.out.println(m[i].getName());
	    	}
	    }
	    HSSFWorkbook wb = new HSSFWorkbook();
	    File file = new File("E:/test.xls");
	    FileOutputStream out = new FileOutputStream(file);
	    int top = 0;
		String[] colNames = { "DWR鎺ュ彛鍜屾柟娉曞悕" , "瀹屾垚鎯呭喌锛堢伆鑹蹭负蹇界暐锛� , "璐熻矗浜� , "澶囨敞" };
		HSSFSheet sheet = wb.createSheet(file.getName());
		HSSFRow topRow = sheet.createRow(top);
		for(int i= 0 ; i < colNames.length ; i++) {
			topRow.createCell(i).setCellValue(colNames[i]);
		}
		Iterator<String> keyIt = bmc.getBeanMethodsMap().keySet().iterator();
		int rowIndex = 1;
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Method[] m = bmc.getBeanMethodsMap().get(key);
			sheet.createRow(rowIndex).createCell(0).setCellValue(key);
			rowIndex++;
			for(int i = 0;i<m.length;i++) {
				sheet.createRow(rowIndex).createCell(0).setCellValue(m[i].getName());
				rowIndex++;
			}
		}
		wb.write(out);
		out.close();*/
		JavaScriptUsedBeanMethodsCollector jsCollector = new JavaScriptUsedBeanMethodsCollector(".*\\.js", "C:\\Git Projects\\");
		if(jsCollector.hasBeanMethodsSettled()) {
			BeanExcelGenerator beg = new BeanExcelGenerator("C:\\Git Projects\\test.xls",jsCollector.getBeanMethods());
			beg.save();
		}
		
		
		
		/*File file = new File("E:/test.xls");
	    FileOutputStream out = new FileOutputStream(file);
	    System.out.println(file.exists());*/
	}
}
