package ui;

import langchain.ChatController;
import factory.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class API1 {
    private static final ChatController chatController = new ChatController();
    private static Connection connection;

    public static void main(String[] args) {
        connection = new ConnectionFactory().getConnection();

        JFrame frame = new JFrame("API1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 550);
        centerFrame(frame);

        JTextArea textArea = new JTextArea(20, 20);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JTextArea inputArea = new JTextArea(3, 20);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        JLabel inputLabel = new JLabel("Entrada:");
        JLabel outputLabel = new JLabel("Saída:");
        inputLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        outputLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JButton sendButton = new JButton("Enviar");
        sendButton.setPreferredSize(new Dimension(100, 30));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputArea.getText().trim();
                if (!userInput.isEmpty()) {
                    // Gerar resposta da IA com base na entrada do usuário
                    String aiResponse = chatController.generateResponse("Following is the database CREATE TABLE `alunos` (\r\n"
                    		+ "  `id` int NOT NULL AUTO_INCREMENT,\r\n"
                    		+ "  `nome` varchar(100) NOT NULL,\r\n"
                    		+ "  `email` varchar(100) NOT NULL,\r\n"
                    		+ "  `data_nascimento` date NOT NULL,\r\n"
                    		+ "  PRIMARY KEY (`id`)\r\n"
                    		+ ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\r\n"
                    		+ "" + "for the following question (translate from portuguese to english unless, tables name, sql commands) " + userInput);
                    // Enviar a resposta da IA para o método executeStatement
                    executeStatement(aiResponse, textArea);
                }
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(outputLabel, BorderLayout.WEST);
        textPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 0);
        inputPanel.add(inputLabel, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        inputPanel.add(new JScrollPane(inputArea), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        inputPanel.add(sendButton, gbc);

        panel.add(textPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    private static void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
    private static void executeStatement(String sqlCommand, JTextArea textArea) {
        try (Statement statement = connection.createStatement()) {
            if (statement.execute(sqlCommand)) {
                ResultSet resultSet = statement.getResultSet();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(metaData.getColumnName(i)).append(": ").append(resultSet.getString(i)).append(", ");
                    }
                    result.append("\n");
                }

                textArea.setText(result.toString());
            } else {
                textArea.setText("Operação realizada com sucesso.");
            }
        } catch (SQLException e) {
            textArea.setText("Erro ao executar o comando: " + e.getMessage());
        }
    }

}
