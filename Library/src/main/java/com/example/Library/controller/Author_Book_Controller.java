package com.example.Library.controller;

import com.example.Library.model.Author_Books;
import com.example.Library.service.Author_Books_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorbooks")
@CrossOrigin
public class Author_Book_Controller {

    @Autowired
    private Author_Books_Service authorBooksService;

    @PostMapping("/addAuthorBooks")
    public Author_Books add(@RequestBody Author_Books authorBooks) {
        return authorBooksService.saveAuthorBooksById(authorBooks);
    }

    @GetMapping("/getAllAuthorBooks")
    public List<Author_Books> getAllAuthorBooks(){
        return authorBooksService.getAllAuthorBooks();
    }
}
