package com.example.Library.data_access;

import com.example.Library.model.Book_Rents;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.List;

public interface Book_Rents_DA {
    public Book_Rents saveBookRents(Book_Rents bookRents);

    List<Book_Rents> getAllBookRents();
}
