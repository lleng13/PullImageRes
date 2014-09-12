package per.llc.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JavaScriptUsedBeanMethodsCollector {
	private Path path = null;
    private MyFileVisitor visitor = null; 
    private Map<String, ArrayList<String>> beanNameMap = null;
    
	public JavaScriptUsedBeanMethodsCollector(String regex, String path) throws Throwable {
		this.path = Paths.get(path);    
    	this.visitor = new MyFileVisitor(regex);
		Files.walkFileTree(this.path, visitor);
	}
	public MyFileVisitor getVisitor() {
		return visitor;
	}
	public void setBeanNameMap() {
		Map<String, ArrayList<String>> map = new HashMap<>();
		for(Path file : getVisitor().getFileList()) {
			
		}
	}
}
