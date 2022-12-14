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
     * ????????? ????????? ?????? ??? ??????
     * ????????? ????????? ?????? ??? json ?????? ??????
     */

    /*
     * electricity
     */
    @PostMapping("/electricity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadElecImg(@RequestBody ImageRequest request)
        throws IOException, ParseException {
        BillType billType = BillType.ELECTRICITY;
        ElectricityInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = electricityImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "?????? ????????? ????????? ?????? ??? ?????? ??????");
    }

    /*
     * water
     */
    @PostMapping("/water")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadWaterImg(@RequestBody ImageRequest request)
        throws IOException, ParseException {
        BillType billType = BillType.WATER;
        WaterInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = waterImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "?????? ????????? ????????? ?????? ??? ?????? ??????");
    }

    /*
     * gas
     */

    @PostMapping("/gas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto uploadGasImg(@RequestBody ImageRequest request)
        throws IOException, ParseException {
        BillType billType = BillType.GAS;
        GasInfo recognizedData = imageService.convert(billType, request);

        Bill savedBillData = gasImageService.save(
            request.getEmail(),
            request.getYear(),
            request.getMonth(),
            recognizedData
        );

        return ResponseDto.success(savedBillData, "?????? ????????? ????????? ?????? ??? ?????? ??????");
    }


    /*
     * ????????? ?????? ??? ????????? ????????? ??????
     */
    @PutMapping("/electricity/{electricityId}/edit")
    public ResponseDto editElecImgData(@PathVariable("electricityId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        electricityImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "?????? ????????? ????????? ????????? ?????? ??????");
    }

    @PutMapping("/water/{waterId}/edit")
    public ResponseDto editWaterImgData(@PathVariable("waterId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        waterImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "?????? ????????? ????????? ????????? ?????? ??????");
    }

    @PutMapping("/gas/{gasId}/edit")
    public ResponseDto editGasImgData(@PathVariable("gasId") Long id,
        @RequestBody ImgDataRequest imgDataRequest) {
        gasImageService.update(id, imgDataRequest);
        return ResponseDto.success(null, "?????? ????????? ????????? ????????? ?????? ??????");
    }


    /*
     * ???????????? ?????? ????????? ??????
     */
    @PostMapping("/electricity/input")
    public ResponseDto inputElecData(@RequestBody ImgDataRequest requestDto) {

        electricityImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toElecEntity(requestDto)
        );

        return ResponseDto.success(null, "????????? ?????? ????????? ?????? ?????? ??????");
    }

    @PostMapping("/water/input")
    public ResponseDto inputWaterData(@RequestBody ImgDataRequest requestDto) {

        waterImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toWaterEntity(requestDto)
        );

        return ResponseDto.success(null, "????????? ?????? ????????? ?????? ?????? ??????");
    }

    @PostMapping("/gas/input")
    public ResponseDto inputGasData(@RequestBody ImgDataRequest requestDto) {

        gasImageService.save(
            requestDto.getEmail(),
            requestDto.getYear(),
            requestDto.getMonth(),
            requestDto.toGasEntity(requestDto)
        );

        return ResponseDto.success(null, "????????? ?????? ????????? ?????? ?????? ??????");
    }

}
