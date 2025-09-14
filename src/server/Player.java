package server;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;
    private int wins;
    private int losses;
    
    public Player(String username) {
        this.username = username;
        this.wins = 0;
        this.losses = 0;
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getWins() {
        return wins;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public void addWin() {
        wins++;
    }
    
    public void addLoss() {
        losses++;
    }
    
    @Override
    public String toString() {
        return username + " - Wins: " + wins + " - Losses: " + losses;
    }
}