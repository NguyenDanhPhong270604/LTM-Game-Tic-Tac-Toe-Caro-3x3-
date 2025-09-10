package caro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private JFrame frame;
    private JButton[] buttons = new JButton[9];
    private JLabel statusLabel;
    private JTextArea historyArea;
    private String playerSymbol;
    private String username;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
        initializeLogin();
    }

    private void initializeLogin() {
        JFrame loginFrame = new JFrame("Tic Tac Toe - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Enter your username:", SwingConstants.CENTER);
        JTextField usernameField = new JTextField();
        JButton connectButton = new JButton("Connect");

        connectButton.addActionListener(e -> {
            username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                connectToServer();
                loginFrame.dispose();
            }
        });

        loginFrame.add(label);
        loginFrame.add(usernameField);
        loginFrame.add(connectButton);
        loginFrame.setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(username);

            initializeGUI();
            new Thread(this::listenToServer).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot connect to server");
            System.exit(0);
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Tic Tac Toe - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        statusLabel = new JLabel("Connecting...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setFocusPainted(false);
            int finalI = i;
            buttons[i].addActionListener(e -> makeMove(finalI));
            boardPanel.add(buttons[i]);
        }

        // Panel cho bảng và lịch sử
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(boardPanel);
        
        // Panel lịch sử
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Lịch sử chơi"));
        
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Làm mới lịch sử");
        refreshButton.addActionListener(e -> requestHistory());
        historyPanel.add(refreshButton, BorderLayout.SOUTH);
        
        mainPanel.add(historyPanel);

        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void makeMove(int position) {
        if (buttons[position].getText().isEmpty()) {
            out.println("MOVE:" + position);
        }
    }

    private void requestHistory() {
        out.println("GET_HISTORY");
    }

    private void listenToServer() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Connection lost");
                JOptionPane.showMessageDialog(frame, "Disconnected from server");
                System.exit(0);
            });
        }
    }

    private void handleServerMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message.startsWith("SYMBOL:")) {
                playerSymbol = message.substring(7);
                statusLabel.setText("You are player " + playerSymbol);
            } else if (message.startsWith("MESSAGE:")) {
                statusLabel.setText(message.substring(8));
            } else if (message.startsWith("MOVE:")) {
                String[] parts = message.split(":");
                int position = Integer.parseInt(parts[1]);
                String symbol = parts[2];
                buttons[position].setText(symbol);
                buttons[position].setEnabled(false);
            } else if (message.startsWith("TURN:")) {
                String currentPlayer = message.substring(5);
                if (currentPlayer.equals(playerSymbol)) {
                    statusLabel.setText("Your turn");
                    enableBoard(true);
                } else {
                    statusLabel.setText("Opponent's turn");
                    enableBoard(false);
                }
            } else if (message.startsWith("WIN:")) {
                String winner = message.substring(4);
                if (winner.equals(playerSymbol)) {
                    statusLabel.setText("You win!");
                } else {
                    statusLabel.setText("You lose!");
                }
                enableBoard(false);
            } else if (message.equals("DRAW")) {
                statusLabel.setText("It's a draw!");
                enableBoard(false);
            } else if (message.equals("START")) {
                statusLabel.setText("Game started! Your symbol: " + playerSymbol);
                if (playerSymbol.equals("X")) {
                    enableBoard(true);
                } else {
                    enableBoard(false);
                }
            } else if (message.startsWith("HISTORY:")) {
                String history = message.substring(8);
                historyArea.setText(history);
            }
        });
    }

    private void enableBoard(boolean enable) {
        for (JButton button : buttons) {
            button.setEnabled(enable && button.getText().isEmpty());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}