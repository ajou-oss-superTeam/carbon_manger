package com.oss.carbonadministrator.controller;

import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 촬영된 전기 고지서 이미지 로컬에 업로드 -> 이후 ai 이미지 인식 처리 진행
    @PostMapping("/electricity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadImg(@RequestParam(name = "image") MultipartFile file) throws IOException {
        String uploadedPath = imageService.uploadToLocal(file);
        return ResponseDto.success(uploadedPath, "촬영 이미지 업로드 성공");
    }

    // AI로 인식된 결과값 보여주기
    // 인식 에러날 경우, 오류 메시지와 함께 0으로 값 설정 후 return -> 사용자가 직접 입력 가능한 페이지로 이동
    @GetMapping("/electricity/{id}")
    public String getRecognizedImgData(@RequestParam String id) {
        return id;
    }

    // 사용자가 입력값 일부 수정(직접 입력) 가능
    @PatchMapping("/electricity/{id}")
    public void editImgData() {

    }
}
