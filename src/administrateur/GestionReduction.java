package administrateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionReduction extends JPanel {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private JTextField txtUsername;
    private JComboBox<String> cmbTypeReduction;
    private JTextField txtCodeReduction;

    public GestionReduction() {
        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();
        JLabel lblTypeReduction = new JLabel("Type de Réduction:");
        cmbTypeReduction = new JComboBox<>();
        cmbTypeReduction.addItem("Etudiant");
        cmbTypeReduction.addItem("Militaire");
        cmbTypeReduction.addItem("Professeur");
        JLabel lblCodeReduction = new JLabel("Code de Réduction:");
        txtCodeReduction = new JTextField();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnSupprimer = new JButton("Supprimer");

        btnAjouter.setBackground(Color.GREEN);
        btnSupprimer.setBackground(Color.RED);

        btnAjouter.addActionListener(e -> ajouterReduction());
        btnSupprimer.addActionListener(e -> supprimerReduction());

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblUsername)
                .addComponent(lblTypeReduction)
                .addComponent(lblCodeReduction));
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtUsername)
                .addComponent(cmbTypeReduction)
                .addComponent(txtCodeReduction)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAjouter)
                        .addComponent(btnSupprimer)));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername)
                .addComponent(txtUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTypeReduction)
                .addComponent(cmbTypeReduction));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblCodeReduction)
                .addComponent(txtCodeReduction));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnAjouter)
                .addComponent(btnSupprimer));
        layout.setVerticalGroup(vGroup);

        add(panel);
    }

    private void ajouterReduction() {
        String username = txtUsername.getText();
        String typeReduction = (String) cmbTypeReduction.getSelectedItem();
        String codeReduction = txtCodeReduction.getText();

        if (username.isEmpty() || codeReduction.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO reduction (username, typeReduction, codeReduction) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, typeReduction);
            statement.setString(3, codeReduction);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Réduction ajoutée avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la réduction.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la réduction : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerReduction() {
        String code = txtCodeReduction.getText();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM reduction WHERE codeReduction = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, code);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Réduction supprimée avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Aucune réduction trouvée pour la suppression !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la réduction : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}