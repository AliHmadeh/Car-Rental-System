package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static com.example.hotelmanagementsystem.DatabaseHelper.insertCustomer;

public class CustomerRegistration extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customer Registration");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Customer Registration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1);

        Label customerNameLabel = new Label("Customer Name:");
        customerNameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        customerNameLabel.setTextFill(Color.WHITE);
        grid.add(customerNameLabel, 0, 1);

        TextField customerNameField = new TextField();
        customerNameField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(customerNameField, 1, 1);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        emailLabel.setTextFill(Color.WHITE);
        grid.add(emailLabel, 0, 2);

        TextField emailField = new TextField();
        emailField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(emailField, 1, 2);

        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        addressLabel.setTextFill(Color.WHITE);
        grid.add(addressLabel, 0, 3);

        TextField addressField = new TextField();
        addressField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(addressField, 1, 3);

        Label mobileLabel = new Label("Mobile:");
        mobileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        mobileLabel.setTextFill(Color.WHITE);
        grid.add(mobileLabel, 0, 4);

        TextField mobileField = new TextField();
        mobileField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(mobileField, 1, 4);

        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        okButton.setOnMouseEntered(e -> okButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        okButton.setOnMouseExited(e -> okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(okButton, 1, 5);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(cancelButton, 2, 5);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        grid.setEffect(dropShadow);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1c92d2, #f2fcfe);");
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        okButton.setOnAction(e -> {
            String customerName = customerNameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String mobile = mobileField.getText();

            if (customerName.isEmpty() || email.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
            } else {
                saveCustomerDetails(customerName, email, mobile, address);

                customerNameField.clear();
                emailField.clear();
                addressField.clear();
                mobileField.clear();

                showAlert("Success", "Customer details saved successfully!");
            }
        });

        cancelButton.setOnAction(e -> {
            customerNameField.clear();
            emailField.clear();
            addressField.clear();
            mobileField.clear();
            launchCarRentalSystem(primaryStage);
        });
    }

    private void saveCustomerDetails(String customerName, String email, String mobile, String address) {
        insertCustomer(customerName, email, mobile, address);

        System.out.println("Customer Name: " + customerName);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Mobile: " + mobile);
    }

    private void launchCarRentalSystem(Stage primaryStage) {
        primaryStage.close();

        Stage carRentalStage = new Stage();
        CarRentalMainPage carRentalApp = new CarRentalMainPage();
        carRentalApp.start(carRentalStage);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
