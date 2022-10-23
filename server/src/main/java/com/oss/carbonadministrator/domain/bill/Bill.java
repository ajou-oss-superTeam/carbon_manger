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
package com.oss.carbonadministrator.domain.bill;

import com.oss.carbonadministrator.domain.base.BaseTimeEntity;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.gas.GasInfo;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.domain.water.WaterInfo;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 고지서 Entity
 * TODO 수도, 가스 서비스 확장
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill extends BaseTimeEntity {

    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "elec_id")
    private ElectricityInfo electricityInfoList;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "water_id")
    private WaterInfo waterInfoList;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gas_id")
    private GasInfo gasInfoList;


    @Column
    private int year;

    @Column
    private int month;
}
