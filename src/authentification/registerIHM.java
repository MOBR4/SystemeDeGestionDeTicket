package Authentification;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.*;

import TrainReservation.welcomeIHM;

public class registerIHM extends javax.swing.JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField telField;

    public registerIHM() {
        initComponents();
    }


    private void initComponents() {

        JLabel jLabel1 = new javax.swing.JLabel();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        emailField = new javax.swing.JTextField();
        telField = new javax.swing.JTextField();
        JButton registerButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("S'inscrire");

        jLabel2.setText("Username:");

        jLabel3.setText("Password:");

        jLabel4.setText("Email:");

        jLabel5.setText("Telephone:");

        registerButton.setText("S'inscrire");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        JButton backButton = new JButton("Retourner");
        backButton.addActionListener(e -> {
            welcomeIHM obj = new welcomeIHM();
            obj.setVisible(true);
            dispose();
        });

        
        
        
        
        
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameField)
                    .addComponent(passwordField)
                    .addComponent(emailField)
                    .addComponent(telField, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(backButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registerButton))
                        .addComponent(jLabel1)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(telField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(registerButton)
                .addGap(27, 27, 27))
        );

        pack();
    }


    
    
    
    
    
    private void registerButtonActionPerformed(ActionEvent evt) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String un = usernameField.getText();
            String pw = new String(passwordField.getPassword());
            String email = emailField.getText();
            String tel = telField.getText();

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ams", "root", "");
            Statement st = con.createStatement();
            String sql = "INSERT INTO user (username, password, email, tel, typeUser) VALUES ('" + un + "', '" + pw + "', '" + email + "', '" + tel + "', 'user')";
            st.executeUpdate(sql);

            JOptionPane.showMessageDialog(this, "Inscription réussite!");
            loginIHM loginFrame = new loginIHM();
            loginFrame.setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errer lors de l'inscription");
        }
    }

   
}
