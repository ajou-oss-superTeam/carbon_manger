package com.oss.carbonadministrator;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {
    @Test
    void contextLoads() {
    }

    @Test
    void jasypt() {
        String url = "abc";
        String username = "def";
        String password = "ggg";

        String encryptUrl = jasyptEncoding(url);
        String encryptName = jasyptEncoding(username);
        String encryptPW = jasyptEncoding(password);

//        System.out.println(encryptUrl+"\n");
//        System.out.println(encryptName+"\n");
//        System.out.println(encryptPW);

        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
        Assertions.assertThat(username).isEqualTo(jasyptDecryt(encryptName));
        Assertions.assertThat(password).isEqualTo(jasyptDecryt(encryptPW));
    }

    public String jasyptEncoding(String value) {

        String key = "oss_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

    private String jasyptDecryt(String input){
        String key = "oss_key";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
