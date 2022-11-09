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
package com.oss.carbonadministrator.service.image.strategy;

import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Electricity implements BillStrategy {


    @Override
    public String callOcrFilename() {
        return "ocr_electricity.py";
    }

    @Override
    public ElectricityInfo toDto(String fileName, JSONObject jsonObject) {
        ElectricityInfo electricityInfo = ElectricityInfo.builder()
            .demandCharge(Integer.parseInt(
                jsonObject.containsKey("base_fee") == true ? ((String) jsonObject.get("base_fee") == "" ? "0" : (String) jsonObject.get("base_fee")) : "0"))
            .energyCharge(Integer.parseInt(
                jsonObject.containsKey("pure_eletric_fee") == true ? ((String) jsonObject.get("pure_eletric_fee") == "" ? "0" : (String) jsonObject.get("pure_eletric_fee")) : "0"))
            .environmentCharge(Integer.parseInt(
                jsonObject.containsKey("environment_fee") == true ? ((String) jsonObject.get("environment_fee") == "" ? "0" : (String) jsonObject.get("environment_fee")) : "0"))
            .fuelAdjustmentRate(Integer.parseInt(
                jsonObject.containsKey("fuel_fee") == true ? ((String) jsonObject.get("fuel_fee") == "" ? "0" : (String) jsonObject.get("fuel_fee")) : "0"))
            .elecChargeSum(Integer.parseInt(
                jsonObject.containsKey("eletric_fee") == true ? ((String) jsonObject.get("eletric_fee") == "" ? "0" : (String) jsonObject.get("eletric_fee")) : "0"))
            .vat(Integer.parseInt(
                jsonObject.containsKey("VATS_fee") == true ? ((String) jsonObject.get("VATS_fee") == "" ? "0" : (String) jsonObject.get("VATS_fee")) : "0"))
            .elecFund(Integer.parseInt(
                jsonObject.containsKey("unknown_fee") == true ? ((String) jsonObject.get("unknown_fee") == "" ? "0" : (String) jsonObject.get("unknown_fee")) : "0"))
            .roundDown(Integer.parseInt(
                jsonObject.containsKey("cutoff_fee") == true ? ((String) jsonObject.get("cutoff_fee") == "" ? "0" : (String) jsonObject.get("cutoff_fee")) : "0"))
            .totalbyCurrMonth(Integer.parseInt(
                jsonObject.containsKey("total_month_fee") == true ? ((String) jsonObject.get("total_month_fee") == "" ? "0" : (String) jsonObject.get("total_month_fee")) : "0"))
            .tvSubscriptionFee(Integer.parseInt(
                jsonObject.containsKey("TV_fee") == true ? ((String) jsonObject.get("TV_fee") == "" ? "0" : (String) jsonObject.get("TV_fee")) : "0"))
            .currMonthUsage(Integer.parseInt(
                jsonObject.containsKey("current_month") == true ? ((String) jsonObject.get("current_month") == "" ? "0" : (String) jsonObject.get("current_month")) : "0"))
            .preMonthUsage(Integer.parseInt(
                jsonObject.containsKey("previous_month") == true ? ((String) jsonObject.get("previous_month") == "" ? "0" : (String) jsonObject.get("previous_month")) : "0"))
            .lastYearUsage(Integer.parseInt(
                jsonObject.containsKey("last_year") == true ? ((String) jsonObject.get("last_year") == "" ? "0" : (String) jsonObject.get("last_year")) : "0"))
            .build();
        electricityInfo.calculateTotalPrice(electricityInfo.getTotalbyCurrMonth(),
            electricityInfo.getTvSubscriptionFee());
        return electricityInfo;
    }

    @Override
    public ElectricityInfo toEntity(ImgDataRequest requestDto) {
        return requestDto.toElecEntity(requestDto);
    }
}
