package com.neo.designpattern.factorymethod.sample;

import com.neo.designpattern.factorymethod.sample.core.Factory;
import com.neo.designpattern.factorymethod.sample.core.Product;
import com.neo.designpattern.factorymethod.sample.idcard.IDCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product idCard1 = factory.create("cp");
        Product idCard2 = factory.create("hhp");
        Product idCard3 = factory.create("ljf");
        idCard1.use();
        idCard2.use();
        idCard3.use();
    }
}
