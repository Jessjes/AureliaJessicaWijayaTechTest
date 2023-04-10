package com.example.Library.service;

import com.example.Library.model.Book_Rents;
import com.example.Library.model.Books;
import com.example.Library.model.People;
import com.example.Library.repository.Book_Rents_Repo;
import com.example.Library.repository.People_Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRentsServiceTest {

    @Mock
    private Book_Rents_Repo bookRentsRepo;

    @Mock
    private People_Repo peopleRepo;

    @InjectMocks
    private Book_Rents_Service bookRentsService;

    @Test
    public void BookRentsService_SaveBook_ReturnSavedBookRents(){

        Books book = Books.builder().name("Book Name 1").build();
        People person = People.builder().name("John Doe").country_id(840).build();

        Book_Rents bookRents = Book_Rents.builder()
                               .book_id(book.getBookId())
                               .people_id(person.getPersonId()).build();

        when(bookRentsRepo.save(Mockito.any(Book_Rents.class))).thenReturn(bookRents);

        Book_Rents savedBookRents = bookRentsService.saveBookRents(bookRents);

        Assertions.assertNotNull(savedBookRents);
    }

    @Test
    public void BookRentsService_GetAll_ReturnMoreThanOneBookRents(){
        List<Book_Rents> expectedList = new ArrayList<>();

        when(bookRentsRepo.findAll()).thenReturn(expectedList);
        List<Book_Rents> actualList = bookRentsService.getAllBookRents();

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    public void BookRentsService_GetTop3RentedBookGlobal_ReturnTop3BookRents(){
        List<Object[]> expectedList = new ArrayList<>();

        Object[] row1 = new Object[] {"Book 1", 4};
        Object[] row2 = new Object[] {"Book 2", 2};
        Object[] row3 = new Object[] {"Book 3", 1};

        expectedList.add(row1);
        expectedList.add(row2);
        expectedList.add(row3);

        when(bookRentsRepo.getRentedBookCount()).thenReturn(expectedList);
        List<Map<String, Object>> actualList = bookRentsService.getTop3RentedBookGlobal();

        Assertions.assertNotNull(actualList);
        Assertions.assertTrue(actualList.size() >= 3);
        Assertions.assertEquals(expectedList.size(), actualList.size());

        for(int i = 0; i < expectedList.size(); i++) {
            Object[] expectedObject = expectedList.get(i);
            Map<String, Object> actualMap = actualList.get(i);

            Assertions.assertEquals(expectedObject[0], actualMap.get("itemName"));
            Assertions.assertEquals(expectedObject[1], actualMap.get("purchaseCount"));
        }
    }

    @Test
    public void BookRentsService_GetTop3RentedBookCountry_ReturnTop3BookRents(){

        Long mockCountryId = (long)702;

        List<Object[]> borrower= new ArrayList<>();
        Object[] borrowerContent1 = new Object[] {"Borrower 1", 2};
        Object[] borrowerContent2 = new Object[] {"Borrower 2", 4};
        Object[] borrowerContent3 = new Object[] {"Borrower 3", 10};

        borrower.add(borrowerContent1);
        borrower.add(borrowerContent2);
        borrower.add(borrowerContent3);

        List<Object[]> expectedList = new ArrayList<>();

        Object[] row1 = new Object[] {1, "Book Name 1", "Author 1", borrower};
        Object[] row2 = new Object[] {2, "Book Name 2", "Author 2", borrower};
        Object[] row3 = new Object[] {3, "Book Name 3", "Author 3", borrower};

        expectedList.add(row1);
        expectedList.add(row2);
        expectedList.add(row3);

        for (Object[] row : expectedList) {
            int book_id = (int)row[0];
            when(peopleRepo.getTop3Borrowers(book_id, mockCountryId)).thenReturn(borrower);
        }

        when(bookRentsRepo.getTop3BooksCountry(mockCountryId)).thenReturn(expectedList);

        List<Map<String, Object>> actualList = bookRentsService.getTop3RentedBookCountry(mockCountryId);

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(3, actualList.size());

        for(int i = 0; i < expectedList.size(); i++) {
            Object[] expectedObject = expectedList.get(i);
            Map<String, Object> actualMap = actualList.get(i);

            List eBorrowerList =(List) expectedObject[3];
            List<Object[]> expectedBorrowerList = eBorrowerList;

            List aBorrowerList = (List) actualMap.get("borrower");
            Object[] actualBorrowerList = aBorrowerList.toArray();

            List<String> expectedBorrower = new ArrayList<>();
            List<String> actualBorrower = new ArrayList<>();

            for(Object actualName : actualBorrowerList){
                actualBorrower.add(actualName.toString());
            }

            for (Object[] expectedName : expectedBorrowerList) {
                expectedBorrower.add(expectedName[0].toString());
            }

            Assertions.assertEquals(expectedBorrower, actualBorrower);
            Assertions.assertEquals(expectedObject[1], actualMap.get("name"));
            Assertions.assertEquals(expectedObject[2], actualMap.get("author"));

        }

    }
}
