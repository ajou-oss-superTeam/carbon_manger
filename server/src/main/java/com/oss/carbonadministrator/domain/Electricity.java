package com.oss.carbonadministrator.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "electricity")
public class Electricity {

    @Id
    @Column
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

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public Electricity() {
        this.totalPrice = this.totalbyCurrMonth + this.tvSubscriptionFee;
    }
}
