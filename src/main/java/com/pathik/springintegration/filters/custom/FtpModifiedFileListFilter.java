package com.pathik.springintegration.filters.custom;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.filters.AbstractFileListFilter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FtpModifiedFileListFilter extends AbstractFileListFilter<FTPFile> {
    private final Map<String, Calendar> fileModTime = new HashMap<>();
    private final Object monitor = new Object();

    public boolean accept(FTPFile file) {
        synchronized (this.monitor) {
            if(this.fileModTime.containsKey(file.getName())){
                // got it
            }else{
                fileModTime.put(file.getName(), file.getTimestamp());
                return true;
            }
            if(this.fileModTime.get(file.getName()).before(file.getTimestamp())){
                this.fileModTime.put(file.getName(), file.getTimestamp());
                return true;
            }
            return false;
        }
    }
}
