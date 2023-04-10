package com.example.Library.repository;

import com.example.Library.model.*;
import com.example.Library.service.Author_Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class BookRentsRepositoryTest {

    @Autowired
    private Book_Rents_Repo bookRentsRepo;

    @Autowired
    private Book_Repo bookRepo;

    @Autowired
    private Author_Repo authors;

    @Autowired
    private Author_Books_Repo authorBooksRepo;

    @Autowired
    private People_Repo peopleRepo;

    @Autowired
    private Author_Repo authorRepo;

    @Test
    public void BookRentsRepository_SaveBookRents_ReturnedSavedBookRents(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();

        bookRepo.save(book);
        peopleRepo.save(person);

        Book_Rents bookRents = Book_Rents.builder()
                               .book_id(book.getBookId())
                               .people_id(person.getPersonId()).build();

        //Act
        Book_Rents savedBookRents = bookRentsRepo.save(bookRents);

        //Assert
        Assertions.assertNotNull(savedBookRents);
        Assertions.assertTrue(savedBookRents.getBook_id() > 0);
        Assertions.assertTrue(savedBookRents.getPerson_id() > 0);
    }

    @Test
    public void BookRentsRepository_GetAll_ReturnMoreThanOneBookRents(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        bookRepo.save(book);
        bookRepo.save(book2);
        peopleRepo.save(person);
        peopleRepo.save(person2);

        Book_Rents bookRents1 = Book_Rents.builder()
                               .book_id(book.getBookId())
                               .people_id(person.getPersonId()).build();

        Book_Rents bookRents2 = Book_Rents.builder()
                                .book_id(book2.getBookId())
                                .people_id(person2.getPersonId()).build();

        bookRentsRepo.save(bookRents1);
        bookRentsRepo.save(bookRents2);

        List<Book_Rents> bookRentList = bookRentsRepo.findAll();
        int size = bookRentList.size();

        Assertions.assertNotNull(bookRentList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void BookRentsRepository_FindById_GetBookRentsNotNull(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();

        bookRepo.save(book);
        peopleRepo.save(person);

        Book_Rents bookRents = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();

        //Act
        bookRentsRepo.save(bookRents);
        Book_Rents returnBookRents = bookRentsRepo.findById(bookRents.getID()).get();

        //Assert
        Assertions.assertNotNull(returnBookRents);
    }

    @Test
    public void BookRentsRepository_UpdateBookRents_ReturnBookRentsNotNull(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();

        bookRepo.save(book);
        peopleRepo.save(person);

        Book_Rents bookRents = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();

        //Act
        bookRentsRepo.save(bookRents);
        Book_Rents returnBookRents = bookRentsRepo.findById(bookRents.getID()).get();
        returnBookRents.setBook_id(3);
        returnBookRents.setPerson_id(3);

        Book_Rents updatedBookRents = bookRentsRepo.save(returnBookRents);

        //Assert
        Assertions.assertNotNull(updatedBookRents.getBook_id());
        Assertions.assertNotNull(updatedBookRents.getPerson_id());
    }

    @Test
    public void BookRentsRepository_DeleteBookRents_ReturnBookRentsEmpty(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        bookRepo.save(book);
        bookRepo.save(book2);
        peopleRepo.save(person);
        peopleRepo.save(person2);

        Book_Rents bookRents1 = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();

        Book_Rents bookRents2 = Book_Rents.builder()
                .book_id(book2.getBookId())
                .people_id(person2.getPersonId()).build();

        bookRentsRepo.save(bookRents1);
        bookRentsRepo.save(bookRents2);
        bookRentsRepo.deleteById((int)bookRents1.getBook_id());
        bookRentsRepo.deleteById((int)bookRents2.getPerson_id());

        Optional<Book_Rents> bookRentsReturn = bookRentsRepo.findById((int)bookRents1.getBook_id());
        Optional<Book_Rents> bookRentsReturn2 = bookRentsRepo.findById((int)bookRents2.getPerson_id());

        //Assert
        Assertions.assertTrue(bookRentsReturn.isEmpty());
        Assertions.assertTrue(bookRentsReturn2.isEmpty());
    }

    @Test
    public void BookRentsRepository_GetRentedBookCount_ReturnBookRentsNotNull(){
        People person = People.builder().name("John Doe").country_id(840).build();
        Books book = Books.builder().name("Book Name 1").build();

        bookRepo.save(book);
        peopleRepo.save(person);

        Book_Rents bookRents = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();

        bookRentsRepo.save(bookRents);

        List<Object[]> bookCount = bookRentsRepo.getRentedBookCount();

        Assertions.assertNotNull(bookCount);
    }

    @Test
    public void BookRentsRepository_GetTop3BooksCountry_ReturnNotEmpty(){

        int mockCountryId = 840;

        People person = People.builder().name("John Doe").country_id(840L).build();
        People person2 = People.builder().name("Jane Doe").country_id(840L).build();
        People person3 = People.builder().name("Will Smith").country_id(840L).build();

        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();
        Books book3 = Books.builder().name("Book Name 3").build();

        Authors author = Authors.builder().name("John Doe").build();
        Authors author2 = Authors.builder().name("Jane Doe").build();
        Authors author3 = Authors.builder().name("James Bond").build();

        bookRepo.save(book);
        bookRepo.save(book2);
        bookRepo.save(book3);

        peopleRepo.save(person);
        peopleRepo.save(person2);
        peopleRepo.save(person3);

        authorRepo.save(author);
        authorRepo.save(author2);
        authorRepo.save(author3);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                .book_id(book.getBookId())
                .author_id(author.getAuthorId()).build();

        Author_Book_PK authorBooksPK2 = Author_Book_PK.builder()
                .book_id(book2.getBookId())
                .author_id(author2.getAuthorId()).build();

        Author_Book_PK authorBooksPK3 = Author_Book_PK.builder()
                .book_id(book3.getBookId())
                .author_id(author3.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();
        Author_Books authorBooks2 = Author_Books.builder().authorBookPk(authorBooksPK2).build();
        Author_Books authorBooks3 = Author_Books.builder().authorBookPk(authorBooksPK3).build();

        Book_Rents bookRents = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();
        Book_Rents bookRents2 = Book_Rents.builder()
                .book_id(book2.getBookId())
                .people_id(person2.getPersonId()).build();
        Book_Rents bookRents3 = Book_Rents.builder()
                .book_id(book3.getBookId())
                .people_id(person3.getPersonId()).build();
        Book_Rents bookRents4 = Book_Rents.builder()
                .book_id(book.getBookId())
                .people_id(person.getPersonId()).build();

        bookRentsRepo.save(bookRents);
        bookRentsRepo.save(bookRents2);
        bookRentsRepo.save(bookRents3);
        bookRentsRepo.save(bookRents4);

        authorBooksRepo.save(authorBooks);
        authorBooksRepo.save(authorBooks2);
        authorBooksRepo.save(authorBooks3);

        List<Object[]> bookList = bookRentsRepo.getTop3BooksCountry(mockCountryId);
        int size = bookList.size();

        Assertions.assertNotNull(bookList);
        Assertions.assertTrue(size > 0);
    }

}
