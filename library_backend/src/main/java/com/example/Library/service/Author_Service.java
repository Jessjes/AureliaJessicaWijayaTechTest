package com.example.Library.service;

import com.example.Library.data_access.Author_DA;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Authors;
import com.example.Library.repository.Author_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Author_Service implements Author_DA {

    @Autowired
    private Author_Repo authorRepo;

    public Author_Service(Author_Repo authorRepo){
        this.authorRepo = authorRepo;
    }

    @Override
    public Authors saveAuthor(Authors author){
        return authorRepo.save(author);
    }

    @Override
    public List<Authors> getAllAuthors(){
        return authorRepo.findAll();
    }

}
