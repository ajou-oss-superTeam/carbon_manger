package com.oss.carbonadministrator;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileTest {

    //    @Test
    void deleteFile() {
        String path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.test";
        File deleteFile = new File(path);

        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    @Test
    void detectPath() {
        File base = new File("..\\ML\\working\\base");

        System.out.println(base.getAbsolutePath());

        String[] basicBase = base.getAbsolutePath().split("base");

        System.out.println(basicBase[0]);
    }
}
