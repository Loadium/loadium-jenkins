package com.loadium.jenkins.loadium;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TestRequirement {

    static final String RESOURCES = System.getProperty("user.dir") + "/src/test/resources";


    public static String readToResourceFile(String fileName) {

        File jf = new File(RESOURCES + "/" + fileName);
        String jo = null;
        try {
            jo = FileUtils.readFileToString(jf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jo;
    }
}