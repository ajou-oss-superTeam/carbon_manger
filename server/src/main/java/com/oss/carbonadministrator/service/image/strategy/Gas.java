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
        return GasInfo.builder().demandCharge(Integer.parseInt(jsonObject.containsKey("demandCharge") == true ? (String) jsonObject.get("demandCharge") : "0"))
                .vat(Integer.parseInt(jsonObject.containsKey("vat") == true ? (String) jsonObject.get("vat") : "0"))
                .roundDown(Integer.parseInt(jsonObject.containsKey("roundDown") == true ? (String) jsonObject.get("roundDown") : "0"))
                .totalbyCurrMonth(Integer.parseInt(jsonObject.containsKey("totalbyCurrMonth") == true ? (String) jsonObject.get("totalbyCurrMonth") : "0"))
                .totalPrice(Integer.parseInt(jsonObject.containsKey("totalPrice") == true ? (String) jsonObject.get("totalPrice") : "0"))
                .currMonthUsage(Integer.parseInt(jsonObject.containsKey("currMonthUsage") == true ? (String) jsonObject.get("currMonthUsage") : "0"))
                .build();
    }

    @Override
    public GasInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toGasEntity(requestDto);
    }
}
