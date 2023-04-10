package com.example.Library.repository;

import com.example.Library.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestResource
public interface Author_Repo extends JpaRepository<Authors, Integer> {


}
