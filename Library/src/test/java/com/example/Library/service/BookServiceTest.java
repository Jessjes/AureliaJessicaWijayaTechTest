package com.example.Library.service;

import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import com.example.Library.repository.Book_Repo;
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
public class BookServiceTest {

    @Mock
    private Book_Repo bookRepo;

    @InjectMocks
    private Book_Service bookService;

    @Test
    public void BookService_SaveBook_ReturnSavedBook(){

        Books book = Books.builder().name("Book Name 1").build();

        when(bookRepo.save(Mockito.any(Books.class))).thenReturn(book);

        Books savedBook = bookService.saveBook(book);

        Assertions.assertNotNull(savedBook);
    }

    @Test
    public void BookService_GetAll_ReturnMoreThanOneBook(){

        List<Books> expectedList = new ArrayList<>();

        when(bookRepo.findAll()).thenReturn(expectedList);
        List<Books> actualList = bookService.getAllBooks();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }
}
