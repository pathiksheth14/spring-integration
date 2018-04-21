package com.pathik.springintegration.file.processor;

import java.io.File;
import java.util.Random;

public class FileProcessor {
    private final Random random = new Random();

    public File process(File file) throws Exception{
        Thread.sleep(random.nextInt(10)*500);
        return file;
    }
}
