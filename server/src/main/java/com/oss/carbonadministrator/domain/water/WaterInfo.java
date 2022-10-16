package com.oss.carbonadministrator.domain.water;

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
 * TODO 인식 데이터, 총 요금 변경
 */
@Getter
@Builder
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

    public int calculateTotalPrice(int demandCharge, int vat) {
        totalPrice = demandCharge + vat;
        return totalPrice;
    }

    public int calculateCarbonUsage() {
        return (int) (this.totalbyCurrMonth * 0.332);
    }

    public void update(ImgDataRequest updateData) {
        this.demandCharge = updateData.getDemandCharge();
        this.fuelAdjustmentRate = updateData.getFuelAdjustmentRate();
        this.vat = updateData.getVat();
        this.roundDown = updateData.getRoundDown();
        this.totalbyCurrMonth = updateData.getTotalbyCurrMonth();
        this.totalPrice = calculateTotalPrice(this.demandCharge, this.vat);
        this.currMonthUsage = updateData.getCurrMonthUsage();
        this.preMonthUsage = updateData.getPreMonthUsage();
        this.lastYearUsage = updateData.getLastYearUsage();
    }
}
