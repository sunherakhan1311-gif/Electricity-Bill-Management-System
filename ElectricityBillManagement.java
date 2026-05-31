import java.sql.*;
import java.util.Scanner;

public class ElectricityBillManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database Connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electricitydb",
                    "root",
                    "");

            // Input Customer Details
            System.out.println("===== Electricity Bill Management System =====");

            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Customer Name: ");
            String customerName = sc.nextLine();

            System.out.print("Enter Meter Number: ");
            String meterNumber = sc.nextLine();

            System.out.print("Enter Units Consumed: ");
            int units = sc.nextInt();

            // Bill Calculation
            double bill;

            if (units <= 100) {
                bill = units * 2;
            } else if (units <= 300) {
                bill = (100 * 2) + ((units - 100) * 3);
            } else {
                bill = (100 * 2) + (200 * 3) + ((units - 300) * 5);
            }

            // Insert Data into Database
            String query = "INSERT INTO customers VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, customerId);
            pst.setString(2, customerName);
            pst.setString(3, meterNumber);
            pst.setInt(4, units);
            pst.setDouble(5, bill);

            int result = pst.executeUpdate();

            if (result > 0) {
                System.out.println("\nCustomer Record Saved Successfully!");
                System.out.println("Bill Amount = ₹" + bill);
            } else {
                System.out.println("Failed to save record.");
            }

            pst.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}