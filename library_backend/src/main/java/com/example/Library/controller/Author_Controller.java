package com.example.Library.controller;

import com.example.Library.model.Authors;
import com.example.Library.service.Author_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@CrossOrigin
public class Author_Controller {

    @Autowired
    private Author_Service authorService;

    @PostMapping("/addAuthor")
    public Authors add(@RequestBody Authors author){
        return authorService.saveAuthor(author);
    }

    @GetMapping("/getAllAuthor")
    public List<Authors> getAllAuthors(){
        return authorService.getAllAuthors();
    }
}
