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
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muddassar
 */
public class FileSort implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String creationTime1 = o1.getName().split("\\.")[1];
        String creationTime2 = o2.getName().split("\\.")[1];
        try {
        
        
            Date date1 = dateFormat.parse(creationTime1);
            Date date2 = dateFormat.parse(creationTime2);
            return date1.compareTo(date2);
        } catch (ParseException ex) {
            Logger.getLogger(FileSort.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
    
}
