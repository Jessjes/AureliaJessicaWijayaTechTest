package com.example.Library.controller;

import com.example.Library.model.Books;
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

@WebMvcTest(controllers = Book_Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Book_Service bookService;

    @Test
    public void BookController_SaveBook_ReturnSavedBook() throws Exception{
        Books book = Books.builder().name("Book Name 1").build();
        String json = new ObjectMapper().writeValueAsString(book);

        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/book/addBook")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(book.getName())));
    }

    @Test
    public void BookController_GetAll_ReturnMoreThanOneBook() throws Exception{
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        List<Books> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(book2);

        when(bookService.getAllBooks()).thenReturn(bookList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/book/getAllBooks")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
