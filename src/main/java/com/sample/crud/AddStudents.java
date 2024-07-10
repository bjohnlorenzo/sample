/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sample.crud;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 *
 * @author Gab
 */
public class AddStudents extends javax.swing.JFrame {

     String dataConn = "jdbc:mysql://localhost:3306/login_db";
        String user = "safdar";
        String password = "123456";
        private JTable jTable1;
private JScrollPane jScrollPane1;


public void registerStudent(String student_number, String last_name, String first_name, String middle_name, String image_path) {
    String sql = "INSERT INTO students (student_number, last_name, first_name, middle_name, image_path) VALUES (?, ?, ?, ?, ?)";

    try (Connection sqlConn = DriverManager.getConnection(dataConn, user, password);
         PreparedStatement pst = sqlConn.prepareStatement(sql)) {

        // Set the parameters
        pst.setString(1, student_number);
        pst.setString(2, last_name);
        pst.setString(3, first_name);
        pst.setString(4, middle_name);
        pst.setString(5, image_path);

        // Execute the update
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student registered successfully!");
            populateTable();
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
     private void editStudent(String studentNumber) {
    Connection sqlConn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        sqlConn = DriverManager.getConnection(dataConn, user, password);
        pst = sqlConn.prepareStatement("SELECT * FROM students WHERE student_number = ?");
        pst.setString(1, studentNumber);

        rs = pst.executeQuery();
        if (rs.next()) {
            // Display the student details in a dialog or a form
            String lastName = rs.getString("last_name");
            String firstName = rs.getString("first_name");
            String middleName = rs.getString("middle_name");
            String imagePath = rs.getString("image_path");

            // Create a dialog box with input fields for editing
            EditStudentDialog dialog = new EditStudentDialog(this, studentNumber, lastName, firstName, middleName, imagePath);
            dialog.setVisible(true);

            // Update the student details if the dialog is closed with OK
            if (dialog.getUpdateStatus()) {
                String newLastName = dialog.getLastName();
                String newFirstName = dialog.getFirstName();
                String newMiddleName = dialog.getMiddleName();
                String newImagePath = dialog.getImagePath();

                // Update the student details in the database
                updateStudent(studentNumber, newLastName, newFirstName, newMiddleName, newImagePath);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error editing student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (sqlConn != null) {
            try {
                sqlConn.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
     private void updateStudent(String studentNumber, String lastName, String firstName, String middleName, String imagePath) {
    Connection sqlConn = null;
    PreparedStatement pst = null;

    try {
        sqlConn = DriverManager.getConnection(dataConn, user, password);
        pst = sqlConn.prepareStatement("UPDATE students SET last_name = ?, first_name = ?, middle_name = ?, image_path = ? WHERE student_number = ?");
        pst.setString(1, lastName);
        pst.setString(2, firstName);
        pst.setString(3, middleName);
        pst.setString(4, imagePath);
        pst.setString(5, studentNumber);
       

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student updated successfully!");
        }
        populateTable();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    } finally {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (sqlConn != null) {
            try {
                sqlConn.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
     private void deleteStudent(String studentNumber) {
    Connection sqlConn = null;
    PreparedStatement pst = null;

    try {
        sqlConn = DriverManager.getConnection(dataConn, user, password);
        pst = sqlConn.prepareStatement("DELETE FROM students WHERE student_number =?");
        pst.setString(1, studentNumber);

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (pst!= null) {
            try {
                pst.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (sqlConn!= null) {
            try {
                sqlConn.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
    public AddStudents() {
        initComponents();
        this.setResizable(false);
         jTable1 = new JTable();
    jScrollPane1 = new JScrollPane(jTable1);

    jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 540, 440));
    populateJList();
    populateTable();
    populateCheckBoxPanel();
     // Add this line
    
     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Fname = new javax.swing.JTextField();
        Mname = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        attach = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Mname2 = new javax.swing.JTextField();
        jPanel5 = new JPanel();
        jPanel1 = new JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1290, 820));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(128, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 224, 36));
        jLabel1.setText("ATTENDIFY");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(492, 492, 492)
                .addComponent(jLabel1)
                .addContainerGap(458, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 137));

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 3, true), "FILL UP", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell Condensed", 1, 24), new java.awt.Color(255, 51, 51))); // NOI18N
        jPanel3.setToolTipText("");
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(128, 0, 0));
        jButton1.setFont(new java.awt.Font("Arial Unicode MS", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(240, 224, 36));
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 160, 60));

        jButton2.setBackground(new java.awt.Color(128, 0, 0));
        jButton2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(240, 224, 36));
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, -1, -1));

        jButton3.setBackground(new java.awt.Color(128, 0, 0));
        jButton3.setFont(new java.awt.Font("Arial Unicode MS", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(240, 224, 36));
        jButton3.setText("RESET");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 160, 60));
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 432, 43, -1));

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        jLabel13.setText("Last Name");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, 39));

