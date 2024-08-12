import Final_P.Client;
import Final_P.Equipment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentalSystem extends JFrame {
    private List<Equipment> equipmentList;
    private List<Client> clientList;
    private JTextArea displayArea;

    public RentalSystem() {
        equipmentList = loadEquipment();  // Load from database
        clientList = loadClients();       // Load from database

        setTitle("Rental System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JButton addEquipmentButton = new JButton("Add Equipment");
        JButton deleteEquipmentButton = new JButton("Delete Equipment");
        JButton addClientButton = new JButton("Add Client");
        JButton displayEquipmentButton = new JButton("Display All Equipment");
        JButton displayClientsButton = new JButton("Display All Clients");
        JButton rentItemButton = new JButton("Rent Item");

        // Add Equipment Button Action
        addEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipmentDetails = JOptionPane.showInputDialog("Enter equipment details (id,category_id,name,description,daily_rate):");
                if (equipmentDetails != null && !equipmentDetails.trim().isEmpty()) {
                    String[] parts = equipmentDetails.split(",");
                    if (parts.length == 5) {
                        Equipment equipment = new Equipment(
                                Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                parts[2],
                                parts[3],
                                Double.parseDouble(parts[4])
                        );
                        addEquipment(equipment);
                        equipmentList.add(equipment);
                    }
                }
            }
        });

        // Delete Equipment Button Action
        deleteEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipmentId = JOptionPane.showInputDialog("Enter equipment ID to delete:");
                if (equipmentId != null && !equipmentId.trim().isEmpty()) {
                    deleteEquipment(Integer.parseInt(equipmentId.trim()));
                    equipmentList = equipmentList.stream()
                            .filter(eq -> eq.getId() != Integer.parseInt(equipmentId.trim()))
                            .collect(Collectors.toList());
                }
            }
        });

        // Add Client Button Action
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientDetails = JOptionPane.showInputDialog("Enter client details (id,last_name,first_name,contact_phone,email):");
                if (clientDetails != null && !clientDetails.trim().isEmpty()) {
                    String[] parts = clientDetails.split(",");
                    if (parts.length == 5) {
                        Client client = new Client(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4]
                        );
                        addClient(client);
                        clientList.add(client);
                    }
                }
            }
        });

        // Display All Equipment Button Action
        displayEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Equipment List:\n");
                for (Equipment equipment : equipmentList) {
                    displayArea.append(equipment + "\n");
                }
            }
        });

        // Display All Clients Button Action
        displayClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Client List:\n");
                for (Client client : clientList) {
                    displayArea.append(client + "\n");
                }
            }
        });

        // Rent Item Button Action
        rentItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientId = JOptionPane.showInputDialog("Enter client ID:");
                if (clientId != null && !clientId.trim().isEmpty()) {
                    String equipmentId = JOptionPane.showInputDialog("Enter equipment ID to rent:");
                    if (equipmentId != null && !equipmentId.trim().isEmpty()) {
                        Client client = clientList.stream()
                                .filter(c -> c.getId() == Integer.parseInt(clientId.trim()))
                                .findFirst().orElse(null);
                        Equipment equipment = equipmentList.stream()
                                .filter(eq -> eq.getId() == Integer.parseInt(equipmentId.trim()))
                                .findFirst().orElse(null);
                        if (client != null && equipment != null) {
                            displayArea.setText(client.getFirstName() + " " + client.getLastName() + " has rented " + equipment.getName());
                            rentEquipment(client.getId(), equipment.getId());
                        } else {
                            displayArea.setText("Client or equipment not found.");
                        }
                    }
                }
            }
        });

        panel.add(addEquipmentButton);
        panel.add(deleteEquipmentButton);
        panel.add(addClientButton);
        panel.add(displayEquipmentButton);
        panel.add(displayClientsButton);
        panel.add(rentItemButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Establish database connection
    private Connection connectToDatabase() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/rentalsystem";
            String username = "root";  // Replace with your DB username
            String password = "password";  // Replace with your DB password

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Load Equipment from Database
    private List<Equipment> loadEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();
        String query = "SELECT equipmentID, categoryID, name, description, dailyRate FROM Rental_Equipment";
        try (Connection connection = connectToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Equipment equipment = new Equipment(
                        resultSet.getInt("equipmentID"),
                        resultSet.getInt("categoryID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("dailyRate")
                );
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }

    // Load Clients from Database
    private List<Client> loadClients() {
        List<Client> clientList = new ArrayList<>();
        String query = "SELECT customerID, lastName, firstName, contactPhone, email FROM Customer_Information";
        try (Connection connection = connectToDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getInt("customerID"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("contactPhone"),
                        resultSet.getString("email")
                );
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    // Add Equipment to Database
    private void addEquipment(Equipment equipment) {
        String query = "INSERT INTO Rental_Equipment (equipmentID, categoryID, name, description, dailyRate) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, equipment.getId());
            preparedStatement.setInt(2, equipment.getCategoryId());
            preparedStatement.setString(3, equipment.getName());
            preparedStatement.setString(4, equipment.getDescription());
            preparedStatement.setDouble(5, equipment.getDailyRate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Client to Database
    private void addClient(Client client) {
        String query = "INSERT INTO Customer_Information (customerID, lastName, firstName, contactPhone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getFirstName());
            preparedStatement.setString(4, client.getContactPhone());
            preparedStatement.setString(5, client.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Equipment from Database
    private void deleteEquipment(int equipmentId) {
        String query = "DELETE FROM Rental_Equipment WHERE equipmentID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, equipmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rent Equipment (Just an example, assuming a rental table exists)
    private void rentEquipment(int clientId, int equipmentId) {
        String query = "INSERT INTO Rental_Transactions (customerID, equipmentID, rentalDate) VALUES (?, ?, NOW())";
        try (Connection connection = connectToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, equipmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
