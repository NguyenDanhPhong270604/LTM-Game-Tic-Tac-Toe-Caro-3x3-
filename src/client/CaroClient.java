package client;

import shared.Message;
import shared.MessageType;
import shared.GameInfo;
import server.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CaroClient extends JFrame {
    private String username;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private GamePanel gamePanel;
    private JButton challengeButton;
    private JButton historyButton;
    private JButton newGameButton;
    private JLabel statusLabel;
    private JList<String> onlinePlayersList;
    private DefaultListModel<String> onlinePlayersModel;
    private String currentOpponent;
    private char mySymbol;
    
    public CaroClient() {
        setupUI();
        connectToServer();
    }
    
    private void setupUI() {
        setTitle("Caro Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Login dialog
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);
        username = loginDialog.getUsername();
        
        if (username == null) {
            System.exit(0);
        }
        
        setTitle("Caro Game - " + username);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Online players panel
        JPanel onlinePanel = new JPanel(new BorderLayout());
        onlinePanel.setBorder(BorderFactory.createTitledBorder("Người chơi online"));
        onlinePanel.setPreferredSize(new Dimension(200, 300));
        
        onlinePlayersModel = new DefaultListModel<>();
        onlinePlayersList = new JList<>(onlinePlayersModel);
        onlinePlayersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(onlinePlayersList);
        onlinePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel onlineButtonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.addActionListener(e -> requestPlayerList());
        onlineButtonPanel.add(refreshButton);
        
        onlinePanel.add(onlineButtonPanel, BorderLayout.SOUTH);
        
        add(onlinePanel, BorderLayout.EAST);
        
        // Game panel
        gamePanel = new GamePanel(this);
        add(gamePanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        challengeButton = new JButton("Thách đấu");
        historyButton = new JButton("Xem lịch sử");
        newGameButton = new JButton("Game mới");
        statusLabel = new JLabel("Chưa trong trận đấu");
        
        // Style buttons
        styleButton(challengeButton, new Color(0, 100, 0));
        styleButton(historyButton, new Color(70, 130, 180));
        styleButton(newGameButton, new Color(220, 20, 60));
        
        controlPanel.add(challengeButton);
        controlPanel.add(historyButton);
        controlPanel.add(newGameButton);
        controlPanel.add(statusLabel);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        // Event handlers
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                challengeSelectedPlayer();
            }
        });
        
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestHistory();
            }
        });
        
        // Double click to challenge
        onlinePlayersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    challengeSelectedPlayer();
                }
            }
        });
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }
    
    private void startNewGame() {
        // Reset game panel
        gamePanel.forceReset();
        currentOpponent = null;
        statusLabel.setText("Sẵn sàng cho game mới");
        
        // Refresh player list
        requestPlayerList();
        
        System.out.println("Đã chuẩn bị cho game mới");
    }
    
    private void challengeSelectedPlayer() {
        String selectedPlayer = onlinePlayersList.getSelectedValue();
        if (selectedPlayer != null && !selectedPlayer.equals(username) && !selectedPlayer.contains("Không có người chơi nào online")) {
            // Lọc bỏ phần trạng thái trong ngoặc
            String cleanName = selectedPlayer.replaceAll("\\s*\\(.*\\)", "").trim();
            if (!cleanName.equals(username)) {
                if (selectedPlayer.contains("(Đang chơi)")) {
                    JOptionPane.showMessageDialog(this, 
                        cleanName + " đang trong trận đấu. Vui lòng chọn người chơi khác!", 
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    challengePlayer(cleanName);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một người chơi khác để thách đấu", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            
            // Send login message
            sendMessage(new Message(MessageType.LOGIN, username));
            
            // Start listener thread
            new Thread(new ServerListener()).start();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Không thể kết nối đến server: " + e.getMessage(), 
                "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Message message = (Message) input.readObject();
                    processMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(CaroClient.this, 
                    "Mất kết nối với server", "Lỗi", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
    
    private void processMessage(Message message) {
        System.out.println("Client received: " + message.getType());
        
        switch (message.getType()) {
            case LOGIN_SUCCESS:
                handleLoginSuccess((Player) message.getContent());
                break;
            case PLAYER_LIST:
                handlePlayerList((List<String>) message.getContent());
                break;
            case CHALLENGE:
                handleChallenge((String) message.getContent());
                break;
            case CHALLENGE_ACCEPTED:
                handleChallengeAccepted(message.getContent());
                break;
            case CHALLENGE_REJECTED:
                handleChallengeRejected((String) message.getContent());
                break;
            case MOVE:
                handleMove(message.getContent());
                break;
            case GAME_OVER:
                handleGameOver((String) message.getContent());
                break;
            case HISTORY_RESPONSE:
                handleHistoryResponse((List<Player>) message.getContent());
                break;
            case ERROR:
                handleError((String) message.getContent());
                break;
            default:
                System.out.println("Unknown message type: " + message.getType());
        }
    }
    
    private void handleLoginSuccess(Player player) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Đã đăng nhập: " + player.getUsername());
            requestPlayerList();
        });
    }
    
    private void handlePlayerList(List<String> players) {
        SwingUtilities.invokeLater(() -> {
            onlinePlayersModel.clear();
            for (String player : players) {
                String playerName = player.replaceAll("\\s*\\(.*\\)", "").trim();
                if (!playerName.equals(username)) {
                    onlinePlayersModel.addElement(player);
                }
            }
            
            if (onlinePlayersModel.isEmpty()) {
                onlinePlayersModel.addElement("Không có người chơi nào online");
            }
        });
    }
    
    public void challengePlayer(String playerName) {
        currentOpponent = playerName;
        sendMessage(new Message(MessageType.CHALLENGE, playerName));
        statusLabel.setText("Đang gửi lời thách đấu đến " + playerName + "...");
    }
    
    private void handleChallenge(String challenger) {
        System.out.println("Received challenge from: " + challenger);
        
        SwingUtilities.invokeLater(() -> {
            int response = JOptionPane.showConfirmDialog(this, 
                challenger + " muốn thách đấu với bạn. Chấp nhận?", 
                "Lời mời thách đấu", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION) {
                System.out.println("Accepting challenge from: " + challenger);
                currentOpponent = challenger;
                sendMessage(new Message(MessageType.CHALLENGE_ACCEPTED, challenger));
            } else {
                System.out.println("Rejecting challenge from: " + challenger);
                sendMessage(new Message(MessageType.CHALLENGE_REJECTED, challenger));
                statusLabel.setText("Sẵn sàng thách đấu");
            }
        });
    }
    
    private void handleChallengeAccepted(Object content) {
        try {
            if (content instanceof GameInfo) {
                GameInfo gameInfo = (GameInfo) content;
                
                SwingUtilities.invokeLater(() -> {
                    currentOpponent = gameInfo.getOpponent();
                    mySymbol = gameInfo.getSymbol();
                    statusLabel.setText("Đang chơi với " + currentOpponent);
                    
                    gamePanel.startGame(mySymbol == 'X', currentOpponent, mySymbol);
                    gamePanel.updateTurn(mySymbol == 'X');
                    
                    String symbolText = (mySymbol == 'X') ? "X (màu xanh)" : "O (màu đỏ)";
                    JOptionPane.showMessageDialog(CaroClient.this, 
                        "Trận đấu bắt đầu với " + currentOpponent + ". Bạn là " + symbolText, 
                        "Trận đấu bắt đầu", JOptionPane.INFORMATION_MESSAGE);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi bắt đầu trận đấu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleChallengeRejected(String opponent) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                opponent + " đã từ chối lời thách đấu", "Từ chối thách đấu", 
                JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Sẵn sàng thách đấu");
        });
    }
    
    private void handleMove(Object content) {
        try {
            if (content instanceof Object[]) {
                Object[] moveData = (Object[]) content;
                
                if (moveData.length >= 2) {
                    int[] move = (int[]) moveData[0];
                    char symbol = (Character) moveData[1];
                    
                    SwingUtilities.invokeLater(() -> {
                        gamePanel.opponentMove(move[0], move[1], symbol);
                        gamePanel.updateTurn(true);
                        statusLabel.setText("Lượt của bạn - Đang chơi với " + currentOpponent);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xử lý nước đi: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleGameOver(String result) {
        System.out.println("Game over: " + result);
        
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                result, 
                "Kết quả", 
                JOptionPane.INFORMATION_MESSAGE);
            
            statusLabel.setText(result);
            gamePanel.gameOver(result);
            requestPlayerList();
        });
    }
    
    private void handleHistoryResponse(List<Player> players) {
        SwingUtilities.invokeLater(() -> {
            PlayerHistory historyDialog = new PlayerHistory(this, players);
            historyDialog.setVisible(true);
        });
    }
    
    private void handleError(String errorMessage) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Sẵn sàng thách đấu");
        });
    }
    
    public void sendMove(int row, int col) {
        int[] move = {row, col};
        sendMessage(new Message(MessageType.MOVE, move));
        gamePanel.updateTurn(false);
        statusLabel.setText("Đợi đối thủ - Đang chơi với " + currentOpponent);
    }
    
    public char getMySymbol() {
        return mySymbol;
    }
    
    public void requestPlayerList() {
        sendMessage(new Message(MessageType.PLAYER_LIST, null));
    }
    
    private void requestHistory() {
        sendMessage(new Message(MessageType.HISTORY_REQUEST, null));
    }
    
    private void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
            System.out.println("Sent message type: " + message.getType());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi gửi tin nhắn: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CaroClient().setVisible(true);
        });
    }
}