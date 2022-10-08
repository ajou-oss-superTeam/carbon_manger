package com.oss.carbonadministrator.dto.request.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WaterImgrequest {

    private int demandCharge;
    private int fuelAdjustmentRate;
    private int vat;
    private int roundDown;
    private int totalbyCurrMonth;
    private int totalPrice;
    private int currMonthUsage;
    private int preMonthUsage;
    private int lastYearUsage;
}
