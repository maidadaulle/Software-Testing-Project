package com.example.librarymanagementsystem;

import Controllers.BookController;
import Controllers.FileController;
import Models.Admin;
import Models.Book;
import Views.LogInView;
import Views.PrintBillView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        @SuppressWarnings("unused")
        FileController fileController = new FileController();
        LogInView lg = new LogInView();
        PrintBillView printBillView = new PrintBillView(new Admin("admin","pass"));
        Scene scene = printBillView.showView(stage);

        stage.setOnCloseRequest(e -> {
            FileController.writeUsers();
            FileController.writeAuthors();
            FileController.writeBooks();
            FileController.writeCategories();
            FileController.writeTransactions();
            Platform.exit();
        });

        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}