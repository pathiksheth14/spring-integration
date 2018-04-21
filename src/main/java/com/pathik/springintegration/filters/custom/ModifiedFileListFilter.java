package com.pathik.springintegration.filters.custom;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.file.filters.AbstractFileListFilter;

/**
 * @author ticino
 * @param <F>
 */
public class ModifiedFileListFilter<F> extends AbstractFileListFilter<F> {

    private final Map<F, Long> fileModTime = new HashMap<F, Long>();

    private final Object monitor = new Object();

    /* (non-Javadoc)
     * @see org.springframework.integration.file.filters.AbstractFileListFilter#accept(java.lang.Object)
     */
    @Override
    public boolean accept(F file) {
        synchronized (this.monitor) {
            if (this.fileModTime.get(file) == null) {
                // new file, never seen
                fileModTime.put(file, ((File) file).lastModified());
                return true;
            }
            if (this.fileModTime.get(file).compareTo(((File) file).lastModified()) == 0) {
                return false;
            }
            if (this.fileModTime.get(file).compareTo(((File) file).lastModified()) < 0) {
                // file was modified, update timestamp
                fileModTime.put(file, ((File) file).lastModified());
                return true;
            }
            // modified in past
            return false;
        }
    }
}
