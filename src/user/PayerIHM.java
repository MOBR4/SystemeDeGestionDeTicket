package user;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayerIHM extends javax.swing.JFrame {

    private JButton retournerButton;
    private JLabel heureDepartLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel prixLabel;
    private JButton payerButton;

    private double totalPrice;
    private String departureTime;
    private String loggedInUsername;
    private String depart;
    private String destination;
    private String classe;

    public PayerIHM(double totalPrice, String departureTime, String loggedInUsername, String depart, String destination, String classe) {
        this.totalPrice = totalPrice;
        this.departureTime = departureTime;
        this.loggedInUsername = loggedInUsername;
        this.depart = depart;
        this.destination = destination;
        this.classe = classe;
        initComponents();
        updateUI();
    }

    private void updateUI() {
        prixLabel.setText("Prix à payer : " + totalPrice + " MAD");
        heureDepartLabel.setText(departureTime);

        payerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verifyCardAndBalance()) {
                    // Implement the logic for payment confirmation here
                    JOptionPane.showMessageDialog(PayerIHM.this, "Payment avec succés!");
                    PrintIHM printIHM = new PrintIHM(loggedInUsername, depart, destination, totalPrice, classe);
                    printIHM.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PayerIHM.this, "Payement erreur, verifier vos informations ou balance", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        // Add ActionListener to "Retourner" button
        retournerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserIHM userIHM = new UserIHM(loggedInUsername);
                userIHM.setVisible(true);

                dispose();

                revalidate();
                repaint();
            }
        });
    }

    private void initComponents() {
        retournerButton = new JButton("Retourner");
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        prixLabel = new JLabel();
        heureDepartLabel = new JLabel();
        payerButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setText("Paiement");

        jLabel2.setText("Veuillez confirmer votre paiement pour le trajet sélectionné.");

        prixLabel.setFont(new java.awt.Font("Tahoma", 0, 14));

        heureDepartLabel.setFont(new java.awt.Font("Tahoma", 0, 14));

        payerButton.setText("Payer");
        payerButton.setPreferredSize(new java.awt.Dimension(100, 40));
        retournerButton.setPreferredSize(new java.awt.Dimension(100, 40));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addComponent(prixLabel)
                .addComponent(heureDepartLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(payerButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(retournerButton)
                )
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prixLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(heureDepartLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(payerButton)
                        .addComponent(retournerButton)
                );
        layout.setVerticalGroup(vGroup);

        pack(); // Pack the frame
    }
    private boolean verifyCardAndBalance() {
        String[] cardInformation = promptForCardInformation();

        if (cardInformation == null) {
            // User canceled the input
            return false;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");

            // Check card legitimacy and balance
            String cardVerificationQuery = "SELECT * FROM compte WHERE numero_carte = ? AND date_expiration = ? AND cvv = ?";
            PreparedStatement cardVerificationStmt = con.prepareStatement(cardVerificationQuery);
            cardVerificationStmt.setString(1, cardInformation[0]); // cardNumber
            cardVerificationStmt.setString(2, cardInformation[1]); // expirationDate
            cardVerificationStmt.setString(3, cardInformation[2]); // cvv
            ResultSet cardVerificationResult = cardVerificationStmt.executeQuery();

            if (cardVerificationResult.next()) {
                double cardBalance = cardVerificationResult.getDouble("solde");
                if (cardBalance >= totalPrice) {
                    // Deduct the amount from the card balance
                    double newBalance = cardBalance - totalPrice;

                    // Update the card balance in the compte table
                    String updateBalanceQuery = "UPDATE compte SET solde = ? WHERE numero_carte = ?";
                    PreparedStatement updateBalanceStmt = con.prepareStatement(updateBalanceQuery);
                    updateBalanceStmt.setDouble(1, newBalance);
                    updateBalanceStmt.setString(2, cardInformation[0]); // cardNumber
                    updateBalanceStmt.executeUpdate();
                    
                    
                    String insertTicketQuery = "INSERT INTO ticket (username, depart, destination, prix, classe) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertTicketStmt = con.prepareStatement(insertTicketQuery);
                    insertTicketStmt.setString(1, loggedInUsername);
                    insertTicketStmt.setString(2, depart); // Replace with the actual departure location
                    insertTicketStmt.setString(3, destination); // Replace with the actual destination location
                    insertTicketStmt.setDouble(4, totalPrice);
                    insertTicketStmt.setString(5, "ClasseType"); // Replace with the actual class type
                    insertTicketStmt.executeUpdate();
                    con.close();
                    return true;
                } else {
                    con.close();
                    JOptionPane.showMessageDialog(this, "Balance Insufisante.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // balance Insuffisant
                }
            } else {
                con.close();
                JOptionPane.showMessageDialog(this, "Carte Invalide", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de verification: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }



    
    private String[] promptForCardInformation() {
        JPanel panel = new JPanel();
        JTextField cardNumberField = new JTextField(10);
        JTextField expirationDateField = new JTextField(5);
        JTextField cvvField = new JTextField(3);

        panel.add(new JLabel("Numero de carte:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Date d'Expiration (MM/YY):"));
        panel.add(expirationDateField);
        panel.add(new JLabel("CVV:"));
        panel.add(cvvField);
        setSize(500,500);

        int result = JOptionPane.showConfirmDialog(null, panel, "Entrer les informations de la carte: ",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String expirationDate = expirationDateField.getText();
            String cvv = cvvField.getText();

            return new String[]{cardNumber, expirationDate, cvv};
        } else {
            return null;
        }
    }



   
}
