package com.oss.carbonadministrator;

import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.repository.ElectricityRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@SpringBootTest
public class JsonTest {

    @Autowired
    private ElectricityRepository electricityRepository;


//    @Test
//    @Ignore
    void basicTest() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        String path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.json";
        Reader reader = new FileReader(path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity elecTest = new Electricity();

        elecTest.setDemandCharge(Integer.parseInt((String)jsonObject.get("base_fee")));

        assertEquals(1600, elecTest.getDemandCharge());
    }

//    @Test
//    @Ignore
    void jsonToDto() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        String output_path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.json";
        Reader reader = new FileReader(output_path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity elecResult = new Electricity();

        elecResult.setDemandCharge(Integer.parseInt((String) jsonObject.get("base_fee")));
        elecResult.setEnergyCharge(Integer.parseInt((String) jsonObject.get("pure_eletric_fee")));
        elecResult.setEnvironmentCharge(Integer.parseInt((String) jsonObject.get("environment_fee")));
        elecResult.setFuelAdjustmentRate(Integer.parseInt((String) jsonObject.get("fuel_fee")));
        elecResult.setElecChargeSum(Integer.parseInt((String) jsonObject.get("eletric_fee")));
        elecResult.setVat(Integer.parseInt((String) jsonObject.get("VATS_fee")));
        elecResult.setElecFund(Integer.parseInt((String) jsonObject.get("unknown_fee")));
        elecResult.setRoundDown(Integer.parseInt((String) jsonObject.get("cutoff_fee")));
        elecResult.setTotalbyCurrMonth(Integer.parseInt((String) jsonObject.get("total_month_fee")));

        int totalFee = elecResult.getTotalbyCurrMonth();

        elecResult.setTotalPrice(totalFee);

        electricityRepository.saveAndFlush(elecResult);

        System.out.println(elecResult.getId());
    }

//    @Test
//    @Ignore
    public void editElec() throws IOException, ParseException {
        Electricity elec = (Electricity) electricityRepository.findById(3L).get();

        elec.setRoundDown(12333);

        electricityRepository.saveAndFlush(elec);
    }
}
