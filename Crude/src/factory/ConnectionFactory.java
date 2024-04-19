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
		 return DriverManager.getConnection("jdbc:mysql://localhost/SEUBANCODEDADOS","USUÁRIO","SENHA");
	 }
	 catch(SQLException excecao) {
		 throw new RuntimeException(excecao);
	 }
 }
}
