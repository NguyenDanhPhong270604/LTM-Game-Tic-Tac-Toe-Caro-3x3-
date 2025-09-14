package client;

import server.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlayerHistory extends JDialog {
    public PlayerHistory(Frame parent, List<Player> players) {
        super(parent, "Lịch sử người chơi", true);
        setupUI(players);
    }
    
    private void setupUI(List<Player> players) {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        String[] columnNames = {"Tên người chơi", "Số trận thắng", "Số trận thua", "Tỷ lệ thắng"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Player player : players) {
            int wins = player.getWins();
            int losses = player.getLosses();
            double winRate = (wins + losses) > 0 ? 
                (double) wins / (wins + losses) * 100 : 0;
            
            model.addRow(new Object[]{
                player.getUsername(),
                wins,
                losses,
                String.format("%.2f%%", winRate)
            });
        }
        
        JTable table = new JTable(model);
        
        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}