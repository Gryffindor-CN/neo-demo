package com.neo.designpattern.singleton.sample2;

public class Singleton {

    // 初始化为 null 的单例，注意 volatile 关键字修饰
    // JVM当发现代码执行顺序变化但结果不变时可能会改变执行顺序来提升自身性能，因此使用 volatile 修饰可以确保 singleton = new Singleton();
    private static volatile Singleton singleton;

    private Singleton() {}

    public static Singleton getInstance() {
        // 如果当前实例为 null 则创建对象
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
