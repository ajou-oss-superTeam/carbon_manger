package com.oss.carbonadministrator.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @ManyToOne
    //@JoinColumn(name = "user_pid")
    private User user;

    @Column
    private Long electric;

    @Column
    private Long gas;

    @Column
    private Long water;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
}
