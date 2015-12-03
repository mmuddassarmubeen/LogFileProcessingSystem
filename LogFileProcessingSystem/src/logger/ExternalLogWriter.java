/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Muddassar
 */
public class ExternalLogWriter implements Runnable {
    private final BlockingQueue<Log> queue;
    
    public ExternalLogWriter(BlockingQueue<Log> writeQueue)
    {
        queue = writeQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Log log = queue.take();
                if(log.getContent() == null)
                {
                    System.out.println("Got external writing poison pill");
                    break;
                }
                StringBuilder builder = log.getContent();
                flush(log.getFileName(),builder.toString());
            } catch(InterruptedException ex) {
                System.out.println("Exception: LogStream.run: " + ex.getMessage());
            }    
        }
    }
    
    private void flush(String path, String data) {

        BufferedWriter writer = null;

        try {

            writer = getOutputStream(path);
            writer.write(data);
            writer.newLine();
            writer.flush();             
        }
        catch(IOException ex) {
            System.out.println("IOException: EventLog.flush: " + ex.getMessage());
        }
        finally {
            closeOutputStream(writer);
        }
    }
    
    private BufferedWriter getOutputStream(String path) throws IOException 
    {
        return new BufferedWriter(new FileWriter(path));         
    }
    
    private void closeOutputStream(BufferedWriter writer) {
        try {
            if(writer != null) {
                writer.close();
            }
        }
        catch(Exception ex) {
            System.out.println("Exception: LogStream.closeOutputStream: " + ex.getMessage());
        }   
    }
}
