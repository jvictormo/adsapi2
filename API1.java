package ui;

import factory.ConnectionFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.plaf.basic.BasicButtonUI;
import langchain.ChatController;

public class API1 {
    private static final ChatController chatController = new ChatController();
    private static Connection connection;

    public static void main(String[] args) {
        connection = new ConnectionFactory().getConnection();

        JFrame frame = new JFrame("API1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 680); // Aumentando o tamanho da frame
        frame.getContentPane().setBackground(Color.BLACK);
        centerFrame(frame);
        System.setProperty("swing.defaultlaf", "javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel leftWrapperPanel = new JPanel(new BorderLayout());
        leftWrapperPanel.setBackground(Color.BLACK);
        leftWrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adicionar margem externa

        // Adicionar o novo painel à esquerda da frame
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usando FlowLayout para centralizar e adicionar espaço ao redor do botão
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(new RoundedBorder(50));
        leftPanel.setPreferredSize(new Dimension(200, frame.getHeight())); // Definir largura interna
        leftPanel.setBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153))); // Adicionando borda degradê

        JButton leftButton = new JButton("Trocar BD");
        leftButton.setForeground(Color.WHITE);
        leftButton.setFont(leftButton.getFont().deriveFont(14f));
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);
        leftButton.setFocusPainted(false);
        leftButton.setOpaque(false);
        leftButton.setBorder(new RoundedBorder(50)); // Reduzindo o tamanho do arredondamento
        leftButton.setUI(new GradientButtonUI());
        leftButton.setPreferredSize(new Dimension(170, 50)); // Ajustando a dimensão preferida do botão esquerdo
        leftButton.setMargin(new Insets(1, 10, 1, 10)); // Ajustando as margens do botão
        
        
        // Criar botões adicionais
        JButton[] additionalButtons = new JButton[3];
        JPanel buttonPanel = new JPanel(new GridLayout(15, 1, 0, 25)); // Layout de grade para botões adicionais, com espaço de 20 pixels vertical
        buttonPanel.setOpaque(false);
        for (int i = 0; i < additionalButtons.length; i++) {
            additionalButtons[i] = new JButton("Botão " + (i + 1));
            additionalButtons[i].setForeground(Color.WHITE);
            additionalButtons[i].setFont(leftButton.getFont().deriveFont(14f));
            additionalButtons[i].setContentAreaFilled(false);
            additionalButtons[i].setBorderPainted(false);
            additionalButtons[i].setFocusPainted(false);
            additionalButtons[i].setOpaque(false);
            
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0)); // Adicionando margem entre os botões

            buttonPanel.add(additionalButtons[i]);
        }

        // Adicionando ação de clique ao botão esquerdo
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação de clique do botão esquerdo
                // Ação de clique do botão esquerdo
                for (JButton button : additionalButtons) {
                    button.setVisible(true);
                }
                selectButton(leftButton, additionalButtons);
            }
        });

        // Adicionar ActionListeners aos botões adicionais para selecionar e destacar a borda
        for (JButton button : additionalButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectButton(button, additionalButtons);
                }
            });
        }

        leftPanel.add(leftButton); // Adicionar o botão ao painel esquerdo
        leftPanel.add(buttonPanel); // Adicionar o painel de botões adicionais abaixo do botão esquerdo

        leftWrapperPanel.add(leftPanel, BorderLayout.WEST); // Adicionar o painel esquerdo ao painel de envolvimento

        frame.getContentPane().add(leftWrapperPanel, BorderLayout.WEST); // Adicionando o novo painel à região oeste

        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        JTextArea outputArea = new JTextArea(20, 20);
        outputArea.setFont(textFont);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
                BorderFactory.createEmptyBorder(10, 5, 5, 5))); // Adicionando borda interna à caixa de entrada e incluindo-a na borda composta

        JTextArea inputArea = new JTextArea(2, 40);
        inputArea.setFont(textFont);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBackground(Color.BLACK);
        inputArea.setForeground(Color.WHITE);
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
                BorderFactory.createEmptyBorder(10, 5, 5, 5))); // Adicionando borda interna à caixa de entrada e incluindo-a na borda composta

        JLabel inputLabel = new JLabel("Envie sua mensagem para a IA:");
        JLabel outputLabel = new JLabel("Resposta da IA:");
        inputLabel.setForeground(Color.WHITE);
        outputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(inputLabel.getFont().deriveFont(16f));
        outputLabel.setFont(outputLabel.getFont().deriveFont(16f));
       

    
