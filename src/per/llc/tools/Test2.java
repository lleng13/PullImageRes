package per.llc.tools;

import java.io.IOException;    
import java.nio.file.Files;       
import java.nio.file.LinkOption;
import java.nio.file.Path;    
import java.nio.file.Paths;    
    
    
public class Test2 {    
    public static void main(String[] args) throws IOException {    
        Path path = Paths.get("E:/edu-mooc/src/main/webapp/src/javascript/");    
        MyFileVisitor visitor = new MyFileVisitor(".*\\.js");    
        Files.walkFileTree(path, visitor);    
            
        for (Path file : visitor.getFileList()) {    
        	String str = file.toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
			//System.out.println(file.toRealPath(LinkOption.NOFOLLOW_LINKS));
			//str.substring(cutLength);
        	System.out.println(str);
        	
        }    
    }    
}
