package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RiiseRent Login");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Welcome to RiiseRent");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1);

        Label userNameLabel = new Label("Username:");
        userNameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        userNameLabel.setTextFill(Color.WHITE);
        grid.add(userNameLabel, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        pwLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        pwLabel.setTextFill(Color.WHITE);
        grid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        pwBox.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(pwBox, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(loginButton, 1, 3);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(cancelButton, 2, 3);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        grid.setEffect(dropShadow);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1c92d2, #f2fcfe);");
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            if (username.equals("admin") && password.equals("2006")){
                launchCarRentalSystem(primaryStage);            }
            else {
                showAlert("Invalid Credentials", "The username or password is incorrect.");
            }
        });

        cancelButton.setOnAction(e -> {
            userTextField.clear();
            pwBox.clear();
        });


    }

    private void launchCarRentalSystem(Stage primaryStage) {
        primaryStage.close();

        Stage carRentalStage = new Stage();
        CarRentalMainPage carRentalApp = new CarRentalMainPage();
        carRentalApp.start(carRentalStage);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}