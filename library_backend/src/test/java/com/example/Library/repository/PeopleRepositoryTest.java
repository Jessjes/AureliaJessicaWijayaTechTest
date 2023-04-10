package com.example.Library.repository;

import com.example.Library.model.Authors;
import com.example.Library.model.Book_Rents;
import com.example.Library.model.Books;
import com.example.Library.model.People;
import jakarta.validation.constraints.AssertTrue;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PeopleRepositoryTest {

    @Autowired
    private People_Repo peopleRepo;

    @Autowired
    private Book_Repo bookRepo;

    @Autowired
    private Book_Rents_Repo bookRentsRepo;

    @Test
    public void PeopleRepository_SavePeople_ReturnSavedPeople(){

        //Arrange
        People person1 = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").build();

        //Act
        peopleRepo.save(person1);
        peopleRepo.save(person2);

        //Assert
        Assertions.assertNotNull(person1);
        Assertions.assertTrue(person1.getPersonId() > 0);

        Assertions.assertNotNull(person2);
        Assertions.assertTrue(person2.getPersonId() > 0);
    }

    @Test
    public void PeopleRepository_GetAllPeople_ReturnMoreThanOne(){

        //Arrange
        People person1 = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").build();

        //Act
        peopleRepo.save(person1);
        peopleRepo.save(person2);

        List<People> peopleList = peopleRepo.findAll();
        int size = peopleList.size();

        //Assert
        Assertions.assertNotNull(peopleList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void PeopleRepository_FindById_ReturnPeopleNotNull(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();

        //Act
        peopleRepo.save(person);
        People peopleReturn = peopleRepo.findById(person.getPersonId()).get();

        //Assert
        Assertions.assertNotNull(peopleReturn);
    }

    @Test
    public void PeopleRepository_UpdatePeople_ReturnPeopleNotNull(){

        //Arrange
        People person1 = People.builder().name("John Doe").country_id(840).build();

        //Act
        peopleRepo.save(person1);

        People savedPerson1 = peopleRepo.findById(person1.getPersonId()).get();

        savedPerson1.setCountry_id(720);
        savedPerson1.setName("Will Smith");

        People updatePerson1 = peopleRepo.save(savedPerson1);

        //Assert
        Assertions.assertNotNull(updatePerson1.getCountry_id());
        Assertions.assertNotNull(updatePerson1.getName());
    }

    @Test
    public void PeopleRepository_DeletePeople_ReturnPeopleEmpty(){

        //Arrange
        People person = People.builder().name("John Doe").country_id(840).build();

        //Act
        peopleRepo.save(person);
        peopleRepo.deleteById(person.getPersonId());

        Optional<People> peopleReturn = peopleRepo.findById(person.getPersonId());

        //Assert
        Assertions.assertTrue(peopleReturn.isEmpty());
    }


    @Test
    public void PeopleRepository_GetDistinctCountryId_ReturnMoreThanOne(){

        //Arrange
        People person1 = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").country_id(720).build();
        People person3 = People.builder().name("Will Smith").country_id(840).build();

        //Act
        peopleRepo.save(person1);
        peopleRepo.save(person2);
        peopleRepo.save(person3);

        List<Long> countryList = peopleRepo.getAllCountryId();
        int size = countryList.size();

        //Assert
        Assertions.assertNotNull(countryList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void PeopleRepository_GetTop3Borrowers_ReturnMoreThanOneBorrower(){

        //Arrange
        Books book = Books.builder().name("Book Name 1").build();
        People person1 = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").country_id(840).build();
        People person3 = People.builder().name("Will Smith").country_id(840).build();
        People person4 = People.builder().name("James Bond").country_id(840).build();

        //Act
        bookRepo.save(book);
        peopleRepo.save(person1);
        peopleRepo.save(person2);
        peopleRepo.save(person3);
        peopleRepo.save(person4);

        Book_Rents bookRents1 = Book_Rents.builder()
                                .book_id(book.getBookId())
                                .people_id(person1.getPersonId()).build();
        Book_Rents bookRents2 = Book_Rents.builder()
                                .book_id(book.getBookId())
                                .people_id(person2.getPersonId()).build();
        Book_Rents bookRents3 = Book_Rents.builder()
                                .book_id(book.getBookId())
                                .people_id(person3.getPersonId()).build();
        Book_Rents bookRents4 = Book_Rents.builder()
                                .book_id(book.getBookId())
                                .people_id(person4.getPersonId()).build();

        bookRentsRepo.save(bookRents1);
        bookRentsRepo.save(bookRents2);
        bookRentsRepo.save(bookRents3);
        bookRentsRepo.save(bookRents4);

        List<Object[]> borrowerList = peopleRepo.getTop3Borrowers(book.getBookId(), person1.getCountry_id());
        int size = borrowerList.size();

        System.out.println(size);

        //Assert
        Assertions.assertNotNull(borrowerList);
        Assertions.assertTrue(size > 0);
    }
}
