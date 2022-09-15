package com.oss.carbonadministrator.controller.image;

import com.oss.carbonadministrator.domain.electricity.Electricity;
import com.oss.carbonadministrator.dto.request.image.ElecImgRequest;
import com.oss.carbonadministrator.dto.request.image.ImageRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.ElecImageService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ElecImageService elecImageService;

    @PostMapping("/electricity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadElecImg(@RequestBody ImageRequest request)
        throws IOException, ParseException {

        UUID uuid = UUID.randomUUID();

        elecImageService.makeBase64ToImage(request.getImage(), ".jpg", uuid);

        elecImageService.imageToJson(uuid.toString());
        Electricity recognizedElecData = elecImageService.jsonToDto(uuid.toString());
        // 데이터 저장 후 json 파일 삭제
        Electricity savedData = elecImageService.save(request.getEmail(), request.getYear(), request.getMonth(), recognizedElecData);

        return ResponseDto.success(savedData, "전기 고지서 데이터 인식 및 저장 성공");
    }

    @PutMapping("/electricity/{electricityId}/edit")
    public ResponseDto editElecImgData(@PathVariable("electricityId") Long electricityId,
        @RequestBody ElecImgRequest requestDto) {
        elecImageService.update(electricityId, requestDto.toElecEntity(requestDto));
        return ResponseDto.success(null, "전기 고지서 데이터 사용자 수정 완료");
    }

    @PostMapping("/electricity/input")
    public ResponseDto inputElecData(@RequestBody ElecImgRequest requestDto) {
        elecImageService.save(requestDto.getEmail(), requestDto.getYear(), requestDto.getMonth(),
            requestDto.toElecEntity(requestDto));
        return ResponseDto.success(null, "사용자 데이터 직접 입력 성공");
    }
}