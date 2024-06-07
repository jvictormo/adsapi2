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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.plaf.basic.BasicButtonUI;
import langchain.ChatController;

public class API1 {
    private static final ChatController chatController = new ChatController();
    private static Connection connection;

    //metodo que realiza uma consulta para saber todas as databases do sistema
    public static String[] getDatabases(){
        connection = new ConnectionFactory().getConnection();
        ArrayList<String> dbs = new ArrayList<>();
        try {
            Statement show_stmt = connection.createStatement();
            ResultSet result = show_stmt.executeQuery("SHOW DATABASES");
            while (result.next()) {
                dbs.add(result.getString(1));
            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dbs.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] databases = getDatabases();

        for (String name : databases) {
            System.out.println(name);
        }

        JFrame frame = new JFrame("API1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700); // Aumentando o tamanho da frame
        frame.getContentPane().setBackground(Color.BLACK);
        centerFrame(frame);

        JPanel leftWrapperPanel = new JPanel(new BorderLayout());
        leftWrapperPanel.setBackground(Color.BLACK);
        leftWrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adicionar margem externa

        // Adicionar o novo painel à esquerda da frame
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usando FlowLayout para centralizar e adicionar espaço ao redor do botão
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setPreferredSize(new Dimension(200, frame.getHeight())); // Definir largura interna
        leftPanel.setBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153))); // Adicionando borda degradê

