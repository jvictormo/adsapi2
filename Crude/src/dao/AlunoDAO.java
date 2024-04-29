package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;
import modelo.bancodeDados;

public class AlunoDAO {
	private Connection connection;
	
	//Realiza a conexão com o banco de dados
	public AlunoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	//Chama a classe banco
	public void le(bancodeDados bancodedados) {
		//Seleciona qual o parametro usar como busca no banco de dados, trocar SEUBANCODEDADOS pelo nome do seu banco de dados
		//trocar SUATABELA pelo nome da sua tabela, rm é o parâmetro usado para buscar dados no banco de dados
	    String sql = "SELECT * FROM SEUBANCODEDADOS.SUATABELA WHERE rm = ?";
	    try {
	        PreparedStatement stmt = connection.prepareStatement(sql);
	        //Onde ele começa a busca
	        stmt.setInt(1, bancodedados.getRm());
	        ResultSet rs = stmt.executeQuery();
	        //Pega os valores do banco de dados
	        while (rs.next()) {
	        	//Pega RM
	            int rm = rs.getInt("rm");
	            //Pega nome
	            String nome = rs.getString("nome");
	            //Pega sexo
	            char sexo = rs.getString("sexo").charAt(0);
	            System.out.println("RM: " + rm + ", Nome: " + nome + ", Sexo: " + sexo);
	        }
	        rs.close();
	        stmt.close();
	     //Verifica exceção
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
}
