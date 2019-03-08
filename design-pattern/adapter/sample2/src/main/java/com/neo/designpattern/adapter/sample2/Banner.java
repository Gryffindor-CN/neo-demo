package com.neo.designpattern.adapter.sample2;

public class Banner {

    private String str;

    public Banner(String str) {
        this.str = str;
    }

    protected void showWithParen() {
        System.out.println("(" + str + ")");
    }

    protected void showWithAster() {
        System.out.println("*" + str + "*");
    }
}
