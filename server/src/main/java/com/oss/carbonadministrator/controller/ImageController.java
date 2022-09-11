package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.dto.request.Image.ElecImgRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseDto uploadElecImg(@RequestParam String email, @RequestParam Integer year, @RequestParam Integer month, @RequestParam(name = "image") MultipartFile file)
        throws IOException, ParseException {

        // AI 이미지 처리
        String fileName = imageService.uploadToLocal(file);
        imageService.imageToJson(fileName);
        Electricity recognizedElecData = imageService.jsonToDto(fileName);
        // 데이터 저장
        Electricity savedData = imageService.save(email, year, month, recognizedElecData);
        // 데이터 파일 삭제
        imageService.deleteFile(fileName);

        return ResponseDto.success(savedData, "전기 고지서 데이터 인식 및 저장 성공");
    }

    // AI로 인식된 결과값 보여주기
    // 인식 에러날 경우, 오류 메시지와 함께 0으로 값 설정 후 return -> 사용자가 직접 입력 가능한 페이지로 이동
    // 사용자가 입력값 일부 수정(직접 입력) 가능
    @PutMapping("/image/electricity/{electricityId}/edit")
    public ResponseDto editImgData(@PathVariable("electricityId") Long electricityId, @RequestBody ElecImgRequest requestDto) {
        imageService.update(electricityId, requestDto.toElecEntity(requestDto));
        return ResponseDto.success(null, "전기 고지서 데이터 사용자 수정 완료");
    }
}
