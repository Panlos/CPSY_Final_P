/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_P;

/**
 *
 * @author aidanpratt
 */

import java.time.LocalDate;

public class Rental {
    private int rentalId;
    private LocalDate date;
    private int customerId;
    private int equipmentId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double cost;

    public Rental(int rentalId, LocalDate date, int customerId, int equipmentId, LocalDate rentalDate, LocalDate returnDate, double cost) {
        this.rentalId = rentalId;
        this.date = date;
        this.customerId = customerId;
        this.equipmentId = equipmentId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.cost = cost;
    }

    // Getters and setters
    public int getRentalId() { return rentalId; }
    public LocalDate getDate() { return date; }
    public int getCustomerId() { return customerId; }
    public int getEquipmentId() { return equipmentId; }
    public LocalDate getRentalDate() { return rentalDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return "Rental ID: " + rentalId + ", Date: " + date + ", Customer ID: " + customerId + ", Equipment ID: " + equipmentId +
               ", Rental Date: " + rentalDate + ", Return Date: " + returnDate + ", Cost: $" + cost;
    }

    public String toCSV() {
        return rentalId + "," + date + "," + customerId + "," + equipmentId + "," + rentalDate + "," + returnDate + "," + cost;
    }
}

