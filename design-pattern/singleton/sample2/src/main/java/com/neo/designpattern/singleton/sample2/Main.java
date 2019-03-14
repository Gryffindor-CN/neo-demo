package com.neo.designpattern.singleton.sample2;

public class Main {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            Singleton s1 = Singleton.getInstance();
        });

        Thread t2 = new Thread(() -> {
            Singleton s2 = Singleton.getInstance();
        });

    }

}
