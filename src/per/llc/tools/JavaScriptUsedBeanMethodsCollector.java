package per.llc.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptUsedBeanMethodsCollector {
	private Path path = null;
    private MyFileVisitor visitor = null; 
    private Map<String, List<String>> beanMethodsMap = null;
    private String regexUrl = "url:'.*?Bean\\..*?'"; //获取调用的URL
    
	public JavaScriptUsedBeanMethodsCollector(String regex, String path) throws Throwable {
		this.path = Paths.get(path);    
    	this.visitor = new MyFileVisitor(regex);
		Files.walkFileTree(this.path, visitor);
	}
	public MyFileVisitor getVisitor() {
		return visitor;
	}
	public boolean hasBeanMethodsSettled(){
//		Map<String, ArrayList<String>> map = new HashMap<>();
		beanMethodsMap = new HashMap<>();
		for(Path filePath : getVisitor().getFileList()) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(filePath.toFile());
				beanMethodsMap = splitIntoMap(filterUrl(in), beanMethodsMap);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public Map<String, List<String>> getBeanMethods() {
		return beanMethodsMap;
	}
	/**
	 * 过滤出符合的url
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public List<String> filterUrl(InputStream in) throws Exception {
		List<String> groups = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str = null;
		//按行读入文件
		while((str=br.readLine())!=null){
			Pattern pt = Pattern.compile(regexUrl);
			Matcher mt = pt.matcher(str);
			while(mt.find()) {
					groups.add(mt.group().substring("url:'".length(),mt.group().length()-1));
			}
		}
		br.close();
		return groups;
	}
	
	/**
	 * 显示流中的行数
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public int countLines(InputStream in) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int lines = 0;
		while(br.readLine()!=null) {
			lines++;
		}
		return lines;
	}
	
	private Map<String, List<String>> splitIntoMap(List<String> urlList, Map<String, List<String>> map) {
		String regexBean = ".*Bean";
		String beanName = null;
		String beanMethod = null;
		Iterator<String> urlIt = urlList.iterator();
		while(urlIt.hasNext()) {
			Pattern pt = Pattern.compile(regexBean);
			String url = urlIt.next();
			Matcher mt = pt.matcher(url);
			mt.find();
			beanName = mt.group();
			beanMethod = url.substring(beanName.length()+1);
			if(isBeanExists(beanName,map)) {
				List<String> list = map.get(beanName);
				if(!isMethodExists(beanMethod, list)){
					//向对应的bean list中添加方法
					list.add(beanMethod);
				}
			} else {
				ArrayList<String> list = new ArrayList<>();
				list.add(beanMethod);
				map.put(beanName, list);
			}
		}
		return map;
	}
	
	private boolean isMethodExists(String method, List<String> list) {
		if(list.contains(method)){
			return true;
		} else {
			return false;
		}
	}
	private boolean isBeanExists(String bean, Map<String, List<String>> map) {
		Set<String> set = map.keySet();
		if(set.contains(bean)) {
			return true;
		} else {
			return false;
		}
	}
}
