package com.example.Library.repository;

import com.example.Library.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Book_Repo extends JpaRepository<Books, Integer> {
}
