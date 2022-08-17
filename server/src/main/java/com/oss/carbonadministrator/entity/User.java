package com.oss.carbonadministrator.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long pid;

    @Column
    @Getter
    @Setter
    private String id;

    @Column
    @Getter
    @Setter
    private String pw;


}
