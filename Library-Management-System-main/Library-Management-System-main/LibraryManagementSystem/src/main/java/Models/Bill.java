package Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = 8217843344216313688L;
    private Date createdDate;
    private int soldBy;
    private ArrayList<Book> books;
    private ArrayList<Integer> quantity;
    private int totalPrice;
    private static int totalBills;
    private int billNumber;
    private BillsType type;

    public Bill(int ID, ArrayList<Book> books, ArrayList<Integer> quantity, int totalPrice, BillsType type) {
        this.createdDate = new Date();
        this.soldBy = ID;
        this.books = books;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        totalBills++;
        this.billNumber = totalBills;
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static int getTotalBills() {
        return totalBills;
    }

    public static void setTotalBills(int totalBills) {
        Bill.totalBills = totalBills;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public int getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(int soldBy) {
        this.soldBy = soldBy;
    }

    public BillsType getType() {
        return type;
    }

    public void setType(BillsType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String str = "Bill no." + billNumber + "\nDate:" + createdDate + "\nEmployee ID:" + soldBy + "\n";
        int i = 0;
        if(this.type == BillsType.Sold){
            for (Book b : books) {
                str += "\n" + b.getISBN() + " ..... " + b.getBookTitle() + " ..... " + b.getSellingPrice() + " ALL";
                str += " x " + quantity.get(i) + " copies";
                i++;
            }
        } else {
            for (Book b : books) {
                str += "\n" + b.getISBN() + " ..... " + b.getBookTitle() + " ..... " + b.getPurchasedPrice() + " ALL";
                str += " x " + quantity.get(i) + " copies";
                i++;
            }
        }
        
        str += "\n\nTotal Price " + totalPrice + " ALL.";
        return str;
    }
}
