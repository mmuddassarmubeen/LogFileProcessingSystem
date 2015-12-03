/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muddassar
 */
public class LogReader implements Runnable {
    
    private final PriorityBlockingQueue<FileInfo> fileQueue;
    private final PriorityBlockingQueue<LogFile> readQueue;
    
    public LogReader(PriorityBlockingQueue<LogFile> priorityQueue, PriorityBlockingQueue<FileInfo> que)
    {
        readQueue = priorityQueue;
        fileQueue = que;
    }

    @Override
    public void run() {
        
        while(true)
        {
        try {
            
            FileInfo fileInfo = fileQueue.take();
            
            if (fileInfo.getFile() == Constants.poisonFile) 
            {
                System.out.println("Poison for reader");
                break;
            } 
            else 
            {
                    try {
                        FileInputStream fis = new FileInputStream(fileInfo.getFile());
                        LogFile logFile = new LogFile(fileInfo.getFile().getName(),fileInfo.getCreatedDate(),fileInfo.getFile(),fis);
                        System.out.println("Added job to processing queue");
                        readQueue.put(logFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    }
    
    
}
