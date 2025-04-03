package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShowCostumersAndCars extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customers and Cars");

        TableView<Customer> customerTable = new TableView<>();
        TableColumn<Customer, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Customer Name");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> customerEmailColumn = new TableColumn<>("Customer Email");
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, String> customerPhoneColumn = new TableColumn<>("Phone");
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Customer, String> customerAddressColumn = new TableColumn<>("Address");
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.getColumns().addAll(customerIdColumn, customerNameColumn, customerEmailColumn, customerPhoneColumn, customerAddressColumn);

        TableView<Car> carTable = new TableView<>();
        TableColumn<Car, Integer> carIdColumn = new TableColumn<>("Car ID");
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> carBrandColumn = new TableColumn<>("Brand");
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Car, String> carModelColumn = new TableColumn<>("Model");
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, Integer> carYearColumn = new TableColumn<>("Year");
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Car, Double> carPriceColumn = new TableColumn<>("Price per Day");
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));

        carTable.getColumns().addAll(carIdColumn, carBrandColumn, carModelColumn, carYearColumn, carPriceColumn);

        loadCustomerData(customerTable);
        loadCarData(carTable);

        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(customerTable, carTable);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadCustomerData(TableView<Customer> customerTable) {
        try (Connection connection = DatabaseHelper.connect();
             Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM customers";  // Correct table name
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                customerTable.getItems().add(new Customer(id, name, email, phone, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadCarData(TableView<Car> carTable) {
        try (Connection connection = DatabaseHelper.connect();
             Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM cars";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double pricePerDay = rs.getDouble("price_per_day");
                carTable.getItems().add(new Car(id, brand, model, year, pricePerDay));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
