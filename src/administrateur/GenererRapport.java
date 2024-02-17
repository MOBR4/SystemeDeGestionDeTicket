package administrateur;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GenererRapport extends JPanel {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public GenererRapport() {
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblNombreClients = new JLabel("Nombre de clients:");
        JLabel lblNombreTickets = new JLabel("Nombre de tickets:");
        JLabel lblTotalVentes = new JLabel("Total des ventes:");

        JTextField txtNombreClients = new JTextField();
        JTextField txtNombreTickets = new JTextField();
        JTextField txtTotalVentes = new JTextField();

        txtNombreClients.setEditable(false);
        txtNombreTickets.setEditable(false);
        txtTotalVentes.setEditable(false);

        add(lblNombreClients);
        add(txtNombreClients);
        add(lblNombreTickets);
        add(txtNombreTickets);
        add(lblTotalVentes);
        add(txtTotalVentes);

        genererRapport(txtNombreClients, txtNombreTickets, txtTotalVentes);
    }

    private void genererRapport(JTextField txtNombreClients, JTextField txtNombreTickets, JTextField txtTotalVentes) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Nombre de clients
            String sqlNombreClients = "SELECT COUNT(*) FROM user WHERE typeUser = 'user'";
            PreparedStatement stmtNombreClients = connection.prepareStatement(sqlNombreClients);
            ResultSet rsNombreClients = stmtNombreClients.executeQuery();
            if (rsNombreClients.next()) {
                int nombreClients = rsNombreClients.getInt(1);
                txtNombreClients.setText(String.valueOf(nombreClients));
            }

            // Nombre de tickets
            String sqlNombreTickets = "SELECT COUNT(*) FROM ticket";
            PreparedStatement stmtNombreTickets = connection.prepareStatement(sqlNombreTickets);
            ResultSet rsNombreTickets = stmtNombreTickets.executeQuery();
            if (rsNombreTickets.next()) {
                int nombreTickets = rsNombreTickets.getInt(1);
                txtNombreTickets.setText(String.valueOf(nombreTickets));
            }

            // Total des ventes
            String sqlTotalVentes = "SELECT SUM(prix) FROM ticket";
            PreparedStatement stmtTotalVentes = connection.prepareStatement(sqlTotalVentes);
            ResultSet rsTotalVentes = stmtTotalVentes.executeQuery();
            if (rsTotalVentes.next()) {
                double totalVentes = rsTotalVentes.getDouble(1);
                txtTotalVentes.setText(String.valueOf(totalVentes));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la génération du rapport : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}
