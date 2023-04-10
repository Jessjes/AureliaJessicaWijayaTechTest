package com.example.Library.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;

import java.io.Serializable;

@Embeddable
@Builder
public class Author_Book_PK implements Serializable {
    private int author_id;

    private int book_id;

    public Author_Book_PK(){}

    public Author_Book_PK(Authors author, Books book) {
        this.author_id = author.getAuthorId();
        this.book_id = book.getBookId();
    }

    public Author_Book_PK(int author_id, int book_id) {
        this.author_id = author_id;
        this.book_id = book_id;
    }

    public int getAuthorId() {
        return author_id;
    }

    public void setAuthorId(int authorId) {
        this.author_id = authorId;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int bookId) {
        this.book_id = bookId;
    }
}
