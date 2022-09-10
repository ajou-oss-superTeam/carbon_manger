package com.oss.carbonadministrator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class FileTest {

//    @Test
    void deleteFile(){
        String path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.test";
        File deleteFile = new File(path);

        if(deleteFile.exists()){
            deleteFile.delete();
        }
    }
}
