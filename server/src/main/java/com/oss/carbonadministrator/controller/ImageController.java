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
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/electricity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadElecImg(@RequestParam String email, @RequestParam Integer year, @RequestParam Integer month, @RequestParam(name = "image") MultipartFile file)
        throws IOException, ParseException {

        // AI 이미지 처리
        String fileName = imageService.uploadToLocal(file);
        imageService.imageToJson(fileName);
        Electricity recognizedElecData = imageService.jsonToDto(fileName);
        // 데이터 저장 후 json 파일 삭제
        Electricity savedData = imageService.save(email, year, month, recognizedElecData);

        return ResponseDto.success(savedData, "전기 고지서 데이터 인식 및 저장 성공");
    }

    @PutMapping("/electricity/{electricityId}/edit")
    public ResponseDto editElecImgData(@PathVariable("electricityId") Long electricityId, @RequestBody ElecImgRequest requestDto) {
        imageService.update(electricityId, requestDto.toElecEntity(requestDto));
        return ResponseDto.success(null, "전기 고지서 데이터 사용자 수정 완료");
    }

    @PostMapping("/electricity/input")
    public ResponseDto inputElecData(@RequestBody ElecImgRequest requestDto) {
        imageService.save(requestDto.getEmail(), requestDto.getYear(), requestDto.getMonth(), requestDto.toElecEntity(requestDto));
        return ResponseDto.success(null, "사용자 데이터 직접 입력 성공");
    }
}
