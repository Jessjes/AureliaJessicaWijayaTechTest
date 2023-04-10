package com.example.Library.service;

import com.example.Library.data_access.Book_Rents_DA;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Book_Rents;
import com.example.Library.repository.Author_Books_Repo;
import com.example.Library.repository.Book_Rents_Repo;
import com.example.Library.repository.Book_Repo;
import com.example.Library.repository.People_Repo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;

@Service
public class Book_Rents_Service implements Book_Rents_DA {

    @Autowired
    private Book_Rents_Repo bookRentsRepo;

    @Autowired
    private Book_Repo bookRepo;

    @Autowired
    private People_Repo peopleRepo;

    @Autowired
    private Author_Books_Repo authorBooksRepo;

    public Book_Rents_Service(Book_Rents_Repo bookRentsRepo, Book_Repo bookRepo, People_Repo peopleRepo, Author_Books_Repo authorBooksRepo){
        this.bookRentsRepo = bookRentsRepo;
        this.bookRepo = bookRepo;
        this.peopleRepo = peopleRepo;
        this.authorBooksRepo = authorBooksRepo;
    }

    @Override
    public Book_Rents saveBookRents(Book_Rents bookRents){
        int people_id = (int)bookRents.getPerson_id();
        int book_id = (int)bookRents.getBook_id();

        return bookRentsRepo.save(bookRents);
    }

    @Override
    public List<Book_Rents> getAllBookRents(){
        return bookRentsRepo.findAll();
    }

    public List<Map<String, Object>> getRentedBooksCount(){
        List<Object[]> countList = bookRentsRepo.getRentedBookCount();
        List<Map<String, Object>> result = new ArrayList<>();

        for(Object[] count : countList){
            Map<String, Object> map = new HashMap<>();
            map.put("itemName", count[0]);
            map.put("purchaseCount", count[1]);
            result.add(map);
        }

        return result;
    }

    public List<Map<String, Object>> getTop3RentedBookGlobal() {
        List<Map<String, Object>> allBook = getRentedBooksCount();
        List<Map<String, Object>> result = allBook.subList(0, 3);

        return result;
    }

    public List<Map<String, Object>> getTop3RentedBookCountry(long id){
        List<Object[]> bookList = (bookRentsRepo.getTop3BooksCountry(id)).subList(0, 3);
        List<Map<String, Object>> result = new ArrayList<>();

        for(Object[] book: bookList)
        {
            int book_id = (int)book[0];
            Map<String, Object> map = new HashMap<>();
            List<String> borrower = new ArrayList<>();

            map.put("name", book[1]);
            map.put("author", book[2]);

            List<Object[]> borrowerName = peopleRepo.getTop3Borrowers(book_id, id);

            for(Object[] person : borrowerName){
                borrower.add(person[0].toString());
            }

            map.put("borrower", borrower);

            result.add(map);
        }

        return result;
    }

    public String testsaja(String test){
        String test1 = test;
        return test1;
    }
}
