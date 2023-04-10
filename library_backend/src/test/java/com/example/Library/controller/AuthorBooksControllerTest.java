package com.example.Library.controller;

import com.example.Library.model.Author_Book_PK;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import com.example.Library.service.Author_Books_Service;
import com.example.Library.service.Author_Service;
import com.example.Library.service.Book_Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = Author_Book_Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Author_Books_Service authorBooksService;

    @MockBean
    private Author_Service authorService;

    @MockBean
    private Book_Service bookService;

    @Test
    public void AuthorBooksController_SaveAuthorBooks_ReturnSavedAuthorBooks() throws Exception{
        Authors author = Authors.builder().author_id(1).name("John Doe").build();
        Books book = Books.builder().book_id(1).name("Book Name 1").build();

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                                                     .book_id(book.getBookId())
                                                     .author_id(author.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();

        String json = new ObjectMapper().writeValueAsString(authorBooks);

        given(authorBooksService.saveAuthorBooksById(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/authorbooks/addAuthorBooks")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorBookPk.authorId", CoreMatchers.is(authorBooks.getAuthorBookPk().getAuthorId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorBookPk.bookId", CoreMatchers.is(authorBooks.getAuthorBookPk().getBookId())));
    }

    @Test
    public void PeopleController_GetAll_ReturnMoreThanOneAuthor() throws Exception{
        Authors author = Authors.builder().name("John Doe").build();
        Authors author2 = Authors.builder().name("Jane Doe").build();
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                .book_id(book.getBookId())
                .author_id(author.getAuthorId()).build();

        Author_Book_PK authorBooksPK2 = Author_Book_PK.builder()
                .book_id(book2.getBookId())
                .author_id(author2.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();
        Author_Books authorBooks2 = Author_Books.builder().authorBookPk(authorBooksPK2).build();

        List<Author_Books> authorBooksList = new ArrayList<>();
        authorBooksList.add(authorBooks);
        authorBooksList.add(authorBooks2);

        when(authorBooksService.getAllAuthorBooks()).thenReturn(authorBooksList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/authorbooks/getAllAuthorBooks")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
