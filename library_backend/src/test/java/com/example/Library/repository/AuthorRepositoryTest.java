package com.example.Library.repository;

import com.example.Library.model.Authors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {

    @Autowired
    private Author_Repo authorRepo;

    @Test
    public void AuthorRepository_SaveAuthor_ReturnedSavedAuthor(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();

        //Act
        Authors savedAuthor = authorRepo.save(author);

        //Assert
        Assertions.assertNotNull(savedAuthor);
        Assertions.assertTrue(savedAuthor.getAuthorId() > 0);
    }

    @Test
    public void AuthorRepository_GetAll_ReturnMoreThanOneAuthor(){

        //Arrange
        Authors author1 = Authors.builder().name("John Doe").build();
        Authors author2 = Authors.builder().name("Jane Doe").build();

        authorRepo.save(author1);
        authorRepo.save(author2);

        List<Authors>authorsList = authorRepo.findAll();
        int size = authorsList.size();

        Assertions.assertNotNull(authorsList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void AuthorRepository_FindById_GetAuthorNotNull(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();

        //Act
        authorRepo.save(author);
        Authors authorReturn = authorRepo.findById(author.getAuthorId()).get();

        //Assert
        Assertions.assertNotNull(authorReturn);
    }

    @Test
    public void AuthorRepository_UpdateAuthor_ReturnAuthorNotNull(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();

        //Act
        authorRepo.save(author);
        Authors authorReturn = authorRepo.findById(author.getAuthorId()).get();
        authorReturn.setName("Jane Doe");

        Authors updatedAuthor = authorRepo.save(authorReturn);

        //Assert
        Assertions.assertNotNull(updatedAuthor.getName());
    }

    @Test
    public void AuthorRepository_DeleteAuthor_ReturnAuthorEmpty(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();

        //Act
        authorRepo.save(author);
        authorRepo.deleteById(author.getAuthorId());

        Optional<Authors> authorReturn = authorRepo.findById(author.getAuthorId());;

        //Assert
        Assertions.assertTrue(authorReturn.isEmpty());
    }
}
