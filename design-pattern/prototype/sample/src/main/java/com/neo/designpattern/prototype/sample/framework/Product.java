package com.neo.designpattern.prototype.sample.framework;

public interface Product extends Cloneable {

    public abstract void use(String s);

    public abstract Product createclone();

}
