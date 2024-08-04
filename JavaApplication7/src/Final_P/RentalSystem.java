/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Final_P;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author aidanpratt
 */
public class RentalSystem extends JFrame {
    private ArrayList<String> equipmentList;
    private ArrayList<String> clientList;
    private JTextArea displayArea;

    public RentalSystem() {
        equipmentList = new ArrayList<>();
        clientList = new ArrayList<>();
        equipmentList.add("a");
        equipmentList.add("b");
        equipmentList.add("c");
        equipmentList.add("d");
        equipmentList.add("e");
        equipmentList.add("f");
        equipmentList.add("g");
        equipmentList.add("h");
        equipmentList.add("i");

        setTitle("Rental System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JButton addEquipmentButton = new JButton("Add Equipment");
        JButton deleteEquipmentButton = new JButton("Delete Equipment");
        JButton addClientButton = new JButton("Add Client");
        JButton displayEquipmentButton = new JButton("Display All Equipment");
        JButton displayClientsButton = new JButton("Display All Clients");
        JButton rentItemButton = new JButton("Rent Item");

        addEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipment = JOptionPane.showInputDialog("Enter equipment name:");
                if (equipment != null && !equipment.trim().isEmpty()) {
                    equipmentList.add(equipment.trim());
                }
            }
        });

        deleteEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equipment = JOptionPane.showInputDialog("Enter equipment name to delete:");
                if (equipment != null && !equipment.trim().isEmpty()) {
                    if(equipmentList.remove(equipment.trim()) == true){
                    equipmentList.remove(equipment.trim());
                    }
                        else{
                    displayArea.setText("Equipment not found. Unable to delete.");
                    }
                }
                
            }
        });

        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String client = JOptionPane.showInputDialog("Enter client name:");
                if (client != null && !client.trim().isEmpty()) {
                    clientList.add(client.trim());
                }
            }
        });

        displayEquipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Equipment List:\n");
                for (String equipment : equipmentList) {
                    displayArea.append(equipment + "\n");
                }
            }
        });

        displayClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Client List:\n");
                for (String client : clientList) {
                    displayArea.append(client + "\n");
                }
            }
        });

        rentItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String client = JOptionPane.showInputDialog("Enter client name:");
                if (client != null && clientList.contains(client.trim())) {
                    String equipment = JOptionPane.showInputDialog("Enter equipment name to rent:");
                    if (equipment != null && equipmentList.contains(equipment.trim())) {
                        displayArea.setText(client.trim() + " has rented " + equipment.trim());
                    } else {
                        displayArea.setText("Equipment not found.");
                    }
                } else {
                    displayArea.setText("Client not found.");
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
}

