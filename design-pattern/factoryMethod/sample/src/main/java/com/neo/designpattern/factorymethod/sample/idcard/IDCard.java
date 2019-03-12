package com.neo.designpattern.factorymethod.sample.idcard;

import com.neo.designpattern.factorymethod.sample.core.Product;

public class IDCard extends Product {

    private String owner;

    /**
     * friendly 构造
     *
     * 此处划重点，Factory Method 模式不希望用户通过 new 关键字创建实例，而是通过 Factory 类进行创建实例。因此，此处无需添加修饰符。
     * @param owner
     */
    IDCard(String owner) {
        System.out.println("制作" + owner + "的 ID 卡。");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println("使用" + owner + "的 ID 卡。");
    }

    public String getOwner() {
        return owner;
    }
}
