package com.oss.carbonadministrator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.codec.binary.Base64;
import java.util.UUID;

@SpringBootTest
public class Base64Test {

    //@Test
    void makeBase64Test(){
        String base64 = "";
        String filename = "test.jpg";
        UUID uuid = UUID.randomUUID();

        byte decode[] = Base64.decodeBase64(base64);
        FileOutputStream fos;

        try{
            File target = new File("../ML/working/"+""+uuid+filename);
            target.createNewFile();
            fos = new FileOutputStream(target);
            fos.write(decode);
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
