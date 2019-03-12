package com.neo.designpattern.factorymethod.sample.core;

public abstract class Factory {

    public Product create(String owner) {
        Product p = doCreate(owner);
        doRegister(p);
        return p;
    }

    protected abstract Product doCreate(String owner);

    protected abstract void doRegister(Product product);
}
