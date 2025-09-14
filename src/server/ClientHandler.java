package server;

import shared.Message;
import shared.MessageType;
import shared.GameInfo;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private CaroServer server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Player player;
    private GameSession currentGame;
    
    public ClientHandler(Socket socket, CaroServer server) {
        this.socket = socket;
        this.server = server;
        
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) input.readObject();
                processMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Player disconnected: " + (player != null ? player.getUsername() : "Unknown"));
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void processMessage(Message message) {
        System.out.println("Server received: " + message.getType() + " from " + 
            (player != null ? player.getUsername() : "unknown"));
            
        switch (message.getType()) {
            case LOGIN:
                handleLogin((String) message.getContent());
                break;
            case CHALLENGE:
                handleChallenge((String) message.getContent());
                break;
            case CHALLENGE_ACCEPTED:
                handleChallengeAccepted((String) message.getContent());
                break;
            case CHALLENGE_REJECTED:
                handleChallengeRejected((String) message.getContent());
                break;
            case MOVE:
                handleMove((int[]) message.getContent());
                break;
            case HISTORY_REQUEST:
                sendHistory();
                break;
            case PLAYER_LIST:
                sendPlayerList();
                break;
            default:
                System.out.println("Unknown message type: " + message.getType());
        }
    }
    
    private void handleLogin(String username) {
        player = server.getPlayerManager().getOrCreatePlayer(username);
        sendMessage(new Message(MessageType.LOGIN_SUCCESS, player));
        server.broadcastPlayerList();
        System.out.println("Player logged in: " + username);
    }
    
    private void handleChallenge(String opponentUsername) {
        System.out.println("Challenge from " + (player != null ? player.getUsername() : "unknown") + " to " + opponentUsername);
        
        ClientHandler opponent = server.getClientByUsername(opponentUsername);
        if (opponent != null) {
            if (opponent.currentGame != null) {
                sendMessage(new Message(MessageType.ERROR, opponentUsername + " đang trong trận đấu khác!"));
                return;
            }
            if (opponent == this) {
                sendMessage(new Message(MessageType.ERROR, "Bạn không thể thách đấu chính mình!"));
                return;
            }
            // Gửi lời thách đấu đến đối thủ
            opponent.sendMessage(new Message(MessageType.CHALLENGE, player.getUsername()));
        } else {
            sendMessage(new Message(MessageType.ERROR, opponentUsername + " không online!"));
        }
    }
    
    private void handleChallengeAccepted(String challengerUsername) {
        System.out.println("Challenge accepted by " + player.getUsername() + " from " + challengerUsername);
        
        ClientHandler challenger = server.getClientByUsername(challengerUsername);
        if (challenger != null && challenger != this) {
            acceptChallenge(challenger);
        } else {
            sendMessage(new Message(MessageType.ERROR, "Không thể tìm thấy người thách đấu!"));
        }
    }
    
    private void handleChallengeRejected(String challengerUsername) {
        System.out.println("Challenge rejected by " + player.getUsername() + " from " + challengerUsername);
        
        ClientHandler challenger = server.getClientByUsername(challengerUsername);
        if (challenger != null) {
            challenger.sendMessage(new Message(MessageType.CHALLENGE_REJECTED, player.getUsername()));
        }
    }
    
    public void acceptChallenge(ClientHandler challenger) {
        System.out.println("Creating game between " + challenger.player.getUsername() + " and " + player.getUsername());
        
        try {
            currentGame = new GameSession(challenger.player, this.player);
            challenger.currentGame = currentGame;
            
            // Gửi thông tin game cho cả hai người chơi
            // Người thách đấu (challenger) là X
            challenger.sendMessage(new Message(MessageType.CHALLENGE_ACCEPTED, 
                new GameInfo(player.getUsername(), 'X')));
            
            // Người được thách đấu (this) là O
            sendMessage(new Message(MessageType.CHALLENGE_ACCEPTED, 
                new GameInfo(challenger.player.getUsername(), 'O')));
            
            System.out.println("Game started: " + challenger.player.getUsername() + " (X) vs " + player.getUsername() + " (O)");
            
            // Cập nhật danh sách người chơi ngay lập tức
            server.broadcastPlayerList();
            
        } catch (Exception e) {
            System.out.println("Error creating game: " + e.getMessage());
            sendMessage(new Message(MessageType.ERROR, "Lỗi khi tạo trận đấu"));
            if (challenger != null) {
                challenger.sendMessage(new Message(MessageType.ERROR, "Lỗi khi tạo trận đấu"));
            }
        }
    }
    
    private void handleMove(int[] move) {
        if (currentGame != null) {
            System.out.println("Move from " + player.getUsername() + ": " + move[0] + "," + move[1]);
            
            boolean validMove = currentGame.makeMove(move[0], move[1], player);
            if (validMove) {
                ClientHandler opponent = getOpponent();
                if (opponent != null) {
                    // Gửi nước đi cho đối thủ
                    Object[] moveData = {move, getPlayerSymbol()};
                    opponent.sendMessage(new Message(MessageType.MOVE, moveData));
                    
                    // Kiểm tra người thắng
                    char winner = currentGame.checkWinner();
                    if (winner != ' ') {
                        handleGameOver(winner);
                    }
                }
            } else {
                sendMessage(new Message(MessageType.ERROR, "Nước đi không hợp lệ!"));
            }
        }
    }
    
    private char getPlayerSymbol() {
        if (currentGame == null) return ' ';
        return (currentGame.getPlayerX() == player) ? 'X' : 'O';
    }
    
    private void handleGameOver(char winner) {
        System.out.println("Game over. Winner: " + winner);
        
        // Cập nhật thống kê
        if (winner == 'X') {
            currentGame.getPlayerX().addWin();
            currentGame.getPlayerO().addLoss();
            System.out.println(currentGame.getPlayerX().getUsername() + " thắng, " + 
                              currentGame.getPlayerO().getUsername() + " thua");
        } else if (winner == 'O') {
            currentGame.getPlayerO().addWin();
            currentGame.getPlayerX().addLoss();
            System.out.println(currentGame.getPlayerO().getUsername() + " thắng, " + 
                              currentGame.getPlayerX().getUsername() + " thua");
        } else if (winner == 'D') {
            System.out.println("Hòa: " + currentGame.getPlayerX().getUsername() + " vs " + 
                              currentGame.getPlayerO().getUsername());
        }
        
        // Lưu lịch sử
        server.getPlayerManager().savePlayerHistory();
        
        // Gửi kết quả cho từng người chơi
        if (winner == 'D') {
            // Hòa
            sendMessage(new Message(MessageType.GAME_OVER, "Hòa"));
            ClientHandler opponent = getOpponent();
            if (opponent != null) {
                opponent.sendMessage(new Message(MessageType.GAME_OVER, "Hòa"));
            }
        } else {
            // Có người thắng
            boolean isWinner = (currentGame.getPlayerX() == player && winner == 'X') || 
                              (currentGame.getPlayerO() == player && winner == 'O');
            
            sendMessage(new Message(MessageType.GAME_OVER, isWinner ? "Bạn thắng" : "Bạn thua"));
            
            ClientHandler opponent = getOpponent();
            if (opponent != null) {
                opponent.sendMessage(new Message(MessageType.GAME_OVER, isWinner ? "Bạn thua" : "Bạn thắng"));
            }
        }
        
        // Kết thúc game và cập nhật trạng thái
        ClientHandler opponent = getOpponent();
        currentGame = null;
        if (opponent != null) {
            opponent.currentGame = null;
        }
        
        // Cập nhật danh sách người chơi (chuyển về "Đang online")
        server.broadcastPlayerList();
        
        System.out.println("Game ended, players are now available for new games");
    }
    
    private ClientHandler getOpponent() {
        if (currentGame == null) return null;
        
        if (currentGame.getPlayerX() == player) {
            return server.getClientByUsername(currentGame.getPlayerO().getUsername());
        } else {
            return server.getClientByUsername(currentGame.getPlayerX().getUsername());
        }
    }
    
    private void sendHistory() {
        List<Player> history = server.getPlayerManager().getAllPlayers();
        sendMessage(new Message(MessageType.HISTORY_RESPONSE, history));
    }
    
    private void sendPlayerList() {
        List<String> players = server.getConnectedUsernames();
        java.util.List<String> playerStatus = new java.util.ArrayList<>();
        for (String playerName : players) {
            ClientHandler client = server.getClientByUsername(playerName);
            if (client != null && client.currentGame != null) {
                playerStatus.add(playerName + " (Đang chơi)");
            } else {
                playerStatus.add(playerName + " (Đang online)");
            }
        }
        sendMessage(new Message(MessageType.PLAYER_LIST, playerStatus));
    }
    
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
            System.out.println("Server sent: " + message.getType() + " to " + 
                (player != null ? player.getUsername() : "unknown"));
        } catch (IOException e) {
            System.out.println("Error sending message to " + 
                (player != null ? player.getUsername() : "unknown client"));
        }
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public boolean isInGame() {
        return currentGame != null;
    }
}