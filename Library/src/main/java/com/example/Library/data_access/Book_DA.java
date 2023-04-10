package com.example.Library.data_access;

import com.example.Library.model.Books;

import java.util.List;

public interface Book_DA {
    public Books saveBook(Books book);

    List<Books> getAllBooks();
}
