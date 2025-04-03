package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowRentals extends Application {

    private TableView<Rental> tableView;
    private ObservableList<Rental> rentalList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rental Details");

        tableView = new TableView<>();
        tableView.setEditable(false);

        TableColumn<Rental, String> rentalIdColumn = new TableColumn<>("Rental ID");
        rentalIdColumn.setCellValueFactory(cellData -> cellData.getValue().rentalIdProperty());

        TableColumn<Rental, String> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty());

        TableColumn<Rental, String> carIdColumn = new TableColumn<>("Car ID");
        carIdColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());

        TableColumn<Rental, String> rentalStartDateColumn = new TableColumn<>("Rental Start Date");
        rentalStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().rentalStartDateProperty());

        TableColumn<Rental, String> rentalEndDateColumn = new TableColumn<>("Rental End Date");
        rentalEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().rentalEndDateProperty());

        TableColumn<Rental, String> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());

        tableView.getColumns().addAll(rentalIdColumn, customerIdColumn, carIdColumn, rentalStartDateColumn, rentalEndDateColumn, totalPriceColumn);

        rentalList = FXCollections.observableArrayList();
        loadRentalData();

        tableView.setItems(rentalList);

        Button refreshButton = new Button("Refresh Rentals");
        refreshButton.setOnAction(e -> {
            rentalList.clear();
            loadRentalData();
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(tableView, refreshButton);

        StackPane.setMargin(refreshButton, new javafx.geometry.Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadRentalData() {
        String query = "SELECT rental_id, customer_id, car_id, rental_start_date, rental_end_date, total_price FROM rentals";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String rentalId = rs.getString("rental_id");
                String customerId = rs.getString("customer_id");
                String carId = rs.getString("car_id");
                String rentalStartDate = rs.getString("rental_start_date");
                String rentalEndDate = rs.getString("rental_end_date");
                String totalPrice = rs.getString("total_price");

                rentalList.add(new Rental(rentalId, customerId, carId, rentalStartDate, rentalEndDate, totalPrice));
            }

        } catch (SQLException e) {
            showAlert("Error", "Error loading rental data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Rental {
        private final javafx.beans.property.SimpleStringProperty rentalId;
        private final javafx.beans.property.SimpleStringProperty customerId;
        private final javafx.beans.property.SimpleStringProperty carId;
        private final javafx.beans.property.SimpleStringProperty rentalStartDate;
        private final javafx.beans.property.SimpleStringProperty rentalEndDate;
        private final javafx.beans.property.SimpleStringProperty totalPrice;

        public Rental(String rentalId, String customerId, String carId, String rentalStartDate, String rentalEndDate, String totalPrice) {
            this.rentalId = new javafx.beans.property.SimpleStringProperty(rentalId);
            this.customerId = new javafx.beans.property.SimpleStringProperty(customerId);
            this.carId = new javafx.beans.property.SimpleStringProperty(carId);
            this.rentalStartDate = new javafx.beans.property.SimpleStringProperty(rentalStartDate);
            this.rentalEndDate = new javafx.beans.property.SimpleStringProperty(rentalEndDate);
            this.totalPrice = new javafx.beans.property.SimpleStringProperty(totalPrice);
        }

        public String getRentalId() {
            return rentalId.get();
        }

        public javafx.beans.property.StringProperty rentalIdProperty() {
            return rentalId;
        }

        public String getCustomerId() {
            return customerId.get();
        }

        public javafx.beans.property.StringProperty customerIdProperty() {
            return customerId;
        }

        public String getCarId() {
            return carId.get();
        }

        public javafx.beans.property.StringProperty carIdProperty() {
            return carId;
        }

        public String getRentalStartDate() {
            return rentalStartDate.get();
        }

        public javafx.beans.property.StringProperty rentalStartDateProperty() {
            return rentalStartDate;
        }

        public String getRentalEndDate() {
            return rentalEndDate.get();
        }

        public javafx.beans.property.StringProperty rentalEndDateProperty() {
            return rentalEndDate;
        }

        public String getTotalPrice() {
            return totalPrice.get();
        }

        public javafx.beans.property.StringProperty totalPriceProperty() {
            return totalPrice;
        }
    }
}
