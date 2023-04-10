package com.example.Library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author_Books {

    @EmbeddedId
    private Author_Book_PK authorBookPk;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "author_books_author_id_fkey"), insertable = false, updatable = false)
    private Authors authors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "author_books_book_id_fkey"), insertable = false, updatable = false)
    private Books books;

    public Author_Books(Authors author, Books book)
    {
        this.authorBookPk = new Author_Book_PK(author, book);
    }

    public Author_Book_PK getAuthorBookPk() {
        return authorBookPk;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setAuthorBookPk(Author_Book_PK authorBookPk){
        this.authorBookPk = authorBookPk;
    }

}

