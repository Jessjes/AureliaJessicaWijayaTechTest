package com.example.Library.data_access;

import com.example.Library.model.Authors;

import java.util.List;

public interface Author_DA
{
    public Authors saveAuthor(Authors author);

    List<Authors> getAllAuthors();
}
