package Views;

import Models.Librarian;
import Models.Roles;
import Models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import Controllers.FileController;
import Controllers.LibrarianController;

public class EmployeeHomePage {
    private User currentUser;

    public EmployeeHomePage(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showView(Stage stage) {
        BorderPane bp = new BorderPane();
        Text text = new Text("Homepage");
        StackPane pane1 = new StackPane();
        text.setFont(new Font(30));
        pane1.getChildren().add(text);
        pane1.setPadding(new Insets(20));
        bp.setTop(pane1);
        GridPane p = new GridPane();
        p.setHgap(10);
        p.setVgap(10);
        p.setPadding(new Insets(10, 10, 10, 10));
        p.setAlignment(Pos.CENTER);
        ArrayList<Button> EmployeeBt = new ArrayList<>();

        Button bookInfo = new Button("Book Information");
        bookInfo.setOnAction(e -> {
            BookView bv = new BookView(currentUser);
            stage.setScene(bv.showView(stage));
        });
        Button authorInfo = new Button("All Authors");
        authorInfo.setOnAction(e -> {
            AuthorInfoView av = new AuthorInfoView(currentUser);
            stage.setScene(av.showView(stage));
        });
        Button categoryInfo = new Button("All Categories");
        categoryInfo.setOnAction(e -> {
            CategoryInfoView categoryInfoView = new CategoryInfoView(currentUser);
            stage.setScene(categoryInfoView.showcategory(stage));
        });
        EmployeeBt.add(bookInfo);
        EmployeeBt.add(authorInfo);
        EmployeeBt.add(categoryInfo);

        if (currentUser.getAccessLevel() == 1 || currentUser.getAccessLevel() == 3
                || currentUser.getUserRole() == Roles.Admin) {
            Button billing = new Button("Print Bill");
            billing.setOnAction(e -> {
                PrintBillView printBill = new PrintBillView(currentUser);
                stage.setScene(printBill.showView(stage));
            });
            EmployeeBt.add(billing);
        }

        if (currentUser.getAccessLevel() == 2 || currentUser.getAccessLevel() == 3
                || currentUser.getUserRole() == Roles.Admin) {
            Button addNewBook = new Button("Add New Book");
            addNewBook.setOnAction(e -> {
                AddBookView addBookView = new AddBookView(currentUser);
                stage.setScene(addBookView.addBook(stage));
            });
            Button modifyBook = new Button("Modify Book data");
            modifyBook.setOnAction(e->{
                ModifyBookView mbv = new ModifyBookView(currentUser, stage);
                stage.setScene(mbv.showView());
            });
            Button totalBookSold = new Button("Sold Book Copies");
            totalBookSold.setOnAction(e->{
                BookStatsView bsv = new BookStatsView(currentUser, stage);
                stage.setScene(bsv.showView());
            });
            Button addStock = new Button("Add Stock");
            addStock.setOnAction(e -> {
                AddStockView sv = new AddStockView(currentUser, stage);
                stage.setScene(sv.showView());
            });
            Button addAuthor = new Button("Add New Author");
            addAuthor.setOnAction(e -> {
                AddAuthorView addAuthorView = new AddAuthorView(currentUser);
                stage.setScene(addAuthorView.addAuthor(stage));
            });

            Button addCategory = new Button("Add New Category");
            addCategory.setOnAction(i -> {
                AddCategoryView addCategoryView = new AddCategoryView(currentUser);
                stage.setScene(addCategoryView.addCategory(stage));
            });

            EmployeeBt.add(addNewBook);
            EmployeeBt.add(modifyBook);
            EmployeeBt.add(totalBookSold);
            EmployeeBt.add(addStock);
            EmployeeBt.add(addAuthor);
            EmployeeBt.add(addCategory);
        }
        if (currentUser.getUserRole() == Roles.Manager) {
            Button libStats = new Button("Librarians Statistics");
            libStats.setOnAction(e->{
                stage.setScene(manageLibrariansView(stage));
            });
            EmployeeBt.add(libStats);
        }
        Button changepass= new Button("Change password");
        EmployeeBt.add(changepass);
        changepass.setOnAction(e->{
            Changepassview changepassview =new Changepassview(currentUser);
            stage.setScene(changepassview.showview(stage));
        });
        Button back = new Button("Log out");
        EmployeeBt.add(back);
        if (currentUser.getUserRole() == Roles.Admin) {
            Button backToAdminPageButton = new Button("Back to Admin");
            EmployeeBt.add(backToAdminPageButton);
            backToAdminPageButton.setOnAction(e -> {
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.showAdminHomePage(stage));
            });
        }

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert error = new Alert(AlertType.INFORMATION);
                error.setHeaderText("You are logging out!");
                error.showAndWait();
                LogInView li = new LogInView();
                stage.setTitle("Library Management System");
                stage.setScene(li.showLogInScene(stage));
            }
        });
        int row = 1;
        HBox currentHbox = new HBox(10);
        for (int i = 0; i < EmployeeBt.size(); i++) {
            currentHbox.getChildren().add(EmployeeBt.get(i));
            if ((i + 1) % 5 == 0 || i == EmployeeBt.size() - 1) {
                p.add(currentHbox, 0, row++);
                currentHbox = new HBox(10);
            }
        }
        bp.setCenter(p);
        Scene sc = new Scene(bp, 700, 500);
        return sc;
    }

    public Scene manageLibrariansView(Stage stage) {
        ArrayList<Librarian> librarians = new ArrayList<>();
        ArrayList<Button> librarianNameBtn = new ArrayList<>();

        for (User librarian : FileController.users) {
            if (librarian instanceof Librarian) {
                librarians.add((Librarian) librarian);
                librarianNameBtn.add(new Button(librarian.getName()));
            }
        }

        Button backBtn = new Button("Back");
        librarianNameBtn.add(backBtn);

        BorderPane border = new BorderPane();

        Text text = new Text("Check Librarians Stats");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        int row = 0;
        HBox currentHBox = new HBox(10);

        for (int i = 0; i < librarianNameBtn.size(); i++) {
            int finalI = i; // Capture the correct value of i
            currentHBox.getChildren().add(librarianNameBtn.get(i));

            if ((i + 1) % 5 == 0 || i == librarianNameBtn.size() - 1) {
                gridPane.add(currentHBox, 0, row++);
                currentHBox = new HBox(10);
            }

            librarianNameBtn.get(i).setOnAction(e -> {
                if (finalI < librarians.size()) {
                    LibrarianController librarianController = new LibrarianController();
                    Librarian librarian = librarianController.findLibrarian(finalI);
                    ManageLibrarianView librarianDetails = new ManageLibrarianView(currentUser);
                    stage.setScene(librarianDetails.showManageLibrarianView(librarian, stage));
                } else if (finalI == librarianNameBtn.size() - 2) { // Back button
                    EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                    stage.setScene(hp.showView(stage));
                } 
            });
        }

        border.setCenter(gridPane);
        return new Scene(border, 700, 500);
    }
}
