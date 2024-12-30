package Controllers;

import Models.Book;
import java.util.ArrayList;

public class StockController {

    public ArrayList<String> needRestock() {
        ArrayList<String> str = new ArrayList<>();
        for (Book book : FileController.books) {
            if (book.getStock() <= 5) {
                str.add(book.getISBN() + " " + book.getBookTitle());
            }
        }
        return str;
    }

    public void updateStockAfterSold(ArrayList<Book> books, ArrayList<Integer> quantities) {
        for (int i = 0; i < books.size(); i++) {
            int currentStock = books.get(i).getStock();
            int quantityToSell = quantities.get(i);


            if (currentStock >= quantityToSell) {
                books.get(i).setStock(currentStock - quantityToSell);
            } else {
                books.get(i).setStock(0);
            }
        }
    }


    public void updateStockAfterBought(Book book, int quantity) {
        if (quantity < 0) {
            System.out.println("Cannot add a negative quantity.");
            return;
        }

        if (book.getStock() > Integer.MAX_VALUE - quantity) {
            System.out.println("Stock addition would cause an integer overflow.");
            return;
        }

        book.setStock(book.getStock() + quantity);
        book.setPurchasedDate();
    }


}
