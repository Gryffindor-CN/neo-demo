package com.neo.designpattern.builder.sample;

/**
 * @Auther: cp.Chen
 * @Date: 2019/3/15 11:47
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
            System.exit(0);
        }

        if (args[0].equals("plain")) {
            TestBuilder builder = new TestBuilder();
            Director director = new Director(builder);
            director.construct();
            String result = builder.getResult();
            System.out.println(result);
        } else if (args[0].equals("html")) {
            HtmlBuilder builder = new HtmlBuilder();
            Director director = new Director(builder);
            director.construct();
            String filename = builder.getResult();
            System.out.println(filename + " 文件编写完成。 ");
        } else {
            usage();
            System.exit(0);
        }
    }

    public static void usage() {
        System.out.println("Usage: java Main plain        编写纯文本文档");
        System.out.println("Usage: java Main html         编写 HTML 文档");
    }
}
