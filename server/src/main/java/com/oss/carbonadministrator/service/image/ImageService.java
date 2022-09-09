package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.exception.ImgUploadFailException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.oss.carbonadministrator.repository.ElectricityRepository;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    @Autowired
    private ElectricityRepository electricityRepository;

    public String uploadToLocal(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImgUploadFailException("업로드 할 이미지가 없습니다.");
        }

        try {
            String sourceFileName = file.getOriginalFilename();
            String uploadedPath = "C:/Image/uploaded_" + sourceFileName;
            File destFile = new File(uploadedPath);
            file.transferTo(destFile);
            return uploadedPath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImgUploadFailException("이미지 업로드 실패하였습니다.");
        }
    }

    public void imageToJson(String img_path, String output_path){
        String[] command = new String[6];
        command[0] = "python";
        command[1] = "..\\ML\\ocr_electronic.py";
        command[2] = "-img_path";
        command[3] = img_path;
        command[4] = "-output_path";
        command[5] = output_path;
        try {
            execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execPython(String[] command) throws IOException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }

    public Electricity jsonToDto(String output_path) throws IOException, ParseException {
        //추후 tv수신요금도 추가해야함
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(output_path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity elecResult = new Electricity();

        elecResult.setDemandCharge(Integer.parseInt((String)jsonObject.get("base_fee")));
        elecResult.setEnergyCharge(Integer.parseInt((String)jsonObject.get("pure_eletric_fee")));
        elecResult.setEnvironmentCharge(Integer.parseInt((String)jsonObject.get("environment_fee")));
        elecResult.setFuelAdjustmentRate(Integer.parseInt((String)jsonObject.get("fuel_fee")));
        elecResult.setElecChargeSum(Integer.parseInt((String)jsonObject.get("eletric_fee")));
        elecResult.setVat(Integer.parseInt((String)jsonObject.get("VATS_fee")));
        elecResult.setElecFund(Integer.parseInt((String)jsonObject.get("unknown_fee")));
        elecResult.setRoundDown(Integer.parseInt((String)jsonObject.get("cutoff_fee")));
        elecResult.setTotalbyCurrMonth(Integer.parseInt((String)jsonObject.get("total_month_fee")));

        //int totalfee = elecResult.getTotalbyCurrMonth() + elecResult.getTvSubscriptionFee();
        int totalFee = elecResult.getTotalbyCurrMonth();
        elecResult.setTotalPrice(totalFee);

        electricityRepository.saveAndFlush(elecResult);

        return elecResult;
    }

    public Electricity editElec(Electricity changeElec, Electricity elec){
        elec.setDemandCharge(changeElec.getDemandCharge());
        elec.setEnergyCharge(changeElec.getEnergyCharge());
        elec.setEnvironmentCharge(changeElec.getEnvironmentCharge());
        elec.setFuelAdjustmentRate(changeElec.getFuelAdjustmentRate());
        elec.setElecChargeSum(changeElec.getElecChargeSum());
        elec.setVat(changeElec.getVat());
        elec.setElecFund(changeElec.getElecFund());
        elec.setRoundDown(changeElec.getRoundDown());
        elec.setTotalbyCurrMonth(changeElec.getTotalbyCurrMonth());

        electricityRepository.saveAndFlush(elec);

        return elec;
    }
}
