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
		BeanMethodsCollector collector = new BeanMethodsCollector(".*\\.js|.*\\.ftl", "E:\\edu-mooc\\");
		if(collector.hasBeanMethodsSettled()) {
			BeanExcelGenerator beg = new BeanExcelGenerator(collector.getVerboseMap());
			beg.save("E:\\爱课程接口完成情况.xls");
		}
	}
}
