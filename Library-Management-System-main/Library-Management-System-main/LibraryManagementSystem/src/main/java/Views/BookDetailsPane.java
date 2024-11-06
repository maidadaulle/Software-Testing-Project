package Views;

import Models.Book;
import Models.Category;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookDetailsPane {
    public VBox showPane(Book foundBook) {
        VBox p = new VBox(10);
        p.setAlignment(Pos.CENTER);

        Label title = new Label("Title: ");
        Label titleC = new Label(foundBook.getBookTitle());
        HBox hbTitle = new HBox(10);
        hbTitle.getChildren().addAll(title, titleC);
        hbTitle.setAlignment(Pos.CENTER_LEFT);

        Label author = new Label("Author: ");
        Label authorC = new Label(foundBook.getAuthor().getName() + " " + foundBook.getAuthor().getSurname());
        HBox hbAuthor = new HBox(10);
        hbAuthor.getChildren().addAll(author, authorC);
        hbAuthor.setAlignment(Pos.CENTER_LEFT);

        Label isbn = new Label("ISBN13: ");
        Label isbnC = new Label(foundBook.getISBN());
        HBox hbISBN = new HBox(10);
        hbISBN.getChildren().addAll(isbn, isbnC);
        hbISBN.setAlignment(Pos.CENTER_LEFT);

        Label categories = new Label("Categories: ");
        VBox vb = new VBox(5);
        for (Category c : foundBook.getBookCategories()) {
            Label category = new Label(c.getCategoryName());
            vb.getChildren().addAll(category);
        }
        HBox hbCategories = new HBox(10);
        hbCategories.getChildren().addAll(categories, vb);
        hbCategories.setAlignment(Pos.CENTER_LEFT);

        Label supplier = new Label("Supplier: ");
        Label supplierC = new Label(foundBook.getSupplier());
        HBox hbSupplier = new HBox(10);
        hbSupplier.getChildren().addAll(supplier, supplierC);
        hbSupplier.setAlignment(Pos.CENTER_LEFT);

        Label purchasedDate = new Label("Last purchased: ");
        Label purchasedDateC;
        if (foundBook.getPurchasedDate() == null) {
            purchasedDateC = new Label("Not purchased yet");
        } else {
            purchasedDateC = new Label(foundBook.getPurchasedDate().toString());
        }
        HBox hbPurchasedDate = new HBox(10);
        hbPurchasedDate.getChildren().addAll(purchasedDate, purchasedDateC);
        hbPurchasedDate.setAlignment(Pos.CENTER_LEFT);

        Label purchasedPrice = new Label("Purchased price: ");
        Label purchasedPriceC = new Label(Integer.toString(foundBook.getPurchasedPrice()) + " ALL");
        HBox hbPurchasedPrice = new HBox(10);
        hbPurchasedPrice.getChildren().addAll(purchasedPrice, purchasedPriceC);
        hbPurchasedPrice.setAlignment(Pos.CENTER_LEFT);

        Label originalPrice = new Label("Purchased price: ");
        Label originalPriceC = new Label(Integer.toString(foundBook.getOriginalPrice()) + " ALL");
        HBox hbOriginalPrice = new HBox(10);
        hbOriginalPrice.getChildren().addAll(originalPrice, originalPriceC);
        hbOriginalPrice.setAlignment(Pos.CENTER_LEFT);

        Label sellingPrice = new Label("Selling price: ");
        Label sellingPriceC = new Label(Integer.toString(foundBook.getSellingPrice()) + " ALL");
        HBox hbSellingPrice = new HBox(10);
        hbSellingPrice.getChildren().addAll(sellingPrice, sellingPriceC);
        hbSellingPrice.setAlignment(Pos.CENTER_LEFT);

        Label stock = new Label("Stock: ");
        Label stockC = new Label(Integer.toString(foundBook.getStock()) + " copies left in store");
        HBox hbStock = new HBox(10);
        hbStock.getChildren().addAll(stock, stockC);
        hbStock.setAlignment(Pos.CENTER_LEFT);

        Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers", "images");
        Path imagePath = folderPath.resolve(foundBook.getCover());
        ImageView cover = new ImageView(new Image(imagePath.toUri().toString()));
        cover.setFitWidth(300);
        cover.setFitHeight(290);
        cover.setPreserveRatio(true);

        VBox p1 = new VBox(5);
        p1.setAlignment(Pos.CENTER_LEFT);
        HBox h1 = new HBox(25);
        h1.setAlignment(Pos.CENTER);
        Button showCatalog = new Button("Back");
        p1.getChildren().addAll(hbTitle, hbAuthor, hbISBN, hbCategories, hbSupplier,
                hbPurchasedDate, hbPurchasedPrice, hbOriginalPrice, hbSellingPrice, hbStock, showCatalog);
        h1.getChildren().addAll(cover, p1);
        p.getChildren().addAll(h1);
        showCatalog.setOnAction(e -> {
            p.getChildren().clear();
            BookCatalogPane bcp = new BookCatalogPane();
            p.getChildren().add(bcp.showPane());
        });
        return p;
    }

}
