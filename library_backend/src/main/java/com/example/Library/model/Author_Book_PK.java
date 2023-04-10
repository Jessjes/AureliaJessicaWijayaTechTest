package com.example.Library.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author_Book_PK implements Serializable {
    private int author_id;

    private int book_id;

    public Author_Book_PK(Authors author, Books book) {
        this.author_id = author.getAuthorId();
        this.book_id = book.getBookId();
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
