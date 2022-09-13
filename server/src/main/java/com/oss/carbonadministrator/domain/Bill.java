package com.oss.carbonadministrator.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;

/*
 * 고지서 Entity
 * TODO 수도, 가스 서비스 확장
 */
@Getter
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "elec_id")
    private Electricity electricity;

    @Column
    private int year;

    @Column
    private int month;
}
