package com.example.Library.repository;

import com.example.Library.model.Author_Book_PK;
import com.example.Library.model.Author_Books;
import com.example.Library.model.Authors;
import com.example.Library.model.Books;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AuthorBooksRepositoryTest {

    @Autowired
    private Author_Books_Repo authorBooksRepo;

    @Autowired
    private Author_Repo authorRepo;

    @Autowired
    private Book_Repo bookRepo;

    @Test
    public void AuthorBooksRepository_SaveAuthorBooks_ReturnedSavedAuthorBooks(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Books book = Books.builder().name("Book Name 1").build();

        authorRepo.save(author);
        bookRepo.save(book);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                                        .book_id(book.getBookId())
                                        .author_id(author.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();

        //Act
        Author_Books savedAuthorBooks = authorBooksRepo.save(authorBooks);

        //Assert
        Assertions.assertNotNull(savedAuthorBooks);
        Assertions.assertTrue(savedAuthorBooks.getAuthorBookPk().getAuthorId() > 0);
        Assertions.assertTrue(savedAuthorBooks.getAuthorBookPk().getBookId() > 0);
    }

    @Test
    public void AuthorBooksRepository_GetAll_ReturnMoreThanOneAuthorBooks(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Authors author2 = Authors.builder().name("Jane Doe").build();
        Books book = Books.builder().name("Book Name 1").build();
        Books book2 = Books.builder().name("Book Name 2").build();

        authorRepo.save(author);
        authorRepo.save(author2);
        bookRepo.save(book);
        bookRepo.save(book2);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                                       .book_id(book.getBookId())
                                       .author_id(author.getAuthorId()).build();

        Author_Book_PK authorBooksPK2 = Author_Book_PK.builder()
                                        .book_id(book2.getBookId())
                                        .author_id(author2.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();
        Author_Books authorBooks2 = Author_Books.builder().authorBookPk(authorBooksPK2).build();

        authorBooksRepo.save(authorBooks);
        authorBooksRepo.save(authorBooks2);

        List<Author_Books> authorBooksList = authorBooksRepo.findAll();
        int size = authorBooksList.size();

        Assertions.assertNotNull(authorBooksList);
        Assertions.assertTrue(size == 2);
    }

    @Test
    public void AuthorBooksRepository_FindById_GetAuthorBooksNotNull(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Books book = Books.builder().name("Book Name 1").build();

        authorRepo.save(author);
        bookRepo.save(book);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                .book_id(book.getBookId())
                .author_id(author.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();

        authorBooksRepo.save(authorBooks);

        Author_Books authorBooksReturn = authorBooksRepo.findById(authorBooks.getAuthorBookPk()).get();

        //Assert
        Assertions.assertNotNull(authorBooksReturn);
    }

    @Test
    public void AuthorBooksRepository_UpdateAuthorBooks_ReturnAuthorBooksNotNull(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Books book = Books.builder().name("Book Name 1").build();

        authorRepo.save(author);
        bookRepo.save(book);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                .book_id(book.getBookId())
                .author_id(author.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();

        //Act
        authorBooksRepo.save(authorBooks);
        Author_Books authorBooksReturn = authorBooksRepo.findById(authorBooks.getAuthorBookPk()).get();
        authorBooksReturn.getAuthorBookPk().setAuthorId(3);
        authorBooksReturn.getAuthorBookPk().setBookId(5);

        Author_Books updatedAuthorBooks = authorBooksRepo.save(authorBooksReturn);

        //Assert
        Assertions.assertNotNull(updatedAuthorBooks.getAuthorBookPk().getAuthorId());
        Assertions.assertNotNull(updatedAuthorBooks.getAuthorBookPk().getBookId());
    }

    @Test
    public void AuthorBooksRepository_DeleteAuthorBooks_ReturnAuthorBooksEmpty(){

        //Arrange
        Authors author = Authors.builder().name("John Doe").build();
        Books book = Books.builder().name("Book Name 1").build();

        authorRepo.save(author);
        bookRepo.save(book);

        Author_Book_PK authorBooksPK = Author_Book_PK.builder()
                .book_id(book.getBookId())
                .author_id(author.getAuthorId()).build();

        Author_Books authorBooks = Author_Books.builder().authorBookPk(authorBooksPK).build();

        authorBooksRepo.save(authorBooks);
        authorBooksRepo.deleteById(authorBooks.getAuthorBookPk());

        Optional<Author_Books> authorBooksReturn = authorBooksRepo.findById(authorBooks.getAuthorBookPk());

        //Assert
        Assertions.assertTrue(authorBooksReturn.isEmpty());
    }
}
