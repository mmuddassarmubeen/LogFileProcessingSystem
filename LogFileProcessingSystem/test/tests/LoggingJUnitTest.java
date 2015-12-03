package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import logger.Constants;
import logger.FileSort;
import logger.LoggingService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Muddassar
 */
public class LoggingJUnitTest extends TestCase {
    
    public LoggingJUnitTest() {
    }
    
    public void testFileCount() throws IOException
    {
        FilenameFilter textFilter = new FilenameFilter() 
        {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".log");
            }
        };
        
        
        File readDirectory = new File(Constants.readDirectory);
        File[] readFiles = readDirectory.listFiles(textFilter);
        File writeDirectory = new File(Constants.writeDirectory);
        
        for(File file: writeDirectory.listFiles()) 
        {
            file.delete();
        }
        
        LoggingService service = new LoggingService();
        service.service(100);
        File[] writeFiles = writeDirectory.listFiles(textFilter);
        assert readFiles.length == writeFiles.length;
        Arrays.sort(writeFiles, new FileSort());
        if(writeFiles.length>1)
        {
            
                FileReader fileReader = new FileReader(writeFiles[0]);
                BufferedReader br = new BufferedReader(fileReader);
                String line = br.readLine();
                System.out.println("assert number");
                assert line.charAt(0) == '1';
        }
    }
    
}
