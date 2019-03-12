package com.neo.designpattern.factorymethod.sample.idcard;

import com.neo.designpattern.factorymethod.sample.core.Factory;
import com.neo.designpattern.factorymethod.sample.core.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {

    private List<String> owners = new ArrayList<>();

    @Override
    protected Product doCreate(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void doRegister(Product product) {
        owners.add(((IDCard)product).getOwner());
    }

    public List<String> getOwners() {
        return owners;
    }
}
