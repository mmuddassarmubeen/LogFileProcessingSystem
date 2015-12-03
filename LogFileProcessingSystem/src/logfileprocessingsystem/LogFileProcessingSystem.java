/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logfileprocessingsystem;

import java.io.File;
import java.io.FilenameFilter;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import logger.LogFile;
import logger.LogReader;
import logger.LogWriter;
import logger.LoggingService;

/**
 *
 * @author Muddassar
 */
public class LogFileProcessingSystem {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println("Configure the number of threads");
        Scanner scan = new Scanner(System.in);
        int number = scan.nextInt();
        long startTime = System.currentTimeMillis();
        LoggingService service = new LoggingService();
        service.service(number);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Total Time:" + totalTime);
        
    }
    
}
