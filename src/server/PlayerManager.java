package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private List<Player> players;
    private static final String HISTORY_FILE = "data/player_history.txt";
    
    public PlayerManager() {
        players = new ArrayList<>();
        loadPlayerHistory();
    }
    
    public Player getOrCreatePlayer(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        
        Player newPlayer = new Player(username);
        players.add(newPlayer);
        return newPlayer;
    }
    
    public List<Player> getAllPlayers() {
        return new ArrayList<>(players);
    }
    
    @SuppressWarnings("unchecked")
    private void loadPlayerHistory() {
        File file = new File(HISTORY_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                players = (List<Player>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Could not load player history: " + e.getMessage());
                players = new ArrayList<>();
            }
        }
    }
    
    public void savePlayerHistory() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            oos.writeObject(players);
        } catch (IOException e) {
            System.out.println("Could not save player history: " + e.getMessage());
        }
    }
}