package Views.Statistics;

import Controllers.BookController;
import Controllers.IncomesController;
import Models.Book;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SoldBooksView {
    private User currentUser;

    public SoldBooksView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showSoldBooksView(Stage stage) {
        BorderPane borderPane = new BorderPane();
        BookController bookController = new BookController();
        IncomesController incomesController = new IncomesController();

        Text text = new Text("Sold books");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        borderPane.setTop(stack);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);

        Label boughtTodayLabel = new Label("Books Sold Today");
        boughtTodayLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font to bold
        gridPane.add(boughtTodayLabel, 0, 0);
        ArrayList<Book> booksBoughtToday = bookController.getBooksSoldToday();
        var quantitiesToday = incomesController.getBillsOfSoldBooksToday();
        int j = 1;

        HBox hBox = new HBox();
        Label bookNameLabel = new Label("Book Name");
        Label bookPriceLabel = new Label("Selling Price");
        bookNameLabel.setStyle("-fx-text-fill: green;"); // Set text color to green
        bookPriceLabel.setStyle("-fx-text-fill: red;");
        hBox.getChildren().addAll(bookNameLabel, bookPriceLabel);
        hBox.setSpacing(10);
        gridPane.add(hBox, 0, 1);
        double totalBoughtToday = 0;
        for (int i = 0; i < booksBoughtToday.size(); i++) {
            Label bookTitleLabel = new Label(booksBoughtToday.get(i).getBookTitle() + " x " + quantitiesToday.get(i));
            Label priceBookLabel = new Label(booksBoughtToday.get(i).getSellingPrice() + " ALL");
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(bookTitleLabel, priceBookLabel);
            hBox1.setSpacing(10);
            bookTitleLabel.setStyle("-fx-font-size: 10;"); // Set font size
            gridPane.add(hBox1, 0, ++j);
            totalBoughtToday += booksBoughtToday.get(i).getSellingPrice()*quantitiesToday.get(i);
        }
        HBox hBoxTotal1 = new HBox();
        Label totalTxtLabel = new Label("Total");
        totalTxtLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;"); // Set font weight to bold and font size

        Label totalPriceLabel = new Label(totalBoughtToday + " ALL");
        totalPriceLabel.setStyle("-fx-text-fill: Grey; -fx-font-size: 14;");
        hBoxTotal1.getChildren().addAll(totalTxtLabel, totalPriceLabel);
        hBoxTotal1.setSpacing(20);
        gridPane.add(hBoxTotal1, 0, ++j);

        Label boughtThisMonthLabel = new Label("Books Sold This Month");
        boughtThisMonthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font to bold
        gridPane.add(boughtThisMonthLabel, 1, 0);

        HBox hBox2 = new HBox();
        Label bookNameLabel2 = new Label("Book Name");
        Label bookPriceLabel2 = new Label("Selling Price");
        bookNameLabel2.setStyle("-fx-text-fill: green;");
        bookPriceLabel2.setStyle("-fx-text-fill: red;");
        hBox2.getChildren().addAll(bookNameLabel2, bookPriceLabel2);
        hBox2.setSpacing(10);
        gridPane.add(hBox2, 1, 1);

        ArrayList<Book> booksBoughtThisMonth = bookController.getBooksSoldThisMonth();
        var quantitiesThisMonth = incomesController.getBillsOfSoldBooksThisMonth();
        j = 1;
        double totalBoughtThisMonth = 0;
        for (int i = 0; i < booksBoughtThisMonth.size(); i++) {
            Label bookTitleLabel = new Label(booksBoughtThisMonth.get(i).getBookTitle()+ " x " + quantitiesThisMonth.get(i));
            Label priceBookLabel = new Label(booksBoughtThisMonth.get(i).getSellingPrice() + " ALL");
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(bookTitleLabel, priceBookLabel);
            hBox1.setSpacing(20);
            bookTitleLabel.setStyle("-fx-font-size: 10;"); // Set font size
            gridPane.add(hBox1, 1, ++j);
            totalBoughtThisMonth += booksBoughtThisMonth.get(i).getSellingPrice() * quantitiesThisMonth.get(i);
        }
        HBox hBoxTotal2 = new HBox();
        Label totalTxtLabel2 = new Label("Total");
        totalTxtLabel2.setStyle("-fx-font-weight: bold; -fx-font-size: 14;"); // Set font weight to bold and font size

        Label totalPriceLabel2 = new Label(totalBoughtThisMonth + " ALL");
        totalPriceLabel2.setStyle("-fx-text-fill: Grey; -fx-font-size: 14;");
        hBoxTotal2.getChildren().addAll(totalTxtLabel2, totalPriceLabel2);
        hBoxTotal2.setSpacing(20);
        gridPane.add(hBoxTotal2, 1, ++j);

        Label boughtThisYearLabel = new Label("Books Sold This Year");
        HBox hBox3 = new HBox();
        Label bookNameLabel3 = new Label("Book Name");
        Label bookPriceLabel3 = new Label("Selling Price");
        bookNameLabel3.setStyle("-fx-text-fill: green;");
        bookPriceLabel3.setStyle("-fx-text-fill: red;");
        hBox3.getChildren().addAll(bookNameLabel3, bookPriceLabel3);
        hBox3.setSpacing(10);
        boughtThisYearLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font to bold
        gridPane.add(boughtThisYearLabel, 2, 0);
        gridPane.add(hBox3, 2, 1);
        ArrayList<Book> booksBoughtThisYear = bookController.getBooksSoldThisYear();
        var quantitiesThisYear = incomesController.getBillsOfSoldBooksThisYear();
        j = 1;
        double totalBoughtThisYear = 0;
        for (int i = 0; i < booksBoughtThisYear.size(); i++) {
            Label bookTitleLabel = new Label(booksBoughtThisYear.get(i).getBookTitle() + " x " + quantitiesThisYear.get(i));
            Label priceBookLabel = new Label(booksBoughtThisYear.get(i).getSellingPrice() + " ALL");
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(bookTitleLabel, priceBookLabel);
            hBox1.setSpacing(30);
            bookTitleLabel.setStyle("-fx-font-size: 10;"); // Set font size
            gridPane.add(hBox1, 2, ++j);
            totalBoughtThisYear += booksBoughtThisYear.get(i).getSellingPrice()*quantitiesThisYear.get(i);
        }
        HBox hBoxTotal3 = new HBox();
        Label totalTxtLabel3 = new Label("Total");
        totalTxtLabel3.setStyle("-fx-font-weight: bold; -fx-font-size: 14;"); // Set font weight to bold and font size

        Label totalPriceLabel3 = new Label(totalBoughtThisYear + " ALL");
        totalPriceLabel3.setStyle("-fx-text-fill: grey; -fx-font-size: 14;");
        hBoxTotal3.getChildren().addAll(totalTxtLabel3, totalPriceLabel3);
        hBoxTotal3.setSpacing(20);
        gridPane.add(hBoxTotal3, 2, ++j);

        StackPane stackBackButton = new StackPane();
        Button backButton = new Button("Back");
        stackBackButton.getChildren().add(backButton);
        backButton.setOnAction(e -> {
            StatisticMainView adminHomePage = new StatisticMainView(currentUser);
            stage.setScene(adminHomePage.showStatisticsView(stage));
        });
        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        borderPane.setBottom(stackBackButton);

        borderPane.setBottom(stackBackButton);
        borderPane.setCenter(gridPane);
        return new Scene(borderPane, 700, 500);
    }
}
