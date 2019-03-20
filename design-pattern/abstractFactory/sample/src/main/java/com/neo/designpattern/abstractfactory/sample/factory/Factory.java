package com.neo.designpattern.abstractfactory.sample.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Factory {
    private static final Logger logger = LoggerFactory.getLogger(Factory.class);

    public static Factory getFactory(String classname) {
        Factory factory = null;
        try {
            factory = (Factory) Class.forName(classname).newInstance();
        } catch (ClassNotFoundException e) {
            logger.error(" 没有找到 " + classname + " 类。");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return factory;
    }

    public abstract Link createLink(String caption, String url);

    public abstract Tray createTray(String caption);

    public abstract Page createPage(String title, String author);
}
