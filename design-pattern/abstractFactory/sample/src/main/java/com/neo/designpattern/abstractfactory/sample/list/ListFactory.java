package com.neo.designpattern.abstractfactory.sample.list;

import com.neo.designpattern.abstractfactory.sample.factory.Factory;
import com.neo.designpattern.abstractfactory.sample.factory.Link;
import com.neo.designpattern.abstractfactory.sample.factory.Page;
import com.neo.designpattern.abstractfactory.sample.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
