package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String username;
    private String password;

    public LoginDialog(Frame parent) {
        super(parent, "Đăng nhập", true);
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(280, 160); // tăng chút để vừa 2 ô nhập
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel chứa Username + Password
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 25));
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 25));
        inputPanel.add(passwordField);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginButton = new JButton("Đăng nhập");
        buttonPanel.add(loginButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText().trim();
                password = new String(passwordField.getPassword()).trim();
                if (!username.isEmpty() && !password.isEmpty()) {
                    dispose(); // đóng dialog nếu hợp lệ
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Enter key sẽ tự động bấm nút đăng nhập
        getRootPane().setDefaultButton(loginButton);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
