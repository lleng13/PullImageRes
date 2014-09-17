package per.llc.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import per.llc.tools.bean.BeanExcelGenerator;
import per.llc.tools.bean.BeanMethodsCollector;


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
		String[] colNames = { "DWR闁规亽鍎辫ぐ娑㈠椽鐏炵偓鐓欐繛澶嬫礀閹拷" , "閻庣懓鏈崹姘跺箚閸涱厼鏋岄柨娑樼墢娴煎棝鎳濋煫顓＄闊洨鏅弳鎰版晬閿燂拷 , "閻犳劗鍠曢惌妤佺閿燂拷 , "濠㈣泛娲﹂弫锟�" };
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
		
		BeanMethodsCollector collector = new BeanMethodsCollector(".*\\.js|.*\\.ftl", "E:\\edu-mooc\\");
		if(collector.hasBeanMethodsSettled()) {
			BeanExcelGenerator beg = new BeanExcelGenerator("E:\\爱课程接口完成情况.xls",collector.getVerboseMap());
			beg.save();
		}
	/*	HSSFWorkbook wb = new HSSFWorkbook();
		FileInputStream in = new FileInputStream("E:\\鐖辫绋嬫帴鍙ｅ畬鎴愭儏鍐�.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getLastRowNum());*/
		
	    /*FileInputStream in = new FileInputStream("E:\\edu-mooc\\src\\main\\webapp\\template\\backend\\course\\category.ftl");
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String str = null;
	    while((str=br.readLine())!=null){
	    	System.out.println(str);
	    }*/
	/*	String str = "2222222222222222222url : 'MemcacheAdminBean.deleteCacheBeanByName'222222222";
		String regex ="(url:'.+?(Bean)\\..+?')|(url : '.+?(Bean)\\..+?')|(url: '.+?(Bean)\\..+?')|(url :'.+?(Bean)\\..+?')";
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(str);
		while(mt.find()) {
			String mtStr = mt.group();
			Pattern trimPt = Pattern.compile(".*?'");
			Matcher trimMt = trimPt.matcher(mtStr);
			if(trimMt.find()) {
				System.out.println(mt.group().substring(trimMt.group().length(), mt.group().length()-1));
		}
		}	*/
	}
}
