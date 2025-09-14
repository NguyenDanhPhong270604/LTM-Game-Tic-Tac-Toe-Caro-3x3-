package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChallengeDialog extends JDialog {
    private JList<String> playerList;
    private JButton challengeButton;
    private JButton cancelButton;
    private String selectedPlayer;
    
    public ChallengeDialog(Frame parent, List<String> players, String currentUser) {
        super(parent, "Chọn người chơi để thách đấu", true);
        setupUI(players, currentUser);
    }
    
    private void setupUI(List<String> players, String currentUser) {
        setLayout(new BorderLayout(10, 10));
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Filter out current user
        java.util.List<String> filteredPlayers = new java.util.ArrayList<>();
        for (String player : players) {
            if (!player.equals(currentUser)) {
                filteredPlayers.add(player);
            }
        }
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Chọn người chơi:"), BorderLayout.NORTH);
        playerList = new JList<>(filteredPlayers.toArray(new String[0]));
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(playerList);
        inputPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        challengeButton = new JButton("Thách đấu");
        cancelButton = new JButton("Hủy");
        buttonPanel.add(challengeButton);
        buttonPanel.add(cancelButton);
        
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        challengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = playerList.getSelectedValue();
                dispose();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlayer = null;
                dispose();
            }
        });
    }
    
    public String getSelectedPlayer() {
        return selectedPlayer;
    }
}