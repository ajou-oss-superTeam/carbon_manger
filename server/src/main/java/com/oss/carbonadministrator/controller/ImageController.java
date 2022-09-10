package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.dto.request.Image.ElecImgRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/image/electricity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadImg(@RequestParam(name = "image") MultipartFile file)
        throws IOException, ParseException {

        String fileName = imageService.uploadToLocal(file);
        imageService.imageToJson(fileName);
        Electricity recognizedElecData = imageService.jsonToDto(fileName);

        return ResponseDto.success(recognizedElecData, "전기 고지서 데이터 인식 성공");
    }

    // AI로 인식된 결과값 보여주기
    // 인식 에러날 경우, 오류 메시지와 함께 0으로 값 설정 후 return -> 사용자가 직접 입력 가능한 페이지로 이동
    // 사용자가 입력값 일부 수정(직접 입력) 가능
    @PatchMapping("/image-edit/electricity")
    public void editImgData(@RequestBody ElecImgRequest requestDto) {
        //파라미터로 데이터를 다시 받아오면 됨.
//        imageService.editElec();
//        return ;
    }
}
