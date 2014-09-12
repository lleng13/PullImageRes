package per.llc.tools;


public class TestPoi {
	public static void main(String[] args) throws Throwable{
		BeanMethodsCollector bmc = new BeanMethodsCollector(".*Bean\\.java$", "E:/edu-mooc/src/main/java/");
		BeanExcelGenerator beg = new BeanExcelGenerator("E:/test.xls",bmc.getBeanMethodsMap());
		beg.save();
	}
}
