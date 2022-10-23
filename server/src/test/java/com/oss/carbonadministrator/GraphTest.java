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

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElecAverage;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.dto.response.ResponseDto;
import com.oss.carbonadministrator.repository.bill.BillRepository;
import com.oss.carbonadministrator.repository.electricity.ElecAverageRepository;
import com.oss.carbonadministrator.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class GraphTest {

    @Autowired
    private ElecAverageRepository elecAverageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Test
    @Transactional(readOnly = true)
    void basicTest() {
        String province = "경기도";
        String city = "123";
        List<ElecAverage> test = elecAverageRepository.findAllByCityAndProvince(city, province);

        for (int sur = 0; sur < test.size(); sur++) {
            System.out.println(
                test.get(sur).getYear() + ", " + test.get(sur).getMonth() + ", " + test.get(sur)
                    .getUseAverage());
        }
    }

    @Test
    @Transactional(readOnly = true)
    void elecGraphtest() {
        Optional<User> target = userRepository.findByEmail("t@gmail.com");
        if (target.isEmpty()) {
            return;
        }
        User testUser = target.get();

        List<Bill> testBill = billRepository.findAllByUser(testUser);

        List<ElecAverage> testElecAver = elecAverageRepository.findAllByCityAndProvince(
            testUser.getCity(), testUser.getProvince());

        ArrayList<String> monthTest = new ArrayList<>();

        ArrayList<ElecAverage> result = new ArrayList<>();

        for (Bill sur : testBill) {
            monthTest.add(sur.getYear() + "/" + sur.getMonth());
        }

        for (ElecAverage sur : testElecAver) {
            if (monthTest.contains(
                sur.getYear() + "/" + sur.getMonth())) {
                result.add(sur);
            }
        }

        System.out.println(monthTest);
        System.out.println(testBill);
        System.out.println(result);
    }

    @Test
    @Transactional(readOnly = true)
    void elecGraphtest2() {
        Optional<User> target = userRepository.findByEmail("t@gmail.com");
        if (target.isEmpty()) {
            return;
        }
        User targetUser = target.get();
        List<Bill> testBill = billRepository.findAllByUser(targetUser);

        List<ElecAverage> testElecAver = elecAverageRepository.findAllByCityAndProvince(
            targetUser.getCity(), targetUser.getProvince());
        ArrayList<String> monthData = new ArrayList<>();
        ArrayList<Integer> billResult = new ArrayList<>();
        ArrayList<Integer> averResult = new ArrayList<>();

        for (Bill sur : testBill) {
            monthData.add(sur.getYear() + "/" + sur.getMonth());
            billResult.add(sur.getElectricityInfoList().getTotalPrice());
        }

        for (ElecAverage sur : testElecAver) {
            if (monthData.contains(
                sur.getYear() + "/" + sur.getMonth())) {
                averResult.add(sur.getChargeAverage());
            }
        }

        int[] billData = billResult.stream().mapToInt(Integer::intValue).toArray();
        int[] averData = averResult.stream().mapToInt(Integer::intValue).toArray();
        DataSets dataSets = new DataSets(billData, averData);

        GraphData graphData = new GraphData(monthData.toArray(new String[monthData.size()]),
            dataSets);

        System.out.println(ResponseDto.success(graphData, "그래프 데이터 전송"));
    }

    @Getter
    @Setter
    private static class GraphData {

        private String[] labels;
        private DataSets datasets;

        private GraphData(String[] labels, DataSets dataSets) {
            this.labels = labels;
            this.datasets = dataSets;
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
