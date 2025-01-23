package Views;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Controllers.BillController;
import Controllers.BookController;
import Controllers.StockController;
import Models.BillsType;
import Models.Book;
import Models.InvalidIsbnFormatException;
import Models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class AddStockView {
    private static User currentUser;
    private static Stage stage;

    public AddStockView(User u, Stage s) {
        currentUser = u;
        stage = s;
    }

    public Scene showView() {
        BorderPane pane = new BorderPane();
        GridPane gPane = new GridPane();
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER_LEFT);
        HBox hb = new HBox(20);
        hb.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setHgap(10);
        gPane.setVgap(10);

        pane.setTop(gPane);
        pane.setCenter(hb);

        Label searchL = new Label("Search with ISBN: ");
        searchL.setId("searchLabel"); // ID for the label
        TextField searchF = new TextField();
        searchF.setId("isbnTextField"); // ID for the ISBN text field
        Label searchMsg = new Label();
        searchMsg.setId("searchMessageLabel"); // ID for the message label
        searchMsg.setStyle("-fx-text-fill: red;");

        Text text = new Text("Find Book For Stock");
        text.setId("titleText"); // ID for the title text
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));

        gPane.add(text, 1, 0);
        gPane.add(searchL, 0, 1);
        gPane.add(searchF, 1, 1);
        gPane.add(searchMsg, 1, 2);

        class MyButtonClickHandler implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent event) {
                EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                stage.setScene(hp.showView(stage));
            }
        }
        MyButtonClickHandler myBtn = new MyButtonClickHandler();

        Button back = new Button("Homepage");
        back.setId("backButton"); // ID for the back button
        gPane.add(back, 2, 1);
        back.setOnAction(myBtn::handle);

        BookController bc = new BookController();
        BillController bb = new BillController();
        StockController ss = new StockController();

        searchF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                bc.verifyISBN(newValue);
                searchF.setStyle("-fx-text-fill: black;");
                searchMsg.setText(null);
            } catch (InvalidIsbnFormatException e) {
                searchF.setStyle("-fx-text-fill: red;");
                searchMsg.setText(e.getMessage());
            }
        });

        searchF.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (searchMsg.getText() == null) {
                    hb.getChildren().clear();
                    vb.getChildren().clear();
                    Book foundBook = bc.findBook(searchF.getText());
                    if (foundBook != null) {

                        Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers",
                                "images");
                        Path imagePath = folderPath.resolve(foundBook.getCover());
                        ImageView cover = new ImageView(new Image(imagePath.toUri().toString()));
                        cover.setFitWidth(300);
                        cover.setFitHeight(290);
                        cover.setPreserveRatio(true);
                        cover.setId("bookCover"); // ID for the book cover image
                        Label book = new Label("Book: " + foundBook.getBookTitle());
                        book.setId("bookLabel"); // ID for the book label
                        Label author = new Label("Author: " + foundBook.getAuthor().getName() + " "
                                + foundBook.getAuthor().getSurname());
                        author.setId("authorLabel"); // ID for the author label
                        Label price = new Label("Purchase price: " + foundBook.getPurchasedPrice());
                        price.setId("priceLabel"); // ID for the price label
                        Label date = new Label();
                        if (foundBook.getPurchasedDate() == null) {
                            date.setText("Last purchased date: Not purchased yet");
                        } else {
                            date.setText("Last purchased date: " + foundBook.getPurchasedDate().toString());
                        }
                        date.setId("dateLabel"); // ID for the date label
                        Label stock = new Label("Current stock: " + foundBook.getStock());
                        stock.setId("stockLabel"); // ID for the stock label

                        Label newStock = new Label("No. books:");
                        newStock.setId("newStockLabel"); // ID for the "No. books" label
                        TextField newStockT = new TextField();
                        newStockT.setId("newStockTextField"); // ID for the new stock text field
                        Label newStockM = new Label();
                        newStockM.setId("newStockMessageLabel"); // ID for the new stock message label
                        newStockM.setStyle("-fx-text-fill: red;");

                        Label stockP = new Label("Stock Price: ");
                        stockP.setId("stockPriceLabel"); // ID for the stock price label
                        TextField stockPF = new TextField();
                        stockPF.setEditable(false);
                        stockPF.setId("stockPriceField"); // ID for the stock price field

                        newStockT.textProperty().addListener((observable, oldValue, newValue) -> {
                            try {
                                if (newValue != null) {
                                    String s = Integer
                                            .toString(totalPriceCalculation(foundBook, Integer.parseInt(newValue)));
                                    stockPF.setText(s);
                                    newStockT.setStyle("-fx-text-fill: black;");
                                    newStockM.setText(null);
                                } else {
                                    stockPF.setText(null);
                                    newStockM.setText("Can't be empty!");
                                }
                            } catch (Exception e2) {
                                newStockT.setStyle("-fx-text-fill: red;");
                                newStockM.setText("Can't have letters in stock field!");
                            }
                        });

                        Button add = new Button("Add Stock");
                        add.setId("addStockButton"); // ID for the Add Stock button
                        add.setOnAction(x -> {
                            if (newStockM.getText() != null) {
                                // Instead of an alert, update the label with the error message
                                newStockM.setText("Check Stock Field!");
                            } else {
                                ArrayList<Book> b = new ArrayList<>();
                                b.add(foundBook);
                                ArrayList<Integer> q = new ArrayList<>();
                                q.add(Integer.parseInt(newStockT.getText()));
                                bb.createBill(currentUser.getId(), b, q,
                                        totalPriceCalculation(foundBook, Integer.parseInt(newStockT.getText())),
                                        BillsType.Bought);
                                ss.updateStockAfterBought(foundBook, Integer.parseInt(newStockT.getText()));
                                date.setText("Last purchased date: " + foundBook.getPurchasedDate().toString());
                                stock.setText("Current stock: " + foundBook.getStock());
                                // Display success message in label instead of alert
                                newStockM.setText("Book stock added successfully!");
                                newStockT.setText(null);
                                stockPF.setText(null);
                            }
                        });

                        GridPane gp1 = new GridPane();
                        gp1.setAlignment(Pos.CENTER);
                        gp1.setVgap(10);
                        gp1.setHgap(10);
                        gp1.add(newStock, 0, 0);
                        gp1.add(newStockT, 1, 0);
                        gp1.add(newStockM, 2, 0);
                        gp1.add(stockP, 0, 1);
                        gp1.add(stockPF, 1, 1);
                        gp1.add(add, 1, 2);

                        vb.getChildren().addAll(book, author, price, date, stock);
                        hb.getChildren().addAll(cover, vb, gp1);
                    } else {
                        // Show standard error message in label
                        searchMsg.setText("This book doesn't exist in the database!");
                    }
                }
            }
        });

        ScrollPane sp = new ScrollPane(pane);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Scene sc = new Scene(sp, 700, 500);
        sp.prefWidthProperty().bind(sc.widthProperty());
        sp.prefHeightProperty().bind(sc.heightProperty());
        return sc;
    }



    public int totalPriceCalculation(Book b, int quantity) {
        return b.getPurchasedPrice() * quantity;
    }
}

