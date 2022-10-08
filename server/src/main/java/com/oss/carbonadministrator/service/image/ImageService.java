package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.dto.request.image.ElecImgRequest;
import com.oss.carbonadministrator.dto.request.image.ImageRequest;
import com.oss.carbonadministrator.exception.ElecInfoNotFoundException;
import com.oss.carbonadministrator.exception.image.ImgUploadFailException;
import com.oss.carbonadministrator.exception.user.HasNoUserException;
import com.oss.carbonadministrator.repository.bill.BillRepository;
import com.oss.carbonadministrator.repository.electricity.ElectricityRepository;
import com.oss.carbonadministrator.repository.user.UserRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
    private final StrategyFactory strategyFactory;


    public static void execPython(String[] command) throws IOException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(commandLine);
    }


    @Transactional
    public void update(Long electricityId, ElecImgRequest updateData) {

        ElectricityInfo savedData = electricityRepository.findById(electricityId)
            .orElseThrow(() -> new ElecInfoNotFoundException("수정할 전기 데이터가 없습니다."));

        savedData.update(updateData);
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
        command[1] = this.basePath().split("working")[0] + strategyFactory.findBillStrategy(BillType.ELECTRICITY).callOcrFilename();
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

    public ElectricityInfo jsonToDto(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String outputPath = this.basePath() + fileName + ".json";

        Reader reader = new FileReader(outputPath);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        ElectricityInfo electricityInfo = ElectricityInfo.builder()
            .demandCharge(Integer.parseInt(
                jsonObject.containsKey("base_fee") == true ? (String) jsonObject.get("base_fee")
                    : "0"))
            .energyCharge(Integer.parseInt(
                jsonObject.containsKey("pure_eletric_fee") == true ? (String) jsonObject.get(
                    "pure_eletric_fee") : "0"))
            .environmentCharge(Integer.parseInt(
                jsonObject.containsKey("environment_fee") == true ? (String) jsonObject.get(
                    "environment_fee") : "0"))
            .fuelAdjustmentRate(Integer.parseInt(
                jsonObject.containsKey("fuel_fee") == true ? (String) jsonObject.get("fuel_fee")
                    : "0"))
            .elecChargeSum(Integer.parseInt(
                jsonObject.containsKey("eletric_fee") == true ? (String) jsonObject.get(
                    "eletric_fee") : "0"))
            .vat(Integer.parseInt(
                jsonObject.containsKey("VATS_fee") == true ? (String) jsonObject.get("VATS_fee")
                    : "0"))
            .elecFund(Integer.parseInt(
                jsonObject.containsKey("unknown_fee") == true ? (String) jsonObject.get(
                    "unknown_fee") : "0"))
            .roundDown(Integer.parseInt(
                jsonObject.containsKey("cutoff_fee") == true ? (String) jsonObject.get("cutoff_fee")
                    : "0"))
            .totalbyCurrMonth(Integer.parseInt(
                jsonObject.containsKey("total_month_fee") == true ? (String) jsonObject.get(
                    "total_month_fee") : "0"))
            .tvSubscriptionFee(Integer.parseInt(
                jsonObject.containsKey("TV_fee") == true ? (String) jsonObject.get("TV_fee") : "0"))
            .currMonthUsage(Integer.parseInt(
                jsonObject.containsKey("current_month") == true ? (String) jsonObject.get(
                    "current_month") : "0"))
            .preMonthUsage(Integer.parseInt(
                jsonObject.containsKey("previous_month") == true ? (String) jsonObject.get(
                    "previous_month") : "0"))
            .lastYearUsage(Integer.parseInt(
                jsonObject.containsKey("last_year") == true ? (String) jsonObject.get("last_year")
                    : "0"))
            .build();
        electricityInfo.calculateTotalPrice(electricityInfo.getTotalbyCurrMonth(),
            electricityInfo.getTvSubscriptionFee());
        deleteFile(fileName + ".json");
        return electricityInfo;
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
    public Bill save(String email, int year, int month, ElectricityInfo recognizedElecData) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new HasNoUserException("해당하는 유저가 존재하지 않습니다.");
        }

        Bill bill = Bill.builder()
            .user(user.get())
            .electricityInfoList(recognizedElecData)
            .year(year)
            .month(month)
            .build();

        return billRepository.save(bill);
    }

    public void makeBase64ToImage(String base64, String fileExtension, UUID uuid) {
        byte[] decode = Base64.decodeBase64(base64);
        FileOutputStream fos;

        try {
            File target = new File("./ML/working/" + "" + uuid + fileExtension);
            target.createNewFile();
            fos = new FileOutputStream(target);
            fos.write(decode);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ElectricityInfo convert(BillType billType, ImageRequest request)
        throws IOException, ParseException {
        UUID uuid = UUID.randomUUID();
        makeBase64ToImage(request.getImage(), ".jpg", uuid);
        imageToJson(uuid.toString());
        return jsonToDto(uuid.toString());
    }
}
