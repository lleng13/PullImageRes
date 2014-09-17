package per.llc.tools.bean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import per.llc.bean.Method;
import per.llc.tools.file.MyFileVisitor;

public class BeanMethodsCollector {
	private Path path = null;
    private MyFileVisitor visitor = null; 
    private Map<String, ArrayList<String>> beanMethodsMap = null;
//	private String regexUrl = "url:'.*?Bean?\\..*?'|url : '.*?Bean?\\..*?'"
//			+ "|url: '.*?Bean?\\..*?'|url:' .*?Bean?\\..*?'"; // 获取调用的URL
	private String regexUrl = "(url:'.+?(Bean)\\..+?')|(url : '.+?(Bean)\\..+?')|"
			+ "(url: '.+?(Bean)\\..+?')|(url :'.+?(Bean)\\..+?')"; // 获取调用的URL
	public BeanMethodsCollector(String regex, String path) throws Throwable {
		this.path = Paths.get(path);    
    	this.visitor = new MyFileVisitor(regex);
		Files.walkFileTree(this.path, visitor);
	}
	public BeanMethodsCollector() {
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
	
	public Map<String, ArrayList<String>> getBeanMethods() {
		return beanMethodsMap;
	}
	/**
	 * get a detailed bean method map
	 * @return
	 *          Map<String, Method>
	 */
	public Map<String, ArrayList<Method>> getVerboseMap() {
		Map<String, ArrayList<Method>> vMap = new HashMap<String, ArrayList<Method>>();
		ArrayList<Method> mlist = null; 
		Method m = null;
		Iterator<String> keyIt = getBeanMethods().keySet().iterator();
		while(keyIt.hasNext()) {
			String key = keyIt.next();
			Iterator<String> methodIt = getBeanMethods().get(key).iterator();
			mlist = new ArrayList<>();
			while(methodIt.hasNext()) {
				m = new Method(key,methodIt.next());
				mlist.add(m);
			}
			vMap.put(key, mlist);
		}
		return vMap;
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
				String mtStr = mt.group();
				Pattern trimPt = Pattern.compile(".*?'");
				Matcher trimMt = trimPt.matcher(mtStr);
				if(trimMt.find()) {
					groups.add(mtStr.substring(trimMt.group().length(), mtStr.length()-1));
				}
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
	
	private Map<String, ArrayList<String>> splitIntoMap(List<String> urlList, Map<String, ArrayList<String>> map) {
		String regexBean = ".*?(Bean)";
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
	
	public boolean isMethodExists(String method, List<String> list) {
		if(list.contains(method)){
			return true;
		} else {
			return false;
		}
	}
	public boolean isBeanExists(String bean, Map<String, ArrayList<String>> xlsMap) {
		Set<String> set = xlsMap.keySet();
		if(set.contains(bean)) {
			return true;
		} else {
			return false;
		}
	}
}
