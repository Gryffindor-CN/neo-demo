package com.neo.designpattern.abstractfactory.sample.list;

import com.neo.designpattern.abstractfactory.sample.factory.Link;

public class ListLink extends Link {
    public ListLink(String caption, String url) {
        super(caption, url);
    }

    @Override
    public String makeHtml() {
        return "  <li><a href=\"" + url +"\" target=\"_blank\">" + caption + "</a></li>\n";
    }
}
