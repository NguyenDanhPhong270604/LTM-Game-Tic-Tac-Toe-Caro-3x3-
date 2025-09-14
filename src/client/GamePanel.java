package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private char[][] board;
    private CaroClient client;
    private boolean myTurn;
    private String opponent;
    private JButton rematchButton;
    private JButton resetButton;
    private Cell[][] cells;
    private char mySymbol;
    private JLabel gameStatusLabel;
    
    public GamePanel(CaroClient client) {
        this.client = client;
        this.board = new char[3][3];
        this.cells = new Cell[3][3];
        initializeBoard();
        setupUI();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));
        setBackground(Color.WHITE);
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("CỜ CARO 3x3", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        
        // Game status label
        gameStatusLabel = new JLabel("Chờ bắt đầu game...", SwingConstants.CENTER);
        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gameStatusLabel.setForeground(Color.DARK_GRAY);
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(gameStatusLabel, BorderLayout.SOUTH);
        add(titlePanel, BorderLayout.NORTH);
        
        // Game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 2, 2));
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setBackground(Color.BLACK);
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell(i, j);
                boardPanel.add(cells[i][j]);
            }
        }
        
        add(boardPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // Rematch button
        rematchButton = new JButton("Thách đấu lại");
        rematchButton.setVisible(false);
        rematchButton.setBackground(new Color(0, 100, 0));
        rematchButton.setForeground(Color.WHITE);
        rematchButton.setFont(new Font("Arial", Font.BOLD, 12));
        rematchButton.addActionListener(e -> {
            if (opponent != null) {
                client.challengePlayer(opponent);
            }
        });
        
        // Reset button
        resetButton = new JButton("Làm mới bàn cờ");
        resetButton.setVisible(false);
        resetButton.setBackground(new Color(70, 130, 180));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 12));
        resetButton.addActionListener(e -> {
            resetGame();
            client.requestPlayerList(); // Yêu cầu cập nhật danh sách
        });
        
        buttonPanel.add(rematchButton);
        buttonPanel.add(resetButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private class Cell extends JPanel {
        private int row;
        private int col;
        
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            setPreferredSize(new Dimension(100, 100));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (myTurn && board[row][col] == ' ') {
                        makeMove(row, col);
                    }
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (myTurn && board[row][col] == ' ') {
                        setBackground(new Color(240, 240, 240)); // Highlight khi hover
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.WHITE);
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (board[row][col] != ' ') {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (board[row][col] == 'X') {
                    g2d.setColor(new Color(0, 128, 0)); // X màu xanh lá đậm
                    g2d.setStroke(new BasicStroke(5));
                    int padding = 15;
                    g2d.drawLine(padding, padding, getWidth() - padding, getHeight() - padding);
                    g2d.drawLine(getWidth() - padding, padding, padding, getHeight() - padding);
                } else {
                    g2d.setColor(Color.RED); // O màu đỏ
                    g2d.setStroke(new BasicStroke(5));
                    int padding = 15;
                    g2d.drawOval(padding, padding, getWidth() - 2 * padding, getHeight() - 2 * padding);
                }
            }
        }
    }
    
    public void makeMove(int row, int col) {
        if (myTurn && board[row][col] == ' ') {
            board[row][col] = mySymbol;
            cells[row][col].repaint();
            System.out.println("You placed " + mySymbol + " at (" + row + "," + col + ")");
            client.sendMove(row, col);
        }
    }
    
    public void opponentMove(int row, int col, char symbol) {
        board[row][col] = symbol;
        cells[row][col].repaint();
        System.out.println("Opponent placed " + symbol + " at (" + row + "," + col + ")");
    }
    
    public void startGame(boolean isFirst, String opponent, char mySymbol) {
        this.opponent = opponent;
        this.mySymbol = mySymbol;
        initializeBoard();
        myTurn = isFirst;
        
        // Repaint all cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].repaint();
            }
        }
        
        // Update UI
        rematchButton.setVisible(false);
        resetButton.setVisible(false);
        
        String turnText = myTurn ? "Lượt của bạn" : "Đợi đối thủ";
        gameStatusLabel.setText("Đang chơi với " + opponent + " - " + turnText);
        gameStatusLabel.setForeground(Color.BLUE);
    }
    
    public void gameOver(String result) {
        myTurn = false;
        rematchButton.setVisible(true);
        resetButton.setVisible(true);
        gameStatusLabel.setText(result);
        gameStatusLabel.setForeground(result.contains("thắng") ? new Color(0, 100, 0) : 
                                    result.contains("thua") ? Color.RED : Color.ORANGE);
        
        System.out.println("Game over: " + result);
    }
    
    public void resetGame() {
        initializeBoard();
        myTurn = false;
        
        // Repaint all cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].repaint();
            }
        }
        
        rematchButton.setVisible(false);
        resetButton.setVisible(false);
        gameStatusLabel.setText("Bàn cờ đã được làm mới. Sẵn sàng cho game mới!");
        gameStatusLabel.setForeground(Color.DARK_GRAY);
        
        System.out.println("Bàn cờ đã reset");
    }
    
    public void updateTurn(boolean myTurn) {
        this.myTurn = myTurn;
        String turnText = myTurn ? "Lượt của bạn" : "Đợi đối thủ";
        gameStatusLabel.setText("Đang chơi với " + opponent + " - " + turnText);
        gameStatusLabel.setForeground(myTurn ? new Color(0, 100, 0) : Color.BLUE);
    }
    
    public void forceReset() {
        resetGame();
        opponent = null;
        gameStatusLabel.setText("Đã thoát game. Sẵn sàng cho game mới!");
    }
    
    // Kiểm tra xem đang trong game hay không
    public boolean isInGame() {
        return opponent != null;
    }
}