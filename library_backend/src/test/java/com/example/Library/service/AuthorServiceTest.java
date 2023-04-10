package com.example.Library.service;

import com.example.Library.model.Authors;
import com.example.Library.repository.Author_Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private Author_Repo authorRepo;

    @InjectMocks
    private Author_Service authorService;

    @Test
    public void AuthorService_SaveAuthor_ReturnSavedAuthor(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();

        when(authorRepo.save(Mockito.any(Authors.class))).thenReturn(author);

        Authors savedAuthor = authorService.saveAuthor(author);

        Assertions.assertNotNull(savedAuthor);
    }

    @Test
    public void AuthorService_getAll_ReturnMoreThanOneAuthor(){

        List<Authors> expectedList = new ArrayList<>();

        when(authorRepo.findAll()).thenReturn(expectedList);
        List<Authors> actualList = authorService.getAllAuthors();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }
}
