package Views;

import java.util.ArrayList;

import Controllers.BillController;
import Controllers.BookController;
import Controllers.FileController;
import Controllers.StockController;
import Models.BillsType;
import Models.Book;
import Models.InvalidIsbnFormatException;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PrintBillView {
    private User currentUser;
    private static ArrayList<Book> books;
    private static ArrayList<Integer> quantity;

    public PrintBillView(User currentUser) {
        this.currentUser = currentUser;
        books = new ArrayList<>();
        quantity = new ArrayList<>();
    }

    public Scene showView(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        Text text = new Text("Print Bill");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        pane.setTop(stack);

        Label totalPrice = new Label("Total Price: ");
        TextField totalPriceF = new TextField();
        totalPriceF.setEditable(false);
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(totalPrice, totalPriceF);

        BookController bc = new BookController();
        BillController bl = new BillController();
        StockController stc = new StockController();

        Label isbnL = new Label("Book ISBN13");
        Label copiesL = new Label("Number of Copies");
        Label priceL = new Label("Book Price");
        TextField isbnF = new TextField();
        TextField copiesF = new TextField();
        TextField priceF = new TextField();
        priceF.setEditable(false);
        Button addField = new Button("+");

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);
        gp.add(isbnL, 0, 0);
        gp.add(priceL, 1, 0);
        gp.add(copiesL, 2, 0);
        gp.add(isbnF, 0, 1);
        gp.add(priceF, 1, 1);
        gp.add(copiesF, 2, 1);
        gp.add(addField, 3, 1);

        VBox vb = new VBox(10);
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().addAll(gp);
        pane.setCenter(vb);

        isbnF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                bc.verifyISBN(newValue);
                isbnF.setStyle("-fx-text-fill: black;");
                if (bc.findBook(newValue) != null) {
                    priceF.setText(Integer.toString(bc.findBook(newValue).getSellingPrice()));
                } else {
                    priceF.clear();
                }
            } catch (InvalidIsbnFormatException e) {
                isbnF.setStyle("-fx-text-fill: red;");
            }
        });

        addField.setOnAction(e -> {
            if (isbnF.getText().isEmpty() || bc.findBook(isbnF.getText()) == null) {
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("Check ISBN Field!");
                error.showAndWait();
            } else if (copiesF.getText().isEmpty()) {
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("Number of copies can't be empty!");
                error.showAndWait();
            } else if (priceF.getText().isEmpty()) {
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("This book doesn't exists in the database!");
                error.showAndWait();
            } else {
                int q = bl.stringToInt(copiesF.getText());
                if(books.contains(bc.findBook(isbnF.getText()))){
                    Alert info = new Alert(AlertType.INFORMATION);
                    info.setHeaderText("Book already added in this bill!");
                    info.showAndWait();
                } else if (q == -2) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setHeaderText("Copies field has to be a number!");
                    error.showAndWait();
                } else if (q == -1) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setHeaderText("Numbers of copies should be greater than 0!");
                    error.showAndWait();
                } else if (q > bc.findBook(isbnF.getText()).getStock()) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setHeaderText("Only " + Integer.toString(bc.findBook(isbnF.getText()).getStock()) + " left!");
                    error.showAndWait();
                } else {
                    quantity.add(q);
                    books.add(bc.findBook(isbnF.getText()));
                    Label lbl1 = new Label(
                            isbnF.getText() + ".................." + bc.findBook(isbnF.getText()).getBookTitle()
                                    + ".................." + copiesF.getText() + " x " + priceF.getText());
                    vb.getChildren().addAll(lbl1);
                    totalPriceF.setText(Integer.toString(totalPriceCalculation()));
                    isbnF.clear();
                    copiesF.clear();
                    priceF.clear();
                }
            }
        });

        Button back = new Button("Cancel");
        back.setOnAction(e -> {
            EmployeeHomePage hp = new EmployeeHomePage(currentUser);
            stage.setScene(hp.showView(stage));
        });
        Button print = new Button("Finish Transaction");
        print.setOnAction(e -> {
            if (!totalPriceF.getText().isEmpty()) {
                bl.createBill(currentUser.getId(), books, quantity, totalPriceCalculation(),
                        BillsType.Sold);
                Alert info = new Alert(AlertType.INFORMATION);
                info.setHeaderText("Bill Printed!");
                info.showAndWait();
                stc.updateStockAfterSold(books, quantity);
                vb.getChildren().clear();
                vb.getChildren().addAll(gp);
                totalPriceF.clear();
                FileController.writeTransactions();
                books.clear();
                quantity.clear();
                FileController.readTransactions();
            } else {
                Alert error = new Alert(AlertType.ERROR);
                error.setHeaderText("No books added in bill!");
                error.showAndWait();
            }

        });
        VBox vbBottom = new VBox(10);
        HBox hb3 = new HBox(10);
        hb3.setAlignment(Pos.CENTER);
        hb3.getChildren().addAll(print, back);
        vbBottom.getChildren().addAll(hb, hb3);
        pane.setBottom(vbBottom);

        ScrollPane sp = new ScrollPane(pane);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Scene sc = new Scene(sp, 700, 500);
        sp.prefWidthProperty().bind(sc.widthProperty());
        sp.prefHeightProperty().bind(sc.heightProperty());
        return sc;
    }

    public int totalPriceCalculation() {
        int tp = 0;
        for (int i = 0; i < books.size(); i++) {
            tp += books.get(i).getSellingPrice() * quantity.get(i);
        }
        return tp;
    }

}
