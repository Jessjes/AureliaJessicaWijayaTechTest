package com.example.Library.data_access;

import com.example.Library.model.People;

import java.util.List;

public interface People_DA {
    public People savePeople(People people);

    public List<People> getAllPeople();
}
