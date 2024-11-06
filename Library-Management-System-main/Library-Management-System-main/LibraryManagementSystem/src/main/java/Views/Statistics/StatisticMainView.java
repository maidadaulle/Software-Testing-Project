package Views.Statistics;

import Models.User;
import Views.AdminHomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatisticMainView {
    private User currentUser;

    public StatisticMainView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showStatisticsView(Stage stage) {
        BorderPane border = new BorderPane();

        Text text = new Text("Statistics");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Button soldBooksBtn = new Button("Sold Books");
        Button boughtBooksBtn = new Button("Bought Books");
        Button incomeBtn = new Button("Income");
        Button costBtn = new Button("Cost");
        Button backBtn = new Button("Back");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(soldBooksBtn, 0, 0);
        grid.add(boughtBooksBtn, 1, 0);
        grid.add(incomeBtn, 2, 0);
        grid.add(costBtn, 3, 0);
        grid.add(backBtn, 4, 0);
        border.setCenter(grid);

        backBtn.setOnAction(e -> {
            AdminHomePage adminHomePage = new AdminHomePage(currentUser);
            stage.setScene(adminHomePage.showAdminHomePage(stage));
        });
        boughtBooksBtn.setOnAction(e -> {
            BoughtBooksView boughtBooksView = new BoughtBooksView(currentUser);
            stage.setScene(boughtBooksView.showBoughtBooksView(stage));
        });
        soldBooksBtn.setOnAction(e -> {
            SoldBooksView soldBooksView = new SoldBooksView(currentUser);
            stage.setScene(soldBooksView.showSoldBooksView(stage));
        });
        incomeBtn.setOnAction(e -> {
            IncomesAdminView incomeView = new IncomesAdminView(currentUser);
            stage.setScene(incomeView.showAdministratorIncomePage(stage));
        });
        costBtn.setOnAction(e -> {
            CostsAdminView costsAdminView = new CostsAdminView(currentUser);
            stage.setScene(costsAdminView.showAdminCostPage(stage));
        });

        return new Scene(border, 700, 500);
    }
}
