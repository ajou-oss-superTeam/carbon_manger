package com.oss.carbonadministrator.domain.water;

import com.oss.carbonadministrator.domain.base.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "water")
public class WaterInfo extends BaseTimeEntity {

    @Id
    @Column(name = "water_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 요금
    @Column
    private int demandCharge;

    // 연료비조정액
    @Column
    private int fuelAdjustmentRate;

    // 부가가치세
    @Column
    private int vat;

    // 월단위절사
    @Column
    private int roundDown;

    // 당월요금계
    @Column
    private int totalbyCurrMonth;

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

}
