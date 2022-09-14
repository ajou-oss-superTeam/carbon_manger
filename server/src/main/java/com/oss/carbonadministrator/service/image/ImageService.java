package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.Bill;
import com.oss.carbonadministrator.domain.Electricity;
import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.exception.ImgUploadFailException;
import com.oss.carbonadministrator.repository.BillRepository;
import com.oss.carbonadministrator.repository.ElectricityRepository;
import com.oss.carbonadministrator.repository.UserRepository;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        command[0] = "python3";
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

    public Electricity jsonToDto(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String output_path = this.basePath() + fileName + ".json";

        Reader reader = new FileReader(output_path);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        Electricity electricity = Electricity.builder()
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
        electricity.calculateTotalPrice(electricity.getTotalbyCurrMonth(), electricity.getTvSubscriptionFee());
        deleteFile(fileName);
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
}
