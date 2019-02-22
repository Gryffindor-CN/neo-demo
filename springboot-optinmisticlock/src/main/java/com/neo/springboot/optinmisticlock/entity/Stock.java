package com.neo.springboot.optinmisticlock.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 11:45
 * @Description:
 */
@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GenericGenerator(name = "numbericGenerator", strategy="identity")
    @GeneratedValue(generator = "numbericGenerator")
    private String id;

    @Version
    private int version;


}
