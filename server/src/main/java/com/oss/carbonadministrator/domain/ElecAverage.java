package com.oss.carbonadministrator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
