package com.oss.carbonadministrator.service.image.strategy;

import com.oss.carbonadministrator.domain.water.WaterInfo;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class Water implements BillStrategy {

    @Override
    public String callOcrFilename() {
        return "ocr_water.py";
    }

    // TODO 데이터 인식
    @Override
    public WaterInfo toDto(String fileName, JSONObject jsonObject) {
        return WaterInfo.builder().build();
    }

    @Override
    public WaterInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toWaterEntity(requestDto);
    }
}
