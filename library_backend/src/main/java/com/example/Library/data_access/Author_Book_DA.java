package com.example.Library.data_access;

import com.example.Library.model.Author_Book_PK;
import com.example.Library.model.Author_Books;

import java.util.List;

public interface Author_Book_DA {
    public Author_Books saveAuthorBooksById(Author_Books authorBook);

    List<Author_Books> getAllAuthorBooks();
}
