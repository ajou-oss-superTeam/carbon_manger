package com.oss.carbonadministrator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "elec_average")
public class ElecAverage {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private String province;

    @Column
    private String city;

    @Column(name = "use_average")
    private int useAverage;

    @Column(name = "charge_average")
    private int chargeAverage;


}
