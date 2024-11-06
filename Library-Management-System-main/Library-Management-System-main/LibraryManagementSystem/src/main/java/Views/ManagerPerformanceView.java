package Views;

import Controllers.*;
import Models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManagerPerformanceView {
    private User currentUser;

    public ManagerPerformanceView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showManagerPerformanceView(Stage stage, Manager manager) {

        BorderPane borderPane = new BorderPane();
        StackPane stackText = new StackPane();
        Text text = new Text(manager.getName() + "'s Performance");
        text.setFont(new Font(30));
        stackText.getChildren().add(text);
        stackText.setPadding(new Insets(20));
        borderPane.setTop(stackText);
        ManagerController managerController = new ManagerController();

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);

        Label nrBillsLabel = new Label("Total number of Bills");
        TextField noBillsField = new TextField(Integer.toString(managerController.totalNoBillsByManager(manager)));
        noBillsField.setEditable(false);
        gp.add(nrBillsLabel, 0, 0);
        gp.add(noBillsField, 1, 0);

        Label booksBoughtLabel = new Label("Books Bought");
        TextField booksBoughtField = new TextField(Integer.toString(managerController.totalNoBooksBoughtByManager(manager)));
        booksBoughtField.setEditable(false);
        gp.add(booksBoughtLabel, 0, 1);
        gp.add(booksBoughtField, 1, 1);

        Label moneySpendTodayLabel = new Label("Money spend today");
        TextField moneySpendTodayField = new TextField(Double.toString(managerController.calculateMoneySpendToday(manager)));
        moneySpendTodayField.setEditable(false);
        gp.add(moneySpendTodayLabel, 0, 2);
        gp.add(moneySpendTodayField, 1, 2);

        Label moneySpendThisMonthLabel = new Label("Money spend this month");
        TextField moneySpendThisMonthField = new TextField(Double.toString(managerController.calculateMoneySpendThisMonth(manager)));
        moneySpendThisMonthField.setEditable(false);
        gp.add(moneySpendThisMonthLabel, 0, 3);
        gp.add(moneySpendThisMonthField, 1, 3);

        Label moneySpendYearLabel = new Label("Money spend this year");
        TextField moneySpendYearField = new TextField(Double.toString(managerController.calculateMoneySpendThisYear(manager)));
        moneySpendYearField.setEditable(false);
        gp.add(moneySpendYearLabel, 0, 4);
        gp.add(moneySpendYearField, 1, 4);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            ManageManagerView manageManagerView = new ManageManagerView(currentUser);
            stage.setScene(manageManagerView.showManageManagerView(manager, stage));
        });
        gp.add(backButton, 2, 5);
        borderPane.setCenter(gp);

        return new Scene(borderPane, 700, 500);
    }
}
