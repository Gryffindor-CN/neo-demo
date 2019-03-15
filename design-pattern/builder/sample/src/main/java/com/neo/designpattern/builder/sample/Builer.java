package com.neo.designpattern.builder.sample;

/**
 * @Auther: cp.Chen
 * @Date: 2019/3/15 11:33
 * @Description:
 */
public interface Builer {

    void makeTitle(String title);

    void makeString(String str);

    void makeItems(String[] items);

    void close();
}
