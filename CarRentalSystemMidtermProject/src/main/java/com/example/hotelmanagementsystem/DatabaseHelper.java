package com.example.hotelmanagementsystem;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:car_rental_system.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String createCustomerTable = """
            CREATE TABLE IF NOT EXISTS customers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                phone TEXT NOT NULL,
                address TEXT
            );
        """;

        String createCarTable = """
            CREATE TABLE IF NOT EXISTS cars (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                brand TEXT NOT NULL,
                model TEXT NOT NULL,
                year INTEGER NOT NULL,
                price_per_day REAL NOT NULL,
                isAvailable INTEGER NOT NULL DEFAULT 1
            );
        """;

        String createRentalsTable = """
            CREATE TABLE IF NOT EXISTS rentals (
                rental_id INTEGER PRIMARY KEY AUTOINCREMENT,
                customer_id INTEGER NOT NULL,
                car_id INTEGER NOT NULL,
                rental_start_date TEXT NOT NULL,
                rental_end_date TEXT NOT NULL,
                FOREIGN KEY (customer_id) REFERENCES customers(id),
                FOREIGN KEY (car_id) REFERENCES cars(id)
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createCustomerTable);
            stmt.execute(createCarTable);
            stmt.execute(createRentalsTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void insertCar(String brand, String model, int year, double pricePerDay) {
        String sql = "INSERT INTO cars (brand, model, year, price_per_day) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, brand);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.setDouble(4, pricePerDay);
            pstmt.executeUpdate();
            System.out.println("Car inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting car: " + e.getMessage());
        }
    }

    public static void insertCustomer(String name, String email, String phone, String address) {
        String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            System.out.println("Customer inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting customer: " + e.getMessage());
        }
    }

    public static void insertRental(int carId, int customerId, String rentalStartDate, String rentalEndDate) {
        double totalPrice = calculateTotalPrice(carId, rentalStartDate, rentalEndDate);

        String insertRentalQuery = "INSERT INTO rentals (car_id, customer_id, rental_start_date, rental_end_date, total_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(insertRentalQuery)) {
            pstmt.setInt(1, carId);
            pstmt.setInt(2, customerId);
            pstmt.setString(3, rentalStartDate);
            pstmt.setString(4, rentalEndDate);
            pstmt.setDouble(5, totalPrice);
            pstmt.executeUpdate();
            System.out.println("Rental inserted successfully.");

            updateCarAvailability(carId, false);
        } catch (SQLException e) {
            System.out.println("Error inserting rental: " + e.getMessage());
        }
    }

    static double calculateTotalPrice(int carId, String rentalStartDate, String rentalEndDate) {
        double pricePerDay = getPricePerDay(carId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // FIXED FORMAT

        LocalDate startDate = LocalDate.parse(rentalStartDate, formatter);
        LocalDate endDate = LocalDate.parse(rentalEndDate, formatter);

        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (rentalDays < 0) {
            throw new IllegalArgumentException("Rental end date cannot be earlier than the start date.");
        }

        return pricePerDay * rentalDays;
    }



    private static double getPricePerDay(int carId) {
        String query = "SELECT price_per_day FROM cars WHERE id = ?";
        double pricePerDay = 0.0;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pricePerDay = rs.getDouble("price_per_day");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching price per day: " + e.getMessage());
        }

        return pricePerDay;
    }

    static void updateCarAvailability(int carId, boolean isAvailable) {
        String updateAvailabilityQuery = "UPDATE cars SET available = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(updateAvailabilityQuery)) {
            pstmt.setInt(1, isAvailable ? 1 : 0);
            pstmt.setInt(2, carId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating car availability: " + e.getMessage());
        }
    }

    public static boolean checkCarAvailability(int carId) {
        String query = "SELECT available FROM cars WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("available");
            }
        } catch (SQLException e) {
            System.out.println("Error checking car availability: " + e.getMessage());
        }
        return false;
    }


}
