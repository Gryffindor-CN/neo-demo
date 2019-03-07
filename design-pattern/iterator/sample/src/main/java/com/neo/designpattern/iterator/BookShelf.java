package com.neo.designpattern.iterator;

public class BookShelf implements Aggregate {
    private Book[] books;

    private int last;

    public BookShelf(int maxsize) {
        this.books = new Book[maxsize];
        this.last = 0;
    }

    public Book get(int index) {
        return books[index];
    }

    public void add(Book book) {
        this.books[last] = book;
        last++;
    }

    public int length() {
        return books.length;
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
