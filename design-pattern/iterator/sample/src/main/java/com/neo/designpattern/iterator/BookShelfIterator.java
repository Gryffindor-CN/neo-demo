package com.neo.designpattern.iterator;

public class BookShelfIterator implements Iterator {
    private BookShelf bookShelf;

    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.length()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        Book book = bookShelf.get(index);
        index++;
        return book;
    }
}