        JButton leftButton = new JButton("Databases");
        leftButton.setForeground(Color.WHITE);
        leftButton.setFont(leftButton.getFont().deriveFont(14f));
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);
        leftButton.setFocusPainted(false);
        leftButton.setOpaque(false);
        leftButton.setBorder(new RoundedBorder(50)); // Reduzindo o tamanho do arredondamento
        leftButton.setUI(new GradientButtonUI());
        leftButton.setPreferredSize(new Dimension(180, 50)); // Ajustando a dimensão preferida do botão esquerdo
        leftButton.setMargin(new Insets(1, 10, 1, 10)); // Ajustando as margens do botão

        JButton[] additionalButtons = new JButton[databases.length];
        JPanel buttonPanel = new JPanel(new GridLayout(15, 1, 0, 25)); // Layout de grade para botões adicionais, com espaço de 20 pixels vertical
        buttonPanel.setOpaque(false);
        for (int i = 0; i < additionalButtons.length; i++) {
            additionalButtons[i] = new JButton(databases[i]);
            additionalButtons[i].setForeground(Color.WHITE);
            additionalButtons[i].setFont(leftButton.getFont().deriveFont(14f));
            additionalButtons[i].setContentAreaFilled(false);
            additionalButtons[i].setBorderPainted(false);
            additionalButtons[i].setFocusPainted(false);
            additionalButtons[i].setOpaque(false);

            additionalButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Fecha a conexão caso exista
                    try {
                        connection.close();
                    } catch (Exception e_) {
                    }

                    // Pega o texto do botão clicado
                    JButton botao_clicado = (JButton) e.getSource();
                    String database_selecionada = botao_clicado.getText();

                    // Faz a conexão com a db específica
                    connection = new ConnectionFactory().conexao(database_selecionada);
                    String fodase = String.format("DB: %s", database_selecionada);
                    leftButton.setText(fodase);
                    // Exibe confirmação da troca
                    String mensagem = String.format("Database %s selecionada!", database_selecionada);
                    JOptionPane.showMessageDialog(null, mensagem, "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            });

            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0)); // Adicionando margem entre os botões

            buttonPanel.add(additionalButtons[i]);
        }

        leftPanel.add(leftButton); // Adicionar o botão ao painel esquerdo
        leftPanel.add(buttonPanel);
        leftWrapperPanel.add(leftPanel, BorderLayout.WEST); // Adicionar o painel esquerdo ao painel de envolvimento

        frame.getContentPane().add(leftWrapperPanel, BorderLayout.WEST);

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setBackground(Color.BLACK);

        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Rótulo e botão "subir"
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 20));

        JButton buttonRight = new JButton("Troca de IA");
        buttonRight.setPreferredSize(new Dimension(25, 50)); // Ajustando altura e largura do botão
        buttonRight.setForeground(Color.WHITE);
        buttonRight.setFont(buttonRight.getFont().deriveFont(14f));
        buttonRight.setContentAreaFilled(false);
        buttonRight.setBorderPainted(false);
        buttonRight.setFocusPainted(false);
        buttonRight.setOpaque(false);
        buttonRight.setBorder(new RoundedBorder(50)); // Borda redonda
        buttonRight.setUI(new GradientButtonUI());
        topPanel.add(buttonRight, BorderLayout.CENTER);
        
        // ActionListener para o botão
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar novo JFrame
                JFrame newFrame = new JFrame("Nova Interface");
                newFrame.setSize(400, 300); // Tamanho médio
                newFrame.getContentPane().setBackground(Color.BLACK);
                newFrame.getContentPane().setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                // Adicionar label
                JLabel label = new JLabel("Qual IA você gostaria de utilizar?");
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Título em negrito
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                newFrame.getContentPane().add(label, gbc);

                // Criar dois botões com efeito degradê
                JButton button1 = new JButton("NSQL");
                button1.setPreferredSize(new Dimension(120, 50)); // Botões mais altos
                button1.setForeground(Color.WHITE);
                button1.setFont(button1.getFont().deriveFont(14f));
                button1.setContentAreaFilled(false);
                button1.setBorderPainted(false);
                button1.setFocusPainted(false);
                button1.setUI(new GradientButtonUI());
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Ação do botão 1
                    }
                });

                JButton button2 = new JButton("SQLCoder");
                button2.setPreferredSize(new Dimension(120, 50)); // Botões mais altos
                button2.setForeground(Color.WHITE);
                button2.setFont(button2.getFont().deriveFont(14f));
                button2.setContentAreaFilled(false);
                button2.setBorderPainted(false);
                button2.setFocusPainted(false);
                button2.setUI(new GradientButtonUI());
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Ação do botão 2
                    }
                });

                // Adicionar botões ao frame
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.CENTER;
                newFrame.getContentPane().add(button1, gbc);

                gbc.gridx = 1;
                newFrame.getContentPane().add(button2, gbc);

                // Centralizar nova janela na tela
                newFrame.setLocationRelativeTo(null);

                // Tornar o novo JFrame visível
                newFrame.setVisible(true);
            }
        });

        painelDireito.add(topPanel, BorderLayout.NORTH);

        JLabel outputLabel = new JLabel("Resposta da IA:");
        topPanel.add(outputLabel, BorderLayout.SOUTH);
        outputLabel.setForeground(Color.WHITE);
        outputLabel.setFont(outputLabel.getFont().deriveFont(16f));
        outputLabel.setBorder(BorderFactory.createEmptyBorder(20, 2, 0, 5)); // Adicionando margem ao rótulo


        // Área de saída (resposta da IA)
        JTextArea outputArea = new JTextArea(20, 20);
        outputArea.setFont(textFont);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
                BorderFactory.createEmptyBorder(5, 10, 5, 5)));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Removendo qualquer borda do JScrollPane

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBackground(Color.BLACK);
        outputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
        outputPanel.add(outputScrollPane, BorderLayout.CENTER);

        painelDireito.add(outputPanel, BorderLayout.CENTER);

        // Área de entrada (envio da mensagem)
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 20)); // Aumentei a margem inferior

        JLabel inputLabel = new JLabel("Envie sua mensagem para a IA:");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(inputLabel.getFont().deriveFont(16f));
        inputLabel.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 5)); // Adicionando margem ao rótulo

        JTextArea inputArea = new JTextArea(2, 40);
        inputArea.setFont(textFont);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBackground(Color.BLACK);
        inputArea.setForeground(Color.WHITE);
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
                BorderFactory.createEmptyBorder(5, 10, 5, 5))); 

        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        inputScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Removendo qualquer borda do JScrollPane

        JPanel inputSubPanel = new JPanel();
        inputSubPanel.setLayout(new BoxLayout(inputSubPanel, BoxLayout.X_AXIS));
        inputSubPanel.setBackground(Color.BLACK);
        inputSubPanel.add(inputScrollPane);
        inputSubPanel.add(Box.createHorizontalStrut(10));

        // Botão "Enviar"
        JButton sendButton = new JButton("Enviar");
        sendButton.setPreferredSize(new Dimension(150, 50));
        sendButton.setBorder(new RoundedBorder(50));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(sendButton.getFont().deriveFont(14f));
        sendButton.setContentAreaFilled(false);
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        sendButton.setUI(new GradientButtonUI());

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

        inputSubPanel.add(sendButton);  // Adicionando o botão de enviar ao lado da caixa de entrada

        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputSubPanel, BorderLayout.CENTER);

        painelDireito.add(inputPanel, BorderLayout.SOUTH);

        JSeparator separator = new JSeparator();
        inputPanel.add(separator, BorderLayout.WEST);

        // Adicionando o painel direito à frame
        frame.getContentPane().add(painelDireito, BorderLayout.CENTER);

        // Certifique-se de que a frame é visível
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
