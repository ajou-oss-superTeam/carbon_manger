package com.oss.carbonadministrator;

import static org.junit.Assert.assertEquals;

import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.repository.ElectricityRepository;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        //elecTest.setDemandCharge(Integer.parseInt((String)jsonObject.get("base_fee")));

        assertEquals(1600, elecTest.getDemandCharge());
    }

//    @Test
//    @Ignore
    void jsonToDto() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        String output_path = "C:\\Users\\dnrla\\Documents\\carbon_manger\\ML\\test.json";
        Reader reader = new FileReader(output_path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity elecResult = Electricity.builder()
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

//    @Test
//    @Ignore
    public void editElec() throws IOException, ParseException {
        Electricity elec = electricityRepository.findById(3L).get();

        //elec.setRoundDown(12333);

        electricityRepository.save(elec);
    }
}
