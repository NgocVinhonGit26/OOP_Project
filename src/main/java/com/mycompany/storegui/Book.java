package com.mycompany.storegui;

import java.util.ArrayList;
import java.util.List;

public class Book extends Media {
    private List<String> authors = new ArrayList<String>();
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void addAuthor(String authorName) {
        if (authors.contains(authorName) == false) {
            authors.add(authorName);
        } else
            return;
    }

    public Book(int id, String title, String category, float funs, float cost, String authors, int quantity,
            String image, String publisher) {
        super(id, title, category, funs, cost, quantity, image);
        addAuthor(authors);
        this.publisher = publisher;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Book) {
            return this.getTitle().compareToIgnoreCase(((Book) o).getTitle());
        }
        return -9999;
    }
}
