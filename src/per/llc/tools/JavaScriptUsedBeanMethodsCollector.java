package per.llc.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Map<String, ArrayList<String>> beanNameMap = null;
    private String regexUrl = "url:'.*Bean\\..*'"; //��ȡ���õ�URL
    
	public JavaScriptUsedBeanMethodsCollector(String regex, String path) throws Throwable {
		this.path = Paths.get(path);    
    	this.visitor = new MyFileVisitor(regex);
		Files.walkFileTree(this.path, visitor);
	}
	public MyFileVisitor getVisitor() {
		return visitor;
	}
	public void setBeanNameMap() throws Exception {
//		Map<String, ArrayList<String>> map = new HashMap<>();
		beanNameMap = new HashMap<>();
		for(Path filePath : getVisitor().getFileList()) {
			FileInputStream in = new FileInputStream(filePath.toFile());
			beanNameMap = splitIntoMap(filterUrl(in), beanNameMap);
		}
	}
	
	/**
	 * ���˳����ϵ�url
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public List<String> filterUrl(InputStream in) throws Exception {
		List<String> groups = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int lines = countLines(in);
		String str = null;
		for(int i=0;i<lines;i++) {
			str = br.readLine();
			Pattern pt = Pattern.compile(regexUrl);
			Matcher mt = pt.matcher(str);
			if(mt.find()) {
				int count = mt.groupCount();
				for(int j=0;j<count;j++) {
					//ȥ��url:'ǰ׺
					groups.add(mt.group(j).substring("url:".length()));
				}
			}
		}
		br.close();
		return groups;
	}
	
	/**
	 * ��ʾ���е�����
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public int countLines(InputStream in) throws Exception {
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(in));
		int lines = lnr.getLineNumber();
		lnr.close();
		return lines;
	}
	
	private Map<String, ArrayList<String>> splitIntoMap(List<String> urlList, Map<String, ArrayList<String>> map) {
		String regexBean = ".*Bean";
		String beanName = null;
		String beanMethod = null;
		Iterator<String> urlIt = urlList.iterator();
		while(urlIt.hasNext()) {
			Pattern pt = Pattern.compile(regexBean);
			Matcher mt = pt.matcher(urlIt.next());
			beanName = mt.group(0);
			beanMethod = mt.group(0).substring(beanName.length()+1);
			if(isBeanExists(beanName,map)) {
				ArrayList<String> list = map.get(beanName);
				//���Ӧ��bean list����ӷ���
				list.add(beanMethod);
			} else {
				ArrayList<String> list = new ArrayList<>();
				list.add(beanMethod);
				map.put(beanName, list);
			}
		}
		return map;
	}
	
	private boolean isBeanExists(String bean, Map<String, ArrayList<String>> map) {
		Set<String> set = map.keySet();
		if(set.contains(bean)) {
			return true;
		} else {
			return false;
		}
	}
}
