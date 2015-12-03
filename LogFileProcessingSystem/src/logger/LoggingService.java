/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muddassar
 */
public class LoggingService {
    
    
    private static ExecutorService readService = null;
    private static ExecutorService writeService = null;
    public void service(int number)
    {
        //PriorityQueue for tracking files which need to be read
        PriorityBlockingQueue<FileInfo> fileQueue = new PriorityBlockingQueue<FileInfo>(number);
        
        //PriorityQueue for storing fileinputstream to be processed
        PriorityBlockingQueue<LogFile> readQueue = new PriorityBlockingQueue<LogFile>(number);
        
        //Adding log objects to the queue which need to be written to disk
        BlockingQueue<Log> writeQueue = new LinkedBlockingQueue<Log>(number);
        
        //Executor for read service, which handles communication between fileQueue and readQueue
        readService = Executors.newFixedThreadPool(number);
        
        //Executor for write service, which handles communication between readQueue and writeQueue
        writeService = Executors.newFixedThreadPool(number);
        File f = new File(Constants.readDirectory);
        
        FilenameFilter textFilter = new FilenameFilter() 
        {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".log");
            }
        };
        
        File[] files = f.listFiles(textFilter);
        Arrays.sort(files, new FileSort());
        
        //Thread which queues sorted files into the fileQueue
        Thread fileQueuer = new Thread(new FileQueuer(fileQueue, files));
        fileQueuer.start();
        
        //ExecutorService starts LogReader threads, which read the files from fileQueue 
        //and adds LogFile into readQueue which has the fileinputstream
        for(int i=0;i<number;i++)
        {
            readService.execute(new LogReader(readQueue, fileQueue));
        }
        
        //Thread to read the fileinputstreams and build a string builder by appending numbers to lines
        Thread writerThread = new Thread(new LogWriter(readQueue,writeQueue));
        writerThread.start();
        
        //ExecutorService starts ExternalLogWriter threads, which read the files from writeQueue 
        //and writes the files to disk
        for(int i=0;i<number;i++)
        {
            writeService.execute(new ExternalLogWriter(writeQueue));
        }
        
        
        //Terminating all threads
        try {
            fileQueuer.join();
            for(int i=0;i<number;i++)
            {
                fileQueue.put(new FileInfo(new Date(), Constants.poisonFile));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LoggingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.terminate();
        
        
        try 
        {
            readQueue.add(new LogFile("poison", new Date(), Constants.poisonFile, null));
            writerThread.join();
            System.out.println("Join called");
        } catch (InterruptedException ex) {
            Logger.getLogger(LoggingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0;i<number;i++)
        {
            System.out.println("Poison added for external writer");
            writeQueue.add(new Log("poison",null));
        }
        
        this.terminateWrite();
        
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        System.out.println("free memory: " + format.format(freeMemory / 1024));
        System.out.println("allocated memory: " + format.format(allocatedMemory / 1024));
        System.out.println("max memory: " + format.format(maxMemory / 1024));
        System.out.println("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        int mb = 1024*1024;
        System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        
    }
    
    public void terminate()
    {
        System.out.println("Terminate called");
        readService.shutdown();
        try {
            readService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoggingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Terminate done");
    }
    
    public void terminateWrite()
    {
        System.out.println("Write Terminate called");
        writeService.shutdown();
        try {
            writeService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoggingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Write Terminate done");
    }
    
}
