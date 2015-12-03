/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muddassar
 */
public class FileQueuer implements Runnable {

    private final PriorityBlockingQueue<FileInfo> fileQueue;
    private final File[] files;
    
    public FileQueuer(PriorityBlockingQueue<FileInfo> queue, File[] fileCollection)
    {
        fileQueue = queue;
        files = fileCollection;
    }
    
    @Override
    public void run() {
        for(File file : files)
        {
            String creationTime = file.getName().split("\\.")[1];
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date;
            try {
                date = dateFormat.parse(creationTime);
                System.out.println(date.toString());
                fileQueue.put(new FileInfo(date, file));
            } catch (ParseException ex) {
                Logger.getLogger(FileQueuer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
