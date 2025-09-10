package caro;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 8000;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static Game game = null;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            }	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String playerSymbol;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Get username
                username = in.readLine();
                System.out.println("User connected: " + username);

                // Assign player symbol and start game if enough players
                synchronized (clientHandlers) {
                    if (clientHandlers.size() % 2 == 1) {
                        playerSymbol = "X";
                        out.println("SYMBOL:X");
                        out.println("MESSAGE:Waiting for another player...");
                    } else {
                        playerSymbol = "O";
                        out.println("SYMBOL:O");
                        game = new Game();
                        broadcast("START", null);
                    }
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("MOVE:")) {
                        int move = Integer.parseInt(message.substring(5));
                        handleMove(move);
                    }
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + username);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientHandlers.remove(this);
            }
        }

        private void handleMove(int move) {
            if (game != null && game.getCurrentPlayer().equals(playerSymbol)) {
                if (game.makeMove(move, playerSymbol)) {
                    broadcast("MOVE:" + move + ":" + playerSymbol, null);
                    
                    if (game.checkWinner() != null) {
                        broadcast("WIN:" + game.checkWinner(), null);
                        game = null;
                    } else if (game.isBoardFull()) {
                        broadcast("DRAW", null);
                        game = null;
                    } else {
                        game.switchPlayer();
                        broadcast("TURN:" + game.getCurrentPlayer(), null);
                    }
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    private static void broadcast(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clientHandlers) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }

    static class Game {
        private String[] board;
        private String currentPlayer;

        public Game() {
            board = new String[9];
            Arrays.fill(board, "");
            currentPlayer = "X";
        }

        public boolean makeMove(int position, String symbol) {
            if (position >= 0 && position < 9 && board[position].isEmpty() && symbol.equals(currentPlayer)) {
                board[position] = symbol;
                return true;
            }
            return false;
        }

        public String checkWinner() {
            // Check rows
            for (int i = 0; i < 3; i++) {
                if (!board[i*3].isEmpty() && board[i*3].equals(board[i*3+1]) && board[i*3].equals(board[i*3+2])) {
                    return board[i*3];
                }
            }

            // Check columns
            for (int i = 0; i < 3; i++) {
                if (!board[i].isEmpty() && board[i].equals(board[i+3]) && board[i].equals(board[i+6])) {
                    return board[i];
                }
            }

            // Check diagonals
            if (!board[0].isEmpty() && board[0].equals(board[4]) && board[0].equals(board[8])) {
                return board[0];
            }
            if (!board[2].isEmpty() && board[2].equals(board[4]) && board[2].equals(board[6])) {
                return board[2];
            }

            return null;
        }

        public boolean isBoardFull() {
            for (String cell : board) {
                if (cell.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public void switchPlayer() {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }

        public String getCurrentPlayer() {
            return currentPlayer;
        }
    }
}
