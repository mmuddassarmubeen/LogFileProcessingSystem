/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muddassar
 */
public class LogWriter implements Runnable{
    
    private final PriorityBlockingQueue<LogFile> readingQueue;
    private AtomicInteger count;
    private final BlockingQueue<Log> writingQueue;
    
    public LogWriter(PriorityBlockingQueue<LogFile> priorityQueue, BlockingQueue<Log> writeQueue)
    {
        readingQueue = priorityQueue;
        count = new AtomicInteger(0);
        writingQueue = writeQueue;
    }

    @Override
    public void run() {
        
        System.out.println("Started writing");
        while(true)
        {
            try {
                LogFile file = readingQueue.take();
                if(file.getFileHandle()==Constants.poisonFile)
                {
                    System.out.println("Got poison writer");
                    break;
                }
                else
                {
                    
                    FileInputStream stream = file.getFileStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String fileName = Constants.writeDirectory + file.getFileName();
                    
                    String line = reader.readLine();
                    while(line !=null)
                    {
                        builder.append(count.incrementAndGet() + ". " + line);
                        builder.append(System.getProperty("line.separator"));
                        line = reader.readLine();
                    }
                    Log log = new Log(fileName, builder);
                    writingQueue.put(log);
                    
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("Finished writing");
        
    }
}
