package Controllers.AdminControllers;

import Controllers.FileController;
import Controllers.IncomesController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeCoverageTesting {

    @BeforeEach
    void setUp(){
        FileController transactions = new FileController();
        IncomesController incomesController = new IncomesController();
    }
    @Test
    void testEmptyBillList() {
        // No transactions in the list
        FileController.transactions = new ArrayList<>();

        Integer result = new IncomesController().numberOfBooksSoldToday();
        assertEquals(0, result);
    }

    @Test
    void testNoSoldBills() {
        // Bills are not of type SOLD
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));
        var quantities = new ArrayList<Integer>();
        quantities.add(1);
        var books = new ArrayList<Book>();
        books.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"",new Date(System.currentTimeMillis() / (24 * 60 * 60 * 1000L) * (24 * 60 * 60 * 1000L))));
        FileController.transactions.add(new Bill(1,books,quantities,10,BillsType.Bought));


        Integer result = new IncomesController().numberOfBooksSoldToday();
        assertEquals(0, result); // No SOLD bills should result in 0
    }

    @Test
    void testNoBooksSoldToday() {
        // There are sold bills, but no books are sold today
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));
        var quantities = new ArrayList<Integer>();
        quantities.add(1);
        var books = new ArrayList<Book>();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // Get the new date (1 day earlier)
        Date yesterday = calendar.getTime();
        books.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"", yesterday));
        FileController.transactions.add(new Bill(1,books,quantities,10,BillsType.Sold));


        Integer result = new IncomesController().numberOfBooksSoldToday();
        assertEquals(0, result); // No books sold today
    }

    @Test
    void testSoldBooksOnDifferentDays() {
        // Books sold on different days, some sold today
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));
        var quantities = new ArrayList<Integer>();
        quantities.add(1);
        var books = new ArrayList<Book>();
        var books2 = new ArrayList<Book>();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // Get the new date (1 day earlier)
        Date yesterday = calendar.getTime();
        books.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"", yesterday));
        books2.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"", new Date()));
        FileController.transactions.add(new Bill(1,books,quantities,10,BillsType.Sold));
        FileController.transactions.add(new Bill(2,books2,quantities,100,BillsType.Sold));

        Integer result = new IncomesController().numberOfBooksSoldToday();
        assertEquals(1, result); // Only book2 was sold today
    }



}
