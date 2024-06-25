package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import factory.ConnectionFactory;
import langchain.ChatController;
import ui.AP1Frame.GradientBorder;
import ui.AP1Frame.GradientButtonUI;
import ui.AP1Frame.RoundedBorder;
import useful.CommandLine;

public class AP1Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection connection;
	private String model = "TheBloke/nsql-llama-2-7B-GGUF/nsql-llama-2-7b.Q8_0.gguf";
	private CommandLine cl = new CommandLine();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AP1Frame frame = new AP1Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AP1Frame() {
		cl.servidor("start");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(1000, 700); // Aumentando o tamanho da frame
		getContentPane().setBackground(Color.BLACK);
		addWindowListener(new WindowAdapter() {
			@Override
	        public void windowClosing(WindowEvent e) {
				cl.model("unload", model);
				cl.servidor("stop");	
	            dispose();
	        }
	    });
		centerFrame(this);
		
		JPanel leftWrapperPanel = new JPanel(new BorderLayout());
		leftWrapperPanel.setBackground(Color.BLACK);
		leftWrapperPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adicionar margem externa

		// Adicionar o novo painel à esquerda da frame
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usando FlowLayout para centralizar
																					// e adicionar espaço ao redor do
																					// botão
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setPreferredSize(new Dimension(200, getHeight())); // Definir largura interna
		leftPanel.setBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153))); // Adicionando borda
																								// degradê

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
		
		String[] databases = getDatabases();
		JButton[] additionalButtons = new JButton[databases.length];
		JPanel buttonPanel = new JPanel(new GridLayout(15, 1, 0, 25)); // Layout de grade para botões adicionais, com
																		// espaço de 20 pixels vertical
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

		getContentPane().add(leftWrapperPanel, BorderLayout.WEST);

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
						// Criar um novo JFrame
						JFrame newFrame = new JFrame("Nova Interface");
						newFrame.setSize(400, 300); // Tamanho médio
						newFrame.getContentPane().setBackground(Color.BLACK);
						newFrame.getContentPane().setLayout(new GridBagLayout());
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.insets = new Insets(10, 10, 10, 10);

						// Adicionar rótulo
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
								cl.model("unload", model);
								model = "TheBloke/nsql-llama-2-7B-GGUF/nsql-llama-2-7b.Q8_0.gguf";
								cl.model("load", model);
								newFrame.dispose();
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
								cl.model("unload", model);
								model = "motherduckdb/DuckDB-NSQL-7B-v0.1-GGUF/DuckDB-NSQL-7B-v0.1-q8_0.gguf";
								cl.model("load", model);
								newFrame.dispose();
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
				outputArea.setBorder(
						BorderFactory.createCompoundBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
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
				inputArea.setBorder(
						BorderFactory.createCompoundBorder(new GradientBorder(new Color(0, 221, 255), new Color(0, 0, 153)),
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
							ChatController chat = new ChatController(model);
							String selectedDatabase = leftButton.getText();
							String databaseSchema = getDatabaseSchema(connection, selectedDatabase);
							String aiResponse = chat.resposta("Use the schema from the table as a reference"
									+ databaseSchema
									+ "Recive the following command and translate it from portuguese to english" + userInput);
							executeStatement(aiResponse, outputArea);
						}
					}
				});

				inputSubPanel.add(sendButton); // Adicionando o botão de enviar ao lado da caixa de entrada

				inputPanel.add(inputLabel, BorderLayout.NORTH);
				inputPanel.add(inputSubPanel, BorderLayout.CENTER);

				painelDireito.add(inputPanel, BorderLayout.SOUTH);

				JSeparator separator = new JSeparator();
				inputPanel.add(separator, BorderLayout.WEST);

				// Adicionando o painel direito à frame
				getContentPane().add(painelDireito, BorderLayout.CENTER);

				// Certifique-se de que a frame é visível
				setVisible(true);
				
				JOptionPane.showMessageDialog(null, "Escolha uma IA e uma Database para começar a utilizar", "Alerta", JOptionPane.WARNING_MESSAGE);
	}
	
	private String getDatabaseSchema(Connection connection, String databaseName) {
		StringBuilder schema = new StringBuilder();
		try (Statement showStmt = connection.createStatement()) {

			String truncatedName = databaseName.substring(4);
			String showTablesQuery = "SHOW TABLES FROM `" + truncatedName + "`";
			try (ResultSet tablesResult = showStmt.executeQuery(showTablesQuery)) {
				while (tablesResult.next()) {
					String tableName = tablesResult.getString(1);
					String showCreateTableQuery = "SHOW CREATE TABLE `" + truncatedName + "`.`" + tableName + "`";
					try (ResultSet tableResult = showStmt.executeQuery(showCreateTableQuery)) {
						if (tableResult.next()) {
							String tableSchema = tableResult.getString(2);
							schema.append(tableSchema).append("\n");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schema.toString();
	}
	
	private void executeStatement(String sqlCommand, JTextArea outputArea) {
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlCommand)) {

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
		} catch (SQLException e) {
			outputArea.setText("Erro ao executar o comando: " + e.getMessage());
		}
	}
	
	public String[] getDatabases() {
		connection = new ConnectionFactory().getConnection();
		ArrayList<String> dbs = new ArrayList<>();
		try {
			Statement show_stmt = connection.createStatement();
			ResultSet result = show_stmt.executeQuery("SHOW DATABASES");
			while (result.next()) {
				dbs.add(result.getString(1));
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbs.toArray(new String[0]);
	}
	
	private static void centerFrame(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
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
