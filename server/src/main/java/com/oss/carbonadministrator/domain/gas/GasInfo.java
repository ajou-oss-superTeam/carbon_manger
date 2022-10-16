package com.oss.carbonadministrator.domain.gas;

import com.oss.carbonadministrator.domain.base.BaseTimeEntity;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import java.util.Optional;
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

    public Optional<Double> calculateCarbonUsage() {
        if (0 == this.totalbyCurrMonth) {
            return Optional.of(0.0);
        }
        return Optional.of(this.totalbyCurrMonth * 2.22);
    }

    public void update(ImgDataRequest updateData) {
        this.demandCharge = updateData.getDemandCharge();
        this.vat = updateData.getVat();
        this.roundDown = updateData.getRoundDown();
        this.totalbyCurrMonth = updateData.getTotalbyCurrMonth();
        this.totalPrice = calculateTotalPrice(this.demandCharge, this.vat);
        this.currMonthUsage = updateData.getCurrMonthUsage();
    }
}
