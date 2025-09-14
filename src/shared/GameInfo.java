package shared;

import java.io.Serializable;

public class GameInfo implements Serializable {
    private String opponent;
    private char symbol;
    
    public GameInfo(String opponent, char symbol) {
        this.opponent = opponent;
        this.symbol = symbol;
    }
    
    public String getOpponent() {
        return opponent;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    @Override
    public String toString() {
        return "GameInfo{opponent='" + opponent + "', symbol=" + symbol + "}";
    }
}