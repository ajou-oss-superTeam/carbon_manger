package com.oss.carbonadministrator.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long pid;

    @ManyToOne
    @JoinColumn(name = "uid")
    @Getter
    @Setter
    private User user;

    @Column
    @Getter
    @Setter
    private Long electric;

    @Column
    @Getter
    @Setter
    private Long gas;

    @Column
    @Getter
    @Setter
    private Long water;

    @Column
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private Date date;
}
