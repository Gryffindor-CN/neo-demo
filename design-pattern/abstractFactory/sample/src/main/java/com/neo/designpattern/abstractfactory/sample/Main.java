package com.neo.designpattern.abstractfactory.sample;

import com.neo.designpattern.abstractfactory.sample.factory.Factory;
import com.neo.designpattern.abstractfactory.sample.factory.Link;
import com.neo.designpattern.abstractfactory.sample.factory.Page;
import com.neo.designpattern.abstractfactory.sample.factory.Tray;

public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory("com.neo.designpattern.abstractfactory.sample.list.ListFactory");

        Link l_baidu = factory.createLink("百度", "http://www.baidu.com");
        Link l_sina = factory.createLink("新浪", "http://www.sina.com");
        Link l_weibo = factory.createLink("新浪微博", "http://www.weibo.com");
        Link l_taobao = factory.createLink("淘宝", "http://www.taobao.com");
        Link l_jd = factory.createLink("京东", "http://www.jd.com");
        Link l_kaola = factory.createLink("网易考拉", "http://www.kaola.com");
        Link l_tmall = factory.createLink("天猫", "http://www.tmall.com");

        Tray mall = factory.createTray("商城");
        Tray traysearch = factory.createTray("搜索引擎");
        Tray traynews = factory.createTray("综合");

        mall.add(l_taobao);
        mall.add(l_jd);
        mall.add(l_tmall);
        mall.add(l_kaola);
        traysearch.add(l_baidu);
        traynews.add(l_sina);
        traynews.add(l_weibo);

        Page page  = factory.createPage("LinkPage", "cpChen");
        page.add(mall);
        page.add(traysearch);
        page.add(traynews);
        page.output();
    }
}
