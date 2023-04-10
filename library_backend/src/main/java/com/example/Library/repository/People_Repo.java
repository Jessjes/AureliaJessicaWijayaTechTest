package com.example.Library.repository;

import com.example.Library.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface People_Repo extends JpaRepository<People, Integer> {

    @Query("SELECT DISTINCT country_id FROM People")
    List<Long> getAllCountryId();

    @Query("SELECT p.name AS borrower, COUNT(*) AS c " +
            "FROM People AS p " +
            "INNER JOIN Book_Rents AS br " +
            "ON p.id = br.people_id " +
            "WHERE br.book_id = ?1 AND p.country_id = ?2 " +
            "GROUP BY borrower " +
            "ORDER BY c DESC")
    List<Object[]> getTop3Borrowers(int book_id, long country_code);
}
