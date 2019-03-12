package com.neo.designpattern.templatemethod.sample;

public class Main {
    public static void main(String[] args) {
        AbstractDisplay display1 = new CharDisplay('H');

        AbstractDisplay display2 = new StringDisplay("Hello, cp.");

        AbstractDisplay display3 = new StringDisplay("你好，cp。");

        display1.display();

        display2.display();

        display3.display();
    }
}
