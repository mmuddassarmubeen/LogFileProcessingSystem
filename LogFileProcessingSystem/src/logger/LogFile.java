/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 *
 * @author Muddassar
 */
public class LogFile implements Comparable<LogFile>{
    
    private String fileName;
    private Date createdDate;
    private File fileHandle;
    private FileInputStream fileStream;
    
    
    public LogFile(String name, Date date, File file, FileInputStream stream)
    {
        fileName = name;
        createdDate = date;
        fileHandle = file;
        fileStream = stream;
    }
    
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDateTime() {
        return createdDate;
    }

    public void setDateTime(Date dateTime) {
        this.createdDate = dateTime;
    }

    public File getFileHandle() {
        return fileHandle;
    }

    public void setFileHandle(File fileHandle) {
        this.fileHandle = fileHandle;
    }

    public FileInputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(FileInputStream fileStream) {
        this.fileStream = fileStream;
    }


    @Override
    public int compareTo(LogFile o) {
        return this.createdDate.compareTo(o.getDateTime());
    }
    
}
