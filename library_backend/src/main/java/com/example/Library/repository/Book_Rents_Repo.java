package com.example.Library.repository;

import com.example.Library.model.Book_Rents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface Book_Rents_Repo extends JpaRepository<Book_Rents, Integer> {

    @Query("SELECT b.name, COUNT(*) AS c " +
            "FROM Books AS b INNER JOIN Book_Rents AS br " +
            "ON b.id = br.book_id " +
            "GROUP BY b.name " +
            "ORDER BY c DESC")
    List<Object[]> getRentedBookCount();

    @Query("SELECT b.id as bid, b.name as book, a.name as author, COUNT(*) as c " +
            "FROM Books AS b " +
            "INNER JOIN Book_Rents AS br " +
            "ON b.id = br.book_id " +
            "INNER JOIN People AS p " +
            "ON br.people_id = p.id " +
            "INNER JOIN Author_Books as ab " +
            "ON b.id = ab.id.book_id " +
            "INNER JOIN Authors AS a " +
            "ON ab.id.author_id = a.id " +
            "WHERE p.country_id = ?1 " +
            "GROUP BY book, bid, author " +
            "ORDER BY c DESC")
    List<Object[]> getTop3BooksCountry(long country_id);
}
