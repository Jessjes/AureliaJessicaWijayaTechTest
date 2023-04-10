package com.example.Library.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@Table(name = "authors")
public class Authors
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int author_id;

    @NotNull
    private String name;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    @OneToMany(mappedBy = "authors")
    private Set<Author_Books> author_books;

    public Authors(){}

    public Authors(int author_id, String name, Date createdAt, Date updatedAt, Set<Author_Books> author_books) {
        this.author_id = author_id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author_books = author_books;
    }

    public Authors(String name){
        this.name = name;
    }

    public int getAuthorId() {
        return author_id;
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

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Authors)) {
            return false;
        }
        Authors author = (Authors) obj;
        return Objects.equals(author.getAuthorId(), this.getAuthorId()) &&
                Objects.equals(author.getName(), this.getName()) &&
                Objects.equals(author.getCreatedAt(), this.getCreatedAt()) &&
                Objects.equals(author.getUpdatedAt(), this.getUpdatedAt());
    }
}

