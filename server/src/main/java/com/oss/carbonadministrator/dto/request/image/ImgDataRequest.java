package com.oss.carbonadministrator.dto.request.image;

import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.gas.GasInfo;
import com.oss.carbonadministrator.domain.water.WaterInfo;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * TODO 이미지 인식 데이터 정해지면 수도, 가스 데이터 분리
 */

@Getter
@AllArgsConstructor
public class ImgDataRequest {

    @Email
    private String email;

    private int year;

    private int month;

    // 기본 요금
    private int demandCharge;

    // 전력량요금
    private int energyCharge;

    // 기후환경요금
    private int environmentCharge;

    // 연료비조정액
    private int fuelAdjustmentRate;

    // 전기요금계
    private int elecChargeSum;

    // 부가가치세
    private int vat;

    // 전력 기금
    private int elecFund;

    // 월단위절사
    private int roundDown;

    // 당월요금계
    private int totalbyCurrMonth;

    // TV 수신료
    private int tvSubscriptionFee;

    // 당월 사용량 (kWh)
    private int currMonthUsage;

    // 전월 사용량 (kWh)
    private int preMonthUsage;

    // 전년동월 사용량 (kWh)
    private int lastYearUsage;

    public ElectricityInfo toElecEntity(ImgDataRequest requestDto) {
        ElectricityInfo elecData = ElectricityInfo.builder()
            .demandCharge(requestDto.getDemandCharge())
            .energyCharge(requestDto.getEnergyCharge())
            .environmentCharge(requestDto.getEnvironmentCharge())
            .fuelAdjustmentRate(requestDto.getFuelAdjustmentRate())
            .elecChargeSum(requestDto.getElecChargeSum())
            .vat(requestDto.getElecFund())
            .elecFund(requestDto.getElecFund())
            .roundDown(requestDto.getRoundDown())
            .totalbyCurrMonth(requestDto.getTotalbyCurrMonth())
            .tvSubscriptionFee(requestDto.getTvSubscriptionFee())
            .currMonthUsage(requestDto.getCurrMonthUsage())
            .preMonthUsage(requestDto.getPreMonthUsage())
            .lastYearUsage(requestDto.getLastYearUsage())
            .build();
        elecData.calculateTotalPrice(elecData.getTotalbyCurrMonth(),
            elecData.getTvSubscriptionFee());
        return elecData;
    }

    public WaterInfo toWaterEntity(ImgDataRequest requestDto) {
        WaterInfo waterData = WaterInfo.builder()
            .demandCharge(requestDto.getDemandCharge())
            .fuelAdjustmentRate(requestDto.getFuelAdjustmentRate())
            .vat(requestDto.getVat())
            .roundDown(requestDto.getRoundDown())
            .totalbyCurrMonth(requestDto.getTotalbyCurrMonth())
            .currMonthUsage(requestDto.getCurrMonthUsage())
            .preMonthUsage(requestDto.getPreMonthUsage())
            .lastYearUsage(requestDto.getLastYearUsage())
            .build();
        waterData.calculateTotalPrice(waterData.getDemandCharge(), waterData.getVat());
        return waterData;
    }

    public GasInfo toGasEntity(ImgDataRequest requestDto) {
        GasInfo gasData = GasInfo.builder()
            .demandCharge(requestDto.getDemandCharge())
            .vat(requestDto.getVat())
            .roundDown(requestDto.getRoundDown())
            .totalbyCurrMonth(requestDto.getTotalbyCurrMonth())
            .currMonthUsage(requestDto.getCurrMonthUsage())
            .build();
        gasData.calculateTotalPrice(gasData.getDemandCharge(), gasData.getVat());
        return gasData;
    }

}
