package com.oss.carbonadministrator;

import com.oss.carbonadministrator.service.image.ImageService;
import com.oss.carbonadministrator.service.image.strategy.BillStrategy;
import com.oss.carbonadministrator.service.image.strategy.BillType;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.UUID;

import com.oss.carbonadministrator.service.image.strategy.StrategyFactory;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PythonTest {

    @Autowired
    ImageService imageService;
    private StrategyFactory strategyFactory;

    @Test
    @Ignore
    void execPythonTest() throws IOException, InterruptedException {

        System.out.println("Python Call");
        String[] command = new String[6];
        command[0] = "python";
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

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }

    @Test
    @Ignore
    void funcTest() throws IOException, InterruptedException {
        String fileName = "receipt1";
        BillType billType = BillType.ELECTRICITY;
        imageService.imageToJson(fileName, billType);
        imageService.deleteFile(fileName + ".json");
    }

    @Test
    @Ignore
    void funcGasTest() throws IOException, InterruptedException {
        String fileName = "20220817_210459";
        BillType billType = BillType.GAS;
        imageService.imageToJson(fileName, billType);
        imageService.deleteFile(fileName + ".json");
    }
}
