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

public class CarRegistration extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Registration");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Car Registration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1); // Span across 2 columns

        Label brandLabel = new Label("Brand:");
        brandLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        brandLabel.setTextFill(Color.WHITE);
        grid.add(brandLabel, 0, 1);

        TextField brandField = new TextField();
        brandField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(brandField, 1, 1);

        Label modelLabel = new Label("Model:");
        modelLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        modelLabel.setTextFill(Color.WHITE);
        grid.add(modelLabel, 0, 2);

        TextField modelField = new TextField();
        modelField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(modelField, 1, 2);

        Label yearLabel = new Label("Year:");
        yearLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        yearLabel.setTextFill(Color.WHITE);
        grid.add(yearLabel, 0, 3);

        TextField yearField = new TextField();
        yearField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(yearField, 1, 3);

        Label priceLabel = new Label("Price per Day:");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        priceLabel.setTextFill(Color.WHITE);
        grid.add(priceLabel, 0, 4);

        TextField priceField = new TextField();
        priceField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(priceField, 1, 4);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(submitButton, 1, 5);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        grid.add(cancelButton, 1, 6);

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

        submitButton.setOnAction(e -> {
            String brand = brandField.getText();
            String model = modelField.getText();
            String year = yearField.getText();
            String price = priceField.getText();

            if (brand.isEmpty() || model.isEmpty() || year.isEmpty() || price.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
            } else {
                saveCarRegistration(brand, model, year, price);
                brandField.clear();
                modelField.clear();
                yearField.clear();
                priceField.clear();
                showAlert("Success", "Car registration saved successfully!");
            }
        });

        cancelButton.setOnAction(e -> {
            brandField.clear();
            modelField.clear();
            yearField.clear();
            priceField.clear();
            launchCarRentalSystem(primaryStage);
        });
    }

    private void launchCarRentalSystem(Stage primaryStage) {
        primaryStage.close();

        Stage carRentalStage = new Stage();
        CarRentalMainPage carRentalApp = new CarRentalMainPage();
        carRentalApp.start(carRentalStage);
    }

    private void saveCarRegistration(String brand, String model, String year, String price) {
        try {
            int yearInt = Integer.parseInt(year); // Convert year to integer
            double pricePerDay = Double.parseDouble(price); // Convert price to double

            DatabaseHelper.insertCar(brand, model, yearInt, pricePerDay); // No need for carId, DB handles it

            System.out.println("Car successfully registered in the database!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Year and Price must be numeric values.");
        } catch (Exception e) {
            System.out.println("Error saving car registration: " + e.getMessage());
        }
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
