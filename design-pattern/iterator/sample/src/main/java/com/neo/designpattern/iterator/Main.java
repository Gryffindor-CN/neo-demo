package com.neo.designpattern.iterator;

public class Main {

    public static void main(String[] args) {
        Book book1 = new Book("红楼梦");
        Book book2 = new Book("西游记");
        Book book3 = new Book("水浒传");
        Book book4 = new Book("西厢记");

        BookShelf bookShelf = new BookShelf(4);
        bookShelf.add(book1);
        bookShelf.add(book2);
        bookShelf.add(book3);
        bookShelf.add(book4);

        System.out.println(String.format("1号书架上共有 %s 本书。", bookShelf.length()));

        BookShelfIterator iterator = (BookShelfIterator) bookShelf.iterator();
        while (iterator.hasNext()) {
            Book book = (Book) iterator.next();
            System.out.println(book.getName());
        }

        System.out.println("========================================= 分割线 ==========================================");

        Book book5 = new Book("红楼梦");
        Book book6 = new Book("西游记");
        Book book7 = new Book("水浒传");
        Book book8= new Book("西厢记");
        Book book9 = new Book("哈利波特");
        Book book10 = new Book("倚天屠龙记");
        Book book11 = new Book("花木兰");

        BigBookShelf bigBookShelf = new BigBookShelf(4);
        bigBookShelf.add(book5);
        bigBookShelf.add(book6);
        bigBookShelf.add(book7);
        bigBookShelf.add(book8);
        bigBookShelf.add(book9);
        bigBookShelf.add(book10);
        bigBookShelf.add(book11);

        System.out.println(String.format("2号书架上共有 %s 本书。", bigBookShelf.length()));

        BigBookShelfIterator it = (BigBookShelfIterator) bigBookShelf.iterator();
        while (it.hasNext()) {
            Book b = (Book) it.next();
            System.out.println(b.getName());
        }
    }
}
