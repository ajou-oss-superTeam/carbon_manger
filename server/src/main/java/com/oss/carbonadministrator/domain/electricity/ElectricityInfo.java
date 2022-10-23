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
package com.oss.carbonadministrator.domain.electricity;

import com.oss.carbonadministrator.domain.base.BaseTimeEntity;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "electricity")
public class ElectricityInfo extends BaseTimeEntity {

    @Id
    @Column(name = "elec_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 요금
    @Column
    private int demandCharge;

    // 전력량요금
    @Column
    private int energyCharge;

    // 기후환경요금
    @Column
    private int environmentCharge;

    // 연료비조정액
    @Column
    private int fuelAdjustmentRate;

    // 전기요금계
    @Column
    private int elecChargeSum;

    // 부가가치세
    @Column
    private int vat;

    // 전력 기금
    @Column
    private int elecFund;

    // 월단위절사
    @Column
    private int roundDown;

    // 당월요금계
    @Column
    private int totalbyCurrMonth;

    // TV 수신료
    @Column
    private int tvSubscriptionFee;

    @Column
    private int totalPrice;

    // 당월 사용량 (kWh)
    @Column
    private int currMonthUsage;

    // 전월 사용량 (kWh)
    @Column
    private int preMonthUsage;

    // 전년동월 사용량 (kWh)
    @Column
    private int lastYearUsage;

    public int calculateTotalPrice(int totalbyCurrMonth, int tvSubscriptionFee) {
        totalPrice = totalbyCurrMonth + tvSubscriptionFee;
        return totalPrice;
    }

    public int calculateCarbonUsage() {
        return (int) (this.totalbyCurrMonth * 0.4666);
    }

    public void update(ImgDataRequest updateData) {
        this.demandCharge = updateData.getDemandCharge();
        this.energyCharge = updateData.getEnergyCharge();
        this.environmentCharge = updateData.getEnvironmentCharge();
        this.fuelAdjustmentRate = updateData.getFuelAdjustmentRate();
        this.elecChargeSum = updateData.getElecChargeSum();
        this.vat = updateData.getVat();
        this.elecFund = updateData.getElecFund();
        this.roundDown = updateData.getRoundDown();
        this.totalbyCurrMonth = updateData.getTotalbyCurrMonth();
        this.tvSubscriptionFee = updateData.getTvSubscriptionFee();
        this.totalPrice = calculateTotalPrice(this.totalbyCurrMonth, this.tvSubscriptionFee);
        this.currMonthUsage = updateData.getCurrMonthUsage();
        this.preMonthUsage = updateData.getPreMonthUsage();
        this.lastYearUsage = updateData.getLastYearUsage();
    }
}