// Adicione a margem ao redor do rótulo "Envie sua mensagem para a IA:"
inputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Adicionando margem de 5 pixels em todos os lados


        JButton sendButton = new JButton("Enviar");
        sendButton.setPreferredSize(new Dimension(150, 50));
        sendButton.setBorder(new RoundedBorder(50));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(sendButton.getFont().deriveFont(14f));
        sendButton.setContentAreaFilled(false);
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0)); // Adicionando margem entre os botões

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputArea.getText().trim();
                if (!userInput.isEmpty()) {
                    String aiResponse = chatController.generateResponse(userInput);
                    executeStatement(aiResponse, outputArea);
                }
            }
        });

        sendButton.setUI(new GradientButtonUI()); // Definindo o degradê para o botão de enviar

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remover borda branca indesejada

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        panel.setBackground(Color.BLACK);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(Color.BLACK);
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout()); // Usando BorderLayout para organizar o rótulo acima da caixa de entrada
        inputPanel.setBackground(Color.BLACK);

        // Criando um painel para a entrada de texto e o botão
        JPanel inputSubPanel = new JPanel();
        inputSubPanel.setLayout(new BoxLayout(inputSubPanel, BoxLayout.X_AXIS));
        inputSubPanel.setBackground(Color.BLACK);

        inputSubPanel.add(inputArea); // Adicionando a caixa de entrada
        inputSubPanel.add(Box.createHorizontalStrut(10)); // Espaço entre a caixa de entrada e o botão
        inputSubPanel.add(sendButton); // Adicionando o botão de enviar

        inputPanel.add(inputLabel, BorderLayout.NORTH); // Adicionando o rótulo acima da caixa de entrada
        inputPanel.add(inputSubPanel, BorderLayout.CENTER); // Adicionando a sub-divisão ao centro

        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel outputLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputLabelPanel.setBackground(Color.BLACK);
        outputLabelPanel.add(outputLabel);

        textPanel.add(outputLabelPanel, BorderLayout.NORTH);
        textPanel.add(outputScrollPane, BorderLayout.CENTER); // Adicionar JScrollPane com borda removida
        textPanel.add(inputPanel, BorderLayout.SOUTH);

        panel.add(textPanel, BorderLayout.CENTER);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

    private static void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    private static void executeStatement(String sqlCommand, JTextArea outputArea) {
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

                outputArea.setText(result.toString());
            } else {
               outputArea.setText("Operação realizada com sucesso.");
            }
        } catch (SQLException e) {
            outputArea.setText("Erro ao executar o comando: " + e.getMessage());
        }
    }

    private static void selectButton(JButton selectedButton, JButton[] otherButtons) {
        selectedButton.setBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)));
        for (JButton button : otherButtons) {
            if (button != selectedButton) {
                button.setBorder(null);
            }
        }
    }

    static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = this.radius;
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    static class GradientButtonUI extends BasicButtonUI {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            JButton button = (JButton) c;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = button.getWidth();
            int height = button.getHeight();
            Color color1 = new Color(0, 221, 255);
            Color color2 = new Color(0, 0, 153);
            GradientPaint gp = new GradientPaint(0, height, color1, 0, 0, color2);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, width, height, 20, 20);
            g2.dispose();
            super.paint(g, c);
        }
    }

    static class GradientBorder implements javax.swing.border.Border {
        private final Color color1;
        private final Color color2;

        GradientBorder(Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, height, color1, 0, 0, color2));
            g2.setStroke(new BasicStroke(7)); // Aumentando a largura da borda
            int borderRadius = 40; // Aumentando o raio do arredondamento
            g2.drawRoundRect(x, y, width - 1, height - 1, borderRadius, borderRadius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }
}
