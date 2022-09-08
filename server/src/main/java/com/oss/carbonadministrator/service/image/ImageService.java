package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.exception.ImgUploadFailException;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public String uploadToLocal(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImgUploadFailException("업로드 할 이미지가 없습니다.");
        }

        try {
            String sourceFileName = file.getOriginalFilename();
            String uploadedPath = "C:/Image/uploaded_" + sourceFileName;
            File destFile = new File(uploadedPath);
            file.transferTo(destFile);
            return uploadedPath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImgUploadFailException("이미지 업로드 실패하였습니다.");
        }
    }
}
