package Views.Statistics;

import Controllers.BookController;
import Controllers.CostsController;
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

public class CostsAdminView {
    private User currentUser;

    public CostsAdminView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showAdminCostPage(Stage stage) {

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 20, 20, 20));

        Text text = new Text("Cost of Bought Books");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        TextField totalBooksDay = new TextField();
        Text textTotalBooksDay = new Text("Total Books Today");
        TextField totalIncomeDay = new TextField();
        Text textIncomeDay = new Text("Total Cost Today");

        TextField totalBooksMonth = new TextField();
        Text textTotalBooksMonth = new Text("Total Books in a Month");
        TextField totalIncomeMonth = new TextField();
        TextField salaryMonth = new TextField();
        Text textSalaryMonth = new Text("Salary Total This Month");
        Text textIncomeMonth = new Text("Total Cost in a Month");

        TextField totalBooksYearly = new TextField();
        Text textTotalBooksYearly = new Text("Total Books in a Year");
        TextField totalIncomeYearly = new TextField();
        TextField salaryYear = new TextField();
        Text textSalaryYear = new Text("Salary Total In a  Year");
        Text textIncomeYearly = new Text("Total Cost in a Year");

        Label inputIncomesLabel = new Label("Select a period");
        inputIncomesLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: grey;");
        DatePicker dateStart = new DatePicker();
        dateStart.setEditable(false);
        DatePicker dateEnd = new DatePicker();
        dateEnd.setEditable(false);
        Button validateDates = new Button("Enter");
        Button clearDates = new Button("Clear");

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(inputIncomesLabel, dateStart, dateEnd, validateDates, clearDates);
        // hBox.getStylesheets().add(cssStyle);
        validateDates.getStyleClass().add("styled-button");
        clearDates.getStyleClass().add("styled-button");

        validateDates.setOnMouseEntered(e -> validateDates.getStyleClass().add("hovered-button"));
        validateDates.setOnMouseExited(e -> validateDates.getStyleClass().remove("hovered-button"));

        clearDates.setOnMouseEntered(e -> clearDates.getStyleClass().add("hovered-button"));
        clearDates.setOnMouseExited(e -> clearDates.getStyleClass().remove("hovered-button"));

        Label generalStatisticLabel = new Label("General Statistics");
        generalStatisticLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: grey;");

        Label totalBooksSelectedTimeLabel = new Label("Total books bought during this period");
        totalBooksSelectedTimeLabel.setVisible(false);
        TextField totalBooksSelectedTimeField = new TextField();
        totalBooksSelectedTimeField.setVisible(false);

        Label totalPriceSelectedTimeLabel = new Label("Total costs during this period");
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
        grid.add(textSalaryMonth, 2, 7);
        grid.add(salaryMonth, 3, 7);

        grid.add(textTotalBooksYearly, 4, 5);
        grid.add(totalBooksYearly, 5, 5);
        grid.add(textIncomeYearly, 4, 6);
        grid.add(totalIncomeYearly, 5, 6);
        grid.add(textSalaryYear, 4, 7);
        grid.add(salaryYear, 5, 7);
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
        salaryMonth.setEditable(false);
        salaryYear.setEditable(false);

        BookController bookController = new BookController();
        CostsController costsController = new CostsController();
        StatisticsController statisticsController = new StatisticsController();
        totalBooksDay.setText(Integer.toString(costsController.numberOfBooksBoughtToday()));
        totalIncomeDay.setText(Double.toString(costsController.getDailyCost()));

        totalBooksMonth.setText(Integer.toString(costsController.numberOfBooksBoughtThisMonth()));
        totalIncomeMonth.setText(Double.toString(costsController.getMonthlyCost()));
        salaryMonth.setText(Double.toString(statisticsController.getTotalSalary()));

        totalBooksYearly.setText(Integer.toString(costsController.numberOfBooksBoughtThisYear()));
        totalIncomeYearly.setText(Double.toString(costsController.getYearlyCost()));
        salaryYear.setText(Double.toString(statisticsController.getTotalSalary() * 12));

        StackPane stackBackButton = new StackPane();
        Button backButton = new Button("Back");
        stackBackButton.getChildren().add(backButton);
        backButton.setOnAction(event -> {
            StatisticMainView statisticMainView = new StatisticMainView(currentUser);
            stage.setScene(statisticMainView.showStatisticsView(stage));
        });
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
            textSalaryMonth.setVisible(shouldBeVisible);
            salaryMonth.setVisible(shouldBeVisible);
            textSalaryYear.setVisible(shouldBeVisible);
            salaryYear.setVisible(shouldBeVisible);

            var result = statisticsController.numberOfBooksBoughtDuringPeriod(dateStart.getValue(), dateEnd.getValue());
            if (result.getErrorMessage() != null) {
                errorLabel.setText(result.getErrorMessage());
                errorLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");
                errorLabel.setVisible(true);
            } else {
                totalBooksSelectedTimeField.setEditable(false);
                totalBooksSelectedTimeField.setText(Integer.toString(result.getUser()));
                totalPriceSelectedTimeField.setEditable(false);
                totalPriceSelectedTimeField.setText(Double.toString(
                        statisticsController.getBookCostsThroughPeriod(dateStart.getValue(), dateEnd.getValue())));
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
            textSalaryMonth.setVisible(shouldBeVisible);
            salaryMonth.setVisible(shouldBeVisible);
            textSalaryYear.setVisible(shouldBeVisible);
            salaryYear.setVisible(shouldBeVisible);

            totalBooksSelectedTimeLabel.setVisible(false);
            totalBooksSelectedTimeField.setVisible(false);
            totalPriceSelectedTimeLabel.setVisible(false);
            totalPriceSelectedTimeField.setVisible(false);
            errorLabel.setVisible(false);
            dateStart.setValue(null);
            dateEnd.setValue(null);
        });

        stackBackButton.setPadding(new Insets(0, 0, 40, 0));
        border.setBottom(stackBackButton);

        return new Scene(border, 700, 500);

    }

}
