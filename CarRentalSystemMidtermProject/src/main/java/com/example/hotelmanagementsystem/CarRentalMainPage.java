package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CarRentalMainPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Rental System");

        DatabaseHelper.createTables();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Car Rental System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1);

        Button carRegistrationButton = createStyledButton("Car Registration");
        grid.add(carRegistrationButton, 0, 1);

        Button customerButton = createStyledButton("Customer");
        grid.add(customerButton, 1, 1);

        Button rentalButton = createStyledButton("Rental");
        grid.add(rentalButton, 0, 2);

        Button returnButton = createStyledButton("Return");
        grid.add(returnButton, 1, 2);

        Button showCustomersAndCarsButton = createStyledButton("Show Customers and Cars");
        grid.add(showCustomersAndCarsButton, 0, 3, 2, 1);

        Button showRentalsButton = createStyledButton("Show Rentals");
        grid.add(showRentalsButton, 0, 4);

        Button logoutButton = createStyledButton("Logout");
        grid.add(logoutButton, 0, 5);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        grid.setEffect(dropShadow);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1c92d2, #f2fcfe);");
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        customerButton.setOnAction(e -> {
            launchCostumerRegistrationApp();
        });

        carRegistrationButton.setOnAction(e -> {
            launchCarRegistrationApp();
        });

        rentalButton.setOnAction(e -> {
            launchRental();
        });

        returnButton.setOnAction(e ->{
            launchReturn();
        });

        showCustomersAndCarsButton.setOnAction(e -> {
            launchShowCustomersAndCars();
        });

        showRentalsButton.setOnAction(e -> {
            launchShowRentals();
        });

        logoutButton.setOnAction(e -> {
            primaryStage.close();
            launchLoginApp(primaryStage);
        });
    }

    private void launchReturn(){
        Stage returns = new Stage();
        Return return1 = new Return();
        return1.start(returns);
    }
    private void launchShowRentals() {
        Stage showRentalsStage = new Stage();
        ShowRentals showRentalsApp = new ShowRentals();
        showRentalsApp.start(showRentalsStage);
    }

    private void launchRental() {
        Stage rental = new Stage();
        Rental rental1 = new Rental();
        rental1.start(rental);
    }

    private void launchLoginApp(Stage primaryStage) {
        primaryStage.close();

        Stage logIn = new Stage();
        LoginApp loginPage = new LoginApp();
        loginPage.start(logIn);
    }

    private void launchCarRegistrationApp() {
        Stage carRegistrationStage = new Stage();
        CarRegistration carRegistrationApp = new CarRegistration();
        carRegistrationApp.start(carRegistrationStage);
    }

    private void launchCostumerRegistrationApp() {
        Stage costumerRegistrationStage = new Stage();
        CustomerRegistration costumerRegistrationApp = new CustomerRegistration();
        costumerRegistrationApp.start(costumerRegistrationStage);
    }

    private void launchShowCustomersAndCars() {
        Stage showCustomers = new Stage();
        ShowCostumersAndCars showCostumersAndCars = new ShowCostumersAndCars();
        showCostumersAndCars.start(showCustomers);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 15 30;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 15 30;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 15 30;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
