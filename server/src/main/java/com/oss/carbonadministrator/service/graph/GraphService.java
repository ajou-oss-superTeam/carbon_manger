package com.oss.carbonadministrator.service.graph;

import com.oss.carbonadministrator.domain.Bill;
import com.oss.carbonadministrator.domain.ElecAverage;
import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.repository.BillRepository;
import com.oss.carbonadministrator.repository.ElecAverageRepository;
import com.oss.carbonadministrator.repository.ElectricityRepository;
import com.oss.carbonadministrator.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GraphService {

    @Autowired
    private ElecAverageRepository elecAverageRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectricityRepository electricityRepository;

    @Transactional(readOnly = true)
    public ResponseDto elecFeeGraph(String email) {
        User targetUser = userRepository.findByEmail(email).get();
        List<Bill> targetBill = billRepository.findAllByUser(targetUser);
        List<ElecAverage> targetElecAver = elecAverageRepository.findAllByCityAndProvince(
            targetUser.getCity(), targetUser.getProvince());

        ArrayList<String> monthData = new ArrayList<>();
        ArrayList<Integer> billResult = new ArrayList<>();
        ArrayList<Integer> averResult = new ArrayList<>();

        for (Bill sur : targetBill) {
            monthData.add(sur.getYear() + "/" + sur.getMonth());
            billResult.add(sur.getElectricityList().getTotalPrice());
        }

        for (ElecAverage sur : targetElecAver) {
            if (monthData.contains(
                sur.getYear() + "/" + sur.getMonth())) {
                averResult.add(sur.getChargeAverage());
            }
        }

        return ResponseDto.success(
            new GraphData(monthData.toArray(new String[monthData.size()]),
                new DataSets(billResult.stream().mapToInt(Integer::intValue).toArray(),
                    averResult.stream().mapToInt(Integer::intValue).toArray())), "그래프 데이터 전송");
    }

    @Getter
    @AllArgsConstructor
    public static class GraphData {

        private String[] labels;
        private DataSets datasets;
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
