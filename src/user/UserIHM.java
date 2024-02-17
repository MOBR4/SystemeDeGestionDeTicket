package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class UserIHM extends javax.swing.JFrame {

    private JComboBox<String> departComboBox;
    private JComboBox<String> destinationComboBox;
    private JComboBox<String> classeComboBox;
    private JComboBox<String> typeReductionComboBox;
    private JTextField codeReductionField;
	private String loggedInUsername;

    public UserIHM(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        initComponents();
        fetchDestinations();
        fetchDeparts();
        fetchClasses();
        fetchTypeReductions();
    }


	@SuppressWarnings("unchecked")
    private void initComponents() {

        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel6 = new javax.swing.JLabel();

        departComboBox = new javax.swing.JComboBox<>();
        destinationComboBox = new javax.swing.JComboBox<>();
        classeComboBox = new javax.swing.JComboBox<>();
        typeReductionComboBox = new javax.swing.JComboBox<>();
        codeReductionField = new javax.swing.JTextField();

        JButton searchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setText("Recherche de Trajet");

        jLabel2.setText("Départ:");

        jLabel3.setText("Destination:");

        jLabel4.setText("Classe:");

        jLabel5.setText("Type de Réduction:");

        jLabel6.setText("Code Réduction:");

        searchButton.setText("Rechercher");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(destinationComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(32, 32, 32)
                                                                .addComponent(departComboBox, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jLabel6))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(typeReductionComboBox, 0, 120, Short.MAX_VALUE)
                                                                        .addComponent(codeReductionField)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(classeComboBox, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addComponent(searchButton)))
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(departComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(classeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(destinationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(typeReductionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(codeReductionField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String depart = departComboBox.getSelectedItem().toString();
        String destination = destinationComboBox.getSelectedItem().toString();
        String classe = classeComboBox.getSelectedItem().toString();
        String typeReduction = typeReductionComboBox.getSelectedItem().toString();
        String codeReduction = codeReductionField.getText();

        if (!hasReduction(loggedInUsername, typeReduction, codeReduction)) {
            JOptionPane.showMessageDialog(this, "User does not have the selected reduction.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            String sql = "SELECT * FROM trajet WHERE depart = ? AND destination = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, depart);
            pst.setString(2, destination);
            ResultSet rs = pst.executeQuery();

            // Create a list to store the results
            List<String> resultList = new ArrayList<>();

            // Process the results and add them to the list
            while (rs.next()) {
                String trajetDepart = rs.getString("depart");
                String trajetDestination = rs.getString("destination");
                double prix1 = rs.getDouble("prix1");
                double prix2 = rs.getDouble("prix2");
                String time = rs.getTime("time").toString(); // Retrieve time from the database

                double discountedPrice;
                switch (typeReduction) {
                    case "etudiant":
                        discountedPrice = (classe.equals("1ère classe") ? prix1 : prix2) * 0.7; // 30% discount
                        break;
                    case "militaire":
                        discountedPrice = (classe.equals("1ère classe") ? prix1 : prix2) * 0.4; // 60% discount
                        break;
                    case "professeur":
                        discountedPrice = (classe.equals("1ère classe") ? prix1 : prix2) * 0.6; // 40% discount
                        break;
                    default:
                        discountedPrice = (classe.equals("1ère classe") ? prix1 : prix2); // No discount by default
                }
                
                
                String resultString =  trajetDepart + " -> " + trajetDestination +
                        ", Prix " + classe + " classe: " + discountedPrice +  " DH" + 
                        ", Heure de départ: " + time;

                resultList.add(resultString);
            }

            con.close();

            // Display the list of results for the user to select
            Object[] options = resultList.toArray();
            String selectedResult = (String) JOptionPane.showInputDialog(
                    this,
                    "Sélectionnez un trajet:",
                    "Résultats de recherche",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Perform any action based on the selected result
            if (selectedResult != null) {
                // Extract the relevant information from the selected result
                // You can split the string and extract the necessary information to use in your logic
                
                String[] parts = selectedResult.split(",");
                String prixPart = parts[1].trim(); // Extracting the Prix ... classe part
                String heureDepartPart = parts[2].trim(); // Extracting the Heure de départ part

                // Extracting the price from the Prix ... classe part (you may need to adjust this based on your actual format)
                String priceText = prixPart.split(":")[1].trim();
                double price = Double.parseDouble(priceText.substring(0, priceText.length() - 3));

                // Now you can pass the extracted information to the PayerIHM or perform any other necessary action
                PayerIHM payerIHM = new PayerIHM(price, heureDepartPart, loggedInUsername, depart, destination, classe);
                payerIHM.setVisible(true);

                // Close the current UserIHM
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Error while searching trajets: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while searching trajets: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean hasReduction(String username, String typeReduction, String codeReduction) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            String sql = "SELECT * FROM reduction WHERE username = ? AND typeReduction = ? AND codeReduction = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, typeReduction);
            pst.setString(3, codeReduction);
            ResultSet rs = pst.executeQuery();

            boolean hasReduction = rs.next();
            con.close();
            return hasReduction;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "SQL Error while checking reduction: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while checking reduction: " + e.getMessage());
            return false;
        }
    }




	private void fetchDeparts() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT nom FROM ville";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                departComboBox.addItem(rs.getString("nom"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while fetching departs: " + e.getMessage());
        }
    }

    private void fetchDestinations() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT nom FROM ville";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                destinationComboBox.addItem(rs.getString("nom"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while fetching destinations: " + e.getMessage());
        }
    }

    private void fetchClasses() {
        // Add code to fetch classes (1ere, 2eme) from the database if needed
        classeComboBox.addItem("1ere");
        classeComboBox.addItem("2eme");
    }

    private void fetchTypeReductions() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            String sql = "SELECT DISTINCT typeReduction FROM reduction";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                typeReductionComboBox.addItem(rs.getString("typeReduction"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while fetching type reductions: " + e.getMessage());
        }
    }


}
