package com.oss.carbonadministrator;

import com.oss.carbonadministrator.service.image.ImageService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.IOException;

@SpringBootTest
public class PythonTest {
    @Autowired
    ImageService imageService;

//    @Test
//    @Ignore
    void execPythonTest() throws IOException, InterruptedException{

        System.out.println("Python Call");
        String[] command = new String[6];
        command[0] = "python";
        //command[1] = "\\workspace\\java-call-python\\src\\main\\resources\\test.py";
        command[1] = "..\\ML\\ocr_electronic.py";
        command[2] = "-img_path";
        command[3] = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\receipt1.jpg";
        command[4] = "-output_path";
        command[5] = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.txt";
        try {
            execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
//        executor.setStreamHandler(pumpStreamHandler);
        executor.execute(commandLine);
    }

    @Test
//    @Ignore
    void funcTest() throws IOException, InterruptedException{
        String fileName = "receipt1";
        imageService.imageToJson(fileName);
    }
}