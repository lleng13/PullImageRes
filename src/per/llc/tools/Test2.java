package per.llc.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {
	public static void main(String[] args) throws Throwable {
		/*
		 * Path path = Paths.get("C:\\Git Projects\\"); MyFileVisitor visitor =
		 * new MyFileVisitor(".*\\.js"); Files.walkFileTree(path, visitor);
		 * 
		 * for (Path file : visitor.getFileList()) { String str =
		 * file.toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
		 * System.out.println(str); }
		 */

		
		  JavaScriptUsedBeanMethodsCollector js = new
		  JavaScriptUsedBeanMethodsCollector(".*\\.js", "C:\\Git Projects\\");
		  js.setBeanNameMap();
		  Map<String, ArrayList<String>> map = js.getBeanNameMap(); 
		  Iterator<String> it = map.keySet().iterator();
		  Iterator<String> it2 = null;
		  ArrayList<String> list = null;
		  String key = null;
		  while(it.hasNext()) 
		  { 
			  key = it.next();
			  System.out.println(key); 
			  list =  map.get(key);
			  it2 = list.iterator();
			  while(it2.hasNext()) {
				  System.out.println(it2.next());
			  }
		  }
		 
		/*String str = "PostBean.addReply";
		String regexUrl = ".*Bean";
		String url = "PostBean.addReply";
		Pattern pt = Pattern.compile(regexUrl);
		Matcher mt = pt.matcher(str);
		while(mt.find()) {
			System.out.println(mt.start()+" "+mt.end());
		    System.out.println(mt.group() + " "+url.substring(mt.group().length()+1));
	}*/
		/*Pattern p=Pattern.compile("a{3}");
		Matcher m=p.matcher("aaaBBBBBcccccaaa");
		while(m.find()) {
				System.out.println(m.groupCount());
			    System.out.println(m.group());
		}*/
	}
}
