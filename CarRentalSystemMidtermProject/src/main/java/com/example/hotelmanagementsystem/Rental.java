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
import java.sql.*;
import java.time.LocalDate;

import static com.example.hotelmanagementsystem.DatabaseHelper.connect;

public class Rental extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rental");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label titleLabel = new Label("Rental");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        grid.add(titleLabel, 0, 0, 2, 1);

        Label carIdLabel = new Label("Car ID:");
        carIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        carIdLabel.setTextFill(Color.WHITE);
        grid.add(carIdLabel, 0, 1);

        ComboBox<String> carComboBox = new ComboBox<>();
        fillCarComboBox(carComboBox);
        grid.add(carComboBox, 1, 1);

        Label customerIdLabel = new Label("Customer ID:");
        customerIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        customerIdLabel.setTextFill(Color.WHITE);
        grid.add(customerIdLabel, 0, 2);

        ComboBox<String> customerComboBox = new ComboBox<>();
        fillCustomerComboBox(customerComboBox);
        grid.add(customerComboBox, 1, 2);

        Label dateLabel = new Label("Rental Date:");
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        dateLabel.setTextFill(Color.WHITE);
        grid.add(dateLabel, 0, 3);

        // Use DatePicker for rental date
        DatePicker rentalDatePicker = new DatePicker();
        rentalDatePicker.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(rentalDatePicker, 1, 3);

        Label dueDateLabel = new Label("Due Date:");
        dueDateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        dueDateLabel.setTextFill(Color.WHITE);
        grid.add(dueDateLabel, 0, 4);

        // Use DatePicker for due date
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5;");
        grid.add(dueDatePicker, 1, 4);

        Button okButton = new Button("OK");
        styleButton(okButton, "#4CAF50", "#45a049");
        grid.add(okButton, 1, 5);

        Button cancelButton = new Button("Cancel");
        styleButton(cancelButton, "#f44336", "#d32f2f");
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

        // Button Actions
        okButton.setOnAction(e -> {
            String selectedCar = carComboBox.getValue();
            String selectedCustomer = customerComboBox.getValue();
            LocalDate rentalDate = rentalDatePicker.getValue(); // Get selected rental date
            LocalDate dueDate = dueDatePicker.getValue(); // Get selected due date

            if (selectedCar == null || selectedCustomer == null || rentalDate == null || dueDate == null) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            try {
                int carId = Integer.parseInt(selectedCar.split(" - ")[0]);
                int customerId = Integer.parseInt(selectedCustomer.split(" - ")[0]);

                boolean isAvailable = DatabaseHelper.checkCarAvailability(carId);

                if (!isAvailable) {
                    showAlert("Error", "Car is not available for rental.");
                } else {
                    DatabaseHelper.insertRental(carId, customerId, rentalDate.toString(), dueDate.toString()); // Convert dates to String
                    rentalDatePicker.setValue(null); // Reset DatePickers
                    dueDatePicker.setValue(null);
                    showAlert("Success", "Rental details saved successfully!");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid car or customer ID format.");
            }
        });

        cancelButton.setOnAction(e -> {
            rentalDatePicker.setValue(null);
            dueDatePicker.setValue(null);
            launchCarRentalSystem(primaryStage);
        });
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

    private void fillCarComboBox(ComboBox<String> comboBox) {
        String query = "SELECT id, brand FROM cars WHERE available = 1";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int carId = rs.getInt("id");
                String carName = rs.getString("brand");
                comboBox.getItems().add(carId + " - " + carName);
            }
        } catch (SQLException e) {
            System.out.println("Error loading car data: " + e.getMessage());
        }
    }

    private void fillCustomerComboBox(ComboBox<String> comboBox) {
        String query = "SELECT id, name FROM customers";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int customerId = rs.getInt("id");
                String customerName = rs.getString("name");
                comboBox.getItems().add(customerId + " - " + customerName);
            }
        } catch (SQLException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }

    private void styleButton(Button button, String baseColor, String hoverColor) {
        button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 14; -fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 10 20;"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
