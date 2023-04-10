package com.example.Library.service;

import com.example.Library.model.People;
import com.example.Library.repository.People_Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private People_Repo peopleRepo;

    @InjectMocks
    private People_Service peopleService;

    @Test
    public void PeopleService_SavePeople_ReturnSavedPeople(){

        People person = People.builder().name("John Doe").country_id(840).build();

        when(peopleRepo.save(Mockito.any(People.class))).thenReturn(person);

        People savedPeople = peopleService.savePeople(person);

        Assertions.assertNotNull(savedPeople);
    }

    @Test
    public void PeopleService_GetAll_ReturnMoreThanOnePeople(){

        List<People> expectedList = new ArrayList<>();

        when(peopleRepo.findAll()).thenReturn(expectedList);
        List<People> actualList = peopleService.getAllPeople();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void PeopleService_GetRandomCountry_ReturnCountryNotNull(){

        Random rand = new Random(1234);

        List<Long> countryIds = List.of(702L, 458L, 840L);
        when(peopleRepo.getAllCountryId()).thenReturn(countryIds);

        List<Map<String, Object>> actualRandomCountry = peopleService.getRandomCountry();

        Assertions.assertEquals(1, actualRandomCountry.size());
        Map<String, Object> country = actualRandomCountry.get(0);
        Assertions.assertNotNull(country);
        Assertions.assertTrue(country.containsKey("country"));

        Map<String, Object> content = (Map<String, Object>) country.get("country");
        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.containsKey("full_name"));
        Assertions.assertTrue(content.containsKey("country_code"));

        String name = (String) content.get("full_name");
        String code = (String) content.get("country_code");

        Assertions.assertTrue("Singapore".equals(name) || "Malaysia".equals(name) || "The United States of America".equals(name));
        Assertions.assertTrue("SG".equals(code) || "MY".equals(code) || "US".equals(code));
    }

    @Test
    public void PeopleService_SetCountryJSON_ContentChanged(){

        List<Map<String, Object>> MockArray = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        String name = "The United States of America";
        String code = "US";

        peopleService.setCountryJSON(MockArray, map, content, name, code);

        Assertions.assertEquals(1, MockArray.size());

        Map<String, Object> actualMap = MockArray.get(0);
        Map<String, Object> actualContent = (Map<String, Object>) actualMap.get("country");

        Assertions.assertEquals(name, actualContent.get("full_name"));
        Assertions.assertEquals(code, actualContent.get("country_code"));
    }

    @Test
    public void PeopleService_TranslateCodeToId_ContentChanged(){

        String mockCode = "MY";
        Long actualTranslation = peopleService.translateCodeToId(mockCode);

        Assertions.assertEquals(458, actualTranslation);
    }

    @Test
    public void PeopleService_GetAllCountryId_ReturnListNotNull(){

        List<Long> expectedList = new ArrayList<>();
        expectedList.add(702L);
        expectedList.add(458L);
        expectedList.add(840L);

        when(peopleRepo.getAllCountryId()).thenReturn(expectedList);
        List<Long> actualList = peopleService.getAllCountryId();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }
}
