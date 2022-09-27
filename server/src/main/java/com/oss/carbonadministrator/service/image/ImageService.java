package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.Bill;
import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.exception.ImgUploadFailException;
import com.oss.carbonadministrator.repository.BillRepository;
import com.oss.carbonadministrator.repository.ElectricityRepository;
import com.oss.carbonadministrator.repository.UserRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final UserRepository userRepository;
    private final ElectricityRepository electricityRepository;
    private final BillRepository billRepository;

    public static void execPython(String[] command) throws IOException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }

    @Transactional
    public void update(Long electricityId, Electricity toElecEntity) {
        Electricity electricity = Electricity.builder()
            .id(electricityId)
            .demandCharge(toElecEntity.getDemandCharge())
            .energyCharge(toElecEntity.getEnergyCharge())
            .environmentCharge(toElecEntity.getEnvironmentCharge())
            .fuelAdjustmentRate(toElecEntity.getFuelAdjustmentRate())
            .elecChargeSum(toElecEntity.getElecChargeSum())
            .vat(toElecEntity.getVat())
            .elecFund(toElecEntity.getElecFund())
            .roundDown(toElecEntity.getRoundDown())
            .tvSubscriptionFee(toElecEntity.getTvSubscriptionFee())
            .totalbyCurrMonth(toElecEntity.getTotalbyCurrMonth())
            .currMonthUsage(toElecEntity.getCurrMonthUsage())
            .preMonthUsage(toElecEntity.getPreMonthUsage())
            .lastYearUsage(toElecEntity.getLastYearUsage())
            .totalPrice(toElecEntity.calculateTotalPrice(toElecEntity.getTotalbyCurrMonth(),
                toElecEntity.getTvSubscriptionFee()))
            .build();

        electricityRepository.save(electricity);
    }

    public String uploadToLocal(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImgUploadFailException("업로드 할 이미지가 없습니다.");
        }

        try {
            String sourceFileName = file.getOriginalFilename();
            String uploadedPath = this.basePath() + sourceFileName;
            File destFile = new File(uploadedPath);
            file.transferTo(destFile);

            return this.fileName(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImgUploadFailException("이미지 업로드 실패하였습니다.");
        }
    }

    public void imageToJson(String fileName) {
        String[] command = new String[6];
        command[0] = "python";
        command[1] = this.basePath().split("working")[0] + "ocr_electronic.py";
        command[2] = "-img_path";
        command[3] = this.basePath() + fileName + ".jpg";
        command[4] = "-output_path";
        command[5] = this.basePath() + fileName + ".json";
        try {
            execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.deleteFile(fileName + ".jpg");
    }

    // TODO tv 수신료 인식 에러 수정 후 추가
    // TODO 사용량 인식되면 데이터 추가
    public Electricity jsonToDto(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String output_path = this.basePath() + fileName + ".json";

        Reader reader = new FileReader(output_path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity electricity = Electricity.builder()
            .demandCharge(Integer.parseInt(jsonObject.containsKey("base_fee") == true ? (String) jsonObject.get("base_fee") : "0"))
            .energyCharge(Integer.parseInt(jsonObject.containsKey("pure_eletric_fee") == true ? (String) jsonObject.get("pure_eletric_fee") : "0"))
            .environmentCharge(Integer.parseInt(jsonObject.containsKey("environment_fee") == true ? (String) jsonObject.get("environment_fee") : "0"))
            .fuelAdjustmentRate(Integer.parseInt(jsonObject.containsKey("fuel_fee") == true ?  (String) jsonObject.get("fuel_fee") : "0"))
            .elecChargeSum(Integer.parseInt(jsonObject.containsKey("eletric_fee") == true ? (String) jsonObject.get("eletric_fee") : "0"))
            .vat(Integer.parseInt(jsonObject.containsKey("VATS_fee") == true ? (String) jsonObject.get("VATS_fee") : "0"))
            .elecFund(Integer.parseInt(jsonObject.containsKey("unknown_fee") == true ? (String) jsonObject.get("unknown_fee") : "0"))
            .roundDown(Integer.parseInt(jsonObject.containsKey("cutoff_fee") == true ? (String) jsonObject.get("cutoff_fee") : "0"))
            .totalbyCurrMonth(Integer.parseInt(jsonObject.containsKey("total_month_fee") == true ? (String) jsonObject.get("total_month_fee") : "0"))
            .tvSubscriptionFee(Integer.parseInt(jsonObject.containsKey("TV_fee") == true ? (String) jsonObject.get("TV_fee") : "0"))
            .currMonthUsage(Integer.parseInt(jsonObject.containsKey("current_month") == true ? (String) jsonObject.get("current_month") : "0"))
            .preMonthUsage(Integer.parseInt(jsonObject.containsKey("previous_month") == true ? (String) jsonObject.get("previous_month") : "0"))
            .lastYearUsage(Integer.parseInt(jsonObject.containsKey("last_year") == true ? (String) jsonObject.get("last_year") : "0"))
            .build();
        deleteFile(fileName + ".json");
        return electricity;
    }

    public void deleteFile(String fileName) {
        String path = this.basePath() + fileName;
        File deleteFile = new File(path);

        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    public String basePath() {
        File base = new File("./ML/working/base");

        String[] result = base.getAbsolutePath().split("base");

        return result[0];
    }

    public String fileName(MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename());
    }

    @Transactional
    public Electricity save(String email, int year, int month, Electricity recognizedElecData) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("찾는 회원이 없습니다.");
        }

        Bill bill;

        if (billRepository.findBillByEmailAndYearAndMonth(user.get().getEmail(), year, month)
            .stream().findAny().isEmpty()) {
            bill = Bill.builder()
                .user(user.get())
                .electricityList(recognizedElecData)
                .year(year)
                .month(month)
                .build();
            billRepository.save(bill);

        } else {
            List<Bill> billList = billRepository.findBillByEmailAndYearAndMonth(
                user.get().getEmail(), year, month);
            bill = billList.get(0);
            bill.setElectricityList(recognizedElecData);
            billRepository.save(bill);
        }

        return bill.getElectricityList();
    }

    public void makeBase64ToImage(String base64, String filename, UUID uuid){
        byte decode[] = Base64.decodeBase64(base64);
        FileOutputStream fos;

        try{
            File target = new File("./ML/working/"+""+uuid+filename);
            target.createNewFile();
            fos = new FileOutputStream(target);
            fos.write(decode);
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