        LName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LNameActionPerformed(evt);
            }
        });
        jPanel3.add(LName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 165, -1));

        jLabel14.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        jLabel14.setText("First Name");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, 39));

        jLabel15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        jLabel15.setText("Student number");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 39));

        Fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FnameActionPerformed(evt);
            }
        });
        jPanel3.add(Fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 165, -1));

        Mname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnameActionPerformed(evt);
            }
        });
        jPanel3.add(Mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 165, -1));

        jButton4.setBackground(new java.awt.Color(128, 0, 0));
        jButton4.setFont(new java.awt.Font("Arial Unicode MS", 1, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(240, 224, 36));
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, 160, 60));

        jButton5.setBackground(new java.awt.Color(128, 0, 0));
        jButton5.setFont(new java.awt.Font("Arial Unicode MS", 1, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(240, 224, 36));
        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 160, 60));

        jButton6.setBackground(new java.awt.Color(128, 0, 0));
        jButton6.setForeground(new java.awt.Color(240, 224, 36));
        jButton6.setText("Attach ");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, -1, -1));

        attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachActionPerformed(evt);
            }
        });
        jPanel3.add(attach, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 165, -1));

        jLabel2.setText("Insert only jpg.,png.,jpeg.");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 450, -1, -1));

        jLabel16.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        jLabel16.setText("Class");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, 39));

        jLabel17.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 18)); // NOI18N
        jLabel17.setText("Middle Name");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, 39));

        Mname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Mname2ActionPerformed(evt);
            }
        });
        jPanel3.add(Mname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 165, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 1270, 560));

        jPanel5.setBackground(new java.awt.Color(128, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 700, 1270, 120));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1696, 143, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Dashboard db = new Dashboard();
        db.setVisible(true);
        db.pack();
        db.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        Mname.setText("");
    LName.setText("");
    Fname.setText("");
    Mname.setText("");
    attach.setText("");
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void LNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LNameActionPerformed

    private void FnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FnameActionPerformed

    private void MnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MnameActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         // delete
        String studentNumber = JOptionPane.showInputDialog(this, "Enter the student number to delete:");

    if (studentNumber!= null &&!studentNumber.isEmpty()) {
        deleteStudent(studentNumber);
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         // Edit student
     // Prompt for student number input
    String studentNumber = JOptionPane.showInputDialog(this, "Enter the student number:");

    if (studentNumber != null && !studentNumber.isEmpty()) {
        editStudent(studentNumber);
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_attachActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename=f.getAbsolutePath();
        attach.setText(filename);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String student_number = Mname.getText();
    String last_name = LName.getText();
    String first_name = Fname.getText();
    String middle_name = Mname.getText();
    String image_path = attach.getText();

    registerStudent(student_number, last_name, first_name, middle_name, image_path);
    populateTable();
   
    Mname.setText("");
    LName.setText("");
    Fname.setText("");
    Mname.setText("");
    attach.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed
private void populateTable() {
    Connection sqlConn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        sqlConn = DriverManager.getConnection(dataConn, user, password);
        pst = sqlConn.prepareStatement("SELECT * FROM students");

        rs = pst.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        DefaultTableModel tableModel = new DefaultTableModel();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(metaData.getColumnLabel(i));
        }

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                if (i == 5) { // Assuming the image path is in the 5th column
                    String imagePath = rs.getString(i);
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    row[i - 1] = imageIcon;
                } else {
                    row[i - 1] = rs.getObject(i);
                }
            }
            tableModel.addRow(row);
        }

        jTable1.setModel(tableModel);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer()); // Set the renderer for the image column

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error populating table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (rs!= null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (pst!= null) {
            try {
                pst.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
        if (sqlConn!= null) {
            try {
                sqlConn.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}

private void populateJList() {
    try (Connection conn = DriverManager.getConnection(dataConn, user, password);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT class_name FROM classes")) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        while (rs.next()) {
            String className = rs.getString("class_name");
            listModel.addElement(className);
        }
        
    } catch (SQLException e) {
        System.out.println("Error populating JList: " + e.getMessage());
    }
}
private JPanel getYourContainer() {
    // Replace jPanel3 with the actual container component where you want to add the checkbox panel
    JPanel yourContainer = jPanel3;
    yourContainer.setLayout(new BorderLayout()); // Set the layout manager to BorderLayout
    return yourContainer;
}

private void populateCheckBoxPanel() {
    try (Connection conn = DriverManager.getConnection(dataConn, user, password);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT class_name FROM classes")) {
        JPanel checkBoxPanel = new JPanel();
       checkBoxPanel.setPreferredSize(new Dimension(50, 50));
        
        while (rs.next()) {
            String className = rs.getString("class_name");
            JCheckBox checkBox = new JCheckBox(className);
            checkBoxPanel.add(checkBox);
        }
        
        // Add the panel to your GUI
        JPanel yourContainer = getYourContainer();
        yourContainer.add(checkBoxPanel, BorderLayout.CENTER); // Add the checkBoxPanel to the center of the yourContainer
    } catch (SQLException e) {
        System.out.println("Error populating checkbox panel: " + e.getMessage());
    }
}

    private void Mname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Mname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Mname2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddStudents().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Fname;
    private javax.swing.JTextField LName;
    private javax.swing.JTextField Mname;
    private javax.swing.JTextField Mname2;
    private javax.swing.JTextField attach;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel5;
    // End of variables declaration//GEN-END:variables

    private static class imagePath {

        private static void setText(String path) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public imagePath() {
        }
    }
}
