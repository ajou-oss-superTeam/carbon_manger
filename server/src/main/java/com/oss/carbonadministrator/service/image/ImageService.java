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
package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.dto.request.image.ImageRequest;
import com.oss.carbonadministrator.exception.image.ImgUploadFailException;
import com.oss.carbonadministrator.service.image.strategy.BillStrategy;
import com.oss.carbonadministrator.service.image.strategy.BillType;
import com.oss.carbonadministrator.service.image.strategy.StrategyFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final StrategyFactory strategyFactory;

    public static void execPython(String[] command) throws IOException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }

    public String findBillOcr(BillType billType) {
        BillStrategy billStrategy = strategyFactory.findBillStrategy(billType);
        return billStrategy.callOcrFilename();
    }

    @Transactional
    public <T> T convert(BillType billType, ImageRequest request)
        throws IOException, ParseException {
        UUID uuidFileName = UUID.randomUUID();
        makeBase64ToImage(request.getImage(), ".jpg", uuidFileName);
        imageToJson(uuidFileName.toString(), billType);
        T result = jsonToDto(uuidFileName.toString(), billType);
//        this.deleteFile(uuidFileName + ".json");
        return result;
    }

    private <T> T jsonToDto(String fileName, BillType billType) throws IOException, ParseException {
        BillStrategy billStrategy = strategyFactory.findBillStrategy(billType);
        JSONParser parser = new JSONParser();
        String outputPath = this.basePath() + fileName + ".json";

        Reader reader = new FileReader(outputPath);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        return billStrategy.toDto(fileName, jsonObject);
    }

    public String uploadToLocal(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImgUploadFailException("업로드 할 이미지가 없습니다.");
        }

        try {
            String sourceFileName = file.getOriginalFilename();
            String uploadedPath = this.basePath() + sourceFileName;
            File destFile = new File(uploadedPath);
            file.transferTo(destFile);

            return this.fileName(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImgUploadFailException("이미지 업로드 실패하였습니다.");
        }
    }

    public void imageToJson(String fileName, BillType billType) {
        String[] command = new String[6];
        command[0] = "python";
        command[1] = this.basePath().split("working")[0] + findBillOcr(billType);
        command[2] = "-img_path";
        command[3] = this.basePath() + fileName + ".jpg";
        command[4] = "-output_path";
        command[5] = this.basePath() + fileName + ".json";
        try {
            execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        this.deleteFile(fileName + ".jpg");
    }

    public void deleteFile(String fileName) {
        String path = this.basePath() + fileName;
        File deleteFile = new File(path);

        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    public String basePath() {
        File base = new File("./ML/working/base");

        String[] result = base.getAbsolutePath().split("base");

        return result[0];
    }

    public String fileName(MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename());
    }


    public void makeBase64ToImage(String base64, String fileExtension, UUID uuid) {
        byte[] decode = Base64.decodeBase64(base64);
        FileOutputStream fos;

        try {
            File target = new File("./ML/working/" + "" + uuid + fileExtension);
            target.createNewFile();
            fos = new FileOutputStream(target);
            fos.write(decode);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
