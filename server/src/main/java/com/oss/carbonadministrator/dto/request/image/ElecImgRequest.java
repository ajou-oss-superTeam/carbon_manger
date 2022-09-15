package com.oss.carbonadministrator.dto.request.image;

import com.oss.carbonadministrator.domain.Electricity;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ElecImgRequest {

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

    public Electricity toElecEntity(ElecImgRequest requestDto) {
        Electricity elecData = Electricity.builder()
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
}
