package com.example.Library.controller;

import com.example.Library.model.People;
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

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = People_Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private People_Service peopleService;

    @Test
    public void PeopleController_SavePeople_ReturnSavedPeople() throws Exception {

        People person = People.builder().name("John Doe").country_id(840).build();
        String json = new ObjectMapper().writeValueAsString(person);

        given(peopleService.savePeople(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform( MockMvcRequestBuilders.post("/people/addPeople")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(person.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath( "$.country_id", CoreMatchers.is((int)person.getCountry_id())));
    }

    @Test
    public void PeopleController_GetAll_ReturnMoreThanOnePeople() throws Exception{

        People person = People.builder().name("John Doe").country_id(840).build();
        People person2 = People.builder().name("Jane Doe").country_id(840).build();

        List<People> peopleList = new ArrayList<>();
        peopleList.add(person);
        peopleList.add(person2);

        when(peopleService.getAllPeople()).thenReturn(peopleList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/people/getAllPeople")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void PeopleController_GetAllCountryId_ReturnMoreThanOneCountry() throws Exception{

        List<Long> expectedList = new ArrayList<>();
        expectedList.add(702L);
        expectedList.add(458L);
        expectedList.add(840L);

        when(peopleService.getAllCountryId()).thenReturn(expectedList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/people/getAllCountryId")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", containsInAnyOrder(expectedList.stream().mapToInt(Long::intValue).boxed().toArray())));
    }

    @Test
    public void PeopleController_GetRandomCountry_ReturnCountryOk() throws Exception{

        List<Map<String, Object>> expectedList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map1Content = new HashMap<>();
        map1Content.put("full_name", "Singapore");
        map1Content.put("country_code", "SG");
        map1.put("country", map1Content);
        expectedList.add(map1);

        when(peopleService.getRandomCountry()).thenReturn(expectedList);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/people/getRandomCountry")
                                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country.full_name", CoreMatchers.is("Singapore")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country.country_code", CoreMatchers.is("SG")));
    }
}