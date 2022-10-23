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
package com.oss.carbonadministrator;

import static org.junit.Assert.assertEquals;

import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.repository.electricity.ElectricityRepository;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonTest {

    @Autowired
    private ElectricityRepository electricityRepository;

    @Test
    @Ignore
    void basicTest() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        String path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.json";
        Reader reader = new FileReader(path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        ElectricityInfo elecTest = new ElectricityInfo();

        //elecTest.setDemandCharge(Integer.parseInt((String)jsonObject.get("base_fee")));

        assertEquals(1600, elecTest.getDemandCharge());
    }

    @Test
    @Ignore
    public void jsonToDto() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        String outputPath = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.json";
        Reader reader = new FileReader(outputPath);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        ElectricityInfo elecResult = ElectricityInfo.builder()
            .demandCharge(Integer.parseInt((String) jsonObject.get("base_fee")))
            .energyCharge(Integer.parseInt((String) jsonObject.get("pure_eletric_fee")))
            .environmentCharge(Integer.parseInt((String) jsonObject.get("environment_fee")))
            .fuelAdjustmentRate(Integer.parseInt((String) jsonObject.get("fuel_fee")))
            .elecChargeSum(Integer.parseInt((String) jsonObject.get("eletric_fee")))
            .vat(Integer.parseInt((String) jsonObject.get("VATS_fee")))
            .elecFund(Integer.parseInt((String) jsonObject.get("unknown_fee")))
            .roundDown(Integer.parseInt((String) jsonObject.get("cutoff_fee")))
            .totalbyCurrMonth(Integer.parseInt((String) jsonObject.get("total_month_fee")))
            .tvSubscriptionFee(Integer.parseInt((String) jsonObject.get("TV_fee")))
            .currMonthUsage(Integer.parseInt((String) jsonObject.get("current_month")))
            .preMonthUsage(Integer.parseInt((String) jsonObject.get("previous_month")))
            .lastYearUsage(Integer.parseInt((String) jsonObject.get("last_year")))
            .build();

        electricityRepository.save(elecResult);

        System.out.println(elecResult.getId());
    }

    @Test
    @Ignore
    public void editElec() throws IOException, ParseException {
        ElectricityInfo elec = electricityRepository.findById(3L).get();
        //elec.setRoundDown(12333);
        electricityRepository.save(elec);
    }
}
