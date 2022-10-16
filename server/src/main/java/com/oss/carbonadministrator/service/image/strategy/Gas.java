package com.oss.carbonadministrator.service.image.strategy;


import com.oss.carbonadministrator.domain.gas.GasInfo;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Gas implements BillStrategy {

    @Override
    public String callOcrFilename() {
        return "ocr_gas.py";
    }

    // TODO 데이터 인식
    @Override
    public GasInfo toDto(String fileName, JSONObject jsonObject) {
        return GasInfo.builder().build();
    }

    @Override
    public GasInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toGasEntity(requestDto);
    }
}
