/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.util.Date;

/**
 *
 * @author Muddassar
 */
public class FileInfo implements Comparable<FileInfo>{
    
    private Date createdDate;
    private File file;

    public FileInfo(Date date, File f)
    {
        createdDate = date;
        file = f;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public int compareTo(FileInfo o) {
        return this.getCreatedDate().compareTo(o.getCreatedDate());
    }
    
    
    
}
