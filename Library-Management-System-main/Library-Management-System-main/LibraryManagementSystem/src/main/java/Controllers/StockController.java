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

    public void updateStockAfterSold(ArrayList<Book> book, ArrayList<Integer> quantity) {
        for (int i = 0; i < book.size(); i++) {
            book.get(i).setStock(book.get(i).getStock() - quantity.get(i));
        }
    }

    public void updateStockAfterBought(Book book, int quantity) {
        book.setStock(book.getStock() + quantity);
        book.setPurchasedDate();
    }

}
