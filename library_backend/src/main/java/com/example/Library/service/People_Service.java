package com.example.Library.service;

import com.example.Library.data_access.People_DA;
import com.example.Library.model.People;
import com.example.Library.repository.People_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class People_Service implements People_DA {

    @Autowired
    private People_Repo peopleRepo;

    public People_Service(People_Repo peopleRepo){
        this.peopleRepo = peopleRepo;
    }

    @Override
    public People savePeople(People people){
        return peopleRepo.save(people);
    }

    @Override
    public List<People> getAllPeople() {
        return peopleRepo.findAll();
    }

    public List<Long> getAllCountryId(){
        return peopleRepo.getAllCountryId();
    }

    public void setCountryJSON(List result, Map map, Map content, String name, String code){
        content.put("full_name", name);
        content.put("country_code", code);

        map.put("country", content);
        result.add(map);
    }

    public Long translateCodeToId(String code){
        long id = 0;

        if(code.equals("SG")) id = 702;
        else if (code.equals("MY")) id = 458;
        else if (code.equals("US")) id = 840;
        else id = 1;

        return (long) id;
    }

    public List<Map<String, Object>> getRandomCountry(){

        Random rand = new Random();
        List<Long> countryList = peopleRepo.getAllCountryId();

        long selected = countryList.get(rand.nextInt(countryList.size()));
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, String> content = new HashMap<>();

        if(selected == 702) setCountryJSON(result, map, content, "Singapore", "SG");
        else if (selected == 458) setCountryJSON(result, map, content, "Malaysia", "MY");
        else if (selected == 840) setCountryJSON(result, map, content, "The United States of America", "US");

        return result;
    };
}
