package com.example.Library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Books
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int book_id;

    @NotNull
    private String name;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    @OneToMany(mappedBy = "books")
    private Set<Author_Books> author_books;

    public Books(String name) {
        this.name = name;
    }

    public int getBookId() {
        return book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}
