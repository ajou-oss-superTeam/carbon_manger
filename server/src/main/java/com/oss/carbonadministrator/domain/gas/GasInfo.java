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
package com.oss.carbonadministrator.domain.gas;

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

/*
 * TODO 인식 데이터 변경
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gas")
public class GasInfo extends BaseTimeEntity {

    @Id
    @Column(name = "gas_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int demandCharge;

    @Column
    private int vat;

    @Column
    private int roundDown;

    @Column
    private int totalbyCurrMonth;

    @Column
    private int totalPrice;

    @Column
    private int accumulatedMonthUsage;

    @Column
    private int previousMonthUsage;

    @Column
    private int checkedUsage;

    @Column
    private double currentMonthUsage;

    @Column
    private double unitEnergy;

    @Column
    private double usedEnergy;

    public int calculateTotalPrice(int totalbyCurrMonth, int vat, int roundDown) {
        totalPrice = totalbyCurrMonth + vat - roundDown;
        return totalPrice;
    }

    public int calculateCarbonUsage() {
        return (int) (this.totalbyCurrMonth * 2.22);
    }

    public void update(ImgDataRequest updateData) {
        this.demandCharge = updateData.getDemandCharge();
        this.vat = updateData.getVat();
        this.roundDown = updateData.getRoundDown();
        this.totalbyCurrMonth = updateData.getTotalbyCurrMonth();
        this.totalPrice = calculateTotalPrice(this.totalbyCurrMonth, this.vat, this.roundDown);
        this.accumulatedMonthUsage = updateData.getAccumulatedMonthUsage();
        this.previousMonthUsage = updateData.getPreviousMonthUsage();
        this.checkedUsage = updateData.getCheckedUsage();
        this.currentMonthUsage = updateData.getCurrentMonthUsage();
        this.unitEnergy = updateData.getUnitEnergy();
        this.usedEnergy = updateData.getUsedEnergy();

    }
}
