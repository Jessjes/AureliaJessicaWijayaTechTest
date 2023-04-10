package com.example.Library.service;

import com.example.Library.data_access.Book_DA;
import com.example.Library.model.Books;
import com.example.Library.repository.Book_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Book_Service implements Book_DA {

    @Autowired
    private Book_Repo bookRepo;

    public Book_Service(Book_Repo bookRepo){
        this.bookRepo = bookRepo;
    }

    @Override
    public Books saveBook(Books book) {
        return bookRepo.save(book);
    }

    @Override
    public List<Books> getAllBooks(){
        return bookRepo.findAll();
    }
}
