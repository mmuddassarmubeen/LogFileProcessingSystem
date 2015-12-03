/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

/**
 *
 * @author Muddassar
 */
public class Log {
    
    private String fileName;
    private StringBuilder content;

    public Log(String file, StringBuilder sb)
    {
        fileName = file;
        content = sb;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }
    
    
    
}
