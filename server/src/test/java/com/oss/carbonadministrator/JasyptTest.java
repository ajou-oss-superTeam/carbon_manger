/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
