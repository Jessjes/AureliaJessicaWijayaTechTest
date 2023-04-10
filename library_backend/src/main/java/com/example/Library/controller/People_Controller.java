package com.example.Library.controller;

import com.example.Library.model.People;
import com.example.Library.service.People_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
@CrossOrigin
public class People_Controller {

    @Autowired
    private People_Service peopleService;

    @PostMapping("/addPeople")
    public People addPeople(@RequestBody People people){
        return peopleService.savePeople(people);
    }

    @GetMapping("/getAllPeople")
    public List<People> getAllPeople(){
        return peopleService.getAllPeople();
    }

    @GetMapping("/getAllCountryId")
    public List<Long> getAllCountryId(){
        return peopleService.getAllCountryId();
    }

    @GetMapping("/getRandomCountry")
    public List<Map<String, Object>> getRandomCountry(){
        return peopleService.getRandomCountry();
    }
}
