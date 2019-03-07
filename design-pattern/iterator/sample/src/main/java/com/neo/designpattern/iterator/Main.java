package com.neo.designpattern.iterator;

public class Main {

    public static void main(String[] args) {
        Book book1 = new Book("一千零一夜");
        Book book2 = new Book("哈利波特");
        Book book3 = new Book("神话");

        BookShelf bookshelf = new BookShelf(3);
        bookshelf.add(book1);
        bookshelf.add(book2);
        bookshelf.add(book3);

        System.out.println(String.format("书架上有 %s 本书", bookshelf.length()));

        Iterator iterator =  bookshelf.iterator();
        while (iterator.hasNext()) {
            Book b = (Book) iterator.next();
            System.out.println(b.getName());
        }
    }
}
