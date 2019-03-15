package com.neo.designpattern.builder.sample;

/**
 * @Auther: cp.Chen
 * @Date: 2019/3/15 11:34
 * @Description:
 */
public class Director {
    private Builer builer;

    public Director(Builer builer) {
        this.builer = builer;
    }

    public void construct() {
        builer.makeTitle("Greeting");
        builer.makeString(" 从早上至下午 ");
        builer.makeItems(new String[] {
                " 早上好。 ",
                " 下午好。 "
        });
        builer.makeString(" 晚上 ");
        builer.makeItems(new String[] {
                " 晚上好。 ",
                " 晚安。 ",
                " 再见。"
        });
        builer.close();
    }
}
