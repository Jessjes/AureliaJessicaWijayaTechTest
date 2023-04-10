package com.example.Library.controller;

import com.example.Library.model.Book_Rents;
import com.example.Library.service.Book_Rents_Service;
import com.example.Library.service.People_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookrents")
@CrossOrigin
public class Book_Rents_Controller {

    @Autowired
    private Book_Rents_Service bookRentsService;

    @Autowired
    private People_Service peopleService;

    @PostMapping("/addBookRents")
    public Book_Rents add(@RequestBody Book_Rents bookRents){
       return bookRentsService.saveBookRents(bookRents);
    }

    @GetMapping("/getAllBookRents")
    public List<Book_Rents> getAllBookRents(){
        return bookRentsService.getAllBookRents();
    }

    @GetMapping("/getRentedBookCount")
    public List<Map<String, Object>> getRentedBookCount(){
        return bookRentsService.getRentedBooksCount();
    }

    @GetMapping("/getTop3ReadBooksGlobal")
    public List<Map<String, Object>> getTop3ReadBooksGlobal(){
        return bookRentsService.getTop3RentedBookGlobal();
    }

    @GetMapping(path = "/getTop3ReadBook")
    public List<Map<String, Object>> getTop3RentedBookCountry(@RequestParam(name = "country_code") String code){

        if (code == null) {
            List<Map<String, Object>> response = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid parameter");

            response.add(errorResponse);
            return response;
        }else{
            Long id = peopleService.translateCodeToId(code);

            return bookRentsService.getTop3RentedBookCountry(id);
        }
    }
}
