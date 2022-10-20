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
                .accumulatedMonthUsage(Integer.parseInt(jsonObject.containsKey("accumulated_month_usage") == true ? (String) jsonObject.get("accumulated_month_usage") : "0"))
                .previousMonthUsage(Integer.parseInt(jsonObject.containsKey("previous_month_usage") == true ? (String) jsonObject.get("previous_month_usage") : "0"))
                .checkedUsage(Integer.parseInt(jsonObject.containsKey("checked_usage") == true ? (String) jsonObject.get("checked_usage") : "0"))
                .currentMonthUsage(Integer.parseInt(jsonObject.containsKey("current_month_usage") == true ? (String) jsonObject.get("current_month_usage") : "0"))
                .unitEnergy(Integer.parseInt(jsonObject.containsKey("unit_energy") == true ? (String) jsonObject.get("unit_energy") : "0"))
                .usedEnergy(Integer.parseInt(jsonObject.containsKey("used_energy") == true ? (String) jsonObject.get("used_energy") : "0"))
                .build();
    }

    @Override
    public GasInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toGasEntity(requestDto);
    }
}
