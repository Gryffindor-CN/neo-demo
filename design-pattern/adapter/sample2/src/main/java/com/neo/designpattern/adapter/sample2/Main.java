package com.neo.designpattern.adapter.sample2;

public class Main {

    public static void main(String[] args) {
        Banner banner = new Banner("Hello world.");
        PrintBanner pb = new PrintBanner(banner);

        pb.printWeak();
        pb.printStrong();
    }

}
