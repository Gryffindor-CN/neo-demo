package com.neo.designpattern.singleton.sample1;

public class Main {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getSingleton();
        Singleton s2 = Singleton.getSingleton();

        if (s1 == s2) {
            System.out.println("s1 与 s2 是相同实例。");
        } else {
            System.out.println("s1 与 s2 是不同实例。");
        }
    }
}
