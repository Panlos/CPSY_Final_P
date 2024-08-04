/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_P;

/**
 *
 * @author aidanpratt
 */
public class Equipment {
    private int id;
    private int categoryId;
    private String name;
    private String description;
    private double dailyRate;

    public Equipment(int id, int categoryId, String name, String description, double dailyRate) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.dailyRate = dailyRate;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Description: " + description + ", Daily Rate: $" + dailyRate;
    }

    public String toCSV() {
        return id + "," + categoryId + "," + name + "," + description + "," + dailyRate;
    }
}

