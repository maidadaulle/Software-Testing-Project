package Controllers.AdminControllers;

import Controllers.FileController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Controllers.IncomesController;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BounderyValueTesting {
    @BeforeEach
    void setUp(){
        FileController transactions = new FileController();
        IncomesController incomesController = new IncomesController();

    }

    @Test
    void testOnEmptyTransactionList(){
        assertEquals(0,new IncomesController().numberOfBooksSoldToday());
    }
    @Test
    void testOnMaxTransactionList(){
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));
        var books = new ArrayList<Book>();
        books.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"",new Date()));
        var quantities = new ArrayList<Integer>();
        quantities.add(1);
        for(int i = 0; i < 100; i++){
            FileController.transactions.add(new Bill(i,books,quantities,10,BillsType.Sold));
        }
        assertEquals(100,new IncomesController().numberOfBooksSoldToday());
    }

    @Test
    void testBoundaryDate(){
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));
        var quantities = new ArrayList<Integer>();
        quantities.add(1);
        var books = new ArrayList<Book>();
        books.add(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"",new Date(System.currentTimeMillis() / (24 * 60 * 60 * 1000L) * (24 * 60 * 60 * 1000L))));
        FileController.transactions.add(new Bill(1,books,quantities,10,BillsType.Sold));
        assertEquals(1,new IncomesController().numberOfBooksSoldToday());
    }

}
