package com.example.Library.controller;

import com.example.Library.model.Authors;
import com.example.Library.model.People;
import com.example.Library.service.Author_Service;
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

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = Author_Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Author_Service authorService;

    @Test
    public void PeopleController_SaveAuthor_ReturnSavedAuthor() throws Exception{
        Authors author = Authors.builder().name("John Doe").build();
        String json = new ObjectMapper().writeValueAsString(author);

        given(authorService.saveAuthor(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/author/addAuthor")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(author.getName())));
    }

    @Test
    public void PeopleController_GetAll_ReturnMoreThanOneAuthor() throws Exception{
        Authors author = Authors.builder().name("John Doe").build();
        Authors author2 = Authors.builder().name("Jane Doe").build();

        List<Authors> authorList = new ArrayList<>();
        authorList.add(author);
        authorList.add(author2);

        when(authorService.getAllAuthors()).thenReturn(authorList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/author/getAllAuthor")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(authorList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("John Doe", "Jane Doe")));

    }

}
