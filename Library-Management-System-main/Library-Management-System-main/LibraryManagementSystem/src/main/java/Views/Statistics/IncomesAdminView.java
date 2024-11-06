package Views.Statistics;

import Controllers.IncomesController;
import Controllers.StatisticsController;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IncomesAdminView {

    private User currentUser;

    public IncomesAdminView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showAdministratorIncomePage(Stage stage) {

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 20, 20, 20));

        Text text = new Text("Income of Sold Books");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        TextField totalBooksDay = new TextField();
        Text textTotalBooksDay = new Text("Total Books Today");
        TextField totalIncomeDay = new TextField();
        Text textIncomeDay = new Text("Total Income Today");

        TextField totalBooksMonth = new TextField();
        Text textTotalBooksMonth = new Text("Total Books in a Month");
        TextField totalIncomeMonth = new TextField();
        Text textIncomeMonth = new Text("Total Income in a Month");

        TextField totalBooksYearly = new TextField();
        Text textTotalBooksYearly = new Text("Total Books in a Year");
        TextField totalIncomeYearly = new TextField();
        Text textIncomeYearly = new Text("Total Income in a Year");

        Label inputIncomesLabel = new Label("Select a period");
        inputIncomesLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: grey;");
        DatePicker dateStart = new DatePicker();
        dateStart.setEditable(false);
        DatePicker dateEnd = new DatePicker();
        dateEnd.setEditable(false);
        Button validateDates = new Button("Enter");
        Button clearDates = new Button("Clear");

     //   String cssStyle =
     //   this.getClass().getResource("Styles/styles.css").toExternalForm();
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(inputIncomesLabel, dateStart, dateEnd, validateDates, clearDates);
     //    hBox.getStylesheets().add(cssStyle);
        validateDates.getStyleClass().add("styled-button");
        clearDates.getStyleClass().add("styled-button");

        validateDates.setOnMouseEntered(e -> validateDates.getStyleClass().add("hovered-button"));
        validateDates.setOnMouseExited(e -> validateDates.getStyleClass().remove("hovered-button"));

        clearDates.setOnMouseEntered(e -> clearDates.getStyleClass().add("hovered-button"));
        clearDates.setOnMouseExited(e -> clearDates.getStyleClass().remove("hovered-button"));

        Label generalStatisticLabel = new Label("General Statistics");
        generalStatisticLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: grey;");

        Label totalBooksSelectedTimeLabel = new Label("Total books sold during this period");
        totalBooksSelectedTimeLabel.setVisible(false);
        TextField totalBooksSelectedTimeField = new TextField();
        totalBooksSelectedTimeField.setVisible(false);

        Label totalPriceSelectedTimeLabel = new Label("Total earnings during this period");
        TextField totalPriceSelectedTimeField = new TextField();
        totalPriceSelectedTimeLabel.setVisible(false);
        totalPriceSelectedTimeField.setVisible(false);

        Label errorLabel = new Label();
        errorLabel.setVisible(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(hBox, 0, 0, 6, 1);
        grid.add(generalStatisticLabel, 2, 3, 6, 1);
        grid.add(textTotalBooksDay, 0, 5);
        grid.add(totalBooksDay, 1, 5);
        grid.add(textIncomeDay, 0, 6);
        grid.add(totalIncomeDay, 1, 6);
        grid.add(textTotalBooksMonth, 2, 5);
        grid.add(totalBooksMonth, 3, 5);
        grid.add(textIncomeMonth, 2, 6);
        grid.add(totalIncomeMonth, 3, 6);
        grid.add(textTotalBooksYearly, 4, 5);
        grid.add(totalBooksYearly, 5, 5);
        grid.add(textIncomeYearly, 4, 6);
        grid.add(totalIncomeYearly, 5, 6);
        grid.add(errorLabel, 1, 3, 6, 1);
        grid.add(totalBooksSelectedTimeLabel, 0, 5, 3, 1);
        grid.add(totalBooksSelectedTimeField, 3, 5, 3, 1);
        grid.add(totalPriceSelectedTimeLabel, 0, 6, 3, 1);
        grid.add(totalPriceSelectedTimeField, 3, 6, 3, 1);

        border.setCenter(grid);

        totalBooksDay.setEditable(false);
        totalIncomeDay.setEditable(false);
        totalBooksMonth.setEditable(false);
        totalIncomeMonth.setEditable(false);
        totalBooksYearly.setEditable(false);
        totalIncomeYearly.setEditable(false);

        IncomesController incomesController = new IncomesController();
        totalBooksDay.setText(Integer.toString(incomesController.numberOfBooksSoldToday()));
        totalIncomeDay.setText(Double.toString(incomesController.getDailyIncome()));
        totalBooksMonth.setText(Integer.toString(incomesController.numberOfBooksSoldThisMonth()));
        totalIncomeMonth.setText(Double.toString(incomesController.getMonthlyIncome()));
        totalBooksYearly.setText(Integer.toString(incomesController.numberOfBooksSoldThisYear()));
        totalIncomeYearly.setText(Double.toString(incomesController.getYearlyIncome()));

        StackPane stackBackButton = new StackPane();
        Button backButton = new Button("Back");
        stackBackButton.getChildren().add(backButton);
        backButton.setOnAction(event -> {
            StatisticMainView statisticMainView = new StatisticMainView(currentUser);
            stage.setScene(statisticMainView.showStatisticsView(stage));
        });

        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        validateDates.setOnAction(e -> {
            boolean shouldBeVisible = false;

            textTotalBooksDay.setVisible(shouldBeVisible);
            totalBooksDay.setVisible(shouldBeVisible);
            textIncomeDay.setVisible(shouldBeVisible);
            totalIncomeDay.setVisible(shouldBeVisible);
            textTotalBooksMonth.setVisible(shouldBeVisible);
            totalBooksMonth.setVisible(shouldBeVisible);
            textIncomeMonth.setVisible(shouldBeVisible);
            totalIncomeMonth.setVisible(shouldBeVisible);
            textTotalBooksYearly.setVisible(shouldBeVisible);
            totalBooksYearly.setVisible(shouldBeVisible);
            textIncomeYearly.setVisible(shouldBeVisible);
            totalIncomeYearly.setVisible(shouldBeVisible);
            generalStatisticLabel.setVisible(shouldBeVisible);

            StatisticsController statisticsController = new StatisticsController();
            var result = statisticsController.numberOfBooksSoldDuringPeriod(dateStart.getValue(), dateEnd.getValue());
            if (result.getErrorMessage() != null) {
                errorLabel.setText(result.getErrorMessage());
                errorLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");
                errorLabel.setVisible(true);
            } else {
                totalBooksSelectedTimeField.setEditable(false);
                totalBooksSelectedTimeField.setText(Integer.toString(result.getUser()));
                totalPriceSelectedTimeField.setEditable(false);
                totalPriceSelectedTimeField.setText(Double.toString(
                        statisticsController.getProfitThroughPeriod(dateStart.getValue(), dateEnd.getValue())));
                totalBooksSelectedTimeField.setVisible(true);
                totalBooksSelectedTimeLabel.setVisible(true);
                totalPriceSelectedTimeField.setVisible(true);
                totalPriceSelectedTimeLabel.setVisible(true);

                errorLabel.setVisible(false);
            }
        });
        clearDates.setOnAction(e -> {
            boolean shouldBeVisible = true;

            textTotalBooksDay.setVisible(shouldBeVisible);
            totalBooksDay.setVisible(shouldBeVisible);
            textIncomeDay.setVisible(shouldBeVisible);
            totalIncomeDay.setVisible(shouldBeVisible);
            textTotalBooksMonth.setVisible(shouldBeVisible);
            totalBooksMonth.setVisible(shouldBeVisible);
            textIncomeMonth.setVisible(shouldBeVisible);
            totalIncomeMonth.setVisible(shouldBeVisible);
            textTotalBooksYearly.setVisible(shouldBeVisible);
            totalBooksYearly.setVisible(shouldBeVisible);
            textIncomeYearly.setVisible(shouldBeVisible);
            totalIncomeYearly.setVisible(shouldBeVisible);
            generalStatisticLabel.setVisible(shouldBeVisible);
            totalBooksSelectedTimeLabel.setVisible(false);
            totalBooksSelectedTimeField.setVisible(false);
            totalPriceSelectedTimeLabel.setVisible(false);
            totalPriceSelectedTimeField.setVisible(false);
            errorLabel.setVisible(false);
            dateStart.setValue(null);
            dateEnd.setValue(null);
        });

        return new Scene(border, 700, 500);

    }
}
