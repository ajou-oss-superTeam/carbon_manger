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
                .accumulatedMonthUsage(Integer.parseInt(jsonObject.containsKey("accumulatedMonthUsage") == true ? (String) jsonObject.get("accumulatedMonthUsage") : "0"))
                .previousMonthUsage(Integer.parseInt(jsonObject.containsKey("previousMonthUsage") == true ? (String) jsonObject.get("previousMonthUsage") : "0"))
                .checkedUsage(Integer.parseInt(jsonObject.containsKey("checkedUsage") == true ? (String) jsonObject.get("checkedUsage") : "0"))
                .currentMonthUsage(Integer.parseInt(jsonObject.containsKey("currentMonthUsage") == true ? (String) jsonObject.get("currentMonthUsage") : "0"))
                .unitEnergy(Integer.parseInt(jsonObject.containsKey("unitEnergy") == true ? (String) jsonObject.get("unitEnergy") : "0"))
                .usedEnergy(Integer.parseInt(jsonObject.containsKey("usedEnergy") == true ? (String) jsonObject.get("usedEnergy") : "0"))
                .build();
    }

    @Override
    public GasInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toGasEntity(requestDto);
    }
}
