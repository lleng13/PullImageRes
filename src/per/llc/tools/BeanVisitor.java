package per.llc.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class BeanVisitor extends SimpleFileVisitor<Path>{
	private ArrayList<Path> list = new ArrayList<>(); 
	 
	private PathMatcher matcher = null;
	public BeanVisitor(String pattern){
		super();
		matcher = FileSystems.getDefault().getPathMatcher("regex:" + pattern);
	}
    @Override    
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)    
            throws IOException {    
        Path name = file.getFileName();    
       // System.out.println("Examing: " + name.toString());    
        if (matcher.matches(name)) {    
            list.add(file);    
        }    
        return FileVisitResult.CONTINUE;    
    }  
    public ArrayList<Path> getList() {    
        return list;    
    }    
}