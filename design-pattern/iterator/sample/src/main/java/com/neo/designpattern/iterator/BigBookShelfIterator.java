package com.neo.designpattern.iterator;

public class BigBookShelfIterator implements Iterator {

    private BigBookShelf bigBookShelf;

    private int index;

    public BigBookShelfIterator(BigBookShelf bigBookShelf) {
        this.bigBookShelf = bigBookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bigBookShelf.length()) {
            return true;
        }
        return false;
    }

    @Override
    public Object next() {
        Book book = bigBookShelf.get(index);
        index++;
        return book;
    }
}
