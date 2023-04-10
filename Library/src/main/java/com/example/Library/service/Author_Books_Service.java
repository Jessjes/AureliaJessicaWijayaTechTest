package com.example.Library.service;

import com.example.Library.data_access.Author_Book_DA;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import com.example.Library.repository.Author_Books_Repo;
import com.example.Library.repository.Author_Repo;
import com.example.Library.repository.Book_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Author_Books_Service implements Author_Book_DA {

    @Autowired
    private Author_Books_Repo authorBooksRepo;

    @Autowired
    private Author_Repo authorRepo;

    @Autowired
    private Book_Repo bookRepo;

    public Author_Books_Service(Author_Books_Repo authorBooksRepo, Author_Repo authorRepo, Book_Repo bookRepo){
        this.authorBooksRepo = authorBooksRepo;
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public Author_Books saveAuthorBooksById(Author_Books authorBook){
        int author_id = authorBook.getAuthorBookPk().getAuthorId();
        int book_id = authorBook.getAuthorBookPk().getBookId();

        Authors author = new Authors();
        author.setAuthor_id(author_id);

        Books book = new Books();
        book.setBook_id(book_id);

        Author_Books authorBooks = new Author_Books(author, book);

        return authorBooksRepo.save(authorBooks);
    }

    @Override
    public List<Author_Books> getAllAuthorBooks(){
        return authorBooksRepo.findAll();
    }

}
