package com.neo.designpattern.iterator;

import java.util.ArrayList;

public class BigBookShelf implements Aggregate {

    private ArrayList<Book> books;

    private int last;

    public BigBookShelf(int initialize) {
        this.books = new ArrayList(initialize);
        this.last = 0;
    }

    public Book get(int index) {
        return books.get(index);
    }

    public void add(Book book) {
        books.add(book);
    }

    public int length() {
        return books.size();
    }

    @Override
    public Iterator iterator() {
        return new BigBookShelfIterator(this);
    }
}
