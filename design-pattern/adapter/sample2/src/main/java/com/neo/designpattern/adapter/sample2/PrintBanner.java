package com.neo.designpattern.adapter.sample2;

public class PrintBanner extends Print {

    private Banner banner;

    public PrintBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    void printWeak() {
        this.banner.showWithParen();
    }

    @Override
    void printStrong() {
        this.banner.showWithAster();
    }
}
