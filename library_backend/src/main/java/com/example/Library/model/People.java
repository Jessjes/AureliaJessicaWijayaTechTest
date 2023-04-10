package com.example.Library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Builder
public class People
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int people_id;

    @NotNull
    private String name;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    @Column(nullable = true)
    private long country_id;

    public People(){}

    public People(String name){
        this.name = name;
    }

    public People(String name, int country_id){
        this.name = name;
        this.country_id = country_id;
    }

    public People(int people_id, String name, Date createdAt, Date updatedAt, long country_id) {
        this.people_id = people_id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.country_id = country_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getPersonId() {
        return people_id;
    }

    public String getName() {
        return name;
    }

    public long getCountry_id() {
        return country_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}

