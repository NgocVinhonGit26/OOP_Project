package com.mycompany.storegui;

import java.util.ArrayList;
import java.util.List;

public class Book extends Media {
    private List<String> authors = new ArrayList<String>();

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

    public Book(int id, String title, String category, float cost, String authors, int quantity) {
        super(id, title, category, cost, quantity);
        addAuthor(authors);
    }
}
