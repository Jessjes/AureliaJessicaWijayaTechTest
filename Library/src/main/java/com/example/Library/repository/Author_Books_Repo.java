package com.example.Library.repository;

import com.example.Library.model.Author_Book_PK;
import com.example.Library.model.Author_Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface Author_Books_Repo extends JpaRepository<Author_Books, Author_Book_PK> {

}
