package com.neo.designpattern.builder.sample;

/**
 * @Auther: cp.Chen
 * @Date: 2019/3/15 11:37
 * @Description:
 */
public class TestBuilder implements Builer {

    private StringBuffer buffer = new StringBuffer();

    @Override
    public void makeTitle(String title) {
        buffer.append("====================================\n");
        buffer.append("『" + title + "』\n");
    }

    @Override
    public void makeString(String str) {
        buffer.append("■" + str + "\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            buffer.append("   · " + items[i] + "\n");
        }
    }

    @Override
    public void close() {
        buffer.append("====================================\n");
    }

    public String getResult() {
        return buffer.toString();
    }
}
