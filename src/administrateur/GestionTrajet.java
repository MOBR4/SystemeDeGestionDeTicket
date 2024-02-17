package administrateur;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class GestionTrajet extends JPanel {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public GestionTrajet() {
        // Panel to hold the components
        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel lblDepart = new JLabel("Depart:");
        JLabel lblDestination = new JLabel("Destination:");
        JLabel lblPrix1Classe = new JLabel("Prix 1ère classe:");
        JLabel lblPrix2Classe = new JLabel("Prix 2ème classe:");
        JLabel lblTime = new JLabel("Heure de départ (HH:MM:SS):");

        JTextField txtDepart = new JTextField();
        JTextField txtDestination = new JTextField();
        JTextField txtPrix1Classe = new JTextField();
        JTextField txtPrix2Classe = new JTextField();
        JTextField txtTime = new JTextField();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        // Définir la couleur de fond des boutons
        btnAjouter.setBackground(Color.GREEN);
        btnModifier.setBackground(Color.YELLOW);
        btnSupprimer.setBackground(Color.RED);

        // Ajouter des action listeners aux boutons
        btnAjouter.addActionListener(e -> ajouterTrajet(txtDepart.getText(), txtDestination.getText(),
                Double.parseDouble(txtPrix1Classe.getText()), Double.parseDouble(txtPrix2Classe.getText()),
                Time.valueOf(txtTime.getText())));

        btnModifier.addActionListener(e -> modifierTrajet(txtDepart.getText(), txtDestination.getText(),
                Double.parseDouble(txtPrix1Classe.getText()), Double.parseDouble(txtPrix2Classe.getText()),
                Time.valueOf(txtTime.getText())));

        btnSupprimer.addActionListener(e -> supprimerTrajet(txtDepart.getText(), txtDestination.getText()));

        // Définir le layout
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblDepart)
                .addComponent(lblDestination)
                .addComponent(lblPrix1Classe)
                .addComponent(lblPrix2Classe)
                .addComponent(lblTime));
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtDepart)
                .addComponent(txtDestination)
                .addComponent(txtPrix1Classe)
                .addComponent(txtPrix2Classe)
                .addComponent(txtTime)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAjouter)
                        .addComponent(btnModifier)
                        .addComponent(btnSupprimer)));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblDepart)
                .addComponent(txtDepart));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblDestination)
                .addComponent(txtDestination));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPrix1Classe)
                .addComponent(txtPrix1Classe));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPrix2Classe)
                .addComponent(txtPrix2Classe));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTime)
                .addComponent(txtTime));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(btnAjouter)
                .addComponent(btnModifier)
                .addComponent(btnSupprimer));
        layout.setVerticalGroup(vGroup);

        add(panel);
    }

    private static void ajouterTrajet(String depart, String destination, double prix1Classe, double prix2Classe, Time time) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO trajet (depart, destination, prix1, prix2, time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, depart);
            statement.setString(2, destination);
            statement.setDouble(3, prix1Classe);
            statement.setDouble(4, prix2Classe);
            statement.setTime(5, time);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Trajet ajouté avec succès !");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du trajet : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void modifierTrajet(String depart, String destination, double prix1Classe, double prix2Classe, Time time) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE trajet SET prix1 = ?, prix2 = ?, time = ? WHERE depart = ? AND destination = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, prix1Classe);
            statement.setDouble(2, prix2Classe);
            statement.setTime(3, time);
            statement.setString(4, depart);
            statement.setString(5, destination);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Trajet modifié avec succès !");
            } else {
                JOptionPane.showMessageDialog(null, "Aucun trajet trouvé pour la modification !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification du trajet : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void supprimerTrajet(String depart, String destination) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM trajet WHERE depart = ? AND destination = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, depart);
            statement.setString(2, destination);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Trajet supprimé avec succès !");
            } else {
                JOptionPane.showMessageDialog(null, "Aucun trajet trouvé pour la suppression !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du trajet : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
