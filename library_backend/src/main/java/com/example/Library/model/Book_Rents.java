package com.example.Library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
public class Book_Rents
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private long people_id;

    @NotNull
    private long book_id;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    public Book_Rents(){}

    public Book_Rents(People person, Books book)
    {
        this.people_id = person.getPersonId();
        this.book_id = book.getBookId();
    }

    public Book_Rents(int person_id, int book_id){
        this.people_id = person_id;
        this.book_id = book_id;
    }

    public long getPerson_id() {
        return people_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public int getID(){
        return this.id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setPerson_id(long person_id) {
        this.people_id = person_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

}
