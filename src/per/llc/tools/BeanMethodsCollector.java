package per.llc.tools;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class BeanMethodsCollector{
	
	
	private Path path = null;
    private BeanVisitor visitor = null;  
    private int cutLength;
    
    private ArrayList<String> packageNameList = new ArrayList<String>();
    private Map<String,Method[]> beanMethodsMap = new HashMap<String, Method[]>();
    
    public BeanMethodsCollector(String regex, String path) throws Exception {
    	this.path = Paths.get(path);    
    	this.visitor = new BeanVisitor(regex);
    	this.cutLength = path.length();
    	Files.walkFileTree(this.path, visitor);
    	setPackageNameList();
    	setBeanMethodsMap();
    }
    
    private void setPackageNameList(){
    	try {
			
			for (Path file : visitor.getList()) { 
				String str = file.toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
				//ȥͷȥβ
				packageNameList.add(str.substring(cutLength,str.length()-5).replace('\\', '.'));
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}    
    }
    
    private void setBeanMethodsMap() throws ClassNotFoundException {
    	Iterator<String> nameIt = getList().iterator();
    	while(nameIt.hasNext()) {
    		String name = nameIt.next();
    		Class<?> c = Class.forName(name);
    		Method[] m = c.getDeclaredMethods();
    		beanMethodsMap.put(name, m);
    	}
    }
    
    public Map<String, Method[]> getBeanMethodsMap() {
    	return beanMethodsMap;
    }
    
    private List<String> getList() {
    	return packageNameList;
    }
}
