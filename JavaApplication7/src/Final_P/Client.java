/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_P;

/**
 *
 * @author aidanpratt
 */
public class Client {
    private int id;
    private String lastName;
    private String firstName;
    private String contactPhone;
    private String email;

    public Client(int id, String lastName, String firstName, String contactPhone, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactPhone = contactPhone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + firstName + " " + lastName + ", Contact: " + contactPhone + ", Email: " + email;
    }

    public String toCSV() {
        return id + "," + lastName + "," + firstName + "," + contactPhone + "," + email;
    }
}

