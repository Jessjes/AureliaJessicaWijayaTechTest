package com.example.Library.controller;

import com.example.Library.model.Author_Books;
import com.example.Library.model.Book_Rents;
import com.example.Library.model.Books;
import com.example.Library.model.People;
import com.example.Library.service.Book_Rents_Service;
import com.example.Library.service.Book_Service;
import com.example.Library.service.People_Service;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = Book_Rents_Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookRentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Book_Rents_Service bookRentsService;

    @MockBean
    private People_Service peopleService;

    @MockBean
    private Book_Service bookService;

    @Test
    public void BookRentsController_SaveBookRents_ReturnSavedBookRents() throws Exception{

        Books book = Books.builder().book_id(1).name("Book Name 1").build();
        People person = People.builder().people_id(1).name("John Doe").country_id(840).build();

        Book_Rents bookRents = Book_Rents.builder()
                                         .book_id(book.getBookId())
                                         .people_id(person.getPersonId()).build();

        given(bookRentsService.saveBookRents(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        String json = new ObjectMapper().writeValueAsString(bookRents);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/bookrents/addBookRents")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.book_id", CoreMatchers.is((int)bookRents.getBook_id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person_id", CoreMatchers.is((int)bookRents.getPerson_id())));
    }

    @Test
    public void BookRentsController_GetAll_ReturnMoreThanOneBookRents() throws Exception{

        Books book = Books.builder().book_id(1).name("Book Name 1").build();
        Books book2 = Books.builder().book_id(2).name("Book Name 2").build();
        People person = People.builder().people_id(1).name("John Doe").country_id(840).build();
        People person2 = People.builder().people_id(2).name("Jane Doe").country_id(840).build();

        Book_Rents bookRents1 = Book_Rents.builder()
                                          .book_id(book.getBookId())
                                          .people_id(person.getPersonId()).build();

        Book_Rents bookRents2 = Book_Rents.builder()
                                          .book_id(book2.getBookId())
                                          .people_id(person2.getPersonId()).build();

        List<Book_Rents> bookRentsList = new ArrayList<>();
        bookRentsList.add(bookRents1);
        bookRentsList.add(bookRents2);

        when(bookRentsService.getAllBookRents()).thenReturn(bookRentsList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/bookrents/getAllBookRents")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void BookRentsController_GetRentedBookCount_ReturnMoreThanOneBookRents() throws Exception{

        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("itemName", "Book Name 1");
        map1.put("purchaseCount", 3);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("itemName", "Book Name 2");
        map2.put("purchaseCount", 2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("itemName", "Book Name 3");
        map3.put("purchaseCount", 1);

        expectedList.add(map1);
        expectedList.add(map2);
        expectedList.add(map3);

        when(bookRentsService.getRentedBooksCount()).thenReturn(expectedList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/bookrents/getRentedBookCount")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", containsInAnyOrder(expectedList.toArray())));
    }

    @Test
    public void BookRentsController_GetTop3RentedBookGlobal_ReturnMoreThanOneBookRents() throws Exception{
        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("itemName", "Book Name 1");
        map1.put("purchaseCount", 3);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("itemName", "Book Name 2");
        map2.put("purchaseCount", 2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("itemName", "Book Name 3");
        map3.put("purchaseCount", 1);

        expectedList.add(map1);
        expectedList.add(map2);
        expectedList.add(map3);

        when(bookRentsService.getTop3RentedBookGlobal()).thenReturn(expectedList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/bookrents/getTop3ReadBooksGlobal")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", containsInAnyOrder(expectedList.toArray())));
    }

    @Test
    public void BookRentsController_GetTop3RentedBookCountry_ReturnMoreThanOneBookRents() throws Exception{

        String mockCountryCode = "SG";
        Long mockCountryId = 840L;

        List<String> borrower = new ArrayList<>();
        borrower.add("Borrower 1");
        borrower.add("Borrower 2");
        borrower.add("Borrower 3");

        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "Book Name 1");
        map1.put("author", "Author 1");
        map1.put("borrower", borrower);

        Map<String, Object> map2 = new HashMap<>();
        map1.put("name", "Book Name 2");
        map1.put("author", "Author 2");
        map1.put("borrower", borrower);

        Map<String, Object> map3 = new HashMap<>();
        map1.put("name", "Book Name 3");
        map1.put("author", "Author 3");
        map1.put("borrower", borrower);

        expectedList.add(map1);
        expectedList.add(map2);
        expectedList.add(map3);

        when(peopleService.translateCodeToId(mockCountryCode)).thenReturn(mockCountryId);
        when(bookRentsService.getTop3RentedBookCountry(mockCountryId)).thenReturn(expectedList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/bookrents/getTop3ReadBook")
                                        .param("country_code", mockCountryCode)
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", containsInAnyOrder(expectedList.toArray())));
    }


}
