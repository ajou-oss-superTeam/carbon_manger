package com.oss.carbonadministrator.controller.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.gas.GasInfo;
import com.oss.carbonadministrator.domain.water.WaterInfo;
import com.oss.carbonadministrator.dto.request.image.ImageRequest;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.service.image.ElectricityImageService;
import com.oss.carbonadministrator.service.image.GasImageService;
import com.oss.carbonadministrator.service.image.ImageService;
import com.oss.carbonadministrator.service.image.WaterImageService;
import com.oss.carbonadministrator.service.image.strategy.BillType;
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
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;
    private final ElectricityImageService electricityImageService;

    private final WaterImageService waterImageService;

    private final GasImageService gasImageService;



    /*
     * 이미지 데이터 인식 후 저장
     * 이미지 데이터 저장 후 json 파일 삭제
     */

    /*
     * electricity
     */
    @PostMapping("/{elecBillType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadElecImg(@PathVariable BillType billType,
        @RequestBody ImageRequest request)
        throws IOException, ParseException {

        ElectricityInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = electricityImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "전기 고지서 데이터 인식 및 저장 성공");
    }

    /*
     * water
     */
    @PostMapping("/{waterBillType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadWaterImg(@PathVariable BillType billType,
        @RequestBody ImageRequest request)
        throws IOException, ParseException {

        WaterInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = waterImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "수도 고지서 데이터 인식 및 저장 성공");
    }

    /*
     * gas
     */

    @PostMapping("/{gasBillType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadGasImg(@PathVariable BillType billType,
        @RequestBody ImageRequest request)
        throws IOException, ParseException {

        GasInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = gasImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "가스 고지서 데이터 인식 및 저장 성공");
    }


    /*
     * 이미지 인식 후 사용자 데이터 수정
     */
    @PutMapping("/electricity/{electricityId}/edit")
    public ResponseDto editElecImgData(@PathVariable("electricityId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        electricityImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "전기 고지서 데이터 사용자 수정 완료");
    }

    @PutMapping("/water/{waterId}/edit")
    public ResponseDto editWaterImgData(@PathVariable("waterId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        waterImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "수도 고지서 데이터 사용자 수정 완료");
    }

    @PutMapping("/gas/{gasId}/edit")
    public ResponseDto editGasImgData(@PathVariable("gasId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        gasImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "수도 고지서 데이터 사용자 수정 완료");
    }


    /*
     * 사용자가 직접 데이터 저장
     */
    @PostMapping("/electricity/input")
    public ResponseDto inputElecData(@RequestBody ImgDataRequest requestDto) {

        electricityImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toElecEntity(requestDto)
        );

        return ResponseDto.success(null, "사용자 전기 데이터 직접 입력 성공");
    }

    @PostMapping("/water/input")
    public ResponseDto inputWaterData(@RequestBody ImgDataRequest requestDto) {

        waterImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toWaterEntity(requestDto)
        );

        return ResponseDto.success(null, "사용자 수도 데이터 직접 입력 성공");
    }

    @PostMapping("/gas/input")
    public ResponseDto inputGasData(@RequestBody ImgDataRequest requestDto) {

        gasImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toGasEntity(requestDto)
        );

        return ResponseDto.success(null, "사용자 가스 데이터 직접 입력 성공");
    }

}
