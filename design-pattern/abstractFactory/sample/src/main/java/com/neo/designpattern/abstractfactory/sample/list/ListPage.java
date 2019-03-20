package com.neo.designpattern.abstractfactory.sample.list;

import com.neo.designpattern.abstractfactory.sample.factory.Item;
import com.neo.designpattern.abstractfactory.sample.factory.Page;

import java.util.Iterator;

public class ListPage extends Page {

    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE html>\n");
        buffer.append("<html>\n");
        buffer.append("<head>\n");
        buffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
        buffer.append("<title>LinkPage</title>\n");
        buffer.append("</head>\n");
        buffer.append("<body>\n");
        buffer.append("<h1>LinkPage</h1>\n");
        buffer.append("<ul>\n");

        Iterator it = content.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            buffer.append(item.makeHtml());
        }

        buffer.append("</ul>\n");
        buffer.append("<hr><address>" + author + "</address></hr>\n");
        buffer.append("</body></html>\n");

        return buffer.toString();
    }
}
