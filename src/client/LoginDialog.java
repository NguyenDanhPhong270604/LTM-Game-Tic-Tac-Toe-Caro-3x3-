package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JButton loginButton;
    private String username;
    
    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginButton = new JButton("Đăng nhập");
        buttonPanel.add(loginButton);
        
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, 
                        "Please enter a username", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Enter key to login
        getRootPane().setDefaultButton(loginButton);
    }
    
    public String getUsername() {
        return username;
    }
}