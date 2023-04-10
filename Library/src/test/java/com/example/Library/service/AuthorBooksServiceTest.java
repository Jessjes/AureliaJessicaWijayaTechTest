package com.example.Library.service;

import com.example.Library.model.Author_Book_PK;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import com.example.Library.repository.Author_Books_Repo;
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
public class AuthorBooksServiceTest {

    @Mock
    private Author_Books_Repo authorBooksRepo;

    @InjectMocks
    private Author_Books_Service authorBooksService;

    @Test
    public void AuthorBooksService_SaveAuthorBooksById_ReturnSavedAuthorBooks(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Books book = Books.builder().name("Book Name 1").build();

        Author_Book_PK authorBookPk = Author_Book_PK.builder()
                                      .author_id(author.getAuthorId())
                                      .book_id(book.getBookId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBookPk).build();

        when(authorBooksRepo.save(Mockito.any(Author_Books.class))).thenReturn(authorBooks);

        Author_Books savedAuthorBooks = authorBooksService.saveAuthorBooksById(authorBooks);

        Assertions.assertNotNull(savedAuthorBooks);
    }

    @Test
    public void AuthorBooksService_GetAll_ReturnMoreThanOneAuthorBooks(){

        List<Author_Books> expectedList = new ArrayList<>();

        when(authorBooksRepo.findAll()).thenReturn(expectedList);
        List<Author_Books> actualList = authorBooksService.getAllAuthorBooks();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }
}
