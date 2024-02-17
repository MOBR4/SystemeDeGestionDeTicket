package user;

import javax.swing.*;
import java.awt.*;

public class PrintIHM extends javax.swing.JFrame {

    private JLabel titleLabel;
    private JTextArea ticketInfoTextArea;

    private String loggedInUsername;
    private String depart;
    private String destination;
    private double totalPrice;
    private String classe;

    public PrintIHM(String loggedInUsername, String depart, String destination, double totalPrice, String classe) {
        this.loggedInUsername = loggedInUsername;
        this.depart = depart;
        this.destination = destination;
        this.totalPrice = totalPrice;
        this.classe = classe;
        initComponents();
        updateUI();
    }

    private void initComponents() {
        titleLabel = new JLabel();
        ticketInfoTextArea = new JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204)); // Dark Blue
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Ticket De Train");

        ticketInfoTextArea.setEditable(false);
        ticketInfoTextArea.setBackground(Color.WHITE);
        ticketInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        ticketInfoTextArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(ticketInfoTextArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollPane)
        );
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(titleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane);
        layout.setVerticalGroup(vGroup);

        pack(); // Pack the frame
        setSize(400, 300); // Set a larger window size
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void updateUI() {
        String ticketInfo = "Username: " + loggedInUsername + "\n" +
                "Depart: " + depart + "\n" +
                "Destination: " + destination + "\n" +
                "Class: " + classe + "\n" +
                "Prix Total: " + totalPrice + " MAD";

        ticketInfoTextArea.setText(ticketInfo);
    }

   
}
