package com.sample.crud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditStudentDialog extends JDialog {
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField imagePathField;
    private boolean updateStatus;

    public EditStudentDialog(JFrame parent, String studentNumber, String lastName, String firstName, String middleName, String imagePath) {
        super(parent, "Edit Student", true);

        // Create input fields for editing
        lastNameField = new JTextField(lastName);
        firstNameField = new JTextField(firstName);
        middleNameField = new JTextField(middleName);
        imagePathField = new JTextField(imagePath);

        // Create OK and Cancel buttons
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStatus = true;
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStatus = false;
                dispose();
            }
        });

        // Add components to the dialog
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Middle Name:"));
        panel.add(middleNameField);
        panel.add(new JLabel("Image Path:"));
        panel.add(imagePathField);
        panel.add(okButton);
        panel.add(cancelButton);

        // Set the dialog's content pane
        setContentPane(panel);

        // Set the dialog's size and location
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // Make the dialog modal
        setModal(true);
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getMiddleName() {
        return middleNameField.getText();
    }

    public String getImagePath() {
        return imagePathField.getText();
    }

    public boolean getUpdateStatus() {
        return updateStatus;
    }
}