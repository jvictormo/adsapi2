package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//Realiza a conexão com o banco de dados
public class ConnectionFactory {
 public Connection getConnection() {
	 try {
		 //Indica qual banco de dados conectar, trocar SEUBANCODEDADOS pelo nome do banco, USUÁRIO trocar pelo usuário do MySQL (padrão root),
		 //senha trocar por senha Mysql
		 return DriverManager.getConnection("jdbc:mysql://localhost/vendas","root","123123");
	 }
	 catch(SQLException excecao) {
		 throw new RuntimeException(excecao);
	 }
 }
 
 //metodo adicional para realizar a conexão a um banco específico
 public Connection conexao(String banco) {
	 try {
		 String link = String.format("jdbc:mysql://localhost/%s", banco);
		 return DriverManager.getConnection(link,"root","123123");
	 }
	 catch(SQLException excecao) {
		 throw new RuntimeException(excecao);
	 }
 }
}