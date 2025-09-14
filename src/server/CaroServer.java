package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.Message;

public class CaroServer {
    private static final int PORT = 8000;
    private List<ClientHandler> clients;
    private PlayerManager playerManager;
    
    public CaroServer() {
        clients = new ArrayList<>();
        playerManager = new PlayerManager();
    }
    
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastPlayerList();
    }
    
    public void broadcastPlayerList() {
        List<String> usernames = getConnectedUsernames();
        for (ClientHandler client : clients) {
            if (client.getPlayer() != null) {
                client.sendMessage(new Message(shared.MessageType.PLAYER_LIST, usernames));
            }
        }
    }
    
    public List<String> getConnectedUsernames() {
        List<String> usernames = new ArrayList<>();
        for (ClientHandler client : clients) {
            if (client.getPlayer() != null) {
                usernames.add(client.getPlayer().getUsername());
            }
        }
        return usernames;
    }
    
    public ClientHandler getClientByUsername(String username) {
        for (ClientHandler client : clients) {
            if (client.getPlayer() != null && client.getPlayer().getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }
    
    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    
    public static void main(String[] args) {
        CaroServer server = new CaroServer();
        server.start();
    }
}