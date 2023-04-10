package com.example.Library.repository;

import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private Book_Repo bookRepo;

    @Test
    public void BookRepository_SaveBook_ReturnedSavedBook(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();

        //Act
        Books savedBook = bookRepo.save(book);

        //Assert
        Assertions.assertNotNull(savedBook);
        Assertions.assertTrue(savedBook.getBookId() > 0);
    }

    @Test
    public void BookRepository_GetAll_ReturnMoreThanOneBook(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        bookRepo.save(book);
        bookRepo.save(book2);

        List<Books> booksList = bookRepo.findAll();
        int size = booksList.size();

        Assertions.assertNotNull(booksList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void BookRepository_FindById_GetBookNotNull(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();

        //Act
        bookRepo.save(book);
        Books bookReturn = bookRepo.findById(book.getBookId()).get();

        //Assert
        Assertions.assertNotNull(bookReturn);
    }

    @Test
    public void BookRepository_UpdateBook_ReturnBookNotNull(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();

        //Act
        bookRepo.save(book);
        Books bookReturn = bookRepo.findById(book.getBookId()).get();
        bookReturn.setName("Jane Doe");

        Books updatedBook = bookRepo.save(bookReturn);

        //Assert
        Assertions.assertNotNull(updatedBook.getName());
    }

    @Test
    public void BookRepository_DeleteBook_ReturnBookEmpty(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();

        //Act
        bookRepo.save(book);
        bookRepo.deleteById(book.getBookId());

        Optional<Books> bookReturn = bookRepo.findById(book.getBookId());;

        //Assert
        Assertions.assertTrue(bookReturn.isEmpty());
    }
}
