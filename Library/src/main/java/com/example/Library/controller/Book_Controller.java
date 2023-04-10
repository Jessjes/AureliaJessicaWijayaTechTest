package com.example.Library.controller;

import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import com.example.Library.service.Book_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class Book_Controller {

    @Autowired
    private Book_Service bookService;

    @PostMapping("/addBook")
    public Books add(@RequestBody Books book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/getAllBooks")
    public List<Books> getAllBooks(){
        return bookService.getAllBooks();
    }
}
