package com.oss.carbonadministrator.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BILL")
public class Bill {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long pid;

    @ManyToOne
    //@JoinColumn(name = "user_pid")
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
