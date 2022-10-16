package com.oss.carbonadministrator.service.graph;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElecAverage;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.exception.user.HasNoUserException;
import com.oss.carbonadministrator.repository.bill.BillRepository;
import com.oss.carbonadministrator.repository.electricity.ElecAverageRepository;
import com.oss.carbonadministrator.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GraphService {

    private final ElecAverageRepository elecAverageRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    private static void extractGovernmentData(
        List<ElecAverage> targetElecAver,
        ArrayList<String> monthData,
        ArrayList<Integer> averResult
    ) {
        for (ElecAverage sur : targetElecAver) {
            if (monthData.contains(
                sur.getYear() + "/" + sur.getMonth())) {
                averResult.add(sur.getChargeAverage());
            }
        }
    }

    private static void extractUserElecPrice(
        List<Bill> targetBill,
        ArrayList<String> monthData,
        ArrayList<Integer> billResult
    ) {
        for (Bill sur : targetBill) {
            monthData.add(sur.getYear() + "/" + sur.getMonth());
            billResult.add(sur.getElectricityInfoList().getTotalPrice());
        }
    }

    private static List<Object> calculatedList(Bill sur) {
        Optional<Double> elecData = sur.getElectricityInfoList().calculateCarbonUsage();
        Optional<Double> gasData = sur.getGasInfoList().calculateCarbonUsage();
        Optional<Double> waterData = sur.getWaterInfoList().calculateCarbonUsage();

        return Arrays.asList(elecData, gasData, waterData);
    }


    @Transactional(readOnly = true)
    public GraphData getElecFeeGraph(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new HasNoUserException("해당하는 유저가 존재하지 않습니다.");
        }
        User targetUser = userRepository.findByEmail(email).get();
        List<Bill> targetBill = billRepository.findAllByUser(targetUser);
        List<ElecAverage> targetElecAver = elecAverageRepository.findAllByCityAndProvince(
            targetUser.getCity(), targetUser.getProvince());

        ArrayList<String> monthData = new ArrayList<>();
        ArrayList<Integer> billResult = new ArrayList<>();
        ArrayList<Integer> averResult = new ArrayList<>();

        extractUserElecPrice(targetBill, monthData, billResult);

        extractGovernmentData(targetElecAver, monthData, averResult);

        return new GraphData(monthData.toArray(new String[monthData.size()]),
            new DataSets(billResult.stream().mapToInt(Integer::intValue).toArray(),
                averResult.stream().mapToInt(Integer::intValue).toArray()));
    }

    public GraphData getAllCarbonGraph(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new HasNoUserException("해당하는 유저가 존재하지 않습니다.");
        }
        User targetUser = userRepository.findByEmail(email).get();
        List<Bill> targetBill = billRepository.findAllByUser(targetUser);

        ArrayList<String> monthData = new ArrayList<>();
        ArrayList<List<Object>> carbonResult = new ArrayList<>();
        String[] legend = {"전기", "가스", "수도"};

        calculateUserCarbonData(targetBill, monthData, carbonResult);

        return new GraphData(monthData.toArray(new String[monthData.size()]), legend, carbonResult);
    }

    private void calculateUserCarbonData(
        List<Bill> targetBill,
        ArrayList<String> monthData,
        ArrayList<List<Object>> billResult
    ) {
        for (Bill sur : targetBill) {
            monthData.add(sur.getYear() + "/" + sur.getMonth());
            billResult.add(calculatedList(sur)); // TODO "" 처리
        }
    }

    @Getter
    public static class GraphData<T> {

        private String[] labels;
        private String[] legend;
        private T datasets;

        public GraphData(String[] labels, T datasets) {
            this.labels = labels;
            this.datasets = datasets;
        }

        public GraphData(String[] labels, String[] legend, T datasets) {
            this.labels = labels;
            this.legend = legend;
            this.datasets = datasets;
        }
    }

    @Getter
    @Setter
    private static class DataSets {

        private int[] userData;
        private int[] averageData;

        private DataSets(int[] userData, int[] averageData) {
            this.userData = userData;
            this.averageData = averageData;
        }
    }
}
