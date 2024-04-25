package factory;

import java.sql.Connection;
import java.sql.SQLException;

//Testa a conexão com o Banco de Dados
public class TstaConexao {
	public static void main(String[] args) throws SQLException{
		Connection connection = new ConnectionFactory().getConnection();
		System.out.println("Conexão aberta!");
		connection.close();
	}
}