package server;

public class GameSession {
    private Player playerX;
    private Player playerO;
    private char[][] board;
    private char currentPlayer;
    
    public GameSession(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new char[3][3];
        this.currentPlayer = 'X';
        initializeBoard();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
    public boolean makeMove(int row, int col, Player player) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
            return false;
        }
        
        // Xác định ký hiệu của người chơi
        char expectedSymbol = (player == playerX) ? 'X' : 'O';
        if (currentPlayer != expectedSymbol) {
            return false;
        }
        
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }
    
    public char checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                System.out.println("Row win: " + board[i][0] + " at row " + i);
                return board[i][0];
            }
        }
        
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                System.out.println("Column win: " + board[0][i] + " at column " + i);
                return board[0][i];
            }
        }
        
        // Check diagonals
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            System.out.println("Diagonal win: " + board[0][0]);
            return board[0][0];
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            System.out.println("Diagonal win: " + board[0][2]);
            return board[0][2];
        }
        
        // Check for draw
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    isFull = false;
                    break;
                }
            }
        }
        
        if (isFull) {
            System.out.println("Game draw");
            return 'D';
        }
        
        return ' ';
    }
    
    public Player getPlayerX() {
        return playerX;
    }
    
    public Player getPlayerO() {
        return playerO;
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public char[][] getBoard() {
        return board;
    }
}