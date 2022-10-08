package com.oss.carbonadministrator.domain.gas;

import com.oss.carbonadministrator.domain.base.BaseTimeEntity;
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
    private int currMonthUsage;

    public int calculateTotalPrice(int demandCharge, int vat) {
        totalPrice = demandCharge + vat;
        return totalPrice;
    }
}
