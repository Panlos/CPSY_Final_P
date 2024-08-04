import Final_P.Client;
import Final_P.Equipment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RentalSystem extends JFrame {
    private List<Equipment> equipmentList;
    private List<Client> clientList;
    private JTextArea displayArea;

    public RentalSystem() {
        equipmentList = loadEquipment("data/RentalEquipment.csv");
        clientList = loadClients("data/CustomerInformation.csv");

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
                        equipmentList.add(equipment);
                        saveEquipment("data/RentalEquipment.csv", equipmentList);
                    }
                }
            }
        });

        deleteEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipmentId = JOptionPane.showInputDialog("Enter equipment ID to delete:");
                if (equipmentId != null && !equipmentId.trim().isEmpty()) {
                    equipmentList = equipmentList.stream()
                            .filter(eq -> eq.getId() != Integer.parseInt(equipmentId.trim()))
                            .collect(Collectors.toList());
                    saveEquipment("data/RentalEquipment.csv", equipmentList);
                }
            }
        });

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
                        clientList.add(client);
                        saveClients("data/CustomerInformation.csv", clientList);
                    }
                }
            }
        });

        displayEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Equipment List:\n");
                for (Equipment equipment : equipmentList) {
                    displayArea.append(equipment + "\n");
                }
            }
        });

        displayClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Client List:\n");
                for (Client client : clientList) {
                    displayArea.append(client + "\n");
                }
            }
        });

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

    private List<Equipment> loadEquipment(String fileName) {
        List<Equipment> equipment = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Equipment eq = new Equipment(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4])
                );
                equipment.add(eq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equipment;
    }

    private List<Client> loadClients(String fileName) {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Client client = new Client(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4]
                );
                clients.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private void saveEquipment(String fileName, List<Equipment> equipmentList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("equipment_id,category_id,name,description,daily_rate\n");
            for (Equipment eq : equipmentList) {
                bw.write(eq.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClients(String fileName, List<Client> clientList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("customer_id,last_name,first_name,contact_phone,email\n");
            for (Client client : clientList) {
                bw.write(client.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
