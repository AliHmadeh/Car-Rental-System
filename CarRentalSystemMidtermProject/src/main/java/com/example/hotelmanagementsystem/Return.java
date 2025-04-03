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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Return extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Return Car");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Return Car");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1);

        Label carIdLabel = new Label("Car ID:");
        carIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        carIdLabel.setTextFill(Color.WHITE);
        grid.add(carIdLabel, 0, 1);

        TextField carIdField = new TextField();
        carIdField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(carIdField, 1, 1);

        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        okButton.setOnMouseEntered(e -> okButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        okButton.setOnMouseExited(e -> okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(okButton, 1, 2);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(cancelButton, 2, 2);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        grid.setEffect(dropShadow);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1c92d2, #f2fcfe);");
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        okButton.setOnAction(e -> {
            String carIdText = carIdField.getText();

            if (carIdText.isEmpty()) {
                showAlert("Error", "Please enter the Car ID.");
            } else {
                try {
                    int carId = Integer.parseInt(carIdText);

                    updateCarAvailability(carId, true);

                    processReturn(carId);

                    carIdField.clear();

                    showAlert("Success", "Car return processed successfully!");
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid Car ID.");
                }
            }
        });

        cancelButton.setOnAction(e -> {
            carIdField.clear();
            launchCarRentalSystem(primaryStage);
        });



    }

    static void updateCarAvailability(int carId, boolean isAvailable) {
        String updateAvailabilityQuery = "UPDATE cars SET available = ? WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(updateAvailabilityQuery)) {
            pstmt.setInt(1, isAvailable ? 1 : 0);
            pstmt.setInt(2, carId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating car availability: " + e.getMessage());
        }
    }

    private void processReturn(int carId) {
        System.out.println("Car ID: " + carId + " has been returned.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void launchCarRentalSystem(Stage primaryStage) {
        primaryStage.close();

        Stage carRentalStage = new Stage();
        CarRentalMainPage carRentalApp = new CarRentalMainPage();
        carRentalApp.start(carRentalStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
