package administrateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminIHM extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton btnGestionTrajet;
    private JButton btnGestionReduction;
    private JButton btnGenererRapport;

    private JPanel mainPanel;
    private JPanel currentPanel;
    private GenererRapport genererRapportPanel;

    public AdminIHM() {
        setTitle("Admin Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des boutons
        btnGestionTrajet = new JButton("Gestion Trajet");
        btnGestionReduction = new JButton("Gestion Reduction");
        btnGenererRapport = new JButton("Generer Rapport");

        btnGestionTrajet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(new GestionTrajet());
            }
        });

        btnGestionReduction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(new GestionReduction());
            }
        });
        btnGenererRapport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(genererRapportPanel); // Display GenererRapport panel
            }
        });
        genererRapportPanel = new GenererRapport();
        // Panel principal avec un BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Dimensions initiales plus petites

        // Panel pour les boutons avec un FlowLayout aligné au centre
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Ajout des boutons au panel des boutons
        buttonPanel.add(btnGestionTrajet);
        buttonPanel.add(btnGestionReduction);
        buttonPanel.add(btnGenererRapport);

        // Ajout du panel des boutons au nord du panel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Initialisation du panel courant
        currentPanel = new JPanel(new BorderLayout());
        mainPanel.add(currentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setSize(500, 300); // Définir des dimensions initiales plus petites
        setLocationRelativeTo(null); // Centrer la fenêtre
    }

    // Méthode pour afficher un nouveau panel
    private void displayPanel(JPanel panel) {
        currentPanel.removeAll();
        currentPanel.add(panel, BorderLayout.CENTER);
        currentPanel.revalidate();
        currentPanel.repaint();
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminIHM frame = new AdminIHM();
                frame.setVisible(true);
            }
        });
    }
}
