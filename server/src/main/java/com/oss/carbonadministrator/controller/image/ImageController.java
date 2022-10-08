package com.oss.carbonadministrator.controller.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.dto.request.image.ElecImgRequest;
import com.oss.carbonadministrator.dto.request.image.ImageRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.BillType;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /*
     * 이미지 데이터 인식 후 저장
     * 이미지 데이터 저장 후 json 파일 삭제
     */
    @PostMapping("/{billType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadImg(@PathVariable BillType billType, @RequestBody ImageRequest request)
        throws IOException, ParseException {

        ElectricityInfo recognizedData = imageService.convert(billType, request);
        Bill savedBillData = imageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "전기 고지서 데이터 인식 및 저장 성공");
    }

    /*
     * 이미지 인식 후 사용자 데이터 수정
     */
    @PutMapping("/electricity/{billTypeId}/edit")
    public ResponseDto editElecImgData(@PathVariable("billTypeId") Long electricityId, @RequestBody ElecImgRequest imgRequest) {
        imageService.update(electricityId, imgRequest);
        return ResponseDto.success(null, "전기 고지서 데이터 사용자 수정 완료");
    }

    /*
     * 사용자가 직접 데이터 저장
     */
    @PostMapping("/electricity/input")
    public ResponseDto inputElecData(@RequestBody ElecImgRequest requestDto) {
        imageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toElecEntity(requestDto)
        );
        return ResponseDto.success(null, "사용자 데이터 직접 입력 성공");
    }
}
