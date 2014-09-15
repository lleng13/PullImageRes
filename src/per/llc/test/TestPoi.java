package per.llc.test;

import per.llc.tools.BeanExcelGenerator;
import per.llc.tools.JavaScriptUsedBeanMethodsCollector;


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
		String[] colNames = { "DWR閹恒儱褰涢崪灞炬煙濞夋洖鎮�" , "鐎瑰本鍨氶幆鍛枌閿涘牏浼嗛懝韫礋韫囩晫鏆愰敍锟� , "鐠愮喕鐭楁禍锟� , "婢跺洦鏁�" };
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
		
		JavaScriptUsedBeanMethodsCollector jsCollector = new JavaScriptUsedBeanMethodsCollector(".*\\.js", "E:\\edu-mooc\\");
		if(jsCollector.hasBeanMethodsSettled()) {
			BeanExcelGenerator beg = new BeanExcelGenerator("E:\\爱课程接口完成情况.xls",jsCollector.getBeanMethods());
			beg.save();
		}
		
		
		
		/*File file = new File("E:/test.xls");
	    FileOutputStream out = new FileOutputStream(file);
	    System.out.println(file.exists());*/
	}
}
